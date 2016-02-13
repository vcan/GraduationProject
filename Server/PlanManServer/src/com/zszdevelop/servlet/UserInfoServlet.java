package com.zszdevelop.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zszdevelop.bean.BaseUser;
import com.zszdevelop.bean.ComsumeCCInfo;
import com.zszdevelop.bean.GoalInfo;
import com.zszdevelop.bean.InsertStatus;
import com.zszdevelop.bean.UserInfo;
import com.zszdevelop.config.ResponseMessage;
import com.zszdevelop.config.ResultCode;
import com.zszdevelop.dao.GoalInfoDao;
import com.zszdevelop.dao.UserInfoDao;
import com.zszdevelop.impl.GoalInfoImpl;
import com.zszdevelop.impl.UserInfoImpl;
import com.zszdevelop.utils.AuthUserUtils;
import com.zszdevelop.utils.OutJsonUtils;
import com.zszdevelop.utils.ServerSettingUtils;

/**
 * Servlet implementation class UserInfoServlet
 */
@WebServlet("/UserInfoServlet")
public class UserInfoServlet extends HttpServlet {
	
    public UserInfoServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServerSettingUtils.settingEncode(request, response);
		// 验证token
		boolean passAuthUser = AuthUserUtils.passAuthUser(request, response);
		if (!passAuthUser) {
			return;
		}
		// 取得参数
		String userId = request.getParameter("userId");
		String authToken = request.getParameter("authToken");
		String nickname = request.getParameter("nickname");
		String birthday = request.getParameter("birthday");
		String sex = request.getParameter("sex");
		String signture = request.getParameter("signture");
		String high  = request.getParameter("high ");
		String goalRecordWeight  = request.getParameter("goalRecordWeight ");
		String actionType  = request.getParameter("actionType ");

		// 对数据进行一个容错处理
		userId = userId == null ? "0" : userId;
		authToken = authToken == null ? "0" : authToken;
		sex = sex == null ? "0" : sex;
		high = high == null ? "0" : high;
		goalRecordWeight = goalRecordWeight == null ? "0" : goalRecordWeight;
		actionType = actionType == null ? "0" : actionType;
		
		UserInfo userInfo = new UserInfo();
		
		userInfo.setBirthday(birthday);
		userInfo.setHigh(Float.parseFloat(userId));
		userInfo.setNickname(nickname);
		userInfo.setSex(Integer.parseInt(sex));
		userInfo.setSignture(signture);
		BaseUser baseUser = new BaseUser();
		baseUser.setAuthToken(authToken);
		baseUser.setUserId(Integer.parseInt(userId));
		userInfo.setBaseUser(baseUser);
		userInfo.setActionType(Float.parseFloat(actionType));
		
		UserInfoDao userInfoDao = new UserInfoImpl();
		 ComsumeCCInfo comsumeCCInfo = userInfoDao.ModifyUserInfo(userInfo);
		
		if (comsumeCCInfo==null) {
			OutJsonUtils.outJson("", ResponseMessage.MESSAGE_OPERATE_EXCEPTION, response, ResultCode.HTTP_ERROR);
			return;
		}
		
		// 将数据以json的形式传递回来
		Gson gson = new Gson();
		String jsonData = gson.toJson(comsumeCCInfo);
		OutJsonUtils.outJson(jsonData,ResponseMessage.MESSAGE_CUCCESS,response,ResultCode.HTTP_OK);
		
	}

}
