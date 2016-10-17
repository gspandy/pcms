package com.pujjr.push.scheduler.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONObject;
import com.pujjr.push.dao.SysPushMapper;
import com.pujjr.push.domain.SysPush;
import com.pujjr.push.enumeration.EChannelFlag;
import com.pujjr.push.enumeration.ESendFlag;
import com.pujjr.push.scheduler.IPushScanScheduler;
import com.pujjr.push.service.IPushMessageService;
import com.pujjr.push.service.IPushParent;
import com.pujjr.push.service.IPushService;
@Service
@Transactional
public class PushScanSchedulerImpl implements IPushScanScheduler{
	private Logger logger = Logger.getLogger(PushScanSchedulerImpl.class);
	@Autowired
	private IPushService pushService;
	
	@Override
	public void pushScan() {
		// TODO Auto-generated method stub
//		logger.debug("扫描消息推送表sys_push");
		Object obj = AopContext.currentProxy();
//		System.out.println(obj);
		List<SysPush> sysPushList = pushService.selectWillPush();
		for(SysPush tempSysPush:sysPushList){
			String channelFlag = tempSysPush.getChannelFlag();
			String accountId = tempSysPush.getTarget();
			String message = tempSysPush.getMessage();
			Object parameterObj = tempSysPush.getParameter();
			String parameter = parameterObj == null?JSONObject.toJSONString(parameterObj):"";
			if(EChannelFlag.INSTATION.getCode().equals(channelFlag)){
				pushService.instationToUser(tempSysPush);
			}else if(EChannelFlag.MAIL.getCode().equals(channelFlag)){
				pushService.mailToUser(tempSysPush);
			}else if(EChannelFlag.MESSAGE.getCode().equals(channelFlag)){
				pushService.messageToUser(tempSysPush);
			}else if(EChannelFlag.WEIXIN.getCode().equals(channelFlag)){
				pushService.weixinToUser(tempSysPush);
			}
		}
//		logger.debug(JSONObject.toJSONString(sysPushList));
	}
}
