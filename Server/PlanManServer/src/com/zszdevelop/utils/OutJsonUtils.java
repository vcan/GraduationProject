package com.zszdevelop.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class OutJsonUtils {

	public static void outJson(String jsonData, String message, HttpServletResponse response) {

		// 添加消息信息，将jsonArray 转为 jsonObject
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", jsonData);
		jsonObject.addProperty("message", message);
		jsonObject.addProperty("code", 200);

		// 设置输出
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(jsonObject);
			System.out.println(jsonObject);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
