package com.pujjr.base.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.domain.GpsLvl;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.service.IGpsService;

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
}
