package com.pujjr.push.enumeration;

public enum EChannelFlag{
	INSTATION("01","站内推送"),
	MESSAGE("02","短信推送"),
	WEIXIN("03","微信推送"),
	MAIL("04","邮件推送");
	private String code;
	private String text;
	
	public String getCode() {
		return code;
	}

//	public void setCode(String code) {
//		this.code = code;
//	}

	public String getText() {
		return text;
	}

//	public void setText(String text) {
//		this.text = text;
//	}

	private EChannelFlag(String code,String text){
		this.code = code;
		this.text = text;
	}
}
