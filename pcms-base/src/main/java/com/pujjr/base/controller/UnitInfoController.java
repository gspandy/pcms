package com.pujjr.base.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.domain.UnitInfo;
import com.pujjr.base.service.IUnitInfoService;
import com.pujjr.utils.Utils;

@RestController
@RequestMapping(value="/unitinfo")
public class UnitInfoController extends BaseController 
{
	@Autowired
	private IUnitInfoService unitInfoService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<HashMap<String,Object>> getUnitInfoList(@RequestParam("enabled")boolean enabled,String unitType )
	{
		return unitInfoService.getUnitInfoList(enabled,unitType);
	}
	@RequestMapping(method=RequestMethod.POST)
	public void addUnitInfo(@RequestBody UnitInfo record,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		record.setId(Utils.get16UUID());
		record.setCreateId(account.getAccountId());
		record.setCreateTime(new Date());
		unitInfoService.addUnitInfo(record);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public void modifyUnitInfo(@RequestBody UnitInfo record,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		record.setUpdateId(account.getAccountId());
		record.setUpdateTime(new Date());
		unitInfoService.modifyUnitInfo(record);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public void deleteUnitInfo(@PathVariable String id)
	{
		unitInfoService.deleteUnitInfoById(id);
	}
	@RequestMapping(value="/getUnitInfoById/{id}",method=RequestMethod.GET)
	public UnitInfo getUnitInfoById(@PathVariable String id)
	{
		return unitInfoService.getUnitInfoById(id);
	}
}
