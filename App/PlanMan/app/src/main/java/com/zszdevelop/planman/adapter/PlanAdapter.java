package com.zszdevelop.planman.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseAdapter;
import com.zszdevelop.planman.bean.ConsumeRecordInfo;
import com.zszdevelop.planman.bean.GoalInfo;
import com.zszdevelop.planman.config.ResultCode;
import com.zszdevelop.planman.utils.LogUtils;
import com.zszdevelop.planman.utils.TimeUtil;
import com.zszdevelop.planman.view_holder.PlanViewHolder;

import java.util.List;

/**
 * Created by zhangshengzhong on 16/3/6.
 */
public class PlanAdapter extends BaseAdapter<GoalInfo,PlanViewHolder> {


    private List<GoalInfo> lists;
    private Context context;

    public PlanAdapter(Context context, int layoutResource, List<GoalInfo> consumeRecordInfo) {
        super(context, layoutResource, consumeRecordInfo);
        this.context = context;
        this.lists = consumeRecordInfo;
    }

    @Override
    public PlanViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_plan, parent, false);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(PlanViewHolder holder, int position) {
        GoalInfo item = getItem(position);
        holder.tv_plan_goal_time.setText(TimeUtil.timestampToYMD(item.getStopTime()));

        holder.tv_plan_goal_value.setText(item.getStopGoal()+"cm");
        holder.tv_plan_goal_describe.setText(item.getGoalDescribe());

        String goalStr = "体重";
        String unitStr = "cm";
        int goalType = item.getGoalType();
        switch (goalType){
            case ResultCode.WEIGHT_CODE:
                goalStr = "体重";
                unitStr = "kg";
                break;
            case ResultCode.CHEST_CODE:
                goalStr = "胸围";
                break;
            case ResultCode.LOIN_CODE:
                goalStr = "腰围";
                break;
            case ResultCode.LEFT_ARM_CODE:
                goalStr = "左臂围";
                break;
            case ResultCode.RIGHT_ARM_CODE:
                goalStr = "右臂围";
                break;
            case ResultCode.SHOULDER_CODE:
                goalStr = "肩宽";
                break;

        }
        holder.tv_plan_title.setText(String.format("目标%s:",goalStr));
        holder.tv_plan_current_value.setText(String.format("训练前%s:  %s%s", goalStr, item.getStartGoal(), unitStr));
        holder.tv_plan_current_time.setText(String.format("训练开始时间:%s", TimeUtil.timestampToYMD(item.getStartTime())));

        holder.iv_plan_stutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"此项计划已完成?",Snackbar.LENGTH_LONG).setAction("完成", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtils.e("完成啦啦啦啦啦");
                    }
                }).show();

            }
        });
    }




    public interface  OnItemClickListener {
        void OnItemClicked(ConsumeRecordInfo bean);
    }

    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    };

}
