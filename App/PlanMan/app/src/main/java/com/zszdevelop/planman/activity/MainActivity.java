package com.zszdevelop.planman.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.adapter.ConsumeRecordAdapter;
import com.zszdevelop.planman.adapter.MainAdapter;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.base.Helper;
import com.zszdevelop.planman.bean.ConsumeRecordInfo;
import com.zszdevelop.planman.bean.GoalRecordInfo;
import com.zszdevelop.planman.bean.HomeInfo;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.config.ResultCode;
import com.zszdevelop.planman.fragment.PlanChangeFragment;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;
import com.zszdevelop.planman.utils.LogUtils;
import com.zszdevelop.planman.view.PullLoadMoreRecyclerView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.plmrv_consume_record)
    PullLoadMoreRecyclerView plmrvConsumeRecord;
    @Bind(R.id.navigation)
    NavigationView navigation;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.tabs_change)
    TabLayout tabsChange;
    @Bind(R.id.vp_main_change)
    ViewPager vpMainChange;
    private List<Fragment> list_fragment = new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordWeights = new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordChests = new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordLoins = new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordLeftArms = new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordRightArms = new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordShoulder = new ArrayList<>();


    List<ConsumeRecordInfo> consumeRecords = new ArrayList<>();


    FragmentManager supportFragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
    private PlanChangeFragment fragment;
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
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initListener();
        fillData();
        fillConsumeRecordData();
//        ShakeListenerUtils shakeListenerUtils = new ShakeListenerUtils(this);
//
//        shakeListenerUtils.setOnShakeListener(new ShakeListenerUtils.onShakeListener() {
//            @Override
//            public void onShake(int i) {
//                ToastUtil.showToast("您抖动了" + i);
//
//            }
//        });

    }

    private void initView() {
        initToolbar();
        initRecyclerView();
        initFab();


    }

    private void initFab() {
        // Set up the white button on the lower right corner
        // more or less with default parameter
        final ImageView fabIconNew = new ImageView(this);
        fabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.button_action_dark));
        final FloatingActionButton rightLowerButton = new FloatingActionButton.Builder(this)
                .setContentView(fabIconNew)
                .build();

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);
        ImageView rlIcon1 = new ImageView(this);
        ImageView rlIcon2 = new ImageView(this);
        ImageView rlIcon3 = new ImageView(this);
        ImageView rlIcon4 = new ImageView(this);

        rlIcon1.setImageDrawable(getResources().getDrawable(R.drawable.smssdk_search_icon));
        rlIcon2.setImageDrawable(getResources().getDrawable(R.drawable.smssdk_sharesdk_icon));
        rlIcon3.setImageDrawable(getResources().getDrawable(R.drawable.button_action));
        rlIcon4.setImageDrawable(getResources().getDrawable(R.drawable.button_action_dark_touch));

        // Build the menu with default options: light theme, 90 degrees, 72dp radius.
        // Set 4 default SubActionButtons
        final FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(rLSubBuilder.setContentView(rlIcon1).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon2).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon3).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon4).build())
                .attachTo(rightLowerButton)
                .build();

        // Listen menu open and close events to animate the button content view
        rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                fabIconNew.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                fabIconNew.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                animation.start();
            }
        });

        rlIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });


    }


    private void initListener() {

           }


    private void fillData() {
//        int userId = Helper.getInstance().getBaseUser().getUserId();

        String url = String.format(API.MAIN_URI, 10001);
        HttpRequest.get(url, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {

                LogUtils.e("fanhui:" + json);
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


                MainAdapter pagerAdapter = new MainAdapter(getSupportFragmentManager(), MainActivity.this, list_fragment);
                vpMainChange.setAdapter(pagerAdapter);
                tabsChange.setTabMode(TabLayout.MODE_FIXED);
                tabsChange.setupWithViewPager(vpMainChange);

//                weightsFragment.refreshFragment(goalRecordWeights);
//                chestsFragment.refreshFragment(goalRecordChests);
//                loinsFragment.refreshFragment(goalRecordLoins);
//                leftArmsFragment.refreshFragment(goalRecordLeftArms);
//                rightArmsFragment.refreshFragment(goalRecordRightArms);
//                shoulderFragment.refreshFragment(goalRecordShoulder);
            }
        });


    }


    private void fillConsumeRecordData() {

        consumeAdapter.clear();

        loadConsumeRecordData(ResultCode.FIRST_PAGE_CODE);

    }

    private void loadConsumeRecordData(int currentPage) {

        String url = String.format(API.CONSUME_RECORD_URI, Helper.getUserId());
        HttpRequest.get(url, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {
                LogUtils.e("fanhui le sm gui" + json);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<ConsumeRecordInfo>>() {
                }.getType();
                consumeRecords = gson.fromJson(json, listType);
                consumeAdapter.appendData(consumeRecords);
                consumeAdapter.notifyDataSetChanged();
                plmrvConsumeRecord.setPullLoadMoreCompleted();
            }
        });
    }


    private void initToolbar() {
        toolbar.setTitle("型男计划");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }

    int currentPage = 1;

    private void initRecyclerView() {
        consumeAdapter = new ConsumeRecordAdapter(this, R.layout.item_consume_record, consumeRecords);
        plmrvConsumeRecord.setAdapter(consumeAdapter);
        plmrvConsumeRecord.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                fillConsumeRecordData();
            }

            @Override
            public void onLoadMore() {

                loadConsumeRecordData(++currentPage);

            }
        });
        plmrvConsumeRecord.setLinearLayout();
    }


}
