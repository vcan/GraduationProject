package com.zszdevelop.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zszdevelop.bean.ConsumeRecordInfo;
import com.zszdevelop.bean.GoalInfo;
import com.zszdevelop.bean.InsertStatus;
import com.zszdevelop.config.ResponseMessage;
import com.zszdevelop.config.ResultCode;
import com.zszdevelop.dao.ConsumeRecordDao;
import com.zszdevelop.dao.GoalInfoDao;
import com.zszdevelop.impl.ConsumeRecordImpl;
import com.zszdevelop.impl.GoalInfoImpl;
import com.zszdevelop.utils.AuthUserUtils;
import com.zszdevelop.utils.OutJsonUtils;
import com.zszdevelop.utils.ServerSettingUtils;

/**
 * Servlet implementation class ConsumeRecordServlet
 */
@WebServlet("/ConsumeRecordServlet")
public class ConsumeRecordServlet extends HttpServlet {
	
    public ConsumeRecordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServerSettingUtils.settingEncode(request, response);
		// 取得参数
		String userId = request.getParameter("userId");
		userId = userId == null ? "0" : userId;
		ConsumeRecordDao consumeRecordDao = new ConsumeRecordImpl();
		ArrayList<ConsumeRecordInfo> consumeRecordInfo = consumeRecordDao.getConsumeRecordInfo(Integer.parseInt(userId));

		// 将数据以json的形式传递回来
		Gson gson = new Gson();
		String jsonData = gson.toJson(consumeRecordInfo);
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
				String consumeRecordTime = request.getParameter("consumeRecordTime");
				String consumeCC = request.getParameter("consumeCC");
				String consumeRecordType = request.getParameter("consumeRecordType");
				String consumeRecordContent = request.getParameter("consumeRecordContent");
				System.out.println(consumeRecordContent);
				// 对数据进行一个容错处理
				userId = userId == null ? "0" : userId;
				authToken = authToken == null ? "0" : authToken;
				consumeCC = consumeCC == null ? "0" : consumeCC;
				consumeRecordType = consumeRecordType == null ? "0" : consumeRecordType;
				
				ConsumeRecordInfo consumeRecordInfo = new ConsumeRecordInfo();
				consumeRecordInfo.setConsumeCC(Double.parseDouble(consumeCC));
				consumeRecordInfo.setConsumeRecordContent(consumeRecordContent);
				consumeRecordInfo.setConsumeRecordTime(consumeRecordTime);
				consumeRecordInfo.setConsumeRecordType(Integer.parseInt(consumeRecordType));
				
				ConsumeRecordDao consumeRecordDao = new ConsumeRecordImpl();
				boolean insertConsumeRecord = consumeRecordDao.insertConsumeRecord(consumeRecordInfo, Integer.parseInt(userId));
				
				if (!insertConsumeRecord) {
					OutJsonUtils.outJson("", ResponseMessage.MESSAGE_OPERATE_EXCEPTION, response, ResultCode.HTTP_ERROR);
					return;
				}// 将数据以json的形式传递回来
				Gson gson = new Gson();
				String jsonData = gson.toJson(new InsertStatus(ResultCode.INSERT_OK));
				OutJsonUtils.outJson(jsonData, ResponseMessage.MESSAGE_CUCCESS, response,ResultCode.HTTP_OK);

	}

}
