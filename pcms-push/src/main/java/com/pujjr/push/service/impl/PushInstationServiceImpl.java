package com.pujjr.push.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.pujjr.push.domain.SysPush;
import com.pujjr.push.handler.PcmsWebSocketHandler;
import com.pujjr.push.scheduler.IPushScanScheduler;
import com.pujjr.push.service.IPushInstationService;
import com.pujjr.push.service.IPushParent;
import com.pujjr.push.service.IPushService;
@Service
public class PushInstationServiceImpl implements IPushInstationService {
	private Logger logger = Logger.getLogger(PushInstationServiceImpl.class);
	@Override
	public void instationToUser(SysPush sysPush) {
		// TODO Auto-generated method stub
		IPushInstationService pushProxy = (IPushInstationService) AopContext.currentProxy();
		ArrayList<WebSocketSession> sessionList = PcmsWebSocketHandler.sessionList;
		int connectCnt = 0;
		String accountId = sysPush.getTarget();
		String message = sysPush.getMessage();
		for(WebSocketSession webSocketSession:sessionList){
			connectCnt++;
			String targetUser = webSocketSession.getHandshakeAttributes().get("accountId").toString();
			try {
				if(webSocketSession.isOpen() && accountId.equals(targetUser)){
					pushProxy.sendMessage(sysPush, webSocketSession);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
		logger.debug("sessionList:"+sessionList+"|当前客户端链接数connectCnt："+connectCnt);
	}


	@Override
	public void instationToAllUser(TextMessage message, Object obj) {
		// TODO Auto-generated method stub

	}


	@Override
	public void instationToUsers(ArrayList<SysPush> sysPushList, Object obj) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void sendMessage(SysPush sysPush, WebSocketSession session) {
//		logger.info("sendMessage");
		// TODO Auto-generated method stub
		try {
			session.sendMessage(new TextMessage(sysPush.getMessage()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
