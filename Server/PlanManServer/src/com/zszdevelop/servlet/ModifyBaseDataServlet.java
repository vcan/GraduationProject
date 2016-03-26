package com.zszdevelop.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zszdevelop.bean.InsertStatus;
import com.zszdevelop.bean.RegisterData;
import com.zszdevelop.config.ResponseMessage;
import com.zszdevelop.config.ResultCode;
import com.zszdevelop.dao.GoalInfoDao;
import com.zszdevelop.dao.ModifyBaseDataDao;
import com.zszdevelop.impl.GoalInfoImpl;
import com.zszdevelop.impl.ModifyBaseDataImpl;
import com.zszdevelop.utils.AuthUserUtils;
import com.zszdevelop.utils.OutJsonUtils;
import com.zszdevelop.utils.ServerSettingUtils;

/**
 * Servlet implementation class ModifyBaseDataServlet
 */
@WebServlet("/ModifyBaseDataServlet")
public class ModifyBaseDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyBaseDataServlet() {
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
		
		
		// 用户基本数据
		ModifyBaseDataDao dao = new ModifyBaseDataImpl();
		RegisterData registerData = new RegisterData();
		registerData.setActionType(Float.parseFloat(actionType));
		registerData.setBirthday(birthday);
		registerData.setGoalRecordData(Float.parseFloat(goalRecordData));
		registerData.setGoalRecordType(Integer.parseInt(goalRecordType));
		registerData.setHigh(Float.parseFloat(high));
		registerData.setSex(Integer.parseInt(sex));
		registerData.setBmi(Float.parseFloat(bmi));
		registerData.setIntakeCC(Integer.parseInt(intakeCC));
		registerData.setConsumeREE(Float.parseFloat(consumeREE));
		registerData.setStandardWeight(Float.parseFloat(standardWeight));
		boolean modifyGoalStatus = dao.modifyBaseData(Integer.parseInt(userId), registerData);
		
		
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
