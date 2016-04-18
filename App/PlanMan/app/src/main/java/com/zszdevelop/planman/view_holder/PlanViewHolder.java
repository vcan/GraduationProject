package com.zszdevelop.planman.view_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.zszdevelop.planman.R;

/**
 * Created by zhangshengzhong on 16/3/6.
 */
public class PlanViewHolder extends RecyclerView.ViewHolder{

    public TextView tv_plan_title;
    public TextView tv_plan_goal_value;
    public TextView tv_plan_goal_time;
    public TextView tv_plan_current_value;
    public TextView tv_plan_current_time;
    public TextView tv_plan_goal_describe;
    public ImageView iv_plan_stutus;
    public NumberProgressBar npb_plan_time;
    public NumberProgressBar npb_plan_value;

    public PlanViewHolder(View itemView) {
        super(itemView);

        tv_plan_title = (TextView) itemView.findViewById(R.id.tv_plan_title);
        tv_plan_goal_value = (TextView) itemView.findViewById(R.id.tv_plan_goal_value);
        tv_plan_goal_time = (TextView) itemView.findViewById(R.id.tv_plan_goal_time);
        tv_plan_current_value = (TextView) itemView.findViewById(R.id.tv_plan_current_value);
        tv_plan_current_time = (TextView) itemView.findViewById(R.id.tv_plan_current_time);
        tv_plan_goal_describe = (TextView) itemView.findViewById(R.id.tv_plan_goal_describe);
        iv_plan_stutus = (ImageView) itemView.findViewById(R.id.iv_plan_stutus);
        npb_plan_time = (NumberProgressBar) itemView.findViewById(R.id.npb_plan_time);
        npb_plan_value = (NumberProgressBar) itemView.findViewById(R.id.npb_plan_value);
    }
}
