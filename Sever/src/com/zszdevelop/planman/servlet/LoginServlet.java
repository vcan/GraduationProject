package com.zszdevelop.planman.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zszdevelop.planman.bean.UserinfoBean;
import com.zszdevelop.planman.dao.UserinfoDao;
import com.zszdevelop.planman.impl.UserinfoImpl;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String queryType = request.getQueryString();
		String value = request.getHeader("X-Auth-Token");
		System.out.println("get:："+request.getQueryString()+">>>>>>>"+value);
		
		// 要先把bean 实例化加载数据
		UserinfoBean godListBean = new UserinfoBean();
		
		UserinfoDao godListDao = new UserinfoImpl();
		// 查询所有你大神数据
		ArrayList<UserinfoBean> allGodList = godListDao.getAllGodList(queryType);

		// 防止编码混乱
		response.setContentType("text/html; charset=utf-8");
		// 将数据以json的形式传递回来
		Gson gson = new Gson();
		String jsonData = gson.toJson(allGodList);
		
		// 添加消息信息，将jsonArray 转为 jsonObject
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("Data", jsonData);
		jsonObject.addProperty("Message", "消息获取成功");
		jsonObject.addProperty("Ret", 200);
		
		
		// 设置输出
		PrintWriter out = response.getWriter();
		out.print(jsonObject);
		System.out.println(jsonObject);
		out.close();

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
