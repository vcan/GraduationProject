package com.zszdevelop.planman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.bean.BaseUser;
import com.zszdevelop.planman.bean.Helper;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.config.Config;
import com.zszdevelop.planman.config.UserConfig;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;
import com.zszdevelop.planman.utils.SharedPreferencesUtil;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class LoginActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.til_login_phone)
    TextInputLayout tilLoginPhone;
    @Bind(R.id.til_verify_code)
    TextInputLayout tilVerifyCode;
    @Bind(R.id.tv_verify_code)
    TextView tvVerifyCode;
    @Bind(R.id.ll_code)
    LinearLayout llCode;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.btn)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initView();
        initListener();


    }

    private void initListener() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goLogin();

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开注册页面
                RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler() {
                    public void afterEvent(int event, int result, Object data) {
                        // 解析注册结果
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            @SuppressWarnings("unchecked")
                            HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                            String country = (String) phoneMap.get("country");
                            String phone = (String) phoneMap.get("phone");

                            // 提交用户信息
                            registerUser(country, phone);
                        }
                    }
                });
                registerPage.show(LoginActivity.this);
            }
        });
    }

    private void goLogin() {
        String phone = tilLoginPhone.getEditText().getText().toString().trim();
        String verifyCode = tilVerifyCode.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {
            return;
        }
        if (TextUtils.isEmpty(verifyCode)) {
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("verifyCode", verifyCode);
        HttpRequest.post(API.LOGIN_URI, map, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {
                Gson gson = new Gson();
                BaseUser baseUser = gson.fromJson(json, BaseUser.class);

                Helper.getInstance().setBaseUser(baseUser);
                SharedPreferencesUtil.setInt(UserConfig.USER_ID, baseUser.getUserId());
                SharedPreferencesUtil.setString(UserConfig.AUTH_TOKEN, baseUser.getAuthToken());
                SharedPreferencesUtil.setBoolean(Config.IS_LOGIN,true);
                Jump2Home();
            }
        });

    }

    private void initView() {

    }

    private void registerUser(String country, String phone) {
    }

    /*跳转到主页面*/
    private void Jump2Home() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
