package com.zszdevelop.planman.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.umeng.analytics.MobclickAgent;
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

//		// 经测试在代码里直接声明透明状态栏更有效
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//			localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//		}
		initToolbar();
	}

	private void initToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}
	}


	/**
	 * startActivity
	 *
	 * @param clazz
	 */
	protected void readyGo(Class<?> clazz) {
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
	}

	/**
	 * startActivity with bundle
	 *
	 * @param clazz
	 * @param bundle
	 */
	protected void readyGo(Class<?> clazz, Bundle bundle) {
		Intent intent = new Intent(this, clazz);
		if (null != bundle) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	/**
	 * startActivity then finish
	 *
	 * @param clazz
	 */
	protected void readyGoThenKill(Class<?> clazz) {
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
		finish();
	}

	/**
	 * startActivity with bundle then finish
	 *
	 * @param clazz
	 * @param bundle
	 */
	protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
		Intent intent = new Intent(this, clazz);
		if (null != bundle) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
		finish();
	}

	/**
	 * startActivityForResult
	 *
	 * @param clazz
	 * @param requestCode
	 */
	protected void readyGoForResult(Class<?> clazz, int requestCode) {
		Intent intent = new Intent(this, clazz);
		startActivityForResult(intent, requestCode);
	}

	/**
	 * startActivityForResult with bundle
	 *
	 * @param clazz
	 * @param requestCode
	 * @param bundle
	 */
	protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
		Intent intent = new Intent(this, clazz);
		if (null != bundle) {
			intent.putExtras(bundle);
		}
		startActivityForResult(intent, requestCode);
	}


	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}


}
