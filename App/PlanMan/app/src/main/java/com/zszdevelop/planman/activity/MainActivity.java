package com.zszdevelop.planman.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.base.Helper;
import com.zszdevelop.planman.bean.ConsumeRecordInfo;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.fragment.HomeFragment;
import com.zszdevelop.planman.fragment.MaterialRecycleViewFragment;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;
import com.zszdevelop.planman.utils.DrawerToolUtils;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MaterialRecycleViewFragment.RefreshCallBack ,HomeFragment.BindToolbarCallBack{


    @Bind(R.id.fl_main_content)
    FrameLayout flMainContent;
    @Bind(R.id.navigation)
    NavigationView navigation;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

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

//        DrawerToolUtils.initToolbar(this, toolbar, "首页");
//        DrawerToolUtils.interactorNavigation(this, toolbar, navigation, drawerLayout);

        HomeFragment homeFragment = HomeFragment.newInstanceFragment(toolbar);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_main_content, homeFragment);
        fragmentTransaction.commit();


    }


    private void initListener() {
    }

    private void fillData() {
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void fillDataListener(int currentPage, int actionType, final MaterialRecycleViewFragment fragment) {
        String url = String.format(API.CONSUME_RECORD_URI, Helper.getUserId());
        HttpRequest.get(url, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<ConsumeRecordInfo>>() {
                }.getType();
                List<ConsumeRecordInfo> consumeRecords = gson.fromJson(json, listType);

                fragment.setViewPagerData(consumeRecords);

            }
        });
    }


    @Override
    public void getToolbar(Toolbar homeToolbar) {
        if (homeToolbar!=null){
            DrawerToolUtils.initToolbar(this, homeToolbar, "3333");
            DrawerToolUtils.interactorNavigation(this, homeToolbar, navigation, drawerLayout);


        }

    }
}
