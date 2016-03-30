package com.zszdevelop.planman.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.adapter.ConsumeRecordAdapter;
import com.zszdevelop.planman.adapter.MainAdapter;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.base.Helper;
import com.zszdevelop.planman.bean.GoalRecordInfo;
import com.zszdevelop.planman.bean.HomeInfo;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.config.ResultCode;
import com.zszdevelop.planman.fragment.PlanChangeFragment;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;
import com.zszdevelop.planman.utils.DrawerToolUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChangeActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs_change)
    TabLayout tabsChange;
    @Bind(R.id.vp_change)
    ViewPager vpChange;
    @Bind(R.id.navigation)
    NavigationView navigation;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private List<Fragment> list_fragment = new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordWeights = new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordChests = new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordLoins = new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordLeftArms = new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordRightArms = new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordShoulder = new ArrayList<>();

    private ConsumeRecordAdapter consumeAdapter;
    private PlanChangeFragment weightsFragment;
    private PlanChangeFragment chestsFragment;
    private PlanChangeFragment loinsFragment;
    private PlanChangeFragment leftArmsFragment;
    private PlanChangeFragment rightArmsFragment;
    private PlanChangeFragment shoulderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        ButterKnife.bind(this);

        initView();
        initListener();
        fillData();
    }


    private void initView() {

        DrawerToolUtils.initToolbar(this, toolbar, "变化趋势");
        DrawerToolUtils.interactorNavigation(this, toolbar, navigation, drawerLayout);



    }

    private void initListener() {

    }


    private void fillData() {
//        int userId = Helper.getInstance().getBaseUser().getUserId();

        String url = String.format(API.MAIN_URI, Helper.getUserId());
        HttpRequest.get(url, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {

                Gson gson = new Gson();
                HomeInfo homeInfo = gson.fromJson(json, HomeInfo.class);
                ArrayList<GoalRecordInfo> goalRecordInfos = homeInfo.getGoalRecordInfos();
                for (int i = 0; i < goalRecordInfos.size(); i++) {
                    GoalRecordInfo goalRecordInfo = goalRecordInfos.get(i);
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


                MainAdapter pagerAdapter = new MainAdapter(getSupportFragmentManager(), ChangeActivity.this, list_fragment);
                vpChange.setAdapter(pagerAdapter);
                tabsChange.setTabMode(TabLayout.MODE_FIXED);
                tabsChange.setupWithViewPager(vpChange);

            }
        });


    }



}
