package com.pujjr.postloan.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
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
import com.pujjr.postloan.dao.RemissionItemMapper;
import com.pujjr.postloan.domain.ApplyRemission;
import com.pujjr.postloan.domain.RemissionItem;
import com.pujjr.postloan.enumeration.LoanApplyTaskType;
import com.pujjr.postloan.service.IRemissionService;
import com.pujjr.postloan.vo.ApplyRemissionVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.RefundTaskVo;
import com.pujjr.postloan.vo.RemissionFeeItemVo;
import com.pujjr.postloan.vo.RemissionTaskVo;

@RestController
@RequestMapping(value="/remission")
public class RemissionController 
{
	@Autowired
	private IRemissionService remissionService;
	@Autowired
	private RemissionItemMapper remissionItemDao;
	
	@RequestMapping(value="/commitApplyRemissionTask/{appId}",method=RequestMethod.POST)
	public void commitApplyRemissionTask(@PathVariable String appId,@RequestBody ApplyRemissionVo vo,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		remissionService.commitApplyRemissionTask(appId, vo, account.getAccountId());
	}
	
	@RequestMapping(value="/getApplyRemissionTaskById/{id}",method=RequestMethod.GET)
	public ApplyRemissionVo getApplyRemissionTaskById(@PathVariable String id)
	{
		ApplyRemissionVo vo = new ApplyRemissionVo();
		ApplyRemission po = remissionService.getApplyRemissionTaskById(id);
		vo.setApplyComment(po.getApplyComment());
		vo.setRemissionDate(po.getRemissionDate());
		RemissionItem remissionItemPo = remissionItemDao.selectByApplyId(LoanApplyTaskType.Remission.getName(),po.getId());
		RemissionFeeItemVo remissionFeeItemVo = new RemissionFeeItemVo();
		BeanUtils.copyProperties(remissionItemPo, remissionFeeItemVo);
		vo.setRemissionFeeItemVo(remissionFeeItemVo);
		return vo;
	}
	
	@RequestMapping(value="/commitApproveRemissionTask/{taskId}",method=RequestMethod.POST)
	public void commitApproveRemissionTask(@PathVariable String taskId,@RequestBody ApproveResultVo vo) throws Exception{
		remissionService.commitApproveRemissionTask(taskId, vo);
	}
	
	@RequestMapping(value="/getApplyRemissionTaskList",method=RequestMethod.GET)
	public PageVo getApplyRemissionTaskList(QueryParamPageVo param,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<RemissionTaskVo> list = remissionService.getApplyRemissionTaskList(account.getAccountId(), null);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
}
