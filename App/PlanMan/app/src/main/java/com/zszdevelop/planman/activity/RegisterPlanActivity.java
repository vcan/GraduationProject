package com.zszdevelop.planman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.Helper;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.fragment.InsertPlanFragment;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;
import com.zszdevelop.planman.utils.LogUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterPlanActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fl_register_plan)
    FrameLayout flRegisterPlan;
    @Bind(R.id.tv_insert_plan_next)
    TextView tvInsertPlanNext;
    private InsertPlanFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_plan);
        ButterKnife.bind(this);


        initView();
        initListener();
        fillData();
    }


    private void initView() {
        initToolbar();
        fragment = new InsertPlanFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_register_plan, fragment);
        fragmentTransaction.commit();
    }

    private void initListener() {
        tvInsertPlanNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });
    }


    private void fillData() {
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("制定计划");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    // 提交数据
    private void submitData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(Helper.getUserId()));
        map.put("authToken", Helper.getToken());
        map.put("goalType", String.valueOf(fragment.goalInfo.getGoalType()));
        map.put("stopTime", String.valueOf(fragment.goalInfo.getStopTime()));
        map.put("startTime", String.valueOf(fragment.goalInfo.getStartTime()));
        map.put("stopGoal", String.valueOf(fragment.goalInfo.getStopGoal()));
        map.put("startGoal", String.valueOf(fragment.goalInfo.getStartGoal()));
        map.put("goalDescribe", fragment.goalInfo.getGoalDescribe());


        LogUtils.e("userId", String.valueOf(Helper.getUserId()));
        LogUtils.e("authToken", Helper.getToken());
        LogUtils.e("goalType", String.valueOf(fragment.goalInfo.getGoalType()));
        LogUtils.e("stopTime", String.valueOf(fragment.goalInfo.getStopTime()));
        LogUtils.e("startTime", String.valueOf(fragment.goalInfo.getStartTime()));
        LogUtils.e("stopGoal", String.valueOf(fragment.goalInfo.getStopGoal()));
        LogUtils.e("startGoal", String.valueOf(fragment.goalInfo.getStartGoal()));
        LogUtils.e("goalDescribe", fragment.goalInfo.getGoalDescribe());

        HttpRequest.post(API.INSTER_GOAL_URI, map, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {
                LogUtils.e("tijiao");
                Intent intent = new Intent(RegisterPlanActivity.this, MaterialMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
