package com.pujjr.sms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.sms.domain.SmsHis;
import com.pujjr.sms.domain.SmsTemplate;
@Service
@Transactional
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
	/**
	 * 生成欢迎提醒短信
	 * @param custName 客户姓名
	 * @param repayDay 还款天数
	 * @param monthRepayAmt 月还款额
	 * @param repayCardNo 还款卡号
	 * @param repayBankName 还款银行
	 * @throws Exception 
	 */
	public String genWelcomeNotice(String custName,String repayDay,double monthRepayAmt,String repayCardNo,String repayBankName) throws Exception;
	/**
	 * 发送欢迎提醒短信
	 * @param appId 申请单号
	 * @param sendUserId 发送人
	 * @param mobile 手机号码
	 * @param custName 客户姓名
	 * @param repayDay 还款天数
	 * @param monthRepayAmt 月还款额
	 * @param repayCardNo 还款卡号
	 * @param repayBankName 还款银行
	 * @throws Exception 
	 */
	public void sendWelcomNotice(String appId,String sendUserId,String mobile,String custName,String repayDay,double monthRepayAmt,String repayCardNo,String repayBankName) throws Exception;
	/**
	 * 还款日前常规提醒短信
	 * @param custName 客户姓名
	 * @param repayDate 还款日期
	 * @param repayAmt 还款金额
	 * @throws Exception 
	 */
	public String genRepayDayNormalNotice(String custName,String repayDate,double repayAmt) throws Exception;
	/**
	 * 发送还款日前常规提醒短信
	 * @param appId 申请单号
	 * @param sendUserId 发送人
	 * @param mobile 手机号码 
	 * @param custName 客户姓名
	 * @param repayDate 还款日期
	 * @param repayAmt 还款金额
	 * @throws Exception 
	 */
	public void sendRepayDayNormalNotice(String appId,String sendUserId,String mobile,String custName,String repayDate,double repayAmt) throws Exception;
	/**
	 * 还款日当天扣款失败提醒短信
	 * @param custName 客户姓名
	 * @param repayDate 还款日期
	 * @param repayAmt 还款金额
	 * @return
	 * @throws Exception 
	 */
	public String genRepayDayFailNotice(String custName,String repayDate,double repayAmt) throws Exception;
	/**
	 * 发送还款日当天扣款失败提醒短信
	 * @param appId 申请单号
	 * @param sendUserId 发送人
	 * @param mobile 手机号码
	 * @param custName 客户姓名
	 * @param repayDate 还款日期
	 * @param repayAmt 还款金额
	 * @throws Exception 
	 */
	public void sendRepayDayFailNotice(String appId, String sendUserId,String mobile,String custName,String repayDate,double repayAmt) throws Exception;
	
	
	
	
}
