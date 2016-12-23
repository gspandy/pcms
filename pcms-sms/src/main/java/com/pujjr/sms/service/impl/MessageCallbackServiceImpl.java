package com.pujjr.sms.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.pujjr.message_api.service.IMessageCallbackService;
import com.pujjr.message_api.service.IMessageService;
import com.pujjr.sms.dao.SmsHisMapper;
import com.pujjr.sms.domain.SmsHis;

public class MessageCallbackServiceImpl implements IMessageCallbackService  {

	@Autowired
	private SmsHisMapper smsHisDao;
	@Override
	public void updateSmsSendStatus(String sendId, String status) {
		// TODO Auto-generated method stub
		SmsHis his = smsHisDao.selectByPrimaryKey(sendId);
		his.setRetTime(new Date());
		his.setSendStatus(status);
		smsHisDao.updateByPrimaryKey(his);
	}



}
