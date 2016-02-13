package com.zszdevelop.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zszdevelop.bean.GoalRecordInfo;
import com.zszdevelop.bean.ModifyStatus;
import com.zszdevelop.config.ResponseMessage;
import com.zszdevelop.config.ResultCode;
import com.zszdevelop.dao.ModifyThereMeasureDao;
import com.zszdevelop.impl.ModifyThereMeasureImpl;
import com.zszdevelop.utils.AuthUserUtils;
import com.zszdevelop.utils.OutJsonUtils;
import com.zszdevelop.utils.ServerSettingUtils;

/**
 * Servlet implementation class ModifyThreeMeasureServlet
 */
@WebServlet("/ModifyThreeMeasureServlet")
public class ModifyThreeMeasureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ModifyThreeMeasureServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServerSettingUtils.settingEncode(request, response);
		// 验证token
		boolean passAuthUser = AuthUserUtils.passAuthUser(request, response);
		if (!passAuthUser) {
			return;
		}
		// 取得参数
		String userId = request.getParameter("userId");
		String authToken = request.getParameter("authToken");
		String goalRecordChest = request.getParameter("goalRecordChest");
		String goalRecordLeftArm = request.getParameter("goalRecordLeftArm");
		String goalRecordLoin = request.getParameter("goalRecordLoin");
		String goalRecordRightArm = request.getParameter("goalRecordRightArm");
		String goalRecordWeight = request.getParameter("goalRecordWeight");
		String goalRecordShoulder = request.getParameter("goalRecordShoulder");
		String goalRecordTime = request.getParameter("goalRecordTime");

		// 对数据进行一个容错处理
		userId = userId == null ? "0" : userId;
		authToken = authToken == null ? "0" : authToken;
		goalRecordChest = goalRecordChest == null ? "0" : goalRecordChest;
		goalRecordLeftArm = goalRecordLeftArm == null ? "0" : goalRecordLeftArm;
		goalRecordLoin = goalRecordLoin == null ? "0" : goalRecordLoin;
		goalRecordRightArm = goalRecordRightArm == null ? "0" : goalRecordRightArm;
		goalRecordWeight = goalRecordWeight == null ? "0" : goalRecordWeight;
		goalRecordShoulder = goalRecordShoulder == null ? "0" : goalRecordShoulder;
		
		GoalRecordInfo goalRecordInfo = new GoalRecordInfo();
		goalRecordInfo.setGoalRecordChest(Float.parseFloat(goalRecordChest));
		goalRecordInfo.setGoalRecordLoin(Float.parseFloat(goalRecordLoin));
		goalRecordInfo.setGoalRecordLeftArm(Float.parseFloat(goalRecordLeftArm));
		goalRecordInfo.setGoalRecordRightArm(Float.parseFloat(goalRecordRightArm));
		goalRecordInfo.setGoalRecordWeight(Float.parseFloat(goalRecordWeight));
		goalRecordInfo.setGoalRecordShoulder(Float.parseFloat(goalRecordShoulder));
		goalRecordInfo.setGoalRecordTime(goalRecordTime);
		
		ModifyThereMeasureDao modifyThereMeasureDao = new ModifyThereMeasureImpl();
		boolean modifyThereMeasure = modifyThereMeasureDao.ModifyThereMeasure(goalRecordInfo, Integer.parseInt(userId));
	
		if (!modifyThereMeasure) {
			OutJsonUtils.outJson("", ResponseMessage.MESSAGE_OPERATE_EXCEPTION, response, ResultCode.HTTP_ERROR);
			return;
		}// 将数据以json的形式传递回来
		Gson gson = new Gson();
		String jsonData = gson.toJson(new ModifyStatus(ResultCode.MODIFY_OK));
		OutJsonUtils.outJson(jsonData, ResponseMessage.MESSAGE_CUCCESS, response,ResultCode.HTTP_OK);

	}

}
