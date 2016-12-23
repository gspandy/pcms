package com.pujjr.schedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.message_api.service.IMessageService;
import com.pujjr.sms.domain.SmsWaitSend;
import com.pujjr.sms.service.ISmsService;

@Service
public class SendSmsService 
{
	@Autowired
	private ISmsService smsService;
	@Autowired
	private IMessageService messageService;
	public void send()
	{
		List<SmsWaitSend> list = smsService.getSmsWaitSendList();
		for(SmsWaitSend item : list)
		{
			//调用短信发送接口
			try {
				String supplyName = messageService.sendSms(item.getId(), item.getMobile(), item.getContent());
				smsService.saveSendedToSmsHis(item,supplyName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//保存至历史表
			
		}
	}
}
