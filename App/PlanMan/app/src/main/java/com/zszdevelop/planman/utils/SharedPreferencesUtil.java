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
	
	public static SharedPreferencesUtil getInstance(Context context){
		if (spu == null) {
			spu = new SharedPreferencesUtil(context);
		}
		return spu;
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
	public  void setBoolean(String key,boolean b){
		sp.edit().putBoolean(key, b).commit();
	}
	

	/**
	 * @param key 键名
	 * @return boolean值
	 */
	public boolean getBoolean(String key,boolean defult){
		return sp.getBoolean(key,defult);
	}
	
	/**
	 * @param key 键名
	 * @param s	设置字符串
	 */
	public void setString(String key,String s){
		sp.edit().putString(key, s).commit();
	}
	
	/**
	 * @param key 键名
	 * @param defult	默认返回值
	 * @return 字符串
	 */
	public String getString(String key,String defult){
		return sp.getString(key, defult);
	}
	public String getString(String key ){
		return sp.getString(key, "");
	}
	/**
	 * @param key 键名
	 * @param i	设置整型值
	 */
	public void setInt(String key,int i){
		sp.edit().putInt(key, i).commit();
	}
	
	/**
	 * @param key 键名
	 * @param i	设置整型值
	 */
	public void setFloat(String key,Float i){
		sp.edit().putFloat(key, i).commit();
	}
	
	/**
	 * @param key 键名
	 * @param defult	默认返回int值
	 * @return	int
	 */
	public int getInt(String key,int defult){
		return sp.getInt(key, defult);
		
	}
	public int getInt(String key){
		return sp.getInt(key, 0);
		
	}
	
	public float getFloat(String key){
		return sp.getFloat(key, 0);
		
	}
	
	public void clear() {
		sp.edit().clear().commit();
	}
	public void remove(String key) {
		sp.edit().remove(key).apply();
	}
}
