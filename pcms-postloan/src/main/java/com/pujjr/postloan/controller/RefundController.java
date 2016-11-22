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
import com.pujjr.postloan.domain.ApplyRefund;
import com.pujjr.postloan.service.IRefundService;
import com.pujjr.postloan.vo.AlterRepayDateTaskVo;
import com.pujjr.postloan.vo.ApplyRefundVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.RefundTaskVo;
import com.pujjr.postloan.vo.RepayFeeItemVo;
@RestController
@RequestMapping(value="/refund")
public class RefundController extends BaseController 
{
	@Autowired
	private IRefundService refundService;
	
	@RequestMapping(value="/getRefundFeeItem/{appId}",method=RequestMethod.GET)
	public RepayFeeItemVo getRefundFeeItem(@PathVariable String appId)
	{
		return refundService.getRefundFeeItem(appId);
	}
	
	@RequestMapping(value="/commitApplyRefundTask/{appId}",method=RequestMethod.POST)
	public void commitApplyRefundTask(@PathVariable String appId, @RequestBody ApplyRefundVo vo,HttpServletRequest request) throws Exception
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		refundService.commitApplyRefundTask(appId, vo, account.getAccountId());
	}
	
	@RequestMapping(value="/getApplyRefundTaskById/{id}",method=RequestMethod.GET)
	public ApplyRefundVo getApplyRefundTaskById(@PathVariable String id)
	{
		ApplyRefundVo vo = new ApplyRefundVo();
		ApplyRefund po = refundService.getApplyRefundTaskById(id);
		vo.setApplyComment(po.getApplyComment());
		vo.setApplyDate(po.getCreateDate());
		vo.setRefundAmount(po.getRefundAmount());
		vo.setRefundDate(po.getRefundDate());
		vo.setStayAmount(po.getStayAmount());
		vo.setStayDay(po.getStayDay());
		return vo;
	}
	
	/**
	 * 功能：提交审批
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-审批结果
	 * @throws Exception 
	 * **/
	@RequestMapping(value="/commitApproveRefundTask/{taskId}",method=RequestMethod.POST)
	public void commitApproveRefundTask(@PathVariable String taskId,@RequestBody ApproveResultVo vo) throws Exception{
		refundService.commitApproveRefundTask(taskId, vo);
	}
	
	@RequestMapping(value="/commitConfirmRefundTask/{taskId}",method=RequestMethod.POST)
	public void commitConfirmRefundTask(@PathVariable String taskId) throws Exception
	{
		refundService.commitConfirmRefundTask(taskId);
	}
	
	@RequestMapping(value="/getApplyRefundTaskList",method=RequestMethod.GET)
	public PageVo getApplyRefundTaskList(QueryParamPageVo param,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<RefundTaskVo> list = refundService.getApplyRefundTaskList(account.getAccountId(), null);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
}
