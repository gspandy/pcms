package com.pujjr.sms.vo;
/**
 * 短信发送对象
 * @author dengpan
 *
 */
public class SmsMessageVo {
	
	private String mobile;
	private String message;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
