package com.zszdevelop.planman.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.zszdevelop.planman.R;
import com.zszdevelop.planman.base.BaseActivity;
import com.zszdevelop.planman.utils.DrawerToolUtils;
import com.zszdevelop.planman.utils.SystemUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutUsActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    @Bind(R.id.tv_about_author)
    TextView tvAboutAuthor;
    @Bind(R.id.tv_project_pager)
    TextView tvProjectPager;
    @Bind(R.id.tv_project_describe)
    TextView tvProjectDescribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);

        DrawerToolUtils.initToolbar(this, toolbar, "型男计划");
        tvVersion.setText(SystemUtils.getVersionName());
        tvAboutAuthor.setText("https://github.com/zszdevelop \nzszdevelop@163.com");
        tvProjectPager.setText("https://github.com/zszdevelop/GraduationProject");
        tvProjectDescribe.setText("我的毕业设计");
    }
}
