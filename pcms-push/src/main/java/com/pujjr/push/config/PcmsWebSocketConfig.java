package com.pujjr.push.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.pujjr.push.handler.PcmsWebSocketHandler;

@Configuration
@EnableWebSocket
public class PcmsWebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer{
//	@Autowired
//	private WebSocketHandler tlmsWebSocketHandler;
	@Autowired
	private HandshakeInterceptor tlmsHandShakeIntercepter;
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub
		System.out.println("register websocket handler");
		registry.addHandler(tlmsWebSocketHandler(), "/tlmsWebSocketServer.ctrl").addInterceptors(tlmsHandShakeIntercepter);
		registry.addHandler(tlmsWebSocketHandler(), "/sockjs/tlmsWebSocketServer/info").addInterceptors(tlmsHandShakeIntercepter).withSockJS();
	}
	@Bean
	public WebSocketHandler tlmsWebSocketHandler(){
		return new PcmsWebSocketHandler();
	}

}
