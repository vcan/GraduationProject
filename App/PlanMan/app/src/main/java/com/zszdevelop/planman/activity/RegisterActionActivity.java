package com.zszdevelop.planman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.bean.BodyData;
import com.zszdevelop.planman.config.ResultCode;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActionActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_register_action_title)
    TextView tvRegisterActionTitle;
    @Bind(R.id.iv_light_action)
    ImageView ivLightAction;
    @Bind(R.id.iv_selected_light)
    ImageView ivSelectedLight;
    @Bind(R.id.cv_light)
    CardView cvLight;
    @Bind(R.id.iv_common_action)
    ImageView ivCommonAction;
    @Bind(R.id.iv_selected_common)
    ImageView ivSelectedCommon;
    @Bind(R.id.cv_common)
    CardView cvCommon;
    @Bind(R.id.iv_hard_action)
    ImageView ivHardAction;
    @Bind(R.id.iv_selected_hard)
    ImageView ivSelectedHard;
    @Bind(R.id.cv_hard)
    CardView cvHard;
    @Bind(R.id.tv_register_action)
    TextView tvRegisterAction;
    @Bind(R.id.tv_complete)
    TextView tvComplete;
    private BodyData bodyData;
    private float actionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_action);
        ButterKnife.bind(this);

        initView();
        initSuperData();
        initListener();
        fillData();
    }

    private void initSuperData() {
        boolean isRegister = getIntent().getBooleanExtra("isRegister", true);
        if (isRegister) {
            bodyData = (BodyData) getIntent().getSerializableExtra("bodyData");
            tvComplete.setVisibility(View.GONE);
            tvRegisterAction.setVisibility(View.VISIBLE);
        } else {
            tvComplete.setVisibility(View.VISIBLE);
            tvRegisterAction.setVisibility(View.GONE);

            actionType = getIntent().getFloatExtra("actionType", ResultCode.lIGHT_ACTION_CODE);
            if (actionType == ResultCode.lIGHT_ACTION_CODE) {
                checkedLight();
            } else if (actionType == ResultCode.COMMON_ACTION_CODE) {
                checkedCommon();
            } else if (actionType == ResultCode.HARD_ACTION_CODE) {
                checkedHard();
            }
        }

    }


    private void fillData() {

    }


    private void initListener() {


        // 轻体力活
        cvLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedLight();
            }
        });

        // 中等体力活
        cvCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedCommon();
            }
        });

        // 重体力活
        cvHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedHard();
            }
        });

        tvRegisterAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bodyData.setActionType(actionType);
                Intent intent = new Intent(RegisterActionActivity.this, RegisterSuggestActivity.class);
                intent.putExtra("bodyData",bodyData);
                startActivity(intent);
            }
        });

        tvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIntent().putExtra("actionType", actionType);
                setResult(RESULT_OK,getIntent());
                finish();
            }
        });
    }

    private void checkedHard() {
        ivSelectedLight.setVisibility(View.INVISIBLE);
        ivSelectedCommon.setVisibility(View.INVISIBLE);
        ivSelectedHard.setVisibility(View.VISIBLE);
        actionType = ResultCode.HARD_ACTION_CODE;
    }

    private void checkedCommon() {
        ivSelectedLight.setVisibility(View.INVISIBLE);
        ivSelectedCommon.setVisibility(View.VISIBLE);
        ivSelectedHard.setVisibility(View.INVISIBLE);
        actionType = ResultCode.COMMON_ACTION_CODE;
    }

    private void checkedLight() {
        ivSelectedLight.setVisibility(View.VISIBLE);
        ivSelectedCommon.setVisibility(View.INVISIBLE);
        ivSelectedHard.setVisibility(View.INVISIBLE);
        actionType = ResultCode.lIGHT_ACTION_CODE;
    }

    private void initView() {
        initToolbar();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("个人数据");
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