package com.zszdevelop.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zszdevelop.config.ResponseMessage;
import com.zszdevelop.config.ResultCode;
import com.zszdevelop.dao.AuthUserDao;
import com.zszdevelop.impl.AuthUserImpl;

public class AuthUserUtils {
	
	
	/**
	 * 手机和验证码是否正确
	 * @param phone
	 * @param verifyCode
	 * @param request
	 * @param response
	 * @return
	 */
	public static boolean passAuthPhoneCode(String phone, String verifyCode, HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("passAuthPhoneCode≥≥≥≥");
		if (TextUtils.isEmpty(phone)) {
			OutJsonUtils.outJson("", ResponseMessage.MESSAGE_NO_PHONE, response,ResultCode.HTTP_ERROR);
			return false;
		}

		 if (verifyCode== null) {
			 OutJsonUtils.outJson("", ResponseMessage.MESSAGE_EDIT_VERIFY_CODE,response,ResultCode.HTTP_ERROR);
			 return false;
			 
		 }
		 int verifyStatus = 0 ;
		try {
			verifyStatus = new VerifyCodeUtils(phone, verifyCode).go();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 if (verifyStatus!=200) {// 验证码状态不等于 200，返回错误
			 OutJsonUtils.outJson("", ResponseMessage.MESSAGE_VERIFY_CODE,response,ResultCode.HTTP_ERROR);

				return false;
			}
		 return true;
	}

	/**
	 * 验证id和token
	 * @param request
	 * @param response
	 * @return
	 */
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
			int userId = Integer.parseInt(userIdStr);
			b = authUserDao.isAuthUser(userId, authToken);
			if (!b) {
				OutJsonUtils.outJson("", ResponseMessage.MESSAGE_AUTH_ERROR, response,ResultCode.HTTP_ERROR);
				return false;
			}
		} catch (Exception e) {
			b = false;
			OutJsonUtils.outJson("", ResponseMessage.MESSAGE_OPERATE_EXCEPTION, response,ResultCode.HTTP_ERROR);
		}
		
		return b;
	}
	
}
