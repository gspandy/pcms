package com.pujjr.sms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.sms.domain.SmsHis;
import com.pujjr.sms.domain.SmsTemplate;
import com.pujjr.sms.domain.SmsWaitSend;
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
	 * 根据模板标识查询短信模板
	 * @param tplKey
	 * @return
	 */
	public SmsTemplate getTemplateByTplKey(String tplKey);
	/**
	 * 查询短信发送历史信息
	 * @return
	 */
	public List<SmsHis> getSmsHisList(); 
	/**
	 * 查询短信待发送列表
	 * @return
	 */
	public List<SmsWaitSend> getSmsWaitSendList();
	/**
	 * 通过ID删除待发送表短信
	 * @param id
	 */
	public void deleteSmsWaitSendById(String id);
	/**
	 * 保存已发送短信至历史表
	 * @param record
	 */
	public void saveSendedToSmsHis(SmsWaitSend record);
	/**
	 * 通过历史记录重新发送短信
	 * @param hisId 历史记录ID
	 * @param operId 重发操作者
	 */
	public void resend(String hisId,String operId);
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
	/**
	 * 发送短信
	 * @param appId 申请单号
	 * @param sendUserId 发送人
	 * @param mobile 手机号码
	 * @param type 类型
	 * @param message 短信内容
	 */
	public void sendMessage(String appId,String sendUserId,String mobile,String type,String message);
	
	/**
	 * 保险续保通知短信
	 * @param appId
	 * @param sendUserId
	 * @param mobile
	 * @param custName
	 * @param carNo
	 * @param endDate
	 * @throws Exception 
	 */
	public void sendInsuranceContinueNotice(String appId,String sendUserId,String mobile,String custName,String carNo,String endDate) throws Exception;
	
	
	
}
