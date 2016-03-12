package com.zszdevelop.planman.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseApplication;
import com.zszdevelop.planman.base.BaseFragment;
import com.zszdevelop.planman.base.Helper;
import com.zszdevelop.planman.bean.GoalInfo;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.config.ResultCode;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;
import com.zszdevelop.planman.utils.JsonUtil;
import com.zszdevelop.planman.utils.LogUtils;
import com.zszdevelop.planman.utils.TimeUtil;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangshengzhong on 16/2/21.
 */
public class PlanFragment extends BaseFragment {


    @Bind(R.id.tv_plan_title)
    TextView tvPlanTitle;
    @Bind(R.id.tv_plan_goal_value)
    TextView tvPlanGoalValue;
    @Bind(R.id.tv_plan_current_value)
    TextView tvPlanCurrentValue;
    @Bind(R.id.npb_plan_value)
    NumberProgressBar npbPlanValue;
    @Bind(R.id.ll_npb_value)
    LinearLayout llNpbValue;
    @Bind(R.id.tv_plan_goal_time)
    TextView tvPlanGoalTime;
    @Bind(R.id.tv_plan_current_time)
    TextView tvPlanCurrentTime;
    @Bind(R.id.npb_plan_time)
    NumberProgressBar npbPlanTime;
    @Bind(R.id.ll_npb_time)
    LinearLayout llNpbTime;
    @Bind(R.id.tv_plan_goal_describe)
    TextView tvPlanGoalDescribe;
    @Bind(R.id.iv_plan_stutus)
    ImageView ivPlanStutus;

    private GoalInfo item;
    private int scaleTime;
    private Timer timerTime;


    public static PlanFragment newInstanceFragment(GoalInfo data) {
        PlanFragment fragment = new PlanFragment();
        fragment.item = data;
        return fragment;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.item_plan;
    }

    @Override
    protected void onBindFragment(View view) {

        initView();
        initListener();
        fillData();

    }

    private void initView() {
        tvPlanGoalTime.setText(TimeUtil.timestampToYMD(item.getStopTime()));

        tvPlanCurrentValue.setText(item.getStopGoal()+"cm");
        tvPlanGoalDescribe.setText(item.getGoalDescribe());

        String goalStr = "体重";
        String unitStr = "kg";
        int goalType = item.getGoalType();
        switch (goalType){
            case ResultCode.WEIGHT_CODE:
                goalStr = "体重";

                break;
            case ResultCode.CHEST_CODE:
                goalStr = "胸围";
                unitStr = "cm";
                break;
            case ResultCode.LOIN_CODE:
                goalStr = "腰围";
                unitStr = "cm";
                break;
            case ResultCode.LEFT_ARM_CODE:
                goalStr = "左臂围";
                unitStr = "cm";
                break;
            case ResultCode.RIGHT_ARM_CODE:
                goalStr = "右臂围";
                unitStr = "cm";
                break;
            case ResultCode.SHOULDER_CODE:
                goalStr = "肩宽";
                unitStr = "cm";
                break;

        }
        tvPlanTitle.setText(String.format("目标%s:",goalStr));
        tvPlanCurrentValue.setText(String.format("训练前%s:  %s%s", goalStr, item.getStartGoal(), unitStr));
        tvPlanCurrentTime.setText(String.format("训练开始时间:%s", TimeUtil.timestampToYMD(item.getStartTime())));
        tvPlanGoalValue.setText(String.format("%s%s",item.getStopGoal(),unitStr));

        if (item.getGoalStatus() == ResultCode.MODIFY_SUCCESS){
            ivPlanStutus.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.smssdk_search_icon));
            ivPlanStutus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "此项计划已完成,向型男又迈进了一步", Snackbar.LENGTH_LONG).show();
                }
            });
        }else {
            ivPlanStutus.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.button_action_dark));
            ivPlanStutus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v,"此项计划已完成?",Snackbar.LENGTH_LONG).setAction("完成", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            submitGoalComplete(item);
                        }
                    }).show();

                }
            });
        }

//        Timer timerValue = new Timer();
//        float totalValue = item.getStopGoal() - item.getStartGoal();
//        float consumeVale = item.get
//        timerValue.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                context.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        holder.npb_plan_time.incrementProgressBy(1);
//                        holder.npb_plan_value.incrementProgressBy(1);
//
//                    }
//                });
//            }
//        }, 1000, 100);

        long totalTime = item.getStopTime() - item.getStartTime();
        long consumeTime = System.currentTimeMillis() - item.getStartTime();
        scaleTime = (int)(((float)consumeTime /(float)totalTime)*100);


    }

    @Override
    public void onResume() {
        LogUtils.e("onResume");
        super.onResume();
        timerTime = new Timer();
        timerTime.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (npbPlanTime != null) {
                            if (npbPlanTime.getProgress() <= scaleTime) {

                                npbPlanTime.incrementProgressBy(1);
                                npbPlanTime.incrementProgressBy(1);
                            }
                        }

                    }
                });
            }
        }, 1000, 100);
    }


    @Override
    public void onStart() {
        super.onStart();
        LogUtils.e("onStart");
    }

    private void submitGoalComplete(final GoalInfo item) {


        HashMap<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(Helper.getUserId()));
        map.put("authToken", Helper.getToken());
        map.put("goalId", String.valueOf(item.getGoalId()));

        HttpRequest.post(API.MODIFY_GOAL_COMPLETE_URI, map, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {
                LogUtils.e(json);
                int modifyStatus = JsonUtil.getIntJsonValueByKey(json, "modifyStatus");
                if (modifyStatus == ResultCode.MODIFY_SUCCESS) {

                    item.setGoalStatus(modifyStatus);
                    ivPlanStutus.setImageDrawable(ContextCompat.getDrawable(BaseApplication.getApplication(), R.drawable.smssdk_search_icon));

                }
            }
        });
    }

    private void initListener() {


    }


    private void fillData() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        timerTime.cancel();
    }

}
