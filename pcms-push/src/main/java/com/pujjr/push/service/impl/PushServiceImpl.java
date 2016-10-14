package com.pujjr.push.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.pujjr.push.dao.IPushDao;
import com.pujjr.push.domain.SysPush;
import com.pujjr.push.scheduler.IPushScanScheduler;
import com.pujjr.push.service.IPushInstationService;
import com.pujjr.push.service.IPushMailService;
import com.pujjr.push.service.IPushMessageService;
import com.pujjr.push.service.IPushService;
import com.pujjr.push.service.IPushWeiXinService;
@Service
@Transactional
public class PushServiceImpl implements IPushService{
	private Logger logger = Logger.getLogger(PushServiceImpl.class);
	@Autowired
	private IPushDao pushDaoImpl;
	@Autowired
	private IPushInstationService pushInstationService;
	@Autowired
	private IPushMailService pushMailService;
	@Autowired
	private IPushMessageService pushMessageService;
	@Autowired
	private IPushWeiXinService pushWeiXinService;
	@Override
	public List<SysPush> selectWillPush() {
		// TODO Auto-generated method stub
		return pushDaoImpl.selectWillPush();
	}
	
	@Override
	public int updateSysPush(SysPush sysPush) {
		// TODO Auto-generated method stub
		pushDaoImpl.updateByPrimaryKey(sysPush);
		return 0;
	}
	
	@Override
	public void messageToUser(SysPush sysPush) {
		// TODO Auto-generated method stub
		pushMessageService.messageToUser(sysPush);
	}

	@Override
	public void instationToUser(SysPush sysPush) {
		// TODO Auto-generated method stub
//		Object obj = AopContext.currentProxy();
//		System.out.println(obj);
		pushInstationService.instationToUser(sysPush);
	}

	@Override
	public void mailToUser(SysPush sysPush) {
		// TODO Auto-generated method stub
		pushMailService.mailToUser(sysPush);
	}

	@Override
	public void weixinToUser(SysPush sysPush) {
		// TODO Auto-generated method stub
		pushWeiXinService.weiXinToUser(sysPush);
	}
}
