package com.pujjr.push.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.web.socket.TextMessage;

public interface IPushHandler {
	/**
	 * 站内推送指定用户
	 * @param accountId 用户编号
	 * @param message 待推送消息
	 * @param obj 参数
	 */
	public void pushMessageToUser(String accountId,String message,Object obj);
	/**
	 * 站内推送多个用户
	 * @param userMessageList 用户，待推送数据数组
	 * @param obj 参数
	 */
	public void pushMessageToUsers(ArrayList<Map<String,TextMessage>> userMessageList,Object obj);
	/**
	 * 站内推送所有用户
	 * @param message 待推送消息
	 * @param obj 参数
	 */
	public void pushMessageToAllUser(TextMessage message,Object obj);
//	短信发送
	/**
	 * 短信发送指定用户
	 * @param accountId 用户编号
	 * @param message 待推送消息
	 * @param obj 参数
	 */
	public void sendMessageToUser(String accountId,TextMessage message,Object obj);
	/**
	 * 短信发送多个用户
	 * @param userMessageList 用户，待推送数据数组
	 * @param obj 参数
	 */
	public void sendMessageToUsers(ArrayList<Map<String,TextMessage>> userMessageList,Object obj);
	/**
	 * 短信发送所有用户
	 * @param message 待推送消息
	 * @param obj 参数
	 */
	public void sendMessageToAllUser(TextMessage message,Object obj);
//	邮件发送
	/**
	 * 邮件发送指定用户
	 * @param accountId 用户编号
	 * @param message 待推送消息
	 * @param obj 参数
	 */
	public void mailMessageToUser(String accountId,TextMessage message,Object obj);
	/**
	 * 邮件发送多个用户
	 * @param userMessageList用户，待推送数据数组
	 * @param obj 参数
	 */
	public void mailMessageToUsers(ArrayList<Map<String,TextMessage>> userMessageList,Object obj);
	/**
	 * 邮件发送所有用户
	 * @param message 待推送消息
	 * @param obj 参数
	 */
	public void mailMessageToAllUser(TextMessage message,Object obj);
}
