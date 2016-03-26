package com.zszdevelop.planman.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.adapter.MaterialPagerAdapter;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.base.Helper;
import com.zszdevelop.planman.bean.ConsumeRecordInfo;
import com.zszdevelop.planman.bean.GoalInfo;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.fragment.MaterialRecycleViewFragment;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;
import com.zszdevelop.planman.utils.DrawerToolUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MaterialMainActivity extends BaseActivity implements MaterialRecycleViewFragment.RefreshCallBack {

    @Bind(R.id.materialViewPager)
    MaterialViewPager materialViewPager;
    @Bind(R.id.navigation)
    NavigationView navigation;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_main);
        ButterKnife.bind(this);


        initListener();
        fillData();
        initView();
    }


    private void initView() {

        initToolbar();


        View logo = findViewById(R.id.logo_white);
        if (logo != null)
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    materialViewPager.notifyHeaderChanged();
                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });

    }

    private void initToolbar() {
        setTitle("");

        toolbar = materialViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0);
        drawerLayout.setDrawerListener(mDrawerToggle);
        DrawerToolUtils.interactorNavigation(this, navigation, drawerLayout);
    }

    private void initListener() {

        // viewpager 滑动监听
        materialViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
//                    case 0:
//                        return HeaderDesign.fromColorResAndUrl(
//                                R.color.green,
//                                "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
                    case 0:
                        return HeaderDesign.fromColorAndDrawable(
                                ContextCompat.getColor(MaterialMainActivity.this, R.color.colorPrimaryDark),
                                ContextCompat.getDrawable(MaterialMainActivity.this, R.drawable.smssdk_search_icon));
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

    }


    private void fillData() {

        // 填充目标记录数据,根据目标数量显示Viewpager/fragment 的数量
        String url = String.format(API.GET_GOAL_PLAN_URI, Helper.getUserId());
        HttpRequest.get(url, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<GoalInfo>>() {
                }.getType();
                List<GoalInfo> goalList = gson.fromJson(json, listType);

                for (int i = 0; i < goalList.size(); i++) {
                    // 填充数据
                    MaterialRecycleViewFragment fragment = MaterialRecycleViewFragment.newInstanceFragment(i, goalList.get(i));
                    fragmentList.add(fragment);
                }
                MaterialPagerAdapter pagerAdapter = new MaterialPagerAdapter(getSupportFragmentManager(), fragmentList);
                materialViewPager.getViewPager().setAdapter(pagerAdapter);
                materialViewPager.getViewPager().setOffscreenPageLimit(materialViewPager.getViewPager().getAdapter().getCount());
                materialViewPager.getPagerTitleStrip().setViewPager(materialViewPager.getViewPager());


            }
        });
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) ||
                super.onOptionsItemSelected(item);
    }

    @Override
    public void fillDataListener(int currentPage, final int actionType, final MaterialRecycleViewFragment fragment) {
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
}
