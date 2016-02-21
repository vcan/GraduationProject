package com.zszdevelop.planman.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.adapter.MainAdapter;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.bean.ChestRecord;
import com.zszdevelop.planman.bean.ConsumeRecordInfo;
import com.zszdevelop.planman.bean.HomeInfo;
import com.zszdevelop.planman.bean.LeftArmRecord;
import com.zszdevelop.planman.bean.LoinRecord;
import com.zszdevelop.planman.bean.RightArmRecord;
import com.zszdevelop.planman.bean.ShoulderRecord;
import com.zszdevelop.planman.bean.WeightRecord;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.fragment.PlanChangeFragment;
import com.zszdevelop.planman.fragment.UserFragment;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;
import com.zszdevelop.planman.utils.LogUtils;

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
    private List<Fragment> list_fragment = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initListener();
        fillData();
    }

    private void initView() {
        initToolbar();


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
                ArrayList<ConsumeRecordInfo> consumeRecordInfos = homeInfo.getConsumeRecordInfos();
                ArrayList<WeightRecord> weightRecords = homeInfo.getWeightRecords();
                ArrayList<ChestRecord> chestRecords = homeInfo.getChestRecords();
                ArrayList<LoinRecord> loinRecords = homeInfo.getLoinRecords();
                ArrayList<LeftArmRecord> leftArmRecords = homeInfo.getLeftArmRecords();
                ArrayList<RightArmRecord> rightArmRecords = homeInfo.getRightArmRecords();
                ArrayList<ShoulderRecord> shoulderRecords = homeInfo.getShoulderRecords();
                android.support.v4.app.FragmentManager supportFragmentManager = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                PlanChangeFragment fragment02 = PlanChangeFragment.newInstanceFragment(weightRecords, chestRecords, loinRecords, leftArmRecords, rightArmRecords, shoulderRecords);

                fragmentTransaction.replace(R.id.fl_content, fragment02);
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

}
