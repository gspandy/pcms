package com.pujjr.push.intercepter;

import java.util.Map;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

//@Configuration
@Component
//public class TlmsHandShakeIntercepter implements HandshakeInterceptor{
public class TlmsHandShakeIntercepter extends HttpSessionHandshakeInterceptor{
//	HandshakeInterceptor
//	HttpSessionHandshakeInterceptor
	@Override
	public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {
		// TODO Auto-generated method stub
		System.out.println("afterHandshake");
	}

/*	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler,
			Map<String, Object> attributes) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("beforeHandshake");
		return true;
	}*/
	
	@Override
	public boolean beforeHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		System.out.println("before Handshake");
//		ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
//		HttpSession session = servletRequest.getServletRequest().getSession(false);
//		String userName = (String) session.getAttribute("user_name");
		
		System.out.println("attributes:"+attributes);
		return super.beforeHandshake(request, response, wsHandler, attributes);
	}
}
