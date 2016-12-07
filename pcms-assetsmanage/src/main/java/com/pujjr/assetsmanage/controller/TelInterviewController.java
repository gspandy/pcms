package com.pujjr.assetsmanage.controller;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.assetsmanage.domain.TelInterview;
import com.pujjr.assetsmanage.service.ITelInterviewService;
import com.pujjr.base.controller.BaseController;
import com.pujjr.base.domain.SysAccount;

@RestController
@RequestMapping(value="/telinterview")
public class TelInterviewController extends BaseController 
{
	@Autowired
	private ITelInterviewService telInterviewService;
	
	@RequestMapping(value="/addTelInterviewResult/{appId}",method=RequestMethod.POST)
	public void addTelInterviewResult(@PathVariable String appId,@RequestBody TelInterview vo,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		telInterviewService.addTelInterviewResult(appId, vo, account.getAccountId());
	}
}
