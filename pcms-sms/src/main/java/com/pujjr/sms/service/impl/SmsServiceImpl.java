package com.pujjr.sms.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.sms.dao.SmsHisMapper;
import com.pujjr.sms.dao.SmsTemplateMapper;
import com.pujjr.sms.dao.SmsWaitSendMapper;
import com.pujjr.sms.domain.SmsHis;
import com.pujjr.sms.domain.SmsTemplate;
import com.pujjr.sms.domain.SmsWaitSend;
import com.pujjr.sms.service.ISmsService;
import com.pujjr.utils.Utils;

@Service
@Transactional
public class SmsServiceImpl implements ISmsService {

	@Autowired
	private SmsTemplateMapper smsTemplateDao;
	@Autowired
	private SmsHisMapper smsHisDao;
	@Autowired
	private SmsWaitSendMapper smsWaitSendDao;
	
	@Override
	public List<SmsTemplate> getSmsTemplateList() {
		// TODO Auto-generated method stub
		return smsTemplateDao.selectList();
	}

	@Override
	public void addSmsTemplate(SmsTemplate record) {
		// TODO Auto-generated method stub
		smsTemplateDao.insert(record);
	}

	@Override
	public void modifySmsTemplate(SmsTemplate record) {
		// TODO Auto-generated method stub
		smsTemplateDao.updateByPrimaryKey(record);
	}

	@Override
	public List<SmsHis> getSmsHisList() {
		// TODO Auto-generated method stub
		return smsHisDao.selectList();
	}

	@Override
	public String genWelcomeNotice(String custName, String repayDay, double monthRepayAmt,String repayCardNo, String repayBankName) throws Exception {
		// TODO Auto-generated method stub
		SmsTemplate tpl = smsTemplateDao.selectByPrimaryKey("welcomeNotice");
		if(tpl == null)
		{
			throw new Exception("未定义欢迎短信提醒模板");
		}
		String tplContent = tpl.getTplContent();
		tplContent=tplContent.replaceAll("#\\{custName\\}", custName);
		tplContent=tplContent.replaceAll("#\\{repayDay\\}", repayDay);
		tplContent=tplContent.replaceAll("#\\{monthRepayAmt\\}", String.valueOf(monthRepayAmt));
		tplContent=tplContent.replaceAll("#\\{repayCardNo\\}", repayCardNo.substring(repayCardNo.length()-4));
		tplContent=tplContent.replaceAll("#\\{repayBankName\\}", repayBankName);
		return tplContent;
	}

	@Override
	public String genRepayDayNormalNotice( String custName, String repayDate,double repayAmt) throws Exception {
		// TODO Auto-generated method stub
		SmsTemplate tpl = smsTemplateDao.selectByPrimaryKey("repayDayNormalNotice");
		if(tpl == null)
		{
			throw new Exception("未定义还款日前常规提醒短信模板");
		}
		String tplContent = tpl.getTplContent();
		tplContent=tplContent.replaceAll("#\\{custName\\}", custName);
		tplContent=tplContent.replaceAll("#\\{repayDate\\}", repayDate);
		tplContent=tplContent.replaceAll("#\\{repayAmt\\}", String.valueOf(repayAmt));
		return tplContent;
	}

	@Override
	public String genRepayDayFailNotice(String custName, String repayDate,double repayAmt) throws Exception {
		// TODO Auto-generated method stub
		SmsTemplate tpl = smsTemplateDao.selectByPrimaryKey("repayDayFailNotice");
		if(tpl == null)
		{
			throw new Exception("未定义还款当天扣款失败提醒短信模板");
		}
		String tplContent = tpl.getTplContent();
		tplContent=tplContent.replaceAll("#\\{custName\\}", custName);
		tplContent=tplContent.replaceAll("#\\{repayDate\\}", repayDate);
		tplContent=tplContent.replaceAll("#\\{repayAmt\\}", String.valueOf(repayAmt));
		return tplContent;
	}

	@Override
	public void sendWelcomNotice(String appId, String sendUserId,String mobile,  String custName, String repayDay,
			double monthRepayAmt, String repayCardNo, String repayBankName) throws Exception {
		// TODO Auto-generated method stub
		String msg = this.genWelcomeNotice(custName, repayDay, monthRepayAmt, repayCardNo, repayBankName);
		SmsWaitSend waitSendPo = new SmsWaitSend();
		waitSendPo.setId(Utils.get16UUID());
		waitSendPo.setAppId(appId);
		waitSendPo.setType("welcomeNotice");
		waitSendPo.setMobile(mobile);
		waitSendPo.setContent(msg);
		waitSendPo.setCreateId(sendUserId);
		waitSendPo.setCreateTime(new Date());
		smsWaitSendDao.insert(waitSendPo);
	}

	@Override
	public void sendRepayDayNormalNotice(String appId, String sendUserId,String mobile, String custName, String repayDate,
			double repayAmt) throws Exception {
		// TODO Auto-generated method stub
		String msg = this.genRepayDayNormalNotice(custName, repayDate, repayAmt);
		SmsWaitSend waitSendPo = new SmsWaitSend();
		waitSendPo.setId(Utils.get16UUID());
		waitSendPo.setAppId(appId);
		waitSendPo.setType("repayDayNormalNotice");
		waitSendPo.setMobile(mobile);
		waitSendPo.setContent(msg);
		waitSendPo.setCreateId(sendUserId);
		waitSendPo.setCreateTime(new Date());
		smsWaitSendDao.insert(waitSendPo);
	}

	@Override
	public void sendRepayDayFailNotice(String appId, String sendUserId, String mobile, String custName, String repayDate,
			double repayAmt) throws Exception {
		// TODO Auto-generated method stub
		String msg = this.genRepayDayFailNotice(custName, repayDate, repayAmt);
		SmsWaitSend waitSendPo = new SmsWaitSend();
		waitSendPo.setId(Utils.get16UUID());
		waitSendPo.setAppId(appId);
		waitSendPo.setType("repayDayFailNotice");
		waitSendPo.setMobile(mobile);
		waitSendPo.setContent(msg);
		waitSendPo.setCreateId(sendUserId);
		waitSendPo.setCreateTime(new Date());
		smsWaitSendDao.insert(waitSendPo);
	}

}
