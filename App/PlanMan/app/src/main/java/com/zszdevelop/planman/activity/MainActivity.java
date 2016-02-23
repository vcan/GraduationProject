package com.zszdevelop.planman.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.adapter.MainAdapter;
import com.zszdevelop.planman.adapter.ShowTypeAdapter;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.bean.GoalRecordInfo;
import com.zszdevelop.planman.bean.HomeInfo;
import com.zszdevelop.planman.bean.ShowType;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.config.ResultCode;
import com.zszdevelop.planman.fragment.PlanChangeFragment;
import com.zszdevelop.planman.fragment.UserFragment;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;
import com.zszdevelop.planman.utils.LogUtils;
import com.zszdevelop.planman.view.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fl_content)
    FrameLayout flContent;
    @Bind(R.id.tabs_main)
    TabLayout tabsMain;
    @Bind(R.id.vp_mian)
    ViewPager vpMian;
    @Bind(R.id.navigation)
    NavigationView navigation;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.plmrv_plan_change)
    PullLoadMoreRecyclerView plmrvPlanChange;
    private List<Fragment> list_fragment = new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordWeights = new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordChests= new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordLoins= new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordLeftArms= new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordRightArms= new ArrayList<>();
    ArrayList<GoalRecordInfo> goalRecordShoulder= new ArrayList<>();


    FragmentManager supportFragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
    private PlanChangeFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initListener();
        fillData();
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
        initGodListRecyclerView();

        UserFragment userFragment = new UserFragment();
        UserFragment userFragment1 = new UserFragment();
        list_fragment.add(userFragment);
        list_fragment.add(userFragment1);

        MainAdapter loginPagerAdapter = new MainAdapter(getSupportFragmentManager(), this, list_fragment);
        vpMian.setAdapter(loginPagerAdapter);
        tabsMain.setTabMode(TabLayout.MODE_FIXED);
        tabsMain.setupWithViewPager(vpMian);

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
                for (int i = 0;i< goalRecordInfos.size() ; i++){
                    GoalRecordInfo goalRecordInfo = goalRecordInfos.get(i);
                    int goalRecordType = goalRecordInfo.getGoalRecordType();
                    switch (goalRecordType){

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

                fragment = PlanChangeFragment.newInstanceFragment(goalRecordWeights);
                fragmentTransaction.replace(R.id.fl_content, fragment);
                fragmentTransaction.commit();
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

    /**
     * 初始化 RecyclerView
     */
    private void initGodListRecyclerView() {
        List<ShowType> showTypes = new ArrayList<>();
        ShowType showType = new ShowType();
        showType.setShowTypeStr("体重");
        showType.setShowType(ResultCode.WEIGHT_CODE);
        ShowType showType1 = new ShowType();
        showType1.setShowTypeStr("胸围");
        showType1.setShowType(ResultCode.CHEST_CODE);
        ShowType showType2 = new ShowType();
        showType2.setShowTypeStr("腰围");
        showType2.setShowType(ResultCode.LOIN_CODE);
        ShowType showType3 = new ShowType();
        showType3.setShowTypeStr("左臂围");
        showType3.setShowType(ResultCode.LEFT_ARM_CODE);
        ShowType showType4 = new ShowType();
        showType4.setShowTypeStr("右臂围");
        showType4.setShowType(ResultCode.RIGHT_ARM_CODE);
        ShowType showType5 = new ShowType();
        showType5.setShowTypeStr("肩围");
        showType5.setShowType(ResultCode.SHOULDER_CODE);
        showTypes.add(showType);
        showTypes.add(showType1);
        showTypes.add(showType2);
        showTypes.add(showType3);
        showTypes.add(showType4);
        showTypes.add(showType5);
        ShowTypeAdapter showTypeAdapter = new ShowTypeAdapter(MainActivity.this, R.layout.item_show_type, showTypes);
        showTypeAdapter.setOnItemClickListener(new ShowTypeAdapter.OnItemClickListener() {
            @Override
            public void OnItemClicked(ShowType bean) {

                int showType = bean.getShowType();
                switch (showType){

                    case ResultCode.WEIGHT_CODE:
                        fragment.refreshFragment(goalRecordWeights);
                        break;

                    case ResultCode.CHEST_CODE:
                        fragment.refreshFragment(goalRecordChests);
                        break;

                    case ResultCode.LOIN_CODE:
                        fragment.refreshFragment(goalRecordLoins);
                        break;

                    case ResultCode.LEFT_ARM_CODE:
                        fragment.refreshFragment(goalRecordLeftArms);
                        break;

                    case ResultCode.RIGHT_ARM_CODE:
                        fragment.refreshFragment(goalRecordRightArms);
                        break;

                    case ResultCode.SHOULDER_CODE:
                        fragment.refreshFragment(goalRecordShoulder);

                        break;

                }

            }
        });
        plmrvPlanChange.setAdapter(showTypeAdapter);
        plmrvPlanChange.setLinearLayout();
//        pullRecycleView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
//            @Override
//            public void onRefresh() {
//                fillData();
//            }
//
//            @Override
//            public void onLoadMore() {
//                int temp = currentPage += 1;
//                loadData(temp);
//
//            }
//        });

    }


}
