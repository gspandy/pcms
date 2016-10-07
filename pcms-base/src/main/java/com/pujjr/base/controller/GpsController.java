package com.pujjr.base.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.domain.BankInfo;
import com.pujjr.base.domain.GpsLvl;
import com.pujjr.base.domain.GpsSupplier;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.service.IGpsService;
import com.pujjr.utils.Utils;

@RestController
@RequestMapping(value="/gps")
public class GpsController extends BaseController 
{
	@Autowired
	private IGpsService gpsService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<GpsLvl> getGpsLvlList()
	{
		return gpsService.getAllGpsLvlList();
	}
	@RequestMapping("/enablegpslvl/{amt}")
	public List<GpsLvl> getEnableGpsLvl(@PathVariable double amt,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		return gpsService.getGpsLvlListByBranchIdAndAmt(account.getBranchId(), amt);
	}
	
	@RequestMapping(value="/gpssupplier",method=RequestMethod.GET)
	public List<GpsSupplier> getGpsSupplierList(@RequestParam("enabled")boolean enabled)
	{
		if(enabled)
		{
			return gpsService.getAllEnabledGpsSupplierList();
		}
		else
		{
			return gpsService.getAllGpsSuppilerList();
		}
	}
	@RequestMapping(value="/gpssupplier",method=RequestMethod.POST)
	public void addGpsSupplier(@RequestBody GpsSupplier gpsSupplier,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		gpsSupplier.setId(Utils.get16UUID());
		gpsSupplier.setCreateId(account.getAccountId());
		gpsSupplier.setCreateTime(new Date());
		gpsService.addGpsSupplier(gpsSupplier);
	}
	@RequestMapping(value="/gpssupplier/{id}",method=RequestMethod.PUT)
	public void modifyGpsSupplier(@RequestBody GpsSupplier gpsSupplier,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		gpsSupplier.setUpdateId(account.getAccountId());
		gpsSupplier.setUpdateTime(new Date());
		gpsService.modifyGpsSupplier(gpsSupplier);
	}
	@RequestMapping(value="/gpssupplier/{id}",method=RequestMethod.DELETE)
	public void deleteGpsSupplier(@PathVariable String id)
	{
		gpsService.deleteGpsSupplierById(id);
	}
}
