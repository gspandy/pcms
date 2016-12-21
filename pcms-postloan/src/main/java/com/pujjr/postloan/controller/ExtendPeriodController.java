package com.pujjr.postloan.controller;


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
import com.pujjr.postloan.domain.ApplyExtendPeriod;
import com.pujjr.postloan.service.IExtendPeriodService;
import com.pujjr.postloan.vo.AlterRepayDateTaskVo;
import com.pujjr.postloan.vo.ApplyExtendPeriodVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.ExtendPeriodFeeItemVo;
import com.pujjr.postloan.vo.ExtendPeriodTaskVo;
import com.pujjr.postloan.vo.RemissionFeeItemVo;

/**
 * @author tom
 *
 */
@RestController
@RequestMapping("/extendperiod")
public class ExtendPeriodController 
{
	@Autowired
	private IExtendPeriodService extendPeriodImpl;
	
	@RequestMapping(value="/getExtendPeriodFeeItem/{appId}",method=RequestMethod.GET)
	public ExtendPeriodFeeItemVo getExtendPeriodFeeItem(@PathVariable("appId") String appId, int extendPeriod)
	{
		return extendPeriodImpl.getExtendPeriodFeeItem(appId, extendPeriod);
	}

	@RequestMapping(value="/commitApplyExtendPeriodTask/{appId}",method=RequestMethod.POST)
	public void commitApplyExtendPeriodTask(@PathVariable String appId,@RequestBody ApplyExtendPeriodVo vo,HttpServletRequest request) throws Exception
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		extendPeriodImpl.commitApplyExtendPeriodTask(account.getAccountId(), appId, vo);
	}
	
	@RequestMapping(value="/commitApproveExtendPeriodTask/{taskId}",method=RequestMethod.POST)
	public void commitApproveExtendPeriodTask(@PathVariable("taskId") String taskId,@RequestBody ApproveResultVo vo,HttpServletRequest request) throws Exception
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		extendPeriodImpl.commitApproveExtendPeriodTask(account.getAccountId(), taskId, vo);
	}
	
	@RequestMapping(value="/commitApproveRemissionTask/{taskId}",method=RequestMethod.POST)
	public void commitApproveRemissionTask(@PathVariable("taskId") String taskId,@RequestBody ApproveResultVo vo,HttpServletRequest request) throws Exception
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		extendPeriodImpl.commitApproveRemissionTask(account.getAccountId(), taskId, vo);
	}
	
	@RequestMapping(value="/commitApplyConfirmExtendPeriodTask/{taskId}",method=RequestMethod.POST)
	public void commitApplyConfirmExtendPeriodTask(@PathVariable("taskId") String taskId,@RequestBody RemissionFeeItemVo vo,HttpServletRequest request) throws Exception
	{
		extendPeriodImpl.commitApplyConfirmExtendPeriodTask(taskId, vo);
	}
	
	@RequestMapping(value="/commitConfirmExtendPeriodTask/{taskId}",method=RequestMethod.POST)
	public void commitConfirmExtendPeriodTask(@PathVariable("taskId") String taskId,HttpServletRequest request) throws Exception
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		extendPeriodImpl.commitConfirmExtendPeriodTask(account.getAccountId(), taskId, null);
	}
	
	@RequestMapping(value="/cancelExtendPeriodTask/{taskId}/{operId}/{cancelComment}",method=RequestMethod.POST)
	public void cancelExtendPeriodTask(@PathVariable("taskId") String taskId,@PathVariable("operId") String operId,@PathVariable("cancelComment") String cancelComment)
	{
		extendPeriodImpl.cancelExtendPeriodTask(taskId, operId, cancelComment);
	}
	
	@RequestMapping(value="/getApplyExtendPeriodTaskList",method=RequestMethod.GET)
	public PageVo getApplyExtendPeriodTaskList(QueryParamPageVo param,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<ExtendPeriodTaskVo> list = extendPeriodImpl.getApplyExtendPeriodTaskList(account.getAccountId(), null);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	
	@RequestMapping(value="/getApplyExtendPeriodTaskById/{id}",method=RequestMethod.GET)
	public ApplyExtendPeriodVo getApplyExtendPeriodTaskById(@PathVariable("id") String id)
	{
		return extendPeriodImpl.getApplyExtendPeriodTaskById(id);
	}
	
	@RequestMapping(value="/modifyApplyExtendPeriodInfo",method=RequestMethod.POST)
	public void modifyApplyExtendPeriodInfo(@RequestBody ApplyExtendPeriod record)
	{
		extendPeriodImpl.modifyApplyExtendPeriodInfo(record);
	}
	
	@RequestMapping(value="/deleteApplyExtendPeriodInfoById/{id}",method=RequestMethod.POST)
	public void deleteApplyExtendPeriodInfoById(@PathVariable("id") String id)
	{
		extendPeriodImpl.deleteApplyExtendPeriodInfoById(id);
	}
}
