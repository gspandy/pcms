package com.pujjr.postloan.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.controller.BaseController;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.postloan.service.IPublicRepayService;
import com.pujjr.postloan.vo.ApplyPublicRepayVo;
import com.pujjr.postloan.vo.RepayFeeItemVo;

@RestController
@RequestMapping("/publicrepay")
public class PublicRepayController extends BaseController
{
	@Autowired
	private IPublicRepayService publicRepayService;
	
	@RequestMapping(value="/getPublicRepayFeeItem/{appId}",method=RequestMethod.GET)
	public RepayFeeItemVo getPublicRepayFeeItem(@PathVariable String appId)
	{
		return publicRepayService.getPublicRepayFeeItem(appId);
	}
	
	@RequestMapping(value="/commitApplyPublicRepayTask/{appId}",method=RequestMethod.POST)
	public void commitApplyPublicRepayTask(@PathVariable String appId,@RequestBody ApplyPublicRepayVo vo,HttpServletRequest request) throws Exception
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		vo.setCreateId(account.getAccountId());
		publicRepayService.commitApplyPublicRepayTask(appId, vo);
	}
}
