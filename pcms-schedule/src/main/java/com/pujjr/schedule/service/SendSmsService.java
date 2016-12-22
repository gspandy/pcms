package com.pujjr.schedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.sms.domain.SmsWaitSend;
import com.pujjr.sms.service.ISmsService;

@Service
public class SendSmsService 
{
	@Autowired
	private ISmsService smsService;
	
	public void send()
	{
		List<SmsWaitSend> list = smsService.getSmsWaitSendList();
		for(SmsWaitSend item : list)
		{
			//调用短信发送接口
			smsService.saveSendedToSmsHis(item);
		}
	}
}
