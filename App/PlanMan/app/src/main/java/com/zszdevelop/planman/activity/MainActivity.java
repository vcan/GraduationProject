package com.zszdevelop.planman.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.adapter.MainAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs_main)
    TabLayout tabsMain;
    @Bind(R.id.vp_mian)
    ViewPager vpMian;
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
    }

    private void initView() {
        initToolbar();


        MainAdapter loginPagerAdapter = new MainAdapter(getSupportFragmentManager(), this, list_fragment);
        vpMian.setAdapter(loginPagerAdapter);
        tabsMain.setTabMode(TabLayout.MODE_FIXED);
        tabsMain.setupWithViewPager(vpMian);

    }

    private void initListener() {


    }

    private void initToolbar() {
        toolbar.setTitle("登录");
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
