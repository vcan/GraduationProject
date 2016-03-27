package com.zszdevelop.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zszdevelop.bean.InsertStatus;
import com.zszdevelop.bean.BodyData;
import com.zszdevelop.config.ResponseMessage;
import com.zszdevelop.config.ResultCode;
import com.zszdevelop.dao.BodyDataDao;
import com.zszdevelop.impl.BodyDataImpl;
import com.zszdevelop.utils.AuthUserUtils;
import com.zszdevelop.utils.OutJsonUtils;
import com.zszdevelop.utils.ServerSettingUtils;

/**
 * Servlet implementation class BodyDataServlet
 */
@WebServlet("/BodyDataServlet")
public class BodyDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BodyDataServlet() {
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
		// 对数据进行一个容错处理
		userId=userId==null?"0":userId;
		
		BodyDataDao dao = new BodyDataImpl();
		BodyData bodyData = dao.getBodyData(Integer.parseInt(userId));
		if (bodyData== null) {
			OutJsonUtils.outJson("", ResponseMessage.MESSAGE_OPERATE_EXCEPTION, response,ResultCode.HTTP_ERROR);
			return;
		}
		
		Gson gson = new Gson();
		String jsonData = gson.toJson(bodyData);
		OutJsonUtils.outJson(jsonData,ResponseMessage.MESSAGE_CUCCESS,response,ResultCode.HTTP_OK);

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServerSettingUtils.settingEncode(request, response);
		
		// 验证token
		boolean passAuthUser = AuthUserUtils.passAuthUser(request, response);
		if (!passAuthUser) {
			return;
		}
		
//		  private int sex;
//		    private long birthday;
//		    private float high;
//		    private float goalRecordData;
//		    private int goalRecordType;
//		    private float actionType;
		
		// 取得参数
		String userId = request.getParameter("userId");
		String sex = request.getParameter("sex");
		String birthday = request.getParameter("birthday");
		String high = request.getParameter("high");
		String goalRecordData = request.getParameter("goalRecordData");
		String goalRecordType = request.getParameter("goalRecordType");
		String actionType = request.getParameter("actionType");
//		   private float bmi;
//		    private int intakeCC = 1500;
//		    private float consumeREE = 1500;
//		    private float standardWeight = 60;
		String bmi = request.getParameter("bmi");
		String intakeCC = request.getParameter("intakeCC");
		String consumeREE = request.getParameter("consumeREE");
		String standardWeight = request.getParameter("standardWeight");
		String maxHeart = request.getParameter("maxHeart");
		
		// 对数据进行一个容错处理
		userId=userId==null?"0":userId;
		sex=sex==null?"0":sex;
		high=high==null?"0":high;
		goalRecordData=goalRecordData==null?"0":goalRecordData;
		goalRecordType=goalRecordType==null?"0":goalRecordType;
		actionType=actionType==null?"0":actionType;
		bmi=bmi==null?"0":bmi;
		intakeCC=intakeCC==null?"0":intakeCC;
		consumeREE=consumeREE==null?"0":consumeREE;
		standardWeight=standardWeight==null?"0":standardWeight;
		maxHeart=maxHeart==null?"0":maxHeart;
		
		
		// 用户基本数据
		BodyDataDao dao = new BodyDataImpl();
		BodyData bodyData = new BodyData();
		bodyData.setActionType(Float.parseFloat(actionType));
		bodyData.setBirthday(birthday);
		bodyData.setGoalRecordData(Float.parseFloat(goalRecordData));
		bodyData.setGoalRecordType(Integer.parseInt(goalRecordType));
		bodyData.setHigh(Float.parseFloat(high));
		bodyData.setSex(Integer.parseInt(sex));
		bodyData.setBmi(Float.parseFloat(bmi));
		bodyData.setIntakeCC(Integer.parseInt(intakeCC));
		bodyData.setConsumeREE(Float.parseFloat(consumeREE));
		bodyData.setStandardWeight(Float.parseFloat(standardWeight));
		bodyData.setMaxHeart(Float.parseFloat(maxHeart));
		boolean modifyGoalStatus = dao.modifyBodyData(Integer.parseInt(userId), bodyData);
		
		
		if (!modifyGoalStatus) {
			OutJsonUtils.outJson("", ResponseMessage.MESSAGE_NO_MODIFY, response,ResultCode.HTTP_ERROR);
			return;
		}

		// 将数据以json的形式传递回来
		Gson gson = new Gson();
		String jsonData = gson.toJson(new InsertStatus(ResultCode.INSERT_OK));
		OutJsonUtils.outJson(jsonData, ResponseMessage.MESSAGE_CUCCESS, response,ResultCode.HTTP_OK);
	}

}
