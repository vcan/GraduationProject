package com.zszdevelop.utils;

import java.util.Random;
import java.util.UUID;

public class AuthTokenUtils {

	public static String getAuthToken(){
		String authToken = getJavaUuid()+getRandomString(12);
		
		return authToken;
	}


	/**
	 * 生成java 的uuid 截取20位
	 * @return
	 */
	private static String getJavaUuid() {

		// 创建 GUID 对象
		UUID uuid = UUID.randomUUID();
		// 得到对象产生的ID
		String a = uuid.toString();
		// 转换为大写
		// a = a.toUpperCase();
		// 替换 -
		a = a.replaceAll("-", "");
		a = a.substring(2,22);
		
		return a;
	}
	/**
	 * 生成随机数
	 * @param length
	 * @return
	 */
	private static  String getRandomString(int length){  
        String str="abcdefghijklmnopqrstuvwxyz0123456789";  
        Random random = new Random();  
        StringBuffer sb = new StringBuffer();  
          
        for(int i = 0 ; i < length; ++i){  
            int number = random.nextInt(36);//[0,62)  
              
            sb.append(str.charAt(number));  
        }
        return sb.toString();  
    }  
}
