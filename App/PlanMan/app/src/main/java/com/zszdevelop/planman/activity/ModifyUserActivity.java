package com.zszdevelop.planman.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.base.Helper;
import com.zszdevelop.planman.bean.BodyData;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.config.ResultCode;
import com.zszdevelop.planman.fragment.BodyDataFragment;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;
import com.zszdevelop.planman.utils.DrawerToolUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ModifyUserActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_complete)
    TextView tvComplete;
    @Bind(R.id.tv_modify_action)
    TextView tvModifyAction;
    private BodyDataFragment fragment;
    private float actionType;
    private BodyData bodyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user);
        ButterKnife.bind(this);

        initView();
        initListener();
        fillData();
    }
    private void initView() {

        fragment = (BodyDataFragment) getSupportFragmentManager().findFragmentById(R.id.fl_modify_base_data);
        DrawerToolUtils.initToolbar(this, toolbar, "编辑个人资料");
    }

    private void fillData() {
        String url = String.format(API.BODY_DATA_URI, Helper.getUserId());
        HttpRequest.get(url, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {

                Gson gson = new Gson();
                bodyData = gson.fromJson(json, BodyData.class);

                fragment.setbodyData(bodyData);
                actionType = bodyData.getActionType();
                setActionType();

            }
        });
    }




    private void initListener() {

        tvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyData();
            }
        });

        tvModifyAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifyUserActivity.this, RegisterActionActivity.class);
                intent.putExtra("isRegister", false);
                intent.putExtra("actionType", actionType);
                startActivityForResult(intent, ResultCode.MODIFY_ACTION);
            }
        });

    }


    private void setActionType() {
        if (actionType == ResultCode.lIGHT_ACTION_CODE) {
            Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.ic_light_person);
            drawable.setBounds(0, 0, 100, 100);
            tvModifyAction.setCompoundDrawables(null, null, drawable, null);
        } else if (actionType == ResultCode.COMMON_ACTION_CODE) {
            Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.ic_common_person);
            drawable.setBounds(0, 0, 100, 100);
            tvModifyAction.setCompoundDrawables(null, null, drawable, null);
        } else if (actionType == ResultCode.HARD_ACTION_CODE) {
            Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.ic_hard_person);
            drawable.setBounds(0, 0, 100, 100);
            tvModifyAction.setCompoundDrawables(null, null, drawable, null);

        }
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
               finish();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!= RESULT_OK){
            return;
        }
        if (requestCode == ResultCode.MODIFY_ACTION){
             actionType = data.getFloatExtra("actionType", ResultCode.lIGHT_ACTION_CODE);
            bodyData.setActionType(actionType);
            setActionType();
        }
    }
}
