package com.zszdevelop.planman.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.bean.ConsumeRecordInfo;
import com.zszdevelop.planman.bean.GoalRecordInfo;
import com.zszdevelop.planman.bean.SlidingDeckModel;
import com.zszdevelop.planman.config.ResultCode;
import com.zszdevelop.planman.fragment.PlanChangeFragment;
import com.zszdevelop.planman.utils.TimeUtil;
import com.zszdevelop.planman.view.BaseSectionAdapter;
import com.zszdevelop.planman.view_holder.ConsumeRecordViewHolder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by zhangshengzhong on 16/3/6.
 */
public class TestAdapter extends BaseSectionAdapter<ConsumeRecordInfo> {

    private BaseActivity context;
    private int layoutRes;
    private int headLayout;
    private int otherLayout;
    private List<ConsumeRecordInfo> lists;


    private PlanChangeFragment weightsFragment;
    private PlanChangeFragment chestsFragment;
    private PlanChangeFragment loinsFragment;
    private PlanChangeFragment leftArmsFragment;
    private PlanChangeFragment rightArmsFragment;
    private PlanChangeFragment shoulderFragment;


    private List<Fragment> list_fragment = new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordWeights = new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordChests = new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordLoins = new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordLeftArms = new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordRightArms = new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordShoulder = new ArrayList<>();


    public TestAdapter(Context context, int layoutRes, List<ConsumeRecordInfo> data) {
        super(context, layoutRes, data);

    }


    public TestAdapter(Activity context, int headLayout, int layoutRes, List<ConsumeRecordInfo> data) {
        this(context, layoutRes, data);
        this.headLayout = headLayout;
        this.layoutRes = layoutRes;
        this.context = (BaseActivity)context;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_mian_header, parent, false);
        return new HeadViewHolder(view);
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

        HeadViewHolder holder = (HeadViewHolder)headHolder ;

        weightsFragment = PlanChangeFragment.newInstanceFragment(goalRecordWeights);
        chestsFragment = PlanChangeFragment.newInstanceFragment(goalRecordChests);
        loinsFragment = PlanChangeFragment.newInstanceFragment(goalRecordLoins);
        leftArmsFragment = PlanChangeFragment.newInstanceFragment(goalRecordLeftArms);
        rightArmsFragment = PlanChangeFragment.newInstanceFragment(goalRecordRightArms);
        shoulderFragment = PlanChangeFragment.newInstanceFragment(goalRecordShoulder);

        list_fragment.add(weightsFragment);
        list_fragment.add(chestsFragment);
        list_fragment.add(loinsFragment);
        list_fragment.add(leftArmsFragment);
        list_fragment.add(rightArmsFragment);
        list_fragment.add(shoulderFragment);


        MainAdapter pagerAdapter = new MainAdapter(context.getSupportFragmentManager(), context, list_fragment);
        holder.vp_main_change.setAdapter(pagerAdapter);
        holder.tabs_change.setTabMode(TabLayout.MODE_FIXED);
        holder.tabs_change.setupWithViewPager(holder.vp_main_change);

    }



    public void setGoalRecordData(List<GoalRecordInfo> data) {
        if (data == null) {
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            GoalRecordInfo goalRecordInfo = data.get(i);
            int goalRecordType = goalRecordInfo.getGoalRecordType();
            switch (goalRecordType) {

                case ResultCode.WEIGHT_CODE:
                    goalRecordWeights.add(goalRecordInfo);

                    break;

                case ResultCode.CHEST_CODE:
                    goalRecordChests.add(goalRecordInfo);

                    break;

                case ResultCode.LOIN_CODE:
                    goalRecordLoins.add(goalRecordInfo);
                    break;

                case ResultCode.LEFT_ARM_CODE:
                    goalRecordLeftArms.add(goalRecordInfo);
                    break;

                case ResultCode.RIGHT_ARM_CODE:
                    goalRecordRightArms.add(goalRecordInfo);
                    break;

                case ResultCode.SHOULDER_CODE:
                    goalRecordShoulder.add(goalRecordInfo);
                    break;

            }
        }
    }


    protected class HeadViewHolder extends RecyclerView.ViewHolder {

        ViewPager vp_main_change;
        TabLayout tabs_change;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            vp_main_change = (ViewPager) itemView.findViewById(R.id.vp_main_change);
            tabs_change = (TabLayout) itemView.findViewById(R.id.tabs_change);
        }
    }


}
