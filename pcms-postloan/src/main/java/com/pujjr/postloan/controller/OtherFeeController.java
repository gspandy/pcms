package com.pujjr.postloan.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.controller.BaseController;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.postloan.service.IOtherFeeService;
import com.pujjr.postloan.vo.ApplyOtherFeeVo;

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
}
