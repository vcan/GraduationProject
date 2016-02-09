package com.zszdevelop.utils;

import java.util.UUID;

public class JavaUuidUtils {



	// 主函数
	public static String getJavaUuid() {

		// 创建 GUID 对象
		UUID uuid = UUID.randomUUID();
		// 得到对象产生的ID
		String a = uuid.toString();
		// 转换为大写
		// a = a.toUpperCase();
		// 替换 -
		a = a.replaceAll("-", "");

		return a;
	}
}
