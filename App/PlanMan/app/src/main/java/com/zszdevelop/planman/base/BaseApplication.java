package com.zszdevelop.planman.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.zszdevelop.planman.db.DBHelper;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import cn.smssdk.SMSSDK;

/**
 * @author ShengZhong
 *
 *         描述： 代表当前应用程序
 */
public class BaseApplication extends Application {
	private static BaseApplication application;
	private static int mainTid;
	private static Handler handler;
	private static BaseApplication instance;

	//对于新增和删除操作add和remove，LinedList比较占优势，因为ArrayList实现了基于动态数组的数据结构，要移动数据。LinkedList基于链表的数据结构,便于增加删除
	private List<Activity> activityList = new LinkedList<Activity>();

	/*public static BaseApplication getApp() {
		return (BaseApplication)getApplication();
	}*/
	
	@Override
	// 在主线程运行的
	public void onCreate() {
		super.onCreate();

		application = this;

		SMSSDK.initSDK(this, "f578407a2d21", "1cfc261cf1b1de61d99809973541d682");
		DBHelper dbHelper = new DBHelper(this);
		try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Context getApplication() {
		return application;
	}



	/*Volley的请求队列*/
	public static RequestQueue getRequestQueue(Context context) {
		return Volley.newRequestQueue(context);
	}



	//单例模式中获取唯一的MyApplication实例
	public static BaseApplication getInstance() {
		if(null == instance) {
			instance = new BaseApplication();
		}
		return instance;
	}

	//添加Activity到容器中
	public void addActivity(Activity activity)  {
		activityList.add(activity);
	}
	//遍历所有Activity并finish
	public void exit(){
		for(Activity activity:activityList) {
			activity.finish();
		}
		System.exit(0);
	}

}
