package com.framework.core.exception;

/**
 * 返回信息统一
 * 
 * @author gaof
 *  
 *  1. statusCode = 601 Session过期 
 * 
 *  2. statusCode = 602  其他异常
 * 
 */
@SuppressWarnings("serial")
public class ResultMsg implements java.io.Serializable {
	//常量字段
	public static final String RESULT_MSG = "result_msg";
	
	//返回成功或失败信息
	public static final String SUCCESS =  "success";
	
	public static final String MSG = "msg";
	
	//系统级异常
	//Session过期
	public static final String _601 = "601";
	//其他系统异常
	public static final String _602 = "602";
	//错误编码
	private String statusCode;
	//错误信息
	private String message;
	//跳转地址
	private String redirectUrl;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	

}
