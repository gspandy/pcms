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
import com.pujjr.base.domain.SysWorkgroup;
import com.pujjr.base.service.ISysAccountService;
import com.pujjr.base.service.ISysWorkgroupService;
import com.pujjr.base.vo.PageVo;

@RestController
@RequestMapping(value="/sysworkgroup")
public class SysWrokgroupController extends BaseController
{
	@Autowired
	private ISysWorkgroupService workgroupService;
	@Autowired
	private ISysAccountService sysAccountService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<SysWorkgroup> getSysWorkgroupList()
	{
		return workgroupService.getSysWorkgroupList();
	}
	@RequestMapping(method=RequestMethod.POST)
	public void addSysWorkgroup(@RequestBody SysWorkgroup sysWorkgroup)
	{
		workgroupService.addWorkgroup(sysWorkgroup);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public void deleteSysWorkgroup(@PathVariable String id) throws Exception
	{
		if(sysAccountService.getSysAccountListByWorkgroupId(id).size()>0)
		{
			throw new Exception("工作组下存在关联账户，移除账户后再执行删除操作");
		}
		workgroupService.deleteWorkgroupById(id);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public void modifySysWorkgroup(@RequestBody SysWorkgroup sysWorkgroup)
	{
		workgroupService.modifyWorkgroup(sysWorkgroup);
	}
	@RequestMapping(value="/{workgroupId}/accounts",method=RequestMethod.GET)
	public PageVo getSysAccountListByWorkgroupId(@PathVariable String workgroupId,String curPage,String pageSize)
	{
		PageHelper.startPage(Integer.parseInt(curPage), Integer.parseInt(pageSize),true);
		List<SysAccount> list = workgroupService.getWorkgroupSysAccounts(workgroupId);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	
	@RequestMapping(value="/{workgroupId}/accounts",method=RequestMethod.POST)
	public void addSysAccountsToWorkgroup(@PathVariable String workgroupId,@RequestBody List<SysAccount> sysAccounts)
	{
		workgroupService.batchAddSysAccountToWorkgroup(workgroupId, sysAccounts);
	}
	@RequestMapping(value="/{workgroupId}/{sysAccountId}",method=RequestMethod.DELETE)
	public void removeSysAccountFromWorkgroup(@PathVariable String workgroupId,@PathVariable String sysAccountId)
	{
		workgroupService.removeSysAccountFromWorkgroup(workgroupId, sysAccountId);;
	}
}
