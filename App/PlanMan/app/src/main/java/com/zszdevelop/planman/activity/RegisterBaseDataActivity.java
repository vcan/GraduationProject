package com.zszdevelop.planman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.base.HelperRegister;
import com.zszdevelop.planman.fragment.BaseDataFragment;
import com.zszdevelop.planman.utils.DrawerToolUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterBaseDataActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_register_describe)
    TextView tvRegisterDescribe;
    @Bind(R.id.btn_register_base_next)
    Button btnRegisterBaseNext;

    private BaseDataFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_base_data);
        ButterKnife.bind(this);

        initView();
        initListener();
    }


    private void initView() {
        DrawerToolUtils.initToolbar(this, toolbar, "个人数据");

        fragment = (BaseDataFragment) getSupportFragmentManager().findFragmentById(R.id.fm_register_base_data);
    }


    private void initListener() {



        btnRegisterBaseNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyData();

            }
        });

    }


    private void verifyData() {
        String birthday = fragment.tvRegisterBirthday.getText().toString().trim();
        String high = fragment.tvRegisterHigh.getText().toString().trim();
        String weight = fragment.tvRegisterWeight.getText().toString().trim();

        if (TextUtils.isEmpty(birthday)) {
            fragment.tvRegisterBirthday.setText("还没有填写生日");
            return;
        }
        if (TextUtils.isEmpty(high)) {
            fragment.tvRegisterHigh.setText("还没有填写身高");
            return;
        }
        if (TextUtils.isEmpty(weight)) {
            fragment.tvRegisterWeight.setText("还没有填写体重");
            return;
        }
        submitData();
    }




    private void submitData() {

        HelperRegister helper = HelperRegister.getInstance();
        helper.setRegisterData(fragment.registerData);
        Intent intent = new Intent(RegisterBaseDataActivity.this, RegisterActionActivity.class);
        startActivity(intent);

    }


}
