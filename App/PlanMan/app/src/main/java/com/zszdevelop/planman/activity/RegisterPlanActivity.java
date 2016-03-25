package com.zszdevelop.planman.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.fragment.InstertPlanFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterPlanActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fl_register_plan)
    FrameLayout flRegisterPlan;
    @Bind(R.id.tv_insert_plan_next)
    TextView tvInsertPlanNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_plan);
        ButterKnife.bind(this);


        initView();
        initListener();
        fillData();
    }


    private void initView() {
        initToolbar();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_register_plan, new InstertPlanFragment());
        fragmentTransaction.commit();
    }

    private void initListener() {

    }


    private void fillData() {
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("制定计划");
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
