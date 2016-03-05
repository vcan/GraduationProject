package com.zszdevelop.planman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.adapter.FigureAdapter;
import com.zszdevelop.planman.config.ResultCode;
import com.zszdevelop.planman.fragment.FigureFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecordFigureActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs_figure)
    TabLayout tabsFigure;
    @Bind(R.id.vp_record_figure)
    ViewPager vpRecordFigure;
    @Bind(R.id.navigation_figure)
    NavigationView navigationFigure;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_figure);
        ButterKnife.bind(this);
        
        initView();
        initListener();
        fillData();
    }



    private void initView() {

        initToolbar();

        fragmentList.add(FigureFragment.newInstanceFragment(ResultCode.WEIGHT_CODE));
        fragmentList.add(FigureFragment.newInstanceFragment(ResultCode.CHEST_CODE));
        fragmentList.add(FigureFragment.newInstanceFragment(ResultCode.LOIN_CODE));
        fragmentList.add(FigureFragment.newInstanceFragment(ResultCode.LEFT_ARM_CODE));
        fragmentList.add(FigureFragment.newInstanceFragment(ResultCode.RIGHT_ARM_CODE));
        fragmentList.add(FigureFragment.newInstanceFragment(ResultCode.SHOULDER_CODE));

        FigureAdapter figureAdapter = new FigureAdapter(getSupportFragmentManager(),this,fragmentList);
        vpRecordFigure.setAdapter(figureAdapter);
        tabsFigure.setTabMode(TabLayout.MODE_FIXED);
        tabsFigure.setupWithViewPager(vpRecordFigure);
        
    }

    private void initListener() {

    }

    private void fillData() {
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("记录身材变化");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mDrawerToggle);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });


        navigationFigure.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                Snackbar.make(MainActivity.this, menuItem.getTitle() + " pressed", Snackbar.LENGTH_LONG).show();
                menuItem.setChecked(true);
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_mian:
                        intent = new Intent(RecordFigureActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.navigation_record_figure:
                        intent = new Intent(RecordFigureActivity.this, RecordFigureActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.navigation_search_food:
                        intent = new Intent(RecordFigureActivity.this, SearchActivity.class);
                        intent.putExtra("SearchType", ResultCode.FOOD_CODE);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.navigation_search_sport:
                        intent = new Intent(RecordFigureActivity.this, SearchActivity.class);
                        intent.putExtra("SearchType", ResultCode.SPORTS_CODE);
                        startActivity(intent);
                        finish();
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }
}
