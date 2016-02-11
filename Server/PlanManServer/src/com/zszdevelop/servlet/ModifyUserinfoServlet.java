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
import com.zszdevelop.bean.Userinfo;
import com.zszdevelop.config.ResponseMessage;
import com.zszdevelop.config.ResultCode;
import com.zszdevelop.dao.ModifyUserinfoDao;
import com.zszdevelop.impl.ModifyUserinfoImpl;
import com.zszdevelop.utils.AuthUserUtils;
import com.zszdevelop.utils.OutJsonUtils;
import com.zszdevelop.utils.ServerSettingUtils;

/**
 * Servlet implementation class ModifyUserinfoServlet
 */
@WebServlet("/ModifyUserinfoServlet")
public class ModifyUserinfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ModifyUserinfoServlet() {
        super();
      
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
		String birthdayStr = request.getParameter("birthday");
		String goalRecordChest = request.getParameter("goalRecordChest");
		String goalRecordLeftArm = request.getParameter("goalRecordLeftArm");
		String goalRecordLoin = request.getParameter("goalRecordLoin");
		String goalRecordRightArm = request.getParameter("goalRecordRightArm");
		String goalRecordWeight = request.getParameter("goalRecordWeight");
		String goalRecordShoulder = request.getParameter("goalRecordShoulder");
		String high = request.getParameter("high");
		String sex = request.getParameter("sex");
		String actionType = request.getParameter("actionType");
		
		// 对数据进行一个容错处理
		userId=userId==null?"0":userId;
		authToken=authToken==null?"0":authToken;
		goalRecordChest=goalRecordChest==null?"0":goalRecordChest;
		goalRecordLeftArm=goalRecordLeftArm==null?"0":goalRecordLeftArm;
		goalRecordLoin=goalRecordLoin==null?"0":goalRecordLoin;
		goalRecordRightArm=goalRecordRightArm==null?"0":goalRecordRightArm;
		goalRecordWeight=goalRecordWeight==null?"0":goalRecordWeight;
		goalRecordShoulder=goalRecordShoulder==null?"0":goalRecordShoulder;
		high=high==null?"170":high;
		sex=sex==null?"0":sex;
		birthdayStr=birthdayStr==null?"0":birthdayStr;
		actionType=actionType==null?"1.3":actionType;
		
		//把参数保存在数据对象
		Userinfo userinfo = new Userinfo();
		userinfo.setBirthday(birthdayStr);
		userinfo.setGoalRecordChest(Float.parseFloat(goalRecordChest));
		userinfo.setGoalRecordLeftArm(Float.parseFloat(goalRecordLeftArm));
		userinfo.setGoalRecordLoin(Float.parseFloat(goalRecordLoin));
		userinfo.setGoalRecordRightArm(Float.parseFloat(goalRecordRightArm));
		userinfo.setGoalRecordWeight(Float.parseFloat(goalRecordWeight));
		userinfo.setGoalRecordShoulder(Float.parseFloat(goalRecordShoulder));
		userinfo.setHigh(Float.parseFloat(high));
		userinfo.setSex(Integer.parseInt(sex));
		userinfo.setActionType(Float.parseFloat(actionType));
		BaseUser baseUser = new BaseUser();
		// 塞用户基本资料
		baseUser.setUserId(Integer.parseInt(userId));
		baseUser.setAuthToken(authToken);
		userinfo.setBaseUser( baseUser);
		
		// 修改资料
		ModifyUserinfoDao modifyUserinfoDao = new ModifyUserinfoImpl();
		 ComsumeCCInfo comsumeCCInfo = modifyUserinfoDao.modifyUser(userinfo);
		if (comsumeCCInfo == null) {
			OutJsonUtils.outJson("", ResponseMessage.MESSAGE_NO_MODIFY, response,ResultCode.HTTP_ERROR);
			return;
		}
		
		Gson gson = new Gson();
		String jsonData = gson.toJson(comsumeCCInfo);
		OutJsonUtils.outJson(jsonData,ResponseMessage.MESSAGE_CUCCESS,response,ResultCode.HTTP_OK);
		
		
	}

}
