package com.zszdevelop.planman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.bean.BodyData;
import com.zszdevelop.planman.event.ExitRegisterEvent;
import com.zszdevelop.planman.fragment.SuggestFragment;
import com.zszdevelop.planman.utils.DrawerToolUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterSuggestActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fl_register_suggest)
    FrameLayout flRegisterSuggest;
    @Bind(R.id.tv_suggest_next)
    TextView tvSuggestNext;
    private BodyData bodyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_suggest);
        ButterKnife.bind(this);


        initView();
        initListener();
        initSuperData();
        fillData();
    }



    private void initSuperData() {
        bodyData = (BodyData) getIntent().getSerializableExtra("bodyData");


    }

    private void initView() {

        EventBus.getDefault().register(this);
        DrawerToolUtils.initToolbar(this, toolbar, "一分钟了解自己");

    }

    private void initListener() {

        tvSuggestNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterSuggestActivity.this, RegisterPlanActivity.class);
                startActivity(intent);

            }
        });

    }

    private void fillData() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_register_suggest, SuggestFragment.newInstanceFragment(bodyData));
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

    public void onEventMainThread(ExitRegisterEvent event) {
        if (event.isExit()){
            finish();
        }
    }
}
