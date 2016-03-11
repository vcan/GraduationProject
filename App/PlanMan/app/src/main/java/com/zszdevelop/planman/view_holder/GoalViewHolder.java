package com.zszdevelop.planman.view_holder;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zszdevelop.planman.R;

/**
 * Created by zhangshengzhong on 16/3/11.
 */
public class GoalViewHolder extends RecyclerView.ViewHolder {

    public ViewPager vp_home_plan;
    public TabLayout tabs_plan;

    public GoalViewHolder(View itemView) {
        super(itemView);
        vp_home_plan = (ViewPager) itemView.findViewById(R.id.vp_home_plan);
        tabs_plan = (TabLayout) itemView.findViewById(R.id.tabs_plan);
    }
}
