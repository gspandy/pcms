package com.pujjr.push.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSONObject;
import com.pujjr.push.handler.PcmsWebSocketHandler;
import com.pujjr.push.service.IPushHandler;
import com.pujjr.push.service.impl.PushHandlerImpl;
import com.pujjr.utils.HttpUtil;


@Controller
public class PushController {
	private Logger logger = Logger.getLogger(PushController.class);
	@Autowired
	private IPushHandler pushHandlerImpl;
	@Value("${pcms.sys_mode}")
	private String sysMode;
	@Value("${sms.message_url}")
	private String messageUrl;
	
	@ResponseBody
	@RequestMapping(value="/startPush.ctrl")
	public String startPush(){
		System.out.println("PushController->startPush");
		
		return "";
	}
	
	/**
	 * 测试：模拟调用pcms-push
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/pushHanlder.ctrl")
	public String pushHanlder(){
		JSONObject parameterJson = new JSONObject();
		parameterJson.put("url", "http://localhost:8080");
		pushHandlerImpl.instationToUser("111", "站内推送给客户", parameterJson);
		pushHandlerImpl.messageToUser("111", "短信推送客户", parameterJson);
		return "";
	}
	
	/**
	 * 测试：模拟sms接收pcms发送短信请求接口 2016-10-13
	 * @param userName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/smsReceiveTest.ctrl")
	public String smsReceiveTest(String userName){
		logger.debug("接受pcms传入待发送短信内容：smsReceiveTest userName:"+userName);
		return "";
	}
}
