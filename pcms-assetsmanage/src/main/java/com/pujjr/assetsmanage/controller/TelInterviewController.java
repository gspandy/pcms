package com.pujjr.assetsmanage.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.assetsmanage.domain.TelIncomeLog;
import com.pujjr.assetsmanage.domain.TelInterview;
import com.pujjr.assetsmanage.enumeration.TelIncomeProcessStatus;
import com.pujjr.assetsmanage.service.ITelInterviewService;
import com.pujjr.base.controller.BaseController;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.utils.Utils;

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
	
	@RequestMapping(value="/createTelInterviewTask/{appId}",method=RequestMethod.POST)
	public void createTelInterviewTask(@PathVariable String appId,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		telInterviewService.createTelInterviewTask(appId, account.getAccountId());
	}
	
	@RequestMapping(value="/getTelInterviewHisList/{appId}",method=RequestMethod.GET)
	public List<TelInterview> getTelInterviewHisList(@PathVariable String appId)
	{
		return telInterviewService.getTelInterviewHisList(appId);
	}
	
	@RequestMapping(value="/commmitTelInterviewTask/{taskId}",method=RequestMethod.POST)
	public void commmitTelInterviewTask(@PathVariable String taskId) throws Exception
	{
		telInterviewService.commmitTelInterviewTask(taskId);
	}
	
	@RequestMapping(value="/addTelIncomeLog/{appId}",method=RequestMethod.POST)
	public void addTelIncomeLog(@PathVariable String appId,@RequestBody TelIncomeLog vo,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		vo.setCreateId(account.getAccountId());
		vo.setId(Utils.get16UUID());
		vo.setAppId(appId);
		if(TelIncomeProcessStatus.Processed.getName().equals(vo.getProcessStatus()))
		{
			vo.setProcessId(account.getAccountId());
			vo.setProcessTime(new Date());
		}
		telInterviewService.addTelIncomeLog(vo);
	}
	@RequestMapping(value="/getTelIncomeLogList/{appId}",method=RequestMethod.GET)
	public List<HashMap<String,Object>> getTelIncomeLogList(@PathVariable String appId)
	{
		return telInterviewService.getTelIncomeLogList(appId);
	}
	
	@RequestMapping(value="/modifyTelIncomeLogInfo/{appId}",method=RequestMethod.POST)
	public void modifyTelIncomeLogInfo(@PathVariable String appId,@RequestBody TelIncomeLog vo,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		if(TelIncomeProcessStatus.Processed.getName().equals(vo.getProcessStatus()))
		{
			vo.setProcessId(account.getAccountId());
			vo.setProcessTime(new Date());
		}
		telInterviewService.modifyTelIncomeLogInfo(vo);
	}
}
