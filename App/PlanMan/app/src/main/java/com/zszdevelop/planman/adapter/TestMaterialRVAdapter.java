package com.zszdevelop.planman.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseApplication;
import com.zszdevelop.planman.base.Helper;
import com.zszdevelop.planman.bean.ConsumeRecordInfo;
import com.zszdevelop.planman.bean.GoalInfo;
import com.zszdevelop.planman.bean.SlidingDeckModel;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.config.ResultCode;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;
import com.zszdevelop.planman.utils.JsonUtil;
import com.zszdevelop.planman.utils.LogUtils;
import com.zszdevelop.planman.utils.TimeUtil;
import com.zszdevelop.planman.view_holder.ConsumeRecordViewHolder;
import com.zszdevelop.planman.view_holder.PlanViewHolder;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangshengzhong on 16/3/12.
 */
public class TestMaterialRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    private Context context;
    private List<ConsumeRecordInfo> lists;
    private GoalInfo goalInfo;
    private int layoutRes;
    private int headLayout;

    public TestMaterialRVAdapter(Activity context, int headLayout, int layoutRes, List<ConsumeRecordInfo> data) {

        this.headLayout = headLayout;
        this.layoutRes = layoutRes;
        this.context = context;
        this.lists = data;
    }


    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {

        return lists.size()+1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_HEADER: {
                 view = LayoutInflater.from(context).inflate(headLayout, parent, false);
                return new PlanViewHolder(view);
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(context).inflate(layoutRes, parent, false);
                return new ConsumeRecordViewHolder(view);
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder itemHolder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                final PlanViewHolder holder = (PlanViewHolder)itemHolder ;
                holder.tv_plan_goal_time.setText(TimeUtil.timestampToYMD(goalInfo.getStopTime()));

                holder.tv_plan_current_value.setText(goalInfo.getStopGoal()+"cm");
                holder.tv_plan_goal_describe.setText(goalInfo.getGoalDescribe());

                String goalStr = "体重";
                String unitStr = "kg";
                int goalType = goalInfo.getGoalType();
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
                holder.tv_plan_title.setText(String.format("目标%s:",goalStr));
                holder.tv_plan_current_value.setText(String.format("训练前%s:  %s%s", goalStr, goalInfo.getStartGoal(), unitStr));
                holder.tv_plan_current_time.setText(String.format("训练开始时间:%s", TimeUtil.timestampToYMD(goalInfo.getStartTime())));
                holder.tv_plan_goal_value.setText(String.format("%s%s",goalInfo.getStopGoal(),unitStr));

                if (goalInfo.getGoalStatus() == ResultCode.MODIFY_SUCCESS){
                    holder.iv_plan_stutus.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.smssdk_search_icon));
                    holder.iv_plan_stutus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Snackbar.make(v, "此项计划已完成,向型男又迈进了一步", Snackbar.LENGTH_LONG).show();
                        }
                    });
                }else {
                    holder.iv_plan_stutus.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.button_action_dark));
                    holder.iv_plan_stutus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Snackbar.make(v,"此项计划已完成?",Snackbar.LENGTH_LONG).setAction("完成", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    submitGoalComplete(goalInfo,holder.iv_plan_stutus);
                                }
                            }).show();

                        }
                    });
                }


                long totalTime = goalInfo.getStopTime() - goalInfo.getStartTime();
                long consumeTime = System.currentTimeMillis() - goalInfo.getStartTime();
//        scaleTime = (int)(((float)consumeTime /(float)totalTime)*100);
                break;
            case TYPE_CELL:
                ConsumeRecordViewHolder holderItem = (ConsumeRecordViewHolder)itemHolder;

                // 通过getItem 取得数据
                ConsumeRecordInfo item = getItem(position);
                holderItem.tv_cr_time.setText(TimeUtil.timestampToData(item.getConsumeRecordTime()));
                holderItem.tv_consume_cc.setText(String.format("摄入:%s大卡", item.getConsumeCC()));

                Gson gson = new Gson();
                Type listType = new TypeToken<List<SlidingDeckModel>>() {}.getType();
                List<SlidingDeckModel> modelList = gson.fromJson(item.getConsumeRecordContent(), listType);

                holderItem.ll_consume_content.removeAllViews();
                for (int i = 0;i< modelList.size();i++){
                    TextView textView = new TextView(context);
                    textView.setText(String.format("%s : %s 大卡",modelList.get(i).getSlidingName(),modelList.get(i).getTotalCC()));
                    holderItem.ll_consume_content.addView(textView);
                }
                break;
        }
    }

    public ConsumeRecordInfo getItem(int position) {

        return lists.get(position-1);
    }

    private void submitGoalComplete(final GoalInfo item, final ImageView iv) {


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
                    iv.setImageDrawable(ContextCompat.getDrawable(BaseApplication.getApplication(), R.drawable.smssdk_search_icon));

                }
            }
        });
    }


    public void setHeaderData(GoalInfo goalInfo) {
        if (goalInfo == null){
            return;
        }
        this.goalInfo = goalInfo;
        notifyDataSetChanged();

    }
}
