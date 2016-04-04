package com.zszdevelop.planman.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.zszdevelop.planman.base.BaseApplication;

/**
 * Created by ShengZhong on 2016/1/29.
 * 判断网络连接
 */
public class NetworkState {

    /**
     * 网络连接是否可用
     * @return
     */

    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) BaseApplication.getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
