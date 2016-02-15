package com.zszdevelop.planman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;
import com.zszdevelop.planman.utils.LogUtils;

import java.util.HashMap;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
        initListener();
        fillData();



    }


    private void initView() {

        startActivity(new Intent(this,LoginActivity.class));

    }


    private void initListener() {
    }


    private void fillData() {
        TextUtils.isEmpty("s");
        HashMap<String,String> map = new HashMap<>();
        map.put("uuid","nixxxx");
        map.put("versionCode","0.9.0");

        HttpRequest.post(API.REGISTER_UUID_URI, map, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {
                LogUtils.e(json);
            }
        });

    }

}
