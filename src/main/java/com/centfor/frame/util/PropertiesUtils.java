package com.centfor.frame.util;

import java.text.MessageFormat;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class PropertiesUtils {
	private PropertyResourceBundle properTyResourceBundle;

	public PropertiesUtils(String fileName) {
		properTyResourceBundle = (PropertyResourceBundle) ResourceBundle
				.getBundle(fileName);
	}

	/**
	 * 根据key获得对应的value
	 * 
	 * @param strPropertyName
	 *            key
	 * @return String
	 */
	public String getString(String strPropertyName) {
		try {
			return properTyResourceBundle.getString(strPropertyName);
		} catch (Exception e) {
			return strPropertyName;
		}
	}

	public String getString(String strPropertyName, Object... obj) {
		String str = properTyResourceBundle.getString(strPropertyName);
		if (str == null) {
			return strPropertyName;
		}
		return MessageFormat.format(str, obj);
	}

	public PropertyResourceBundle getBundle() {
		return properTyResourceBundle;
	}
}
