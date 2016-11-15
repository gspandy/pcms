package com.pujjr.postloan.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.vo.PageVo;
import com.pujjr.base.vo.QueryParamPageVo;
import com.pujjr.postloan.enumeration.ChargeMode;
import com.pujjr.postloan.po.OfferInfoPo;
import com.pujjr.postloan.schedule.ScheduleService;
import com.pujjr.postloan.service.IChargeService;

@RestController
@RequestMapping(value="/charge")
public class ChargeController 
{
	@Autowired
	private IChargeService chargeService;
	@Autowired
	private ScheduleService schedule;
	
	@RequestMapping(value="/getEnableChargeList",method=RequestMethod.GET)
	public PageVo getEnableChargeList(QueryParamPageVo param) throws ParseException
	{
		//schedule.dayJob();
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<HashMap<String,Object>> list = chargeService.getEnableChargeList();
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	@RequestMapping(value="/setChargeMode/{chargeMode}",method=RequestMethod.POST)
	public void setChargeMode(@RequestBody List<String> chargeIds,@PathVariable String chargeMode) throws Exception
	{
		if(chargeMode.equals(ChargeMode.UnionFile.getName()))
		{
			chargeService.setChargeMode(chargeIds, ChargeMode.UnionFile);
		}
		if(chargeMode.equals(ChargeMode.UnionRealTime.getName()))
		{
			chargeService.setChargeMode(chargeIds, ChargeMode.UnionRealTime);
		}
	}
	@RequestMapping(value="/confirmManualOffer/{merchantNo}",method=RequestMethod.GET)
	public void confirmManualOffer(@PathVariable String merchantNo,HttpServletRequest request) throws Exception
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		String ossKey = chargeService.confirmManualOffer(merchantNo, account.getAccountId());
	}
	
	@RequestMapping(value="/getWatingOfferChargeList/{chargeMode}",method=RequestMethod.GET)
	public PageVo getWatingOfferChargeList(QueryParamPageVo param,@PathVariable String chargeMode)
	{
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<OfferInfoPo> list = new ArrayList<OfferInfoPo>();
		if(chargeMode.equals(ChargeMode.UnionFile.getName()))
		{
			list = chargeService.getWatingOfferChargeList(ChargeMode.UnionFile);
		}
		if(chargeMode.equals(ChargeMode.UnionRealTime.getName()))
		{
			list = chargeService.getWatingOfferChargeList(ChargeMode.UnionRealTime);
		}
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
}
