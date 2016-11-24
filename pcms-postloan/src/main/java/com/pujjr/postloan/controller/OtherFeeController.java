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
import com.pujjr.base.controller.BaseController;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.vo.PageVo;
import com.pujjr.base.vo.QueryParamPageVo;
import com.pujjr.postloan.service.IOtherFeeService;
import com.pujjr.postloan.vo.AlterRepayDateTaskVo;
import com.pujjr.postloan.vo.ApplyOtherFeeVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.OtherFeeTaskVo;

@RestController
@RequestMapping(value="/otherfee")
public class OtherFeeController extends BaseController 
{
	@Autowired
	private IOtherFeeService otherFeeService;
	
	@RequestMapping(value="/commitApplyOtherFeeTask/{appId}",method=RequestMethod.POST)
	public void commitApplyOtherFeeTask(@PathVariable String appId,@RequestBody ApplyOtherFeeVo vo,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		otherFeeService.commitApplyOtherFeeTask(appId, vo, account.getAccountId());
	}
	
	@RequestMapping(value="/getApplyOtherFeeTaskById/{id}",method=RequestMethod.GET)
	public ApplyOtherFeeVo getApplyOtherFeeTaskById(@PathVariable String id)
	{
		return otherFeeService.getApplyOtherFeeTaskById(id);
	}
	
	@RequestMapping(value="/commitApproveOtherFeeTask/{taskId}",method=RequestMethod.POST)
	public void commitApproveOtherFeeTask(@PathVariable String taskId, @RequestBody ApproveResultVo vo) throws Exception
	{
		otherFeeService.commitApproveOtherFeeTask(taskId, vo);
	}
	
	@RequestMapping(value="/getApplyOtherFeeTaskList",method=RequestMethod.GET)
	public PageVo getApplyOtherFeeTaskList(QueryParamPageVo param,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<OtherFeeTaskVo> list = otherFeeService.getApplyOtherFeeTaskList(account.getAccountId(), null);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
}
