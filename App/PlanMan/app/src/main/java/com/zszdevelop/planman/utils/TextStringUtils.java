package com.zszdevelop.planman.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 作者：LigthWang
 * @date 创建时间：2015年8月29日 下午4:57:12
 * @version 1.0
 * @parameter 内容描述：判断文本的类型
 * @since
 * @return
 */
public class TextStringUtils {

	/** 判断字符串是否为空 */
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str) || "null".equals(str);
	}

	/** 判断字符串是否为邮箱 */
	public static boolean isEmail(String str) {
		Pattern pattern = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher mc = pattern.matcher(str);
		return mc.matches();
	}

	/** 判断是否是手机号码 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((17[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/** 判断List是否为空 */
	public static boolean listIsNullOrEmpty(Collection<?> list) {
		return list == null || list.isEmpty();
	}

	/** 判断map是否为空 */
	public static boolean mapIsNullOrEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty() || map.size() == 0;
	}

	/** 判断object是否为空 */
	public static boolean objectIsNull(Object object) {
		return object == null;
	}

	/**
	 * 将字符串的编码转换成utf—8
	 * 
	 * @param str
	 * @return
	 */
	public static String encodeString(String str) {
		if (str == null) {
			str = "";
		} else if (str.equals("") || str.length() == 0) {
			str = "";
		} else {
			try {
				str = URLEncoder.encode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return str;
	}

	public static String decodeUtf8(String str) {
		try {
			return URLDecoder.decode(str, "UTF-8");
		} catch (Exception e) {
			return str;
		}
	}
}
