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
	public void instationToUser(String accountId,String message,Object obj);
	/**
	 * 站内推送多个用户
	 * @param userMessageList 用户，待推送数据数组
	 * @param obj 参数
	 */
	public void instationToUsers(ArrayList<Map<String,TextMessage>> userMessageList,Object obj);
	/**
	 * 站内推送所有用户
	 * @param message 待推送消息
	 * @param obj 参数
	 */
	public void instationToAllUser(TextMessage message,Object obj);
//	短信发送
	/**
	 * 短信发送指定用户
	 * @param accountId 用户编号
	 * @param message 待推送消息
	 * @param obj 参数
	 */
	public void messageToUser(String accountId,String message,Object obj);
	/**
	 * 短信发送多个用户
	 * @param userMessageList 用户，待推送数据数组
	 * @param obj 参数
	 */
	public void messageToUsers(ArrayList<Map<String,TextMessage>> userMessageList,Object obj);
	/**
	 * 短信发送所有用户
	 * @param message 待推送消息
	 * @param obj 参数
	 */
	public void messageToAllUser(TextMessage message,Object obj);
//	邮件发送
	/**
	 * 邮件发送指定用户
	 * @param accountId 用户编号
	 * @param message 待推送消息
	 * @param obj 参数
	 */
	public void mailToUser(String accountId,TextMessage message,Object obj);
	/**
	 * 邮件发送多个用户
	 * @param userMessageList用户，待推送数据数组
	 * @param obj 参数
	 */
	public void mailToUsers(ArrayList<Map<String,TextMessage>> userMessageList,Object obj);
	/**
	 * 邮件发送所有用户
	 * @param message 待推送消息
	 * @param obj 参数
	 */
	public void mailToAllUser(TextMessage message,Object obj);
	
//	微信发送
	/**
	 * 微信发送指定用户
	 * @param accountId 用户编号
	 * @param message 待推送消息
	 * @param obj 参数
	 */
	public void weiXinToUser(String accountId,TextMessage message,Object obj);
	/**
	 * 微信发送多个用户
	 * @param userMessageList用户，待推送数据数组
	 * @param obj 参数
	 */
	public void weiXinToUsers(ArrayList<Map<String,TextMessage>> userMessageList,Object obj);
	/**
	 * 微信发送所有用户
	 * @param message 待推送消息
	 * @param obj 参数
	 */
	public void weiXinToAllUser(TextMessage message,Object obj);
}
