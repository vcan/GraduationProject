package com.zszdevelop.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zszdevelop.bean.HomeInfo;
import com.zszdevelop.config.ResponseMessage;
import com.zszdevelop.config.ResultCode;
import com.zszdevelop.dao.HomeDao;
import com.zszdevelop.impl.HomeImpl;
import com.zszdevelop.utils.AuthUserUtils;
import com.zszdevelop.utils.OutJsonUtils;
import com.zszdevelop.utils.ServerSettingUtils;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public HomeServlet() {
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServerSettingUtils.settingEncode(request, response);
		String userId = request.getParameter("userId");
		System.out.println("用户ID："+userId);
		HomeDao homeDao = new HomeImpl();
		HomeInfo homeData = homeDao.getHomeData(userId);
		if (homeData== null) {
			OutJsonUtils.outJson("", ResponseMessage.MESSAGE_OPERATE_EXCEPTION, response,ResultCode.HTTP_ERROR);
			return;
		}
		
		Gson gson = new Gson();
		String jsonData = gson.toJson(homeData);
		OutJsonUtils.outJson(jsonData,ResponseMessage.MESSAGE_CUCCESS,response,ResultCode.HTTP_OK);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		ServerSettingUtils.settingEncode(request, response);
		AuthUserUtils.passAuthUser(request, response);
	}

}
