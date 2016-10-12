package com.pujjr.file.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.controller.BaseController;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.file.domain.Directory;
import com.pujjr.file.service.IDirectoryService;
import com.pujjr.utils.Utils;

@RestController
@RequestMapping(value="/directory")
public class DirectoryController  extends BaseController
{
	@Autowired
	private IDirectoryService directoryService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Directory> getDirectoryList(@RequestParam("enabled")boolean enabled)
	{
		return directoryService.getDirectoryList(enabled);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public void addDirectory(@RequestBody Directory record,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		record.setId(Utils.get16UUID());
		record.setEnabled(true);
		record.setCreateTime(new Date());
		record.setCreateId(account.getAccountId());
		directoryService.addDirectory(record);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public void modifyDirectory(@RequestBody Directory record,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		record.setUpdateTime(new Date());
		record.setUpdateId(account.getAccountId());
		directoryService.modifyDirectory(record);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public void deleteDirectory(@PathVariable String id) throws Exception
	{
		directoryService.delelteDirectoryById(id);
	}
	@RequestMapping(value="/{parentId}/subdirectory",method=RequestMethod.GET)
	public List<Directory> getSubDirectoryList(@PathVariable String parentId)
	{
		return directoryService.getSubDirectoryList(parentId);
	}
}
