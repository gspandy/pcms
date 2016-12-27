package com.pujjr.postloan.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.sms.vo.SmsMessageVo;

@Service
@Transactional(rollbackFor=Exception.class)
public interface IPostLoanSmsService 
{
	public SmsMessageVo genPostLoanSms(String tplKey,String appId) throws Exception;
}
