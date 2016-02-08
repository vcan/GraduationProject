package com.zszdevelop.planman.http;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.zszdevelop.planman.base.BaseApplication;


/**
 * @author 作者：LigthWang
 * @date 创建时间：2015年8月29日 下午5:57:27
 * @version 1.0
 * @parameter 内容描述：土司的工具
 * @since
 * @return
 */
public class ToastUtil {

	public static Toast mToast;

	public static void showToast(Context mContext, String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
		}
		mToast.setText(msg);
		mToast.show();
	}

	public static void showToast( String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(BaseApplication.getApplication(), "", Toast.LENGTH_SHORT);
		}
		mToast.setText(msg);
		mToast.show();
	}

	/**
	 * 可以直接在子线程中使用的土司
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showSuperToast(final Activity context, final String msg) {
		if ("main".equals(Thread.currentThread().getName())) {
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		} else {
			context.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

}
