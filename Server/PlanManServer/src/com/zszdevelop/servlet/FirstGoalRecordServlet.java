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
import com.zszdevelop.config.ResponseMessage;
import com.zszdevelop.config.ResultCode;
import com.zszdevelop.dao.FirstGoalRecordDao;
import com.zszdevelop.dao.GoalInfoDao;
import com.zszdevelop.impl.FirstGoalRecordImpl;
import com.zszdevelop.impl.GoalInfoImpl;
import com.zszdevelop.utils.OutJsonUtils;
import com.zszdevelop.utils.ServerSettingUtils;

/**
 * Servlet implementation class FirstGoalRecordServlet
 */
@WebServlet("/FirstGoalRecordServlet")
public class FirstGoalRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public FirstGoalRecordServlet() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServerSettingUtils.settingEncode(request, response);
		// 取得参数
		String userId = request.getParameter("userId");
		String goalRecordType = request.getParameter("goalRecordType");
		FirstGoalRecordDao firstGoalRecordDao = new FirstGoalRecordImpl();
		GoalRecordInfo firstGoalRecord = firstGoalRecordDao.getFirstGoalRecord(Integer.parseInt(userId), Integer.parseInt(goalRecordType));
		
		Gson gson = new Gson();
		String jsonData = gson.toJson(firstGoalRecord);
		OutJsonUtils.outJson(jsonData,ResponseMessage.MESSAGE_CUCCESS,response,ResultCode.HTTP_OK);
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
