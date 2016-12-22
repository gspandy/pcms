package com.pujjr.assetsmanage.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.assetsmanage.service.IAlterCustInfoService;
import com.pujjr.assetsmanage.vo.AlterBankInfoVo;
import com.pujjr.assetsmanage.vo.AlterColesseeVo;
import com.pujjr.assetsmanage.vo.AlterLinkmanVo;
import com.pujjr.assetsmanage.vo.AlterTenantVo;
import com.pujjr.base.controller.BaseController;
import com.pujjr.base.domain.SysAccount;

@RestController
@RequestMapping(value="/altercustinfo")
public class AlterCustInfoController extends BaseController 
{
	@Autowired
	private IAlterCustInfoService alterCustInfoService;
	
	@RequestMapping(value="/alterTenantInfo",method=RequestMethod.POST)
	public void alterTenantInfo(@RequestBody AlterTenantVo vo ,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		alterCustInfoService.alterTenantInfo(vo, account.getAccountId());
	}
	
	@RequestMapping(value="/alterColesseeInfo",method=RequestMethod.POST)
	public void alterColesseeInfo(@RequestBody AlterColesseeVo vo,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		alterCustInfoService.alterColesseeInfo(vo, account.getAccountId());
	}
	
	@RequestMapping(value="/alterLinkmanInfo",method=RequestMethod.POST)
	public void alterLinkmanInfo(@RequestBody AlterLinkmanVo vo,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		alterCustInfoService.alterLinkmanInfo(vo, account.getAccountId());
	}
	
	@RequestMapping(value="/alterBankInfo",method=RequestMethod.POST)
	public void alterBankInfo(@RequestBody AlterBankInfoVo vo,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		alterCustInfoService.alterBankInfo(vo, account.getAccountId());
	}
	
	@RequestMapping(value="/getAlterBankInfoHisList/{appId}",method=RequestMethod.GET)
	public List<HashMap<String,Object>> getAlterBankInfoHisList(@PathVariable String appId)
	{
		return alterCustInfoService.getAlterBankInfoHisList(appId);
	}
}
