package com.zszdevelop.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zszdevelop.bean.BaseUser;
import com.zszdevelop.dao.UuidDao;
import com.zszdevelop.impl.UuidImpl;
import com.zszdevelop.utils.JavaUuidUtils;

/**
 * Servlet implementation class UuidServlet
 */
@WebServlet("/UuidServlet")
public class UuidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public UuidServlet() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("你是get了: ").append(request.getContextPath());
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		//针对post请求，设置允许接收中文
        request.setCharacterEncoding("UTF-8");
		// 返回时防止编码混乱
		response.setContentType("text/html; charset=utf-8");
        
        String uuid = request.getParameter("uuid");
		String versionCode = request.getParameter("versionCode");
        UuidDao uuidDao = new UuidImpl();
        
        // 用户基本数据
        BaseUser uuidinfo = new BaseUser();
        boolean isUuidExist = uuidDao.isExist(uuid);
        if (isUuidExist) {
        	 uuidinfo = uuidDao.getUuidinfo(uuid);
		}else {
			uuidDao.insertUuid(uuid);
			uuidinfo = uuidDao.getUuidinfo(uuid);
		}
        
        
     // 将数据以json的形式传递回来
     		Gson gson = new Gson();
     		String jsonData = gson.toJson(uuidinfo);
     		
     		// 添加消息信息，将jsonArray 转为 jsonObject
     		JsonObject jsonObject = new JsonObject();
     		jsonObject.addProperty("data", jsonData);
     		jsonObject.addProperty("message", "消息获取成功");
     		jsonObject.addProperty("code", 200);
       
     	// 设置输出
    		PrintWriter out = response.getWriter();
    		out.print(jsonObject);
    		System.out.println(jsonObject);
    		out.close();


		}


}
