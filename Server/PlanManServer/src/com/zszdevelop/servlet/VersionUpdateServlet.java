package com.zszdevelop.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.zszdevelop.bean.BodyData;
import com.zszdevelop.bean.UpdateInfo;
import com.zszdevelop.config.ResponseMessage;
import com.zszdevelop.config.ResultCode;
import com.zszdevelop.dao.BodyDataDao;
import com.zszdevelop.dao.VersionUpdateDao;
import com.zszdevelop.impl.BodyDataImpl;
import com.zszdevelop.impl.VersionUpdateImpl;
import com.zszdevelop.utils.OutJsonUtils;
import com.zszdevelop.utils.ServerSettingUtils;

/**
 * Servlet implementation class VersionUpdateServlet
 */
@WebServlet("/VersionUpdateServlet")
public class VersionUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public VersionUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServerSettingUtils.settingEncode(request, response);
		
		VersionUpdateDao dao = new VersionUpdateImpl();
		UpdateInfo versionUpdate = dao.getVersionUpdate();
		
			if (versionUpdate== null) {
			OutJsonUtils.outJson("", ResponseMessage.MESSAGE_OPERATE_EXCEPTION, response,ResultCode.HTTP_ERROR);
			return;
		}
		
		Gson gson = new Gson();
		String jsonData = gson.toJson(versionUpdate);
		OutJsonUtils.outJson(jsonData,ResponseMessage.MESSAGE_CUCCESS,response,ResultCode.HTTP_OK);

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
