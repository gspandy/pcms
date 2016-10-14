package com.pujjr.push.service;

import java.util.ArrayList;
import java.util.Map;

import com.pujjr.push.domain.SysPush;


public interface IPushMessageService{
	/**
	 * 短信发送指定用户
	 */
	public void messageToUser(SysPush sysPush);
	/**
	 * 短信发送多个用户
	 */
	public void messageToUsers(ArrayList<SysPush> sysPushList,Object obj);
	/**
	 * 短信发送所有用户
	 */
	public void messageToAllUser(String message,Object obj);
	public void sendMessage(SysPush sysPush,Object obj);
}
