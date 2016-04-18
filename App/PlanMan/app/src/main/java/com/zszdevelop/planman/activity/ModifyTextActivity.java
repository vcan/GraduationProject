package com.zszdevelop.planman.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ModifyTextActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_modify_text)
    EditText etModifyText;
    @Bind(R.id.tv_modify_complete)
    TextView tvModifyComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_text);
        ButterKnife.bind(this);
        initView();
        initListener();


    }


    private void initView() {
        initToolbar();

    }

    private void initListener() {
        tvModifyComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String modifyResult = etModifyText.getText().toString().trim();
                getIntent().putExtra("modifyResult", modifyResult);
                setResult(RESULT_OK,getIntent());
                finish();
            }
        });
    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("填写备注");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
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
