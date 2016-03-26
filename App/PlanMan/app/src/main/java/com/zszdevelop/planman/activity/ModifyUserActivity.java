package com.zszdevelop.planman.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.fragment.BaseDataFragment;
import com.zszdevelop.planman.utils.DrawerToolUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ModifyUserActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_modify_action)
    TextView tvModifyAction;

    private BaseDataFragment fragment;

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

        fragment = (BaseDataFragment) getSupportFragmentManager().findFragmentById(R.id.fl_modify_base_data);
        DrawerToolUtils.initToolbar(this,toolbar,"编辑个人资料");
    }

    private void initListener() {

        

    }

    private void fillData() {
//        HttpRequest.get();get
    }

}
