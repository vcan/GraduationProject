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
import com.zszdevelop.planman.base.HelperRegister;
import com.zszdevelop.planman.bean.RegisterData;
import com.zszdevelop.planman.config.UserConfig;
import com.zszdevelop.planman.utils.TimeUtil;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_suggest);
        ButterKnife.bind(this);

        initView();
        initListener();
        fillData();
    }

    private void initView() {
        initToolbar();

    }

    private void initListener() {

        tvSuggestNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterSuggestActivity.this,RegisterPlanActivity.class);
                startActivity(intent);
            }
        });

    }

    private void fillData() {

        RegisterData registerData = HelperRegister.getInstance().getRegisterData();
        float goalRecordWeight = registerData.getGoalRecordData();
        float high = registerData.getHigh();

        float actionType = registerData.getActionType();

        // 男性静态能量消耗值 男性REE = (10 × 体重) ＋ (6.25 × 身高) - (5 × 年龄) ＋ 5
        // 女性静态能量消耗值 女性REE = (10 × 体重) ＋ (6.25 × 身高) - (5 × 年龄) - 161
        // 其每天所需的热量 = REE × 活动系数 = 1294 × 1.5 = 1941(大卡)

//        男性：(身高cm－80)×70﹪=标准体重 女性：(身高cm－70)×60﹪=标准体重
//        标准体重正负10﹪为正常体重

        int intakeCC = 1500;
        float consumeREE = 1500;
        float standardWeight = 60;

        try {
            String birthdayStr = String.valueOf(registerData.getBirthday());
            if (registerData.getSex() == UserConfig.MAN) {// 男性
                consumeREE = (int) ((10 * goalRecordWeight) + (6.25 * high) - (5 * TimeUtil.BirthDayToAge(birthdayStr)) + 5);
                standardWeight = (float) ((high -80)*0.7);
            } else {// 女性
                consumeREE = (int) ((10 * goalRecordWeight) + (6.25 * high) - (5 * TimeUtil.BirthDayToAge(birthdayStr)) - 161);
                standardWeight = (float) ((high -70)*0.6);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        intakeCC = (int) (consumeREE * actionType);


        float highM = high / 100;
        float bmi = goalRecordWeight / (highM * highM);


        tvSuggestBmiValue.setText(String.valueOf(bmi));
        tvSuggestReeValue.setText(String.format("%s大卡", intakeCC));
        tvSuggestWeightValue.setText(String.format("%.2fKG",standardWeight));
        tvSuggestWeightScopeValue.setText(String.format("%.2f~%.2fKG",standardWeight*0.9,standardWeight*1.1));

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
