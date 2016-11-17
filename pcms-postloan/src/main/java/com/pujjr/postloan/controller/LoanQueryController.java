package com.pujjr.postloan.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.vo.PageVo;
import com.pujjr.base.vo.QueryParamPageVo;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.postloan.service.ILoanQueryService;
import com.pujjr.postloan.vo.RepayFeeItemVo;

@RestController
@RequestMapping(value="/loanquery")
public class LoanQueryController 
{
	@Autowired
	private ILoanQueryService loanQueryService;
	@Autowired
	private IAccountingService accountingService;
	
	@RequestMapping(value="/getLoanCustList",method=RequestMethod.GET)
	public PageVo getLoanCustList(QueryParamPageVo param,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<HashMap<String,Object>> list = loanQueryService.getLoanCustList();
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	
	@RequestMapping(value="/getLoanCustApplyInfo/{appId}",method=RequestMethod.GET)
	public HashMap<String,Object> getLoanCustApplyInfo(@PathVariable String appId)
	{
		return loanQueryService.getLoanCustApplyInfo(appId);
	}
	@RequestMapping(value="/getLoanCustNeedRepayInfo/{appId}",method=RequestMethod.GET)
	public RepayFeeItemVo getLoanCustNeedRepayInfo(@PathVariable String appId)
	{	
		RepayFeeItemVo feeItemVo = accountingService.getRepayingFeeItems(appId, false, null, false);
		return feeItemVo;
	}
	
	@RequestMapping(value="/getLoanCustRepayLog/{appId}",method=RequestMethod.GET)
	public PageVo getLoanCustRepayLog(@PathVariable String appId,QueryParamPageVo param,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<HashMap<String,Object>> list = loanQueryService.getLoanCustRepayLog(appId);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	@RequestMapping(value="/getLoanCustChargeLog/{appId}",method=RequestMethod.GET)
	public PageVo getLoanCustChargeLog(@PathVariable String appId,QueryParamPageVo param,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<HashMap<String,Object>> list = loanQueryService.getLoanCustChargeLog(appId);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	
	@RequestMapping(value="/getLoanToDoTaskList",method=RequestMethod.GET)
	public PageVo getLoanToDoTaskList(QueryParamPageVo param,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<HashMap<String,Object>> list = loanQueryService.getLoanToDoTaskList(account.getAccountId());
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
}
