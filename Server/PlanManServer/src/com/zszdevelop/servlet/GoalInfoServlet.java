package com.zszdevelop.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zszdevelop.bean.GoalInfo;
import com.zszdevelop.bean.GoalRecordInfo;
import com.zszdevelop.bean.InsertStatus;
import com.zszdevelop.config.ResponseMessage;
import com.zszdevelop.config.ResultCode;
import com.zszdevelop.dao.GoalInfoDao;
import com.zszdevelop.dao.ThereMeasureDao;
import com.zszdevelop.impl.GoalInfoImpl;
import com.zszdevelop.impl.ThereMeasureImpl;
import com.zszdevelop.utils.AuthUserUtils;
import com.zszdevelop.utils.OutJsonUtils;
import com.zszdevelop.utils.ServerSettingUtils;

/**
 * Servlet implementation class GoalInfoServlet
 */
@WebServlet("/GoalInfoServlet")
public class GoalInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoalInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServerSettingUtils.settingEncode(request, response);
		// 取得参数
		String userId = request.getParameter("userId");
		GoalInfoDao goalInfoDao = new GoalInfoImpl();
		ArrayList<GoalInfo> lists = goalInfoDao.getGoalInfo(Integer.parseInt(userId));

		// 将数据以json的形式传递回来
		Gson gson = new Gson();
		String jsonData = gson.toJson(lists);
		OutJsonUtils.outJson(jsonData,ResponseMessage.MESSAGE_CUCCESS,response,ResultCode.HTTP_OK);

		
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
		String startGoal = request.getParameter("startGoal");
		String stopGoal = request.getParameter("stopGoal");
		String startTime = request.getParameter("startTime");
		String stopTime = request.getParameter("stopTime");
		String goalType = request.getParameter("goalType");
		String goalStatus = request.getParameter("goalStatus");
		String goalDescribe = request.getParameter("goalDescribe");
		System.out.println(goalDescribe+">>>>");

		// 对数据进行一个容错处理
		userId = userId == null ? "0" : userId;
		authToken = authToken == null ? "0" : authToken;
		startGoal = startGoal == null ? "0" : startGoal;
		stopGoal = stopGoal == null ? "0" : stopGoal;
		goalType = goalType == null ? "0" : goalType;
		goalStatus = goalStatus == null ? "0" : goalStatus;
		
		GoalInfo goalInfo = new GoalInfo();
		goalInfo.setGoalStatus(Integer.parseInt(goalStatus));
		goalInfo.setGoalType(Integer.parseInt(goalType));
		goalInfo.setStartGoal(Float.parseFloat(startGoal));
		goalInfo.setStopGoal(Float.parseFloat(stopGoal));
		goalInfo.setStartTime(startTime);
		goalInfo.setStopTime(stopTime);
		goalInfo.setGoalDescribe(goalDescribe);
		
		GoalInfoDao goalInfoDao = new GoalInfoImpl();
		boolean insertGoalInfo = goalInfoDao.insertGoalInfo(goalInfo, Integer.parseInt(userId));
		
		
		if (!insertGoalInfo) {
			OutJsonUtils.outJson("", ResponseMessage.MESSAGE_OPERATE_EXCEPTION, response, ResultCode.HTTP_ERROR);
			return;
		}// 将数据以json的形式传递回来
		Gson gson = new Gson();
		String jsonData = gson.toJson(new InsertStatus(ResultCode.INSERT_OK));
		OutJsonUtils.outJson(jsonData, ResponseMessage.MESSAGE_CUCCESS, response,ResultCode.HTTP_OK);

	}

}
