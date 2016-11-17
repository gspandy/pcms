package com.pujjr.postloan.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
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
import com.pujjr.postloan.domain.ApplyPublicRepay;
import com.pujjr.postloan.service.IPublicRepayService;
import com.pujjr.postloan.vo.ApplyPublicRepayVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.PublicRepayTaskVo;
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
	
	@RequestMapping(value="/getApplyPublicRepayInfo/{applyId}",method=RequestMethod.GET)
	public ApplyPublicRepayVo getApplyPublicRepayInfo(@PathVariable String applyId)
	{
		ApplyPublicRepayVo vo = new ApplyPublicRepayVo();
		RepayFeeItemVo feeItemVo = new RepayFeeItemVo();
		ApplyPublicRepay po = publicRepayService.getApplyPublicRepayTaskById(applyId);
		
		feeItemVo.setRepayCapital(po.getRepayCapital());
		feeItemVo.setRepayInterest(po.getRepayInterest());
		feeItemVo.setRepayOverdueAmount(po.getRepayOverdueAmount());
		feeItemVo.setOtherAmount(po.getOtherFee());
		feeItemVo.setOtherOverdueAmount(po.getOtherOverdueAmount());
		
		vo.setFeeItem(feeItemVo);
		vo.setApplyComment(po.getApplyComment());
		vo.setChargeAmount(po.getChargeAmount());
		vo.setChargeDate(po.getChargeDate());
		
		return vo;
	}
	@RequestMapping(value="/commitApprovePublicRepayTask/{taskId}",method=RequestMethod.POST)
	public void commitApprovePublicRepayTask(@PathVariable String taskId, @RequestBody ApproveResultVo vo) throws Exception
	{
		publicRepayService.commitApprovePublicRepayTask(taskId, vo);
	}
	
	@RequestMapping(value="/getApplyPublicRepayTaskList",method=RequestMethod.GET)
	public PageVo getApplyPublicRepayTaskList(QueryParamPageVo param,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<PublicRepayTaskVo> list = publicRepayService.getApplyPublicRepayTaskList(account.getAccountId(), null);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	
	
}
