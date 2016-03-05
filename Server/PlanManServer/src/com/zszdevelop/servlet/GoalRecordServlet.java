package com.zszdevelop.servlet;

import java.io.IOException;
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
import com.zszdevelop.dao.GoalRecordDao;
import com.zszdevelop.impl.GoalInfoImpl;
import com.zszdevelop.impl.GoalRecordImpl;
import com.zszdevelop.utils.AuthUserUtils;
import com.zszdevelop.utils.OutJsonUtils;
import com.zszdevelop.utils.ServerSettingUtils;

/**
 * Servlet implementation class GoalRecordServlet
 */
@WebServlet("/GoalRecordServlet")
public class GoalRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GoalRecordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
		String goalRecordTime = request.getParameter("goalRecordTime");
		String goalRecordType = request.getParameter("goalRecordType");
		String goalRecordData = request.getParameter("goalRecordData");
		
		// 对数据进行一个容错处理
		userId = userId == null ? "0" : userId;
		authToken = authToken == null ? "0" : authToken;
		goalRecordData = goalRecordData == null ? "0" : goalRecordData;
		goalRecordType = goalRecordType == null ? "0" : goalRecordType;
	
		GoalRecordInfo goalRecordInfo = new GoalRecordInfo();
		goalRecordInfo.setGoalRecordData(Float.parseFloat(goalRecordData));
		goalRecordInfo.setGoalRecordTime(goalRecordTime);
		goalRecordInfo.setGoalRecordType(Integer.parseInt(goalRecordType));
		
		GoalRecordDao goalRecordDao = new GoalRecordImpl();
		boolean isInsert = goalRecordDao.insertGoalRecord(goalRecordInfo, Integer.parseInt(userId));
		
		
		if (!isInsert) {
			OutJsonUtils.outJson("", ResponseMessage.MESSAGE_OPERATE_EXCEPTION, response, ResultCode.HTTP_ERROR);
			return;
		}// 将数据以json的形式传递回来
		Gson gson = new Gson();
		String jsonData = gson.toJson(new InsertStatus(ResultCode.INSERT_OK));
		OutJsonUtils.outJson(jsonData, ResponseMessage.MESSAGE_CUCCESS, response,ResultCode.HTTP_OK);

	}

}
