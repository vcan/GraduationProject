package com.zszdevelop.planman.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.base.Helper;
import com.zszdevelop.planman.bean.BaseUser;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.config.Config;
import com.zszdevelop.planman.config.UserConfig;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;
import com.zszdevelop.planman.utils.LogUtils;
import com.zszdevelop.planman.utils.SharedPreferencesUtil;
import com.zszdevelop.planman.utils.UUIDInstallation;

import java.util.HashMap;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
        initListener();

        firstLogin();



    }


    private void initView() {

//

    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.e(">>>>>>>>splash...");
    }

    private void initListener() {
    }

    /**
     * 判断是不是第一次登陆
     */
    private void firstLogin() {
        boolean isFirstLogin = SharedPreferencesUtil.getInstance().getBoolean(Config.FIRST_APP, true);
        boolean isEditRegister = SharedPreferencesUtil.getInstance().getBoolean(Config.IS_EDIT_REGISTER, false);
        if (isFirstLogin) {
            registerUUID();
        } else {
            if (isEditRegister){
                Jump2Home();
            }else {
                Intent intent = new Intent(SplashActivity.this,RegisterBaseDataActivity.class);
                startActivity(intent);
            }

        }
    }


    private void registerUUID() {
        HashMap<String,String> map = new HashMap<>();
        map.put("uuid", UUIDInstallation.getUUID(this));

        HttpRequest.post(API.REGISTER_UUID_URI, map, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {
                LogUtils.e("uuid的返回值:"+json);
                Gson gson = new Gson();
                BaseUser baseUser =gson.fromJson(json, BaseUser.class);

                Helper.getInstance().setBaseUser(baseUser);
                SharedPreferencesUtil.getInstance().setInt(UserConfig.USER_ID, baseUser.getUserId());
                SharedPreferencesUtil.getInstance().setString(UserConfig.AUTH_TOKEN, baseUser.getAuthToken());
                SharedPreferencesUtil.getInstance().setBoolean(Config.FIRST_APP, false);

                if (baseUser.isFirstLogin()){
                    Intent intent = new Intent(SplashActivity.this,RegisterBaseDataActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Jump2Home();
                }


            }
        });

    }

    /*跳转到主页面*/
    private void Jump2Home() {


//        if (Helper.getLoginStatus()) {
            Intent intent = new Intent(this, MaterialMainActivity.class);
            startActivity(intent);
            finish();
//        }else {
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }

    }


}
