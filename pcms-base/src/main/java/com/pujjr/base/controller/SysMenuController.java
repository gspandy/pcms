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
import com.pujjr.base.domain.SysMenu;
import com.pujjr.base.service.ISysMenuService;
import com.pujjr.utils.Utils;

@RestController
@RequestMapping(value="/sysmenu")
public class SysMenuController extends BaseController
{
	@Autowired
	private ISysMenuService sysMenuService;

	@RequestMapping(method=RequestMethod.GET)
	public List<SysMenu> getAllSysMenuList()
	{
		return sysMenuService.getAllSysMenuList();
	}
	@RequestMapping(method=RequestMethod.POST)
	public void addSysMenu(@RequestBody SysMenu sysMenu,HttpServletRequest request)
	{
		SysAccount sysAccount  = (SysAccount)request.getAttribute("account");
		sysMenu.setId(Utils.get16UUID());
		sysMenu.setCreateId(sysAccount.getAccountId());
		sysMenu.setCreateTime(new Date());
		sysMenuService.addSysMenu(sysMenu);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public void modifySysMenu(@RequestBody SysMenu record,HttpServletRequest request)
	{
		SysAccount sysAccount  = (SysAccount)request.getAttribute("account");
		record.setUpdateId(sysAccount.getAccountId());
		record.setUpdateTime(new Date());
		sysMenuService.modifySysMenu(record);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public void deletSysMenuById(@PathVariable String id) throws Exception
	{
		sysMenuService.deleteSysMenuById(id);
	}
	
	@RequestMapping(value="/{parentId}/submenu",method=RequestMethod.GET)
	public List<SysMenu> getChildSysMenuList(@PathVariable String parentId)
	{
		return sysMenuService.getChildSysMenuList(parentId);
	}
}
