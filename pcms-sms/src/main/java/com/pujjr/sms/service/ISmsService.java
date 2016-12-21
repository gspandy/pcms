package com.pujjr.sms.service;

import java.util.List;

import com.pujjr.sms.domain.SmsHis;
import com.pujjr.sms.domain.SmsTemplate;

public interface ISmsService 
{
	/**
	 * 查询短信模板列表
	 * @return
	 */
	public List<SmsTemplate> getSmsTemplateList();
	/**
	 * 新增短信模板
	 * @param record
	 */
	public void addSmsTemplate(SmsTemplate record);
	/**
	 * 修改短信模板信息
	 */
	public void modifySmsTemplate(SmsTemplate record);
	/**
	 * 查询短信发送历史信息
	 * @return
	 */
	public List<SmsHis> getSmsHisList(); 
	
}
