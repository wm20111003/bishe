package com.centfor.frame.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * message/messageResources内容读取类
 * 
 * @author zhengyongsheng
 */
public class ResourceUtils {
	private static PropertyResourceBundle properTyResourceBundle;

	public ResourceUtils() {
	}

	static {
		properTyResourceBundle = (PropertyResourceBundle) ResourceBundle
				.getBundle("message/messageResources", Locale.getDefault());
	}

	/**
	 * 根据key获得对应的value
	 * 
	 * @param strPropertyName
	 *            key
	 * @return String
	 */
	public static String getString(String strPropertyName) {
		try {
			return properTyResourceBundle.getString(strPropertyName);
		} catch (Exception e) {
			return strPropertyName;
		}
	}

	public static String getString(String strPropertyName, Object... obj) {
		String str = properTyResourceBundle.getString(strPropertyName);
		if (str == null) {
			return strPropertyName;
		}
		return MessageFormat.format(str, obj);
	}

	public static PropertyResourceBundle getBundle() {
		return properTyResourceBundle;
	}

	/*public static void main(String[] args) {
		System.out.print(Resource.getString("9iu.org"));
	}*/
}
