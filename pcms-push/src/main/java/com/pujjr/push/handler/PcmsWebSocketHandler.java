package com.pujjr.push.handler;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
@Configuration
public class PcmsWebSocketHandler implements WebSocketHandler{
	private Logger logger = Logger.getLogger(PcmsWebSocketHandler.class);
	public static final ArrayList<WebSocketSession> sessionList;
	static{
		sessionList = new ArrayList<WebSocketSession>();
	}

	@Override
	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus arg1) throws Exception {
		// TODO Auto-generated method stub
		logger.debug("TlmsWebSocketHandler->afterConnectionClosed webSocketSession:"+webSocketSession);
		ArrayList<WebSocketSession> sessionList = PcmsWebSocketHandler.sessionList;
		for(int i = 0;i < sessionList.size();i++){
			WebSocketSession tempSession = sessionList.get(i);
			System.out.println("TlmsWebSocketHandler->afterConnectionClosed tempSession:"+tempSession);
			if(webSocketSession.equals(tempSession)){
				sessionList.remove(tempSession);
				System.out.println("i:"+sessionList.size());
			}	
		}
		logger.debug("TlmsWebSocketHandler->afterConnectionClosed 剩余链接数 sessionList.size():"+sessionList.size());
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
		// TODO Auto-generated method stub
		logger.debug("afterConnectionEstablished");
		webSocketSession.sendMessage(new TextMessage("connect to server success!"));
	}

	@Override
	public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> message) throws Exception {
		// TODO Auto-generated method stub
		Object recObject = message.getPayload();
		if(sessionList.size() == 0){
			if(recObject != null && !"".equals(recObject)){
				sessionList.add(webSocketSession);
				webSocketSession.getHandshakeAttributes().put("accountId", recObject);
			}
		}else{
			boolean isExist = false;
			WebSocketSession existSession = null;
			for(WebSocketSession tempSession:sessionList){
				String tempAccountId = tempSession.getHandshakeAttributes().get("accountId")+"";
				if(tempAccountId.equals(recObject)){
					isExist = true;
					existSession = tempSession;
				}
			}
			if(!isExist){
				webSocketSession.getHandshakeAttributes().put("accountId", recObject);
				sessionList.add(webSocketSession);
			}else{
				existSession.getHandshakeAttributes().put("accountId", recObject);
			}
		}
		logger.debug("接收客户端数据："+recObject+"|剩余链接数 sessionList.size():"+sessionList.size());
	}

	@Override
	public void handleTransportError(WebSocketSession arg0, Throwable arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

}
