package com.pujjr.base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.service.ISysAccountService;
import com.pujjr.base.vo.PageVo;

@RestController
@RequestMapping(value="/sysaccount")
public class SysAccountController  extends BaseController
{
	@Autowired
	private ISysAccountService sysAccountService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<SysAccount> getSysAccountList()
	{
		return sysAccountService.getSysAccount();
	}
	@RequestMapping(method=RequestMethod.POST)
	public void addSysAccount(@RequestBody SysAccount SysAccount) throws Exception
	{
		sysAccountService.addSysAccount(SysAccount);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public void deleteSysAccount(@PathVariable String id)
	{
		sysAccountService.deleteSysAccountById(id);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public void modifySysAccount(@RequestBody SysAccount SysAccount)
	{
		sysAccountService.modifySysAccount(SysAccount);
	}
}
