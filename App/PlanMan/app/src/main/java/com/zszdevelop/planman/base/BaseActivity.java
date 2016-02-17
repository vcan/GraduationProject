package com.zszdevelop.planman.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.zszdevelop.planman.R;

/**
 * @author ShengZhong
 * 
 *         Activity的基类 主要操作：
 *          1.安全退出的广播 
 *          2.返回键的处理
 */
public abstract class BaseActivity extends AppCompatActivity {

	public static BaseActivity activity;




	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		// 经测试在代码里直接声明透明状态栏更有效
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
			localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
		}
		initToolbar();
	}

	private void initToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}
	}






	@Override
	protected void onResume() {
		super.onResume();
		activity = this;
	}

	@Override
	protected void onPause() {
		super.onPause();
		activity = null;
	}




}
