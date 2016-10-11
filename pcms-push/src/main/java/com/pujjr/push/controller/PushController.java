package com.pujjr.push.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

import com.pujjr.push.handler.PcmsWebSocketHandler;


@Controller
public class PushController {
	@ResponseBody
	@RequestMapping(value="/startPush.ctrl")
	public void startPush(){
		System.out.println("PushController->startPush");
		ArrayList<WebSocketSession> sessionList = PcmsWebSocketHandler.sessionList;
		int openCnt = 0;
		for(WebSocketSession webSocketSession:sessionList){
			String sendToClient = webSocketSession.getAttributes().get("userName").toString();
			try {
				if(webSocketSession.isOpen()){
					openCnt++;
					webSocketSession.sendMessage(new TextMessage(sendToClient));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		System.out.println("sessionList:"+sessionList+"|当前链接数openCnt："+openCnt);
	}
}
