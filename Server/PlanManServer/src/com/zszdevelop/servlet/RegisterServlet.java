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
import com.zszdevelop.dao.LoginDao;
import com.zszdevelop.dao.RegisterDao;
import com.zszdevelop.impl.LoginImpl;
import com.zszdevelop.impl.RegisterImpl;
import com.zszdevelop.utils.AuthUserUtils;
import com.zszdevelop.utils.OutJsonUtils;
import com.zszdevelop.utils.ServerSettingUtils;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegisterServlet() {
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
		String userId = request.getParameter("userId");
		String authToken = request.getParameter("authToken");
		
		boolean passAuthPhoneCode = AuthUserUtils.passAuthPhoneCode(phone, verifyCode, request, response);
		if (!passAuthPhoneCode) {// 如果不能通过验证手机和验证码，就直接返回
			return;
		}
		// 用户基本数据
		BaseUser baseUser = new BaseUser();

		LoginDao loginDao = new LoginImpl();
		baseUser = loginDao.getUserInfo(phone);
		if (baseUser != null) {//不等于空：已经注册，直接返回
			OutJsonUtils.outJson("", ResponseMessage.MESSAGE_ALREADY_REGISTER, response, ResultCode.HTTP_ERROR);
			return;
		}
		
		
		RegisterDao registerDao = new RegisterImpl();
		boolean registerUserInfo = registerDao.registerUserInfo(phone, userId, authToken);
		
		if (!registerUserInfo) {
			OutJsonUtils.outJson("", ResponseMessage.MESSAGE_OPERATE_EXCEPTION, response, ResultCode.HTTP_ERROR);
			return;
		}
		
		// 
		baseUser = loginDao.getUserInfo(phone);
		// 将数据以json的形式传递回来
		Gson gson = new Gson();
		String jsonData = gson.toJson(baseUser);
		OutJsonUtils.outJson(jsonData, ResponseMessage.MESSAGE_CUCCESS, response,ResultCode.HTTP_OK);
		
	}

}
