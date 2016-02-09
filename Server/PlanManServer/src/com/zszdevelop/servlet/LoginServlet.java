package com.zszdevelop.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zszdevelop.config.ResponseMessage;
import com.zszdevelop.dao.AuthUserDao;
import com.zszdevelop.impl.AuthUserImpl;
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

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServerSettingUtils.settingEncode(request, response);
		AuthUserUtils.passAuthUser(request, response);
		
		
		
	}

}
