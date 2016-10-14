package com.pujjr.push.aspect;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSONObject;
import com.pujjr.push.domain.SysPush;
import com.pujjr.push.enumeration.ESendFlag;
import com.pujjr.push.service.IPushService;

/**
 * 消息推送aspect 2016-10-12
 * @author pujjr
 *
 */
@Aspect
public class PushAspect {
	@Autowired
	private IPushService pushServiceImpl;
	private Logger logger = Logger.getLogger("PushAspect");
//	@After("execution(* com.pujjr.push.service.impl.PushService.sendMessage(..)) && args(sysPush,webSocketSession)")
	@After("execution(* com.pujjr.push.service.impl.PushInstationServiceImpl.sendMessage(..)) && args(sysPush,webSocketSession)")
	public void afterSend(SysPush sysPush,WebSocketSession webSocketSession){
		logger.debug("AopContext.currentProxy():"+AopContext.currentProxy());
		logger.debug("已站内推送："+JSONObject.toJSONString(sysPush));
		sysPush.setSendTime(Calendar.getInstance().getTime());
		sysPush.setSendFlag(ESendFlag.ALREADY_SEND.getCode());
		pushServiceImpl.updateSysPush(sysPush);
	}
	
	@After("execution(* com.pujjr.push.service.impl.PushMessageServiceImpl.sendMessage(..)) && args(sysPush,obj1)")
	public void afterMessage(SysPush sysPush,Object obj1){
		logger.debug("AopContext.currentProxy():"+AopContext.currentProxy());
		logger.debug("已发送短信："+JSONObject.toJSONString(sysPush));
		sysPush.setSendTime(Calendar.getInstance().getTime());
		sysPush.setSendFlag(ESendFlag.ALREADY_SEND.getCode());
		pushServiceImpl.updateSysPush(sysPush);
	}
}
