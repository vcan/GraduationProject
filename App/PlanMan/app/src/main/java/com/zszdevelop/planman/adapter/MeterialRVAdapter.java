package com.zszdevelop.planman.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.bean.ConsumeRecordInfo;
import com.zszdevelop.planman.bean.GoalInfo;
import com.zszdevelop.planman.bean.SlidingDeckModel;
import com.zszdevelop.planman.fragment.PlanFragment;
import com.zszdevelop.planman.utils.TimeUtil;
import com.zszdevelop.planman.view.BaseSectionAdapter;
import com.zszdevelop.planman.view_holder.ConsumeRecordViewHolder;
import com.zszdevelop.planman.view_holder.GoalViewHolder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangshengzhong on 16/3/11.
 */
public class MeterialRVAdapter extends BaseSectionAdapter<ConsumeRecordInfo> {

    private Context context;
    private int layoutRes;
    private int headLayout;
    private int otherLayout;
    private List<ConsumeRecordInfo> lists;



    private List<GoalInfo> goalList = new ArrayList<>();
    private List<Fragment> fragmentLists = new ArrayList<>();


    public MeterialRVAdapter(Context context, int layoutRes, List<ConsumeRecordInfo> data) {
        super(context, layoutRes, data);
        this.context =context;
        this.lists = data;

    }


    public MeterialRVAdapter(Activity context, int headLayout, int layoutRes, List<ConsumeRecordInfo> data) {
        this(context, layoutRes, data);
        this.headLayout = headLayout;
        this.layoutRes = layoutRes;
        this.context =context;
        this.lists = data;
    }


    @Override
    public boolean hasHeader() {
        return true;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_consume_record, parent, false);
        return new ConsumeRecordViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeadViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_header, parent, false);
        return new GoalViewHolder(view);
    }

    @Override
    public void onBindItemView(RecyclerView.ViewHolder itemHolder, int position) {
        ConsumeRecordViewHolder holder = (ConsumeRecordViewHolder)itemHolder;
        ConsumeRecordInfo item = getItem(position);


        holder.tv_cr_time.setText(TimeUtil.timestampToData(item.getConsumeRecordTime()));
        holder.tv_consume_cc.setText(String.format("摄入:%s大卡", item.getConsumeCC()));

        Gson gson = new Gson();
        Type listType = new TypeToken<List<SlidingDeckModel>>() {}.getType();
        List<SlidingDeckModel> modelList = gson.fromJson(item.getConsumeRecordContent(), listType);

        holder.ll_consume_content.removeAllViews();
        for (int i = 0;i< modelList.size();i++){
            TextView textView = new TextView(context);
            textView.setText(String.format("%s : %s 大卡",modelList.get(i).getSlidingName(),modelList.get(i).getTotalCC()));
            holder.ll_consume_content.addView(textView);
        }
    }

    @Override
    public void onBindHeadView(RecyclerView.ViewHolder headHolder, int position) {
        if (goalList == null){
            return;
        }

        GoalViewHolder holder = (GoalViewHolder)headHolder ;
        fragmentLists.clear();
        for (int i = 0;i< goalList.size();i++ ){
            GoalInfo goalInfo = goalList.get(i);
            PlanFragment fragment = PlanFragment.newInstanceFragment(goalInfo);
            fragmentLists.add(fragment);
        }
//        HomePagerAdapter pagerAdapter = new HomePagerAdapter(context.getSupportFragmentManager(), context, fragmentLists);
//        holder.vp_home_plan.setAdapter(pagerAdapter);
//        holder.tabs_plan.setTabMode(TabLayout.MODE_FIXED);
//        holder.tabs_plan.setupWithViewPager(holder.vp_home_plan);
    }



    public void appendGoalData(List<GoalInfo> data) {
        if (data == null) {
            return;
        }

        this.goalList .addAll(data) ;
    }




}
