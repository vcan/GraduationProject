package com.zszdevelop.utils;

import com.google.gson.Gson;
import com.zszdevelop.bean.VerifyCodeStatus;

public class VerifyCodeUtils {
	private String appkey;
	private String phone ;
	private String zone ;
	private String code ;

	/**
	 * 
	 * @param appkey 应用KEY
	 * @param phone 电话号码 xxxxxxxxx
	 * @param zone 区号 86
	 * @param code 验证码 xx
	 */
	public VerifyCodeUtils(String phone,String code) {
		super();
		this.appkey = "f578407a2d21";
		this.phone = phone;
		this.zone = "86";
		this.code = code;
	}

	/**
	 * 服务端发起验证请求验证移动端(手机)发送的短信
	 * @return
	 * @throws Exception
	 */
	public  int go() throws Exception{
		System.out.println("go次数");
		
		String address = "https://webapi.sms.mob.com/sms/verify";
		MobClient client = null;
		try {
			client = new MobClient(address);
			client.addParam("appkey", appkey).addParam("phone", phone)
					.addParam("zone", zone).addParam("code", code);
			client.addRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			client.addRequestProperty("Accept", "application/json");
			String result = client.post();
			Gson gson = new Gson();
			VerifyCodeStatus status = gson.fromJson(result, VerifyCodeStatus.class);
			System.out.println("sta++="+result);
			return status.getStatus();
		} finally {
			client.release();
		}
	}
}
