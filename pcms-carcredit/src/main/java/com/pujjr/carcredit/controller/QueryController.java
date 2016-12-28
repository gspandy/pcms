package com.pujjr.carcredit.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pujjr.base.controller.BaseController;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.vo.PageVo;
import com.pujjr.carcredit.domain.Apply;
import com.pujjr.carcredit.domain.FraudInnerResult;
import com.pujjr.carcredit.po.WorkflowProcessResultPo;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.service.IQueryService;
import com.pujjr.carcredit.service.ITaskService;
import com.pujjr.carcredit.vo.HisOperVo;
import com.pujjr.carcredit.vo.QueryParamApplyVo;
import com.pujjr.utils.Utils;

@RestController
@RequestMapping(value="/query")
public class QueryController extends BaseController 
{
	@Autowired
	private IQueryService queryService;
	@Autowired
	private IApplyService applyService;
	@Autowired
	private ITaskService taskService;
	
	@ResponseBody
	@RequestMapping(value="/hisOper/{appId}/{accountId}",method=RequestMethod.GET)
   	public List<HisOperVo> queryHisOper(@PathVariable String appId,@PathVariable String accountId)
   	{
		System.out.println("**************appId:"+appId);
		System.out.println("**************accountId:"+accountId);
   		return queryService.querytHisOper(appId, accountId);
   	}
	
	@RequestMapping(value="/applyList",method=RequestMethod.GET)
	public PageVo queryApplyList(QueryParamApplyVo param,HttpServletRequest request) throws ParseException
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		param.setQueryAccountId(sysAccount.getAccountId());
		List<HashMap<String,Object>> list = queryService.queryApplyList(param);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	@RequestMapping(value="/conditionLoanCommentList/{appId}",method=RequestMethod.GET)
	public List<HashMap<String,Object>> queryApplyConditionLoanCommentList(@PathVariable String appId)
	{
		return queryService.queryApplyConditionLoanCommentList(appId);
	}
	
    @RequestMapping(value="/getWorkflowProcessResult/{appId}",method=RequestMethod.GET)
	public List<WorkflowProcessResultPo> getWorkflowProcessResultByAppId(@PathVariable String appId) throws Exception
	{
		Apply apply = applyService.getApply(appId);
		return taskService.getWorkflowProcessResult(apply.getProcInstId());
	}
    @RequestMapping(value="/queryApplyRunPathNodeMap/{appId}",method=RequestMethod.GET)
   	public HashMap<String,Object> queryApplyRunPathNodeMap(@PathVariable String appId)
   	{
   		return queryService.queryApplyRunPathNodeList(appId);
   	}
    

	@RequestMapping(value="/getWorkflowProcessResultByProcInstId/{procInstId}",method=RequestMethod.GET)
	public List<WorkflowProcessResultPo> getWorkflowProcessResultByProcInstId(@PathVariable String procInstId) throws Exception
	{
		return taskService.getWorkflowProcessResult(procInstId);
	}
	
	@RequestMapping(value="/queryFraudInnerResult/{appId}",method=RequestMethod.GET)
	public List<FraudInnerResult> queryFraudInnerResult(@PathVariable String appId)
	{
		return queryService.queryFraudInnerResult(appId);
	}
}
