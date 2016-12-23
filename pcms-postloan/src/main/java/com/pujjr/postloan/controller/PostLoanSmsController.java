package com.pujjr.postloan.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.controller.BaseController;
import com.pujjr.postloan.service.IPostLoanSmsService;
import com.pujjr.sms.vo.SmsMessageVo;

@RestController
@RequestMapping(value="/postloansms")
public class PostLoanSmsController extends BaseController 
{
	@Autowired
	private IPostLoanSmsService postLoanSmsService;
	
	@RequestMapping(value="/genPostLoanSms/{appId}/{tplKey}",method=RequestMethod.GET)
	public SmsMessageVo genPostLoanSms(@PathVariable String tplKey, @PathVariable String appId) throws Exception
	{
		return   postLoanSmsService.genPostLoanSms(tplKey, appId);
	}
}
