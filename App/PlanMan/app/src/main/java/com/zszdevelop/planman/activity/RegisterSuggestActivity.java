package com.zszdevelop.planman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.base.Helper;
import com.zszdevelop.planman.bean.BodyData;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterSuggestActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_suggest_title)
    TextView tvSuggestTitle;
    @Bind(R.id.tv_suggest_bmi)
    TextView tvSuggestBmi;
    @Bind(R.id.tv_suggest_bmi_value)
    TextView tvSuggestBmiValue;
    @Bind(R.id.tv_suggest_bmi_describe)
    TextView tvSuggestBmiDescribe;
    @Bind(R.id.tv_suggest_weight_scope)
    TextView tvSuggestWeightScope;
    @Bind(R.id.tv_suggest_weight_scope_value)
    TextView tvSuggestWeightScopeValue;
    @Bind(R.id.ll_suggest_weight_scope)
    LinearLayout llSuggestWeightScope;
    @Bind(R.id.tv_suggest_weight)
    TextView tvSuggestWeight;
    @Bind(R.id.tv_suggest_weight_value)
    TextView tvSuggestWeightValue;
    @Bind(R.id.ll_suggest_weight)
    LinearLayout llSuggestWeight;
    @Bind(R.id.tv_suggest_ree)
    TextView tvSuggestRee;
    @Bind(R.id.tv_suggest_ree_value)
    TextView tvSuggestReeValue;
    @Bind(R.id.ll_suggest_ree)
    LinearLayout llSuggestRee;
    @Bind(R.id.tv_suggest_ree_describe)
    TextView tvSuggestReeDescribe;
    @Bind(R.id.tv_suggest_heart_rate)
    TextView tvSuggestHeartRate;
    @Bind(R.id.tv_suggest_heart_rate_value)
    TextView tvSuggestHeartRateValue;
    @Bind(R.id.ll_suggest_heart_rate)
    LinearLayout llSuggestHeartRate;
    @Bind(R.id.tv_suggest_heart_rate_describe)
    TextView tvSuggestHeartRateDescribe;
    @Bind(R.id.cv_my_data)
    CardView cvMyData;
    @Bind(R.id.tv_suggest_next)
    TextView tvSuggestNext;
    private BodyData bodyData;
    private float standardWeight;
    private float bmi;
    private int intakeCC;
    private float consumeREE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_suggest);
        ButterKnife.bind(this);

        initSuperData();
        initView();
        initListener();
        fillData();
    }

    private void initSuperData() {
        bodyData = (BodyData) getIntent().getSerializableExtra("bodyData");
    }

    private void initView() {
        initToolbar();

    }

    private void initListener() {

        tvSuggestNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();

            }
        });

    }

    private void submitData() {

//        standardWeight = bodyData.getStandardWeight();
//        bmi = bodyData.getBmi();
//        intakeCC = bodyData.getIntakeCC();
//        consumeREE = bodyData.getConsumeREE();


        HashMap<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(Helper.getUserId()));
        map.put("authToken", Helper.getToken());
        map.put("birthday", String.valueOf(bodyData.getBirthday()));
        map.put("high", String.valueOf(bodyData.getHigh()));
        map.put("sex", String.valueOf(bodyData.getSex()));
        map.put("goalRecordData", String.valueOf(bodyData.getGoalRecordData()));
        map.put("goalRecordType", String.valueOf(bodyData.getGoalRecordType()));
        map.put("actionType", String.valueOf(bodyData.getActionType()));
        map.put("standardWeight", String.valueOf(bodyData.getStandardWeight()));
        map.put("bmi", String.valueOf(bodyData.getBmi()));
        map.put("intakeCC", String.valueOf(bodyData.getIntakeCC()));
        map.put("consumeREE", String.valueOf(bodyData.getConsumeREE()));
        map.put("maxHeart", String.valueOf(bodyData.getMaxHeart()));

        HttpRequest.post(API.MODIFY_BASE_DATA_URI, map, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {
                Intent intent = new Intent(RegisterSuggestActivity.this, RegisterPlanActivity.class);
                startActivity(intent);
            }
        });

    }

    private void fillData() {

        standardWeight = bodyData.getStandardWeight();
        bmi = bodyData.getBmi();
        intakeCC = bodyData.getIntakeCC();
        consumeREE = bodyData.getConsumeREE();
        float maxHeart = bodyData.getMaxHeart();
//        中低强度的运动应该达到人最大心率(最大心率=220-实际年龄)的60%~75%

        tvSuggestBmiValue.setText(String.valueOf(bmi));
        tvSuggestReeValue.setText(String.format("%s大卡", intakeCC));
        tvSuggestWeightValue.setText(String.format("%.2fKG", standardWeight));
        tvSuggestWeightScopeValue.setText(String.format("%.2f~%.2fKG", standardWeight * 0.9, standardWeight * 1.1));
        tvSuggestHeartRateValue.setText(String.format("%s 次/分钟到 %s 次/分钟",maxHeart*0.6,maxHeart*0.75));
    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("一分钟了解自己");
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

}
