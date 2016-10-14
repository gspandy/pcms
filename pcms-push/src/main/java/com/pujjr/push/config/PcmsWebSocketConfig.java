package com.pujjr.push.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocket
public class PcmsWebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer{
	@Autowired
	private WebSocketHandler pcmsWebSocketHandler;
	@Autowired
	private HandshakeInterceptor pcmsHandShakeIntercepter;
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub
		System.out.println("register websocket handler");
		registry.addHandler(pcmsWebSocketHandler, "/websocket/server").addInterceptors(pcmsHandShakeIntercepter);
		registry.addHandler(pcmsWebSocketHandler, "/websocket/sockjs").addInterceptors(pcmsHandShakeIntercepter).withSockJS();
	}
//	@Bean
//	public WebSocketHandler tlmsWebSocketHandler(){
//		return new TlmsWebSocketHandler();
//	}

}
