package com.zszdevelop.planman.activity;

import android.os.Bundle;
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
import com.zszdevelop.planman.adapter.MeterialPagerAdapter;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.base.Helper;
import com.zszdevelop.planman.bean.ConsumeRecordInfo;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.fragment.MeterialRecycleViewFragment;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MaterialMainActivity extends BaseActivity implements MeterialRecycleViewFragment.RefreshCallBack {

    private MaterialViewPager mViewPager;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private MeterialRecycleViewFragment meterialFragment1;
    private MeterialRecycleViewFragment meterialFragment2;
    private MeterialRecycleViewFragment meterialFragment3;
    private MeterialRecycleViewFragment meterialFragment4;
    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_main);

//        if (!BuildConfig.DEBUG)
//            Fabric.with(this, new Crashlytics());

        setTitle("");

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        toolbar = mViewPager.getToolbar();
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

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

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);

        meterialFragment1 = MeterialRecycleViewFragment.newInstanceFragment(1);
        meterialFragment2 = MeterialRecycleViewFragment.newInstanceFragment(2);
        meterialFragment3 = MeterialRecycleViewFragment.newInstanceFragment(3);
        meterialFragment4 = MeterialRecycleViewFragment.newInstanceFragment(4);
        fragmentList.add(meterialFragment1);
        fragmentList.add(meterialFragment2);
        fragmentList.add(meterialFragment3);
        fragmentList.add(meterialFragment4);
        MeterialPagerAdapter pagerAdapter = new MeterialPagerAdapter(getSupportFragmentManager(),fragmentList);

        mViewPager.getViewPager().setAdapter(pagerAdapter);

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
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
                                ContextCompat.getDrawable(MaterialMainActivity.this,R.drawable.smssdk_search_icon));
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

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        View logo = findViewById(R.id.logo_white);
        if (logo != null)
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
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
    public void fillDataListener(int currentPage, final int actionType) {
        String url = String.format(API.CONSUME_RECORD_URI, Helper.getUserId());
        HttpRequest.get(url, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<ConsumeRecordInfo>>() {
                }.getType();
                List<ConsumeRecordInfo> consumeRecords = gson.fromJson(json, listType);
                if (actionType == 1){
                    meterialFragment1.notifyAdapter(consumeRecords);
                }if (actionType == 2){
                    meterialFragment2.notifyAdapter(consumeRecords);
                }if (actionType == 3){
                    meterialFragment3.notifyAdapter(consumeRecords);
                }if (actionType == 4){
                    meterialFragment4.notifyAdapter(consumeRecords);
                }


            }
        });
    }
}
