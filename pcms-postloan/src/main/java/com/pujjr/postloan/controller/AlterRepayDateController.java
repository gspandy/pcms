package com.pujjr.postloan.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import com.pujjr.postloan.domain.ApplyAlterRepayDate;
import com.pujjr.postloan.domain.WaitingChargeNew;
import com.pujjr.postloan.enumeration.FeeType;
import com.pujjr.postloan.enumeration.LoanApplyTaskType;
import com.pujjr.postloan.service.IAlterRepayDateService;
import com.pujjr.postloan.service.ILoanQueryService;
import com.pujjr.postloan.vo.AlterRepayDateFeeItemVo;
import com.pujjr.postloan.vo.AlterRepayDateTaskVo;
import com.pujjr.postloan.vo.ApplyAlterRepayDateVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.NewRepayPlanVo;
import com.pujjr.postloan.vo.SettleTaskVo;
import com.pujjr.utils.Utils;

@RestController
@RequestMapping(value="/alterrepaydate")
public class AlterRepayDateController extends BaseController 
{
	@Autowired
	private IAlterRepayDateService alterRepayDateService;
	@Autowired
	private ILoanQueryService loanQueryService;
	
	@RequestMapping(value="/getAlterRepayDateFeeItem/{appId}",method=RequestMethod.GET)
	public AlterRepayDateFeeItemVo getAlterRepayDateFeeItem(@PathVariable String appId,String oldClosingDate,String newClosingDate) throws ParseException
	{
		return alterRepayDateService.getAlterRepayDateFeeItem(appId, Utils.htmlTime2Date(oldClosingDate, "yyyyMMdd"), Utils.htmlTime2Date(newClosingDate, "yyyyMMdd"));
	}
	@RequestMapping(value="/commitApplyAlterRepayDateTask/{appId}",method=RequestMethod.POST)
	public void commitApplyAlterRepayDateTask(@PathVariable String appId,@RequestBody ApplyAlterRepayDateVo vo,HttpServletRequest request) throws Exception
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		alterRepayDateService.commitApplyAlterRepayDateTask(appId, vo, account.getAccountId());
	}
	
	@RequestMapping(value="/getApplyAlterRepayDateTaskById/{id}",method=RequestMethod.GET)
	public ApplyAlterRepayDateVo getApplyAlterRepayDateTaskById(@PathVariable String id)
	{
		ApplyAlterRepayDateVo vo = new ApplyAlterRepayDateVo();
		AlterRepayDateFeeItemVo feeItemVo  = new AlterRepayDateFeeItemVo();
		ApplyAlterRepayDate po = alterRepayDateService.getApplyAlterRepayDateTaskById(id);
		feeItemVo.setAlterDay(po.getAlterDay());
		feeItemVo.setAlterInterest(po.getAlterInterest());
		List<WaitingChargeNew> newWaitingChargeList = loanQueryService.getWaitingChargeNewList(po.getId(), LoanApplyTaskType.AlterRepayDate.getName(), FeeType.Plan.getName(),false);
		List<NewRepayPlanVo> newRepayPlanList = new ArrayList<NewRepayPlanVo>();
		for(WaitingChargeNew item : newWaitingChargeList)
		{
			NewRepayPlanVo newPlanVo = new NewRepayPlanVo();
			newPlanVo.setPeriod(item.getPeriod());
			newPlanVo.setRepayCapital(item.getRepayCapital());
			newPlanVo.setRepayCnterest(item.getRepayInterest());
			newPlanVo.setRemainCapital(item.getRemainCapitao());
			newPlanVo.setRepayTotalAmount(item.getRepayCapital()+item.getRepayInterest());
			newPlanVo.setValueDate(item.getValueDate());
			newPlanVo.setClosingDate(item.getClosingDate());
			newRepayPlanList.add(newPlanVo);
		}
		feeItemVo.setNewRepayPlanList(newRepayPlanList);
		vo.setFeeItem(feeItemVo);
		vo.setApplyComment(po.getApplyComment());
		vo.setOldClosingDate(po.getOldClosingDate());
		vo.setNewClosingDate(po.getNewClosingDate());
		vo.setApplyEffectDate(po.getApplyEffectDate());
		
		return vo;
		
	}
	
	/**
	 * 功能：提交审批
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-审批结果
	 * @throws Exception 
	 * **/
	@RequestMapping(value="/commitApproveAlterRepayDateTask/{taskId}",method=RequestMethod.POST)
	public void commitApproveAlterRepayDateTask(@PathVariable String taskId,@RequestBody ApproveResultVo vo) throws Exception{
		alterRepayDateService.commitApproveAlterRepayDateTask(taskId, vo);
	}
	
	@RequestMapping(value="/commitConfirmAlterRepayDateTask/{taskId}",method=RequestMethod.POST)
	public void commitConfirmAlterRepayDateTask(@PathVariable String taskId) throws Exception
	{
		alterRepayDateService.commitConfirmAlterRepayDateTask(taskId);
	}
	
	@RequestMapping(value="/getApplyAlterRepayDateTaskList",method=RequestMethod.GET)
	public PageVo getApplyAlterRepayDateTaskList(QueryParamPageVo param,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<AlterRepayDateTaskVo> list = alterRepayDateService.getApplyAlterRepayDateTaskList(account.getAccountId(), null);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
}
