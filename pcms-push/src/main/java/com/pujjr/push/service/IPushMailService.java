package com.pujjr.push.service;

import java.util.ArrayList;
import java.util.Map;

import com.pujjr.push.domain.SysPush;


public interface IPushMailService extends IPushParent{
	/**
	 * 邮件发送指定用户
	 */
	public void mailToUser(SysPush sysPush);
	/**
	 * 邮件发送多个用户
	 */
	public void mailToUsers(ArrayList<SysPush> sysPushList,Object obj);
	/**
	 * 邮件发送所有用户
	 */
	public void mailToAllUser(String message,Object obj);
	public void sendMessage(SysPush sysPush,Object obj);
}
