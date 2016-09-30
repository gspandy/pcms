package com.pujjr.base.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.domain.SysParam;
import com.pujjr.base.service.ISysParamService;
import com.pujjr.utils.Utils;

@RestController
@RequestMapping(value="/sysparam")
public class SysParamController extends BaseController
{
	@Autowired
	private ISysParamService sysParamService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<SysParam>  getSysParamList(String paramName)
	{
		return sysParamService.getSysParamList(paramName);
	}
	@RequestMapping(method=RequestMethod.POST)
	public void addSysParam(@RequestBody SysParam record,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		record.setId(Utils.get16UUID());
		record.setCreateId(account.getAccountId());
		record.setCreateTime(new Date());
		sysParamService.addSysParam(record);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public void modifySysParam(@RequestBody SysParam record,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		record.setUpdateId(account.getAccountId());
		record.setUpdateTime(new Date());
		sysParamService.modifySysParam(record);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public void modifySysParam(@PathVariable String id,HttpServletRequest request)
	{
		sysParamService.deleteSysParam(id);
	}
}
