package com.pujjr.push.service.impl;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import com.pujjr.push.service.IPushHandler;
/**
 * 消息发送业务逻辑2016-10-10
 * @author pujjr
 *
 */
@Component
public class PushHandlerImpl implements IPushHandler {

	@Override
	public void pushMessageToUser(String accountId, String message, Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pushMessageToUsers(ArrayList<Map<String, TextMessage>> userMessageList, Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pushMessageToAllUser(TextMessage message, Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessageToUser(String accountId, TextMessage message, Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessageToUsers(ArrayList<Map<String, TextMessage>> userMessageList, Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessageToAllUser(TextMessage message, Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mailMessageToUser(String accountId, TextMessage message, Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mailMessageToUsers(ArrayList<Map<String, TextMessage>> userMessageList, Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mailMessageToAllUser(TextMessage message, Object obj) {
		// TODO Auto-generated method stub
		
	}

	
}
