package com.zszdevelop.planman.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.base.Helper;
import com.zszdevelop.planman.bean.BodyData;
import com.zszdevelop.planman.config.API;
import com.zszdevelop.planman.fragment.SuggestFragment;
import com.zszdevelop.planman.http.HttpRequest;
import com.zszdevelop.planman.http.HttpRequestListener;
import com.zszdevelop.planman.utils.DrawerToolUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class KnowMeActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fl_know_me_content)
    FrameLayout flKnowMeContent;
    @Bind(R.id.navigation)
    NavigationView navigation;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konw_me);
        ButterKnife.bind(this);

        initView();
        initListener();
        fillData();

    }


    private void initView() {

        DrawerToolUtils.initToolbar(this, toolbar, "一分钟了解自己");

        DrawerToolUtils.interactorNavigation(this, toolbar, navigation, drawerLayout);


    }

    @Override
    protected void onStart() {
        super.onStart();
        navigation.setCheckedItem(R.id.navigation_know_me);
    }

    private void initListener() {
    }

    private void fillData() {
        String url = String.format(API.BODY_DATA_URI, Helper.getUserId());
        HttpRequest.get(url, new HttpRequestListener() {
            @Override
            public void onSuccess(String json) {

                Gson gson = new Gson();
                BodyData bodyData = gson.fromJson(json, BodyData.class);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fl_know_me_content, SuggestFragment.newInstanceFragment(bodyData,true));
                fragmentTransaction.commit();
            }
        });
    }

}
