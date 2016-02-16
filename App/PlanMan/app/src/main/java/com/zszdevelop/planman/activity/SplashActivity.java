package com.zszdevelop.planman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.bean.BaseUser;
import com.zszdevelop.planman.bean.Helper;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.config.Config;
import com.zszdevelop.planman.config.UserConfig;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;
import com.zszdevelop.planman.utils.LogUtils;
import com.zszdevelop.planman.utils.SharedPreferencesUtil;
import com.zszdevelop.planman.utils.UUIDInstallation;

import java.util.HashMap;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
        initListener();

        firstLoging();



    }


    private void initView() {

//

    }


    private void initListener() {
    }

    /**
     * 判断是不是第一次登陆
     */
    private void firstLoging() {
        boolean isFirstLogin = SharedPreferencesUtil.getBoolean(Config.FIRST_APP, true);
        if (isFirstLogin) {
            registerUUID();
        } else {
            Jump2Home();
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
                SharedPreferencesUtil.setInt(UserConfig.USER_ID, baseUser.getUserId());
                SharedPreferencesUtil.setString(UserConfig.AUTH_TOKEN, baseUser.getAuthToken());
                SharedPreferencesUtil.setBoolean(Config.FIRST_APP,false);

                Jump2Home();
            }
        });

    }

    /*跳转到主页面*/
    private void Jump2Home() {

//
//        if (Helper.getLoginStatus()) {
//            fillUserCenter();
//        }

        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        finish();
    }


}
