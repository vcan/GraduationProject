package com.zszdevelop.planman.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.adapter.PlanAdapter;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.base.Helper;
import com.zszdevelop.planman.bean.GoalInfo;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.config.ResultCode;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;
import com.zszdevelop.planman.utils.DrawerToolUtils;
import com.zszdevelop.planman.view.PullLoadMoreRecyclerView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyPlanActivity extends BaseActivity {


    List<GoalInfo> goalLists = new ArrayList<>();
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.plmrv_my_plan)
    PullLoadMoreRecyclerView plmrvMyPlan;
    @Bind(R.id.navigation)
    NavigationView navigation;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
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
        DrawerToolUtils.initToolbar(this,toolbar,"我的计划");
        DrawerToolUtils.interactorNavigation(this,toolbar,navigation,drawerLayout);
        initRecyclerView();
    }

    private void initListener() {

    }


    private void fillData() {

        String url = String.format(API.GET_GOAL_PLAN_URI, Helper.getUserId(), ResultCode.ALL_GOAL_RECORED_CODE);
        HttpRequest.get(url, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<GoalInfo>>() {
                }.getType();
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
        plmrvMyPlan.setLinearLayout();
    }


}
