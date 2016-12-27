package com.pujjr.sms.vo;

import java.util.Date;

import com.pujjr.base.vo.QueryParamPageVo;
/**
 * 短信历史记录查询参数
 * @author 150032
 *
 */
public class SmsHisQueryParamVo extends QueryParamPageVo 
{
	//发送号码
	private String mobile;
	//发送开始日期
	private Date sendStartDate;
	//发送结束日期
	private Date sendEndDate;
	//发送结果
	private String sendResult;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Date getSendStartDate() {
		return sendStartDate;
	}
	public void setSendStartDate(Date sendStartDate) {
		this.sendStartDate = sendStartDate;
	}
	public Date getSendEndDate() {
		return sendEndDate;
	}
	public void setSendEndDate(Date sendEndDate) {
		this.sendEndDate = sendEndDate;
	}
	public String getSendResult() {
		return sendResult;
	}
	public void setSendResult(String sendResult) {
		this.sendResult = sendResult;
	}
	
}
