package com.zszdevelop.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zszdevelop.config.ResponseMessage;
import com.zszdevelop.config.ResultCode;
import com.zszdevelop.dao.AuthUserDao;
import com.zszdevelop.impl.AuthUserImpl;

public class AuthUserUtils {

	public static boolean passAuthUser(HttpServletRequest request, HttpServletResponse response){
		boolean b = false;

		String userIdStr = request.getParameter("userId");
		String authToken = request.getParameter("authToken");
		System.out.println(userIdStr+">>>>"+authToken);
		Gson gson = new Gson();
		if (TextUtils.isEmpty(userIdStr)) {
			OutJsonUtils.outJson("", ResponseMessage.MESSAGE_NO_USERID, response,ResultCode.HTTP_ERROR);
			return false;
		}
		if (TextUtils.isEmpty(authToken)) {
			OutJsonUtils.outJson("", ResponseMessage.MESSAGE_NO_AUTHTOKEN, response,ResultCode.HTTP_ERROR);
			return false;
		}
		
		
		AuthUserDao authUserDao = new AuthUserImpl();
		try {
			b = true;
			int userId = Integer.parseInt(userIdStr);
			boolean authUser = authUserDao.isAuthUser(userId, authToken);
		} catch (Exception e) {
			b = false;
			OutJsonUtils.outJson("", ResponseMessage.MESSAGE_OPERATE_EXCEPTION, response,ResultCode.HTTP_ERROR);
		}
		
		return b;
	}
	
}
