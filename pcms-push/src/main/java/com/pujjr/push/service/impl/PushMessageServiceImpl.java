package com.pujjr.push.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.pujjr.push.domain.SysPush;
import com.pujjr.push.handler.PcmsWebSocketHandler;
import com.pujjr.push.service.IPushMessageService;
import com.pujjr.push.service.IPushParent;
import com.pujjr.utils.HttpUtil;

@Service
public class PushMessageServiceImpl implements IPushMessageService {
	private Logger logger = Logger.getLogger(PushMessageServiceImpl.class);
	@Value("${sms.message_url}")
	private String messageUrl;
	@Override
	public void messageToAllUser(String message, Object obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageToUser(SysPush sysPush) {
		// TODO Auto-generated method stub
		IPushMessageService pushProxy = (IPushMessageService) AopContext.currentProxy();
		ArrayList<WebSocketSession> sessionList = PcmsWebSocketHandler.sessionList;
		int connectCnt = 0;
		String email = sysPush.getTarget();
		String message = sysPush.getMessage();
		Object paraOjb = new Object();
		pushProxy.sendMessage(sysPush, message);
		logger.debug("sessionList:"+sessionList+"|当前客户端链接数connectCnt："+connectCnt);
	}

	@Override
	public void messageToUsers(ArrayList<SysPush> sysPushList, Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(SysPush sysPush, Object obj) {
		// TODO Auto-generated method stub
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("userName", obj.toString()));
		try {
			HttpUtil.request(messageUrl, paramList);
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
