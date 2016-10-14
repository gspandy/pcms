package com.pujjr.push.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.pujjr.push.domain.SysPush;

public interface IPushInstationService extends IPushParent{
	/**
	 * 站内推送指定用户
	 * @param sysPush 待推送用户数据
	 */
	public void instationToUser(SysPush sysPush);
	/**
	 * 站内推送多个用户
	 * @param sysPushList 待推送用户数据
	 * @param obj 参数
	 */
	public void instationToUsers(ArrayList<SysPush> sysPushList,Object obj);
	/**
	 * 站内推送所有用户
	 * @param message 待推送消息
	 * @param obj 参数
	 */
	public void instationToAllUser(TextMessage message,Object obj);
	/**
	 * 推送消息
	 * @param sysPush 待推送行记录
	 * @param session websocket session
	 */
	public void sendMessage(SysPush sysPush,WebSocketSession session);
}
