package com.pujjr.base.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.domain.Holiday;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.service.IHolidayService;
import com.pujjr.utils.Utils;

@RestController
@RequestMapping("/holiday")
public class HolidayController extends BaseController
{
	@Autowired
	private IHolidayService holidayService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Holiday> getHolidayList()
	{
		return holidayService.getHolidayList();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public void addHoliday(@RequestBody Holiday param,HttpServletRequest request) throws Exception
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		param.setId(Utils.get16UUID());
		param.setCreateId(account.getAccountId());
		param.setCreateTime(new Date());
		holidayService.addHoliday(param);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public void modifyHoliday(@RequestBody Holiday param,HttpServletRequest request)
	{
		holidayService.modifyHoliday(param);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public void deleteHoliday(@PathVariable String id)
	{
		holidayService.deleteHolidayById(id);
	}
	@RequestMapping(value="/initHoliday/{year}",method=RequestMethod.GET)
	public void initHoliday(@PathVariable String year,HttpServletRequest request) throws ParseException
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		holidayService.initHoliday(year, account.getAccountId());
	}
}
