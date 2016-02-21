package com.zszdevelop.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zszdevelop.bean.BaseUser;
import com.zszdevelop.config.ResponseMessage;
import com.zszdevelop.config.ResultCode;
import com.zszdevelop.dao.AuthUserDao;
import com.zszdevelop.dao.LoginDao;
import com.zszdevelop.impl.AuthUserImpl;
import com.zszdevelop.impl.LoginImpl;
import com.zszdevelop.utils.AuthTokenUtils;
import com.zszdevelop.utils.AuthUserUtils;
import com.zszdevelop.utils.OutJsonUtils;
import com.zszdevelop.utils.ServerSettingUtils;
import com.zszdevelop.utils.TextUtils;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServerSettingUtils.settingEncode(request, response);
		String phone = request.getParameter("phone");
		String verifyCode = request.getParameter("verifyCode");
		System.out.println("login次数");
		boolean passAuthPhoneCode = AuthUserUtils.passAuthPhoneCode(phone,verifyCode,request, response);
		
		if (!passAuthPhoneCode) {// 如果不能通过验证手机和验证码，就直接返回
			return ;
		}
		
		// 用户基本数据
		BaseUser baseUser = new BaseUser();
		
		LoginDao loginDao = new LoginImpl();
		baseUser = loginDao.getUserInfo(phone);
		if (baseUser== null) {
			OutJsonUtils.outJson("", ResponseMessage.MESSAGE_NO_REGISTER, response,ResultCode.HTTP_ERROR);
			return;
		}

		// 将数据以json的形式传递回来
		Gson gson = new Gson();
		String jsonData = gson.toJson(baseUser);
		OutJsonUtils.outJson(jsonData, ResponseMessage.MESSAGE_CUCCESS, response,ResultCode.HTTP_OK);
	}

}
