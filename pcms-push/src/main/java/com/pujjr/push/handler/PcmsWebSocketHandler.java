package com.pujjr.push.handler;

import java.util.ArrayList;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
//@Configuration
@Component
public class PcmsWebSocketHandler implements WebSocketHandler{
	public static final ArrayList<WebSocketSession> sessionList;
	static{
		sessionList = new ArrayList<WebSocketSession>();
	}

	@Override
	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus arg1) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("afterConnectionClosed");
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("afterConnectionEstablished");
		
		System.out.println(webSocketSession.getRemoteAddress());
		System.out.println(webSocketSession.getAttributes());
		webSocketSession.sendMessage(new TextMessage("服务端链接成功"));
	}

	@Override
	public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> message) throws Exception {
		// TODO Auto-generated method stub
		Object recObject = message.getPayload();
		sessionList.add(webSocketSession);
		webSocketSession.getAttributes().put("userName", recObject);
		System.out.println("webSocketSession.getHandshakeAttributes().get(\"userName\"):"+webSocketSession.getAttributes().get("userName"));
		System.out.println("接收客户端数据："+recObject);
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
