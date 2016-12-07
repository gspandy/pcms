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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pujjr.assetsmanage.domain.InsuranceClaims;
import com.pujjr.assetsmanage.service.IInsuranceManageService;
import com.pujjr.assetsmanage.vo.ApplyInsuranceVo;
import com.pujjr.base.controller.BaseController;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.po.InsuranceHisPo;
import com.pujjr.utils.Utils;

@RestController
@RequestMapping(value="/insmanage")
public class InsuranceManageController extends BaseController 
{
	@Autowired
	private IInsuranceManageService insMngService;
	
	@RequestMapping(value="/getInsuranceHisList/{appId}",method=RequestMethod.GET)
	public List<HashMap<String, Object>> getInsuranceHisList(@PathVariable String appId)
	{
		return insMngService.getInsuranceHisList(appId);
	}
	
	@RequestMapping(value="/createInsuranceContinueTask/{appId}",method=RequestMethod.POST)
	public void createInsuranceContinueTask(@PathVariable String appId,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		insMngService.createInsuranceContinueTask(appId, account.getAccountId());
	}
	
	@RequestMapping(value="/addInsurance/{appId}/{signId}/{insType}",method=RequestMethod.POST)
	public void addInsurance(@PathVariable String appId,@PathVariable  String signId,@PathVariable String insType,ApplyInsuranceVo vo,@RequestParam("file") MultipartFile[] file,HttpServletRequest request) throws Exception
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		insMngService.addInsurance(appId, signId, insType, vo,file,account.getAccountId());
	}
	
	@RequestMapping(value="/commitInsuranceContinue/{taskId}",method=RequestMethod.POST)
	public void commitInsuranceContinue(@PathVariable String taskId)
	{
		insMngService.commitInsuranceContinue(taskId);
	}
	
	@RequestMapping(value="/getInsuranceHisById/{id}",method=RequestMethod.GET)
	public InsuranceHisPo getInsuranceHisById(@PathVariable String id)
	{
		return insMngService.getInsuranceHisById(id);
	}
	
	@RequestMapping(value="/addInsuranceClaims/{appId}/{insuranceId}",method=RequestMethod.POST)
	public InsuranceClaims addInsuranceClaims(@PathVariable String appId,@PathVariable String insuranceId,@RequestBody InsuranceClaims vo,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		vo.setId(Utils.get16UUID());
		vo.setAppId(appId);
		vo.setInsuranceId(insuranceId);
		vo.setCreateId(account.getAccountId());
		vo.setCreateTime(new Date());
		insMngService.addInsuranceClaims(vo);
		return vo;
	}
}
