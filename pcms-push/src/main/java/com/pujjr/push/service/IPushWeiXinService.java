package com.pujjr.push.service;

import java.util.ArrayList;
import java.util.Map;

import com.pujjr.push.domain.SysPush;


public interface IPushWeiXinService extends IPushParent{
	/**
	 * 微信发送指定用户
	 */
	public void weiXinToUser(SysPush sysPush);
	/**
	 * 微信发送多个用户
	 */
	public void weiXinToUsers(ArrayList<SysPush> sysPushList,Object obj);
	/**
	 * 微信发送所有用户
	 */
	public void weiXinToAllUser(String message,Object obj);
	public void sendMessage(SysPush sysPush,Object obj);
}
