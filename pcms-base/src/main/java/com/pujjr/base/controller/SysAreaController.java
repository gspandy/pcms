package com.pujjr.base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.domain.SysArea;
import com.pujjr.base.service.ISysAreaService;

@RestController
@RequestMapping(value="/sysarea")
public class SysAreaController  extends BaseController
{
	@Autowired
	private ISysAreaService sysAreaService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<SysArea> getSysAreaList()
	{
		return sysAreaService.getSysAreaList();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public void addSysArea(@RequestBody SysArea sysArea)
	{
		sysAreaService.addSysArea(sysArea);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public void modifySysArea(@RequestBody SysArea sysArea)
	{
		sysAreaService.modifySysArea(sysArea);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public void deleteSysArea(@PathVariable String id) throws Exception
	{
		if(id == null ||id.equals(""))
		{
			throw new Exception("ID为空,无法删除");
		}
		sysAreaService.deleteSysAreaById(id);
	}
	
	@RequestMapping(value="/{parentId}/subsysarea",method=RequestMethod.GET)
	public List<SysArea> getSysAreaListByParentId(@PathVariable String parentId)
	{
		return sysAreaService.getSysAreaListByParentId(parentId);
	}
	
	@RequestMapping(value="/province",method=RequestMethod.GET)
	public List<SysArea> getProvinceList()
	{
		return sysAreaService.getProvinceList();
	}
	
	@RequestMapping(value="/province/{provinceId}/city",method=RequestMethod.GET)
	public List<SysArea> getCityList(@PathVariable String provinceId)
	{
		return sysAreaService.getCityByProvinceId(provinceId);
	}
	
	@RequestMapping(value="province/{provinceId}/city/{cityId}/county",method=RequestMethod.GET)
	public List<SysArea> getCountyList(@PathVariable String cityId)
	{
		return sysAreaService.getCountyByCityId(cityId);
	}
}
