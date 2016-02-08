package com.zszdevelop.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UuidServlet
 */
@WebServlet("/UuidServlet")
public class UuidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public UuidServlet() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("你是get了: ").append(request.getContextPath());
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//针对post请求，设置允许接收中文
        request.setCharacterEncoding("UTF-8");
		// 返回时防止编码混乱
		response.setContentType("text/html; charset=utf-8");
//		//得到响应流对象
        PrintWriter out = response.getWriter();
       
		String uuid = request.getParameter("uuid");
		String versionCode = request.getParameter("versionCode");
		System.out.println("xxxxxxxxxxx:"+uuid);
		response.getWriter().append("请求的uuid为: ").append(uuid);
		response.getWriter().append("请求的versionCode为: ").append(versionCode);
		System.out.println("xxxxxxxxxxx:"+versionCode);
		
//		response.setContentType("text/html;charset=UTF-8");
//		PrintWriter out = response.getWriter();
//
//		printPageStart(out);
//
//		Enumeration<String> en = request.getParameterNames();
//
//		while (en.hasMoreElements()) {
//
//		String paramName = (String) en.nextElement();
//		out.println(paramName + " = " + request.getParameter(paramName) + "<br/>");

//		}
//
//		printPageEnd(out);
		}
		
		
		
//	     PrintWriter writer = response.getWriter();
//	        Map<String, String[]> params = request.getParameterMap();
//	        String queryString = "";
//	        for (String key : params.keySet()) {
//	            String[] values = params.get(key);
//	            for (int i = 0; i < values.length; i++) {
//	                String value = values[i];
//	                queryString += key + "=" + value + "&";
//	            }
//	        }
//	        // 去掉最后一个空格
////	        queryString = queryString.substring(0, queryString.length() - 1);
//	        writer.println("POST " + request.getRequestURL() + " " + queryString);
	
	/** Prints out the start of the html page
	* @param out the PrintWriter object
	*/
	private void printPageStart(PrintWriter out) {

	out.println("<html>");
	out.println("<head>");
	out.println("<title>Servlet ExampleServlet</title>");
	out.println("</head>");
	out.println("<body>");

	}

	/** Prints out the end of the html page
	* @param out the PrintWriter object
	*/
	private void printPageEnd(PrintWriter out) {

	out.println("</body>");
	out.println("</html>");
	out.close();
	}


}
