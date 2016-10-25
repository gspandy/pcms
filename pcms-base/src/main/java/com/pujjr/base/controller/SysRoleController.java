package com.pujjr.base.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.domain.SysRole;
import com.pujjr.base.service.ISysRoleService;
import com.pujjr.utils.Utils;

@RestController
@RequestMapping(value="/sysrole")
public class SysRoleController extends BaseController
{
	@Autowired
	private ISysRoleService SysRoleService;

	@RequestMapping(method=RequestMethod.GET)
	public List<SysRole> getAllSysRoleList()
	{
		return SysRoleService.getSysRoleList();
	}
	@RequestMapping(method=RequestMethod.POST)
	public void addSysRole(@RequestBody SysRole SysRole,HttpServletRequest request)
	{
		SysAccount sysAccount  = (SysAccount)request.getAttribute("account");
		SysRole.setId(Utils.get16UUID());
		SysRole.setCreateId(sysAccount.getAccountId());
		SysRole.setCreateTime(new Date());
		SysRoleService.addSysRole(SysRole);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public void modifySysRole(@RequestBody SysRole record,HttpServletRequest request)
	{
		SysAccount sysAccount  = (SysAccount)request.getAttribute("account");
		record.setUpdateId(sysAccount.getAccountId());
		record.setUpdateTime(new Date());
		SysRoleService.modifySysRole(record);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public void deletSysRoleById(@PathVariable String id) throws Exception
	{
		SysRoleService.deleteSysRoleById(id);
	}
	
}
