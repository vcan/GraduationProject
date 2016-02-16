package com.zszdevelop.planman.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.zszdevelop.planman.base.BaseApplication;


/**
 * @author LigthWang
 *
 * 描述：SharedPreferences单例工具
 */
public class SharedPreferencesUtil {

	private SharedPreferences sp;
	
	private static SharedPreferencesUtil spu = null;
	
	private final static String SHARED_NAME = "UserInfo";

	public SharedPreferencesUtil(Context context) {
		this.sp = context.getSharedPreferences(SHARED_NAME, 0);
	}
	

	public static SharedPreferencesUtil getInstance() {
		if (spu == null) {
			spu = new SharedPreferencesUtil(BaseApplication.getApplication().getApplicationContext());
		}
		return spu;
	}
	
	/**
	 * @param key 键值名
	 * @param b	设置boolean值
	 */
	public  static void setBoolean(String key,boolean b){
		SharedPreferencesUtil.getInstance().sp.edit().putBoolean(key, b).commit();
	}
	

	/**
	 * @param key 键名
	 * @param b	默认返回值
	 * @return boolean值
	 */
	public static boolean getBoolean(String key,boolean defult){
		return SharedPreferencesUtil.getInstance().sp.getBoolean(key,defult);
	}
	
	/**
	 * @param key 键名
	 * @param s	设置字符串
	 */
	public static void setString(String key,String s){
		SharedPreferencesUtil.getInstance().sp.edit().putString(key, s).commit();
	}
	
	/**
	 * @param key 键名
	 * @param defult	默认返回值
	 * @return 字符串
	 */
	public static String getString(String key,String defult){
		return SharedPreferencesUtil.getInstance().sp.getString(key, defult);
	}
	public static String getString(String key ){
		return SharedPreferencesUtil.getInstance().sp.getString(key, "");
	}
	/**
	 * @param key 键名
	 * @param i	设置整型值
	 */
	public static void setInt(String key,int i){
		SharedPreferencesUtil.getInstance().sp.edit().putInt(key, i).commit();
	}
	
	/**
	 * @param key 键名
	 * @param i	设置整型值
	 */
	public static void setFloat(String key,Float i){
		SharedPreferencesUtil.getInstance().sp.edit().putFloat(key, i).commit();
	}
	
	/**
	 * @param key 键名
	 * @param defult	默认返回int值
	 * @return	int
	 */
	public static int getInt(String key,int defult){
		return SharedPreferencesUtil.getInstance().sp.getInt(key, defult);
		
	}
	public static int getInt(String key){
		return SharedPreferencesUtil.getInstance().sp.getInt(key, 0);
		
	}
	
	public static float getFloat(String key){
		return SharedPreferencesUtil.getInstance().sp.getFloat(key, 0);
		
	}
	
	public void clear() {
		sp.edit().clear().commit();
	}
}
