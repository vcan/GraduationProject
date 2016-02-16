package com.zszdevelop.servlet;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.net.ssl.TrustManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jni.SSLContext;

import com.zszdevelop.utils.AuthUserUtils;
import com.zszdevelop.utils.ServerSettingUtils;

/**
 * Servlet implementation class CodeServlet
 */
@WebServlet("/CodeServlet")
public class CodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public CodeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServerSettingUtils.settingEncode(request, response);
//		requestCode();
	}


//	private void requestCode() {
//		 String result = requestData("https://api.sms.mob.com/sms/verify",
//				 "appkey=xxxx&phone=xxx&zone=xx&&code=xx");
//				        System.out.println(result);
//				    }
//				 
//				    /**
//				     * 发起https 请求
//				     * @param address
//				     * @param m
//				     * @return
//				     */
//				    public  static String requestData(String address ,String params){
//				 
//				            HttpURLConnection conn = null;
//				            try {
//				            // Create a trust manager that does not validate certificate chains
//				            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
//				                public X509Certificate[] getAcceptedIssuers(){return null;}
//				                public void checkClientTrusted(X509Certificate[] certs, String authType){}
//				                public void checkServerTrusted(X509Certificate[] certs, String authType){}
//				            }};
//				 
//				            // Install the all-trusting trust manager
//				            SSLContext sc = SSLContext.getInstance("TLS");
//				            sc.init(null, trustAllCerts, new SecureRandom());
//				 
//				            //ip host verify
//				            HostnameVerifier hv = new HostnameVerifier() {
//				                 public boolean verify(String urlHostName, SSLSession session) {
//				                 return urlHostName.equals(session.getPeerHost());
//				                 }
//				            };
//				 
//				            //set ip host verify
//				            HttpsURLConnection.setDefaultHostnameVerifier(hv);
//				 
//				            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//				 
//				            URL url = new URL(address);
//				            conn = (HttpURLConnection) url.openConnection();
//				            conn.setRequestMethod("POST");// POST
//				            conn.setConnectTimeout(3000);
//				            conn.setReadTimeout(3000);
//				            // set params ;post params 
//				            if (params!=null) {
//				                conn.setDoOutput(true);
//				                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
//				                out.write(params.getBytes(Charset.forName("UTF-8")));
//				                out.flush();
//				                out.close();
//				            }
//				            conn.connect();
//				            //get result 
//				            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//				                String result = parsRtn(conn.getInputStream());
//				                return result;
//				            } else {
//				                System.out.println(conn.getResponseCode() + " "+ conn.getResponseMessage());
//				            }
//				        } catch (Exception e) {
//				            e.printStackTrace();
//				        } finally {
//				            if (conn != null)
//				                conn.disconnect();
//				        }
//				        return null;
//		
//	}

}
