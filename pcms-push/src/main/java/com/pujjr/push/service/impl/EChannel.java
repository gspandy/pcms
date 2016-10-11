package com.pujjr.push.service.impl;

public enum EChannel {
	WEB{public String getValue(){return "1";}},//站内推送
	MESSAGE{public String getValue(){return "2";}},//短信推送
	WEIXIN{public String getValue(){return "3";}},//微信推送
	MAIL{public String getValue(){return "4";}};//邮件推送
	public abstract String getValue();
}
