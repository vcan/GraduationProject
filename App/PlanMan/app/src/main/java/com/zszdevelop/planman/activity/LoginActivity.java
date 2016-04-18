//package com.zszdevelop.planman.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.design.widget.TextInputLayout;
//import android.support.v7.widget.Toolbar;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.LinearLayout;
//
//import com.google.gson.Gson;
//import com.zszdevelop.planman.R;
//import com.zszdevelop.planman.base.BaseActivity;
//import com.zszdevelop.planman.base.Helper;
//import com.zszdevelop.planman.bean.BaseUser;
//import com.zszdevelop.planman.config.API;
//import com.zszdevelop.planman.config.Config;
//import com.zszdevelop.planman.config.UserConfig;
//import com.zszdevelop.planman.http.HttpRequest;
//import com.zszdevelop.planman.http.HttpRequestListener;
//import com.zszdevelop.planman.http.ToastUtil;
//import com.zszdevelop.planman.utils.CountdownHandlerUtils;
//import com.zszdevelop.planman.utils.LogUtils;
//import com.zszdevelop.planman.utils.SharedPreferencesUtil;
//import com.zszdevelop.planman.utils.TextStringUtils;
//
//import java.util.HashMap;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import cn.smssdk.EventHandler;
//import cn.smssdk.SMSSDK;
//
//public class LoginActivity extends BaseActivity {
//
//
//    EventHandler eh = new EventHandler() {
//
//        @Override
//        public void afterEvent(int event, int result, Object data) {
//
//            if (result == SMSSDK.RESULT_COMPLETE) {
//                //回调完成
//                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
//                    //提交验证码成功
//                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//                    //获取验证码成功
//                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
//                    //返回支持发送验证码的国家列表
//                }
//            } else {
//                ((Throwable) data).printStackTrace();
//            }
//        }
//    };
//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
//    @Bind(R.id.til_login_phone)
//    TextInputLayout tilLoginPhone;
//    @Bind(R.id.til_verify_code)
//    TextInputLayout tilVerifyCode;
//    @Bind(R.id.btn_verify_code)
//    Button btnVerifyCode;
//    @Bind(R.id.ll_code)
//    LinearLayout llCode;
//    @Bind(R.id.btn_login)
//    Button btnLogin;
//    private String phone;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        ButterKnife.bind(this);
//        LogUtils.e("........消息");
//        initView();
//        initListener();
//
//
//    }
//
//    private void initListener() {
//
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btnLogin.setClickable(false);
//                LogUtils.e("发送验证请求");
//                goLogin();
//
//            }
//        });
//
//        btnVerifyCode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                phone = tilLoginPhone.getEditText().getText().toString().trim();
//                if (TextUtils.isEmpty(phone) || !TextStringUtils.isMobileNO(phone)) {
//                    ToastUtil.showToast(LoginActivity.this, "请输入正确手机号");
//                    return;
//                }
//                CountdownHandlerUtils.startCountdown(btnVerifyCode);
//
//                SMSSDK.getVerificationCode("86", phone);
//            }
//
//        });
//    }
//
//    private void goLogin() {
//        phone = tilLoginPhone.getEditText().getText().toString().trim();
//        String verifyCode = tilVerifyCode.getEditText().getText().toString().trim();
//
//        if (TextUtils.isEmpty(phone)|| !TextStringUtils.isMobileNO(phone)) {
//            btnLogin.setClickable(true);
//            return;
//        }
//        if (TextUtils.isEmpty(verifyCode)) {
//            btnLogin.setClickable(true);
//            return;
//        }
//
//        HashMap<String, String> map = new HashMap<>();
//        map.put("phone", phone);
//        map.put("verifyCode", verifyCode);
//
//        HttpRequest.post(API.LOGIN_URI, map, new HttpRequestListener() {
//            @Override
//            public void onSuccess(String json) {
//
//                Gson gson = new Gson();
//                BaseUser baseUser = gson.fromJson(json, BaseUser.class);
//
//                Helper.getInstance().setBaseUser(baseUser);
//                SharedPreferencesUtil.setInt(UserConfig.USER_ID, baseUser.getUserId());
//                SharedPreferencesUtil.setString(UserConfig.AUTH_TOKEN, baseUser.getAuthToken());
//                SharedPreferencesUtil.setBoolean(Config.IS_LOGIN, true);
//                Jump2Home();
//                btnLogin.setClickable(true);
//            }
//
//            @Override
//            public void onError(int code, String message) {
//                btnLogin.setClickable(true);
//                super.onError(code, message);
//
//            }
//        });
//
//    }
//
//    private void initView() {
//
//        SMSSDK.registerEventHandler(eh); //注册短信回调
//
//    }
//
//    private void registerUser(String country, String phone) {
//    }
//
//    /*跳转到主页面*/
//    private void Jump2Home() {
//
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//        finish();
//    }
//}
