package com.pujjr.base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.domain.SysJob;
import com.pujjr.base.service.ISysJobService;

@RestController
@RequestMapping(value="/sysjob")
public class SysJobController 
{
	@Autowired
	private ISysJobService sysJobService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<SysJob> getSysJobList()
	{
		return sysJobService.getSysJobList();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public void addSysJob(@RequestBody SysJob sysJob)
	{
		sysJobService.addSysJob(sysJob);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public void modifySysJob(@RequestBody SysJob sysJob)
	{
		sysJobService.modifySysJob(sysJob);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public void deleteSysJob(@PathVariable String id) throws Exception
	{
		if(id == null ||id.equals(""))
		{
			throw new Exception("ID为空,无法删除");
		}
		sysJobService.deleteSysJobById(id);
	}
}
