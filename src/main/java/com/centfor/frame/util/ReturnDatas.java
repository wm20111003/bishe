package com.centfor.frame.util;

import java.io.Serializable;
import java.util.Map;
/**
 * 返回对象的封装
 * @author caomei
 *
 */
@SuppressWarnings("serial")
public class ReturnDatas implements Serializable{
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String WARNING = "warning";
	public static final String NOTLOGIN = "notLogin";
	public static final String ALREADYPAY ="alreadyPay";
	private String statusCode="200";
	private String status;
	private String message;
	private Object data;
    private Map map;
	private Page page;
	private Object queryBean;
	
	public ReturnDatas() {
		
	}
	
	public static ReturnDatas getSuccessReturnDatas() {
	return new ReturnDatas(ReturnDatas.SUCCESS);
	}
	public static ReturnDatas getErrorReturnDatas() {
		return new ReturnDatas(ReturnDatas.ERROR);
		}
	public static ReturnDatas getWarningReturnDatas() {
		return new ReturnDatas(ReturnDatas.WARNING);
		}
	
	
	public ReturnDatas(String status) {
		this.status = status;
	}
	
	public ReturnDatas(String status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public ReturnDatas(String status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object obj) {
		this.data = obj;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Object getQueryBean() {
		return queryBean;
	}

	public void setQueryBean(Object queryBean) {
		this.queryBean = queryBean;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
}
