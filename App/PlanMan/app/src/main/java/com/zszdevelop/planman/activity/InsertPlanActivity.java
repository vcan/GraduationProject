package com.zszdevelop.planman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.base.Helper;
import com.zszdevelop.planman.bean.GoalRecordInfo;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.config.ResultCode;
import com.zszdevelop.planman.fragment.InsertPlanFragment;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;
import com.zszdevelop.planman.utils.DrawerToolUtils;
import com.zszdevelop.planman.utils.LogUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InsertPlanActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_complete)
    TextView tvComplete;
    @Bind(R.id.rl_toolbar)
    RelativeLayout rlToolbar;
    @Bind(R.id.fl_insert_plan)
    FrameLayout flInsertPlan;
    @Bind(R.id.navigation)
    NavigationView navigation;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    private InsertPlanFragment fragment;
    private int actionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instert_plan);
        ButterKnife.bind(this);

        initSuperData();
        initView();
        initlistener();
        fillData();
    }

    private void initSuperData() {

        actionType = getIntent().getIntExtra("actionType", ResultCode.WEIGHT_CODE);

    }


    private void initView() {
        DrawerToolUtils.initToolbar(this, toolbar, "新增计划");

        fragment = new InsertPlanFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_insert_plan, fragment);
        fragmentTransaction.commit();
    }



    private void initlistener() {


        tvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });
    }



    private void fillData() {


        String url = String.format(API.FIRST_RECORD_FIGUTR_URI, Helper.getUserId(), actionType);
        HttpRequest.get(url, new HttpRequestListener() {
                    @Override
                    public void onSuccess(String json) {
                        Gson gson = new Gson();
                        GoalRecordInfo goalRecordInfo = gson.fromJson(json, GoalRecordInfo.class);

                        int goalRecordType = goalRecordInfo.getGoalRecordType();
                        fragment.setDefaultSelect(actionType,goalRecordInfo.getGoalRecordData());

                    }
                }

        );

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


        HttpRequest.post(API.INSTER_GOAL_URI, map, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {
                LogUtils.e("tijiao");
                Intent intent = new Intent(InsertPlanActivity.this, MaterialMainActivity.class);
                startActivity(intent);
            }
        });
    }

}
