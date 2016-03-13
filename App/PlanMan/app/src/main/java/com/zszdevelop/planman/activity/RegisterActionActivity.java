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
import com.zszdevelop.planman.base.HelperRegister;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_action);
        ButterKnife.bind(this);


        initView();
        initListener();
        fillData();
    }


    private void fillData() {

    }


    private void initListener() {



        // 轻体力活
        cvLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivSelectedLight.setVisibility(View.VISIBLE);
                ivSelectedCommon.setVisibility(View.INVISIBLE);
                ivSelectedHard.setVisibility(View.INVISIBLE);
                HelperRegister.getInstance().getRegisterData().setActionType(ResultCode.lIGHT_ACTION_CODE);
            }
        });

        // 中等体力活
        cvCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivSelectedLight.setVisibility(View.INVISIBLE);
                ivSelectedCommon.setVisibility(View.VISIBLE);
                ivSelectedHard.setVisibility(View.INVISIBLE);
                HelperRegister.getInstance().getRegisterData().setActionType(ResultCode.COMMON_ACTION_CODE);
            }
        });

        // 重体力活
        cvHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivSelectedLight.setVisibility(View.INVISIBLE);
                ivSelectedCommon.setVisibility(View.INVISIBLE);
                ivSelectedHard.setVisibility(View.VISIBLE);
                HelperRegister.getInstance().getRegisterData().setActionType(ResultCode.HARD_ACTION_CODE);
            }
        });

        tvRegisterAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActionActivity.this,RegisterSuggestActivity.class);
                startActivity(intent);
            }
        });

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