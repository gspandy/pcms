package com.pujjr.base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.domain.SysDictData;
import com.pujjr.base.domain.SysDictType;
import com.pujjr.base.service.ISysDictService;
@RestController
@RequestMapping(value="/sysdict")
public class SysDictController extends BaseController
{
	@Autowired
	private ISysDictService sysDictService;
	
	@RequestMapping(value="/dicttype",method=RequestMethod.GET)
	public List<SysDictType> getSysDictTypeList()
	{
		return sysDictService.getSysDictTypeList();
	}
	@RequestMapping(value="/dicttype",method=RequestMethod.POST)
	public void addSysDictTypeService(@RequestBody SysDictType sysDictType)
	{
		sysDictService.addSysDictType(sysDictType);
	}
	@RequestMapping(value="/dicttype/{id}",method=RequestMethod.PUT)
	public void modifySysDictType(@RequestBody SysDictType sysDictType)
	{
		sysDictService.modifySysDictType(sysDictType);
	}
	@RequestMapping(value="/dicttype/{id}",method=RequestMethod.DELETE)
	public void deleteSysDictType(@PathVariable String id) throws Exception
	{
		sysDictService.deleteSysDictTypeById(id);
	}
	@RequestMapping(value="/dicttype/{id}/dictdata",method=RequestMethod.GET)
	public List<SysDictData> getDictDataListByDictTypeId(@PathVariable String id)
	{
		return sysDictService.getDictDataListByDictTypeId(id);
	}
	@RequestMapping(value="/dictdata",method=RequestMethod.POST)
	public void addSysDictData(@RequestBody SysDictData sysDictData)
	{
		sysDictService.addSysDictData(sysDictData);
	}
	@RequestMapping(value="/dictdata/{id}",method=RequestMethod.DELETE)
	public void deleteSysDictData(@PathVariable String id)
	{
		sysDictService.deleteSysDictDataById(id);
	}
	
	@RequestMapping(value="/dictdata/{id}",method=RequestMethod.PUT)
	public void modifySysDictData(@RequestBody SysDictData sysDictData)
	{
		sysDictService.modifySysDictData(sysDictData);
	}
	@RequestMapping(value="/dicttypecode/{dictTypeCode}/dictdata",method=RequestMethod.GET)
	public List<SysDictData> getDictDataListByDictTypeCode(@PathVariable String dictTypeCode)
	{
		return sysDictService.getDictDataListByDictTypeCode(dictTypeCode);
	}
}
