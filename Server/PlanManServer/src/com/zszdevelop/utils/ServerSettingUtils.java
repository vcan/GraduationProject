package com.zszdevelop.utils;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServerSettingUtils {
	
	public static void settingEncode(HttpServletRequest request, HttpServletResponse response){
		
        try {
        	//针对post请求，设置允许接收中文
			request.setCharacterEncoding("UTF-8");
			// 返回时防止编码混乱
			response.setContentType("text/html; charset=utf-8");
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
