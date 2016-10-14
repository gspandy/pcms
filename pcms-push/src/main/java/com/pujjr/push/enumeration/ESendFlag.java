package com.pujjr.push.enumeration;

public enum ESendFlag {
	WILL_SEND("1","待发送"),
	ALREADY_SEND("2","已经");
	private String code;
	private String text;
	private ESendFlag(String code,String text){
		this.code = code;
		this.text = text;
	}
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
	
}
