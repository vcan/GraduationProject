package com.zszdevelop.planman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.adapter.PlanAdapter;
import com.zszdevelop.planman.base.Helper;
import com.zszdevelop.planman.bean.GoalInfo;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.config.ResultCode;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;
import com.zszdevelop.planman.view.PullLoadMoreRecyclerView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyPlanActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.plmrv_my_plan)
    PullLoadMoreRecyclerView plmrvMyPlan;
    @Bind(R.id.navigation)
    NavigationView navigation;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    List<GoalInfo> goalLists = new ArrayList<>();
    private PlanAdapter planAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plan);
        ButterKnife.bind(this);

        initView();
        initListener();
        fillData();
    }



    private void initView() {
        initToolbar();
        initRecyclerView();
    }

    private void initListener() {

    }


    private void fillData() {

        String url = String.format(API.GET_GOAL_PLAN_URI, Helper.getUserId());
        HttpRequest.get(url, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<GoalInfo>>(){}.getType();
                List<GoalInfo> tempList = gson.fromJson(json, listType);
                planAdapter.appendData(tempList);
                planAdapter.notifyDataSetChanged();
                plmrvMyPlan.setPullLoadMoreCompleted();
            }
        });
    }

    private void initRecyclerView() {

        planAdapter = new PlanAdapter(this, R.layout.item_plan, goalLists);
        plmrvMyPlan.setAdapter(planAdapter);
//        plmrvMyPlan.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
//            @Override
//            public void onRefresh() {
//                fillConsumeRecordData();
//            }
//
//            @Override
//            public void onLoadMore() {
//
//                loadConsumeRecordData(++currentPage);
//
//            }
//        });
        plmrvMyPlan.setLinearLayout();
    }


    private void initToolbar() {
        toolbar.setTitle("型男计划");
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


        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                Snackbar.make(MainActivity.this, menuItem.getTitle() + " pressed", Snackbar.LENGTH_LONG).show();
                menuItem.setChecked(true);
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_mian:
                        intent = new Intent(MyPlanActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.navigation_plan:
                        intent = new Intent(MyPlanActivity.this, MyPlanActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.navigation_record_figure:
                        intent = new Intent(MyPlanActivity.this, RecordFigureActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.navigation_search_food:
                        intent = new Intent(MyPlanActivity.this, SearchActivity.class);
                        intent.putExtra("SearchType", ResultCode.FOOD_CODE);
                        startActivity(intent);
                        break;

                    case R.id.navigation_search_sport:
                        intent = new Intent(MyPlanActivity.this, SearchActivity.class);
                        intent.putExtra("SearchType", ResultCode.SPORTS_CODE);
                        startActivity(intent);
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

    }

}
