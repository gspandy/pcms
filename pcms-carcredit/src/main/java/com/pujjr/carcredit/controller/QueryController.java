package com.pujjr.carcredit.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pujjr.base.controller.BaseController;
import com.pujjr.base.vo.PageVo;
import com.pujjr.carcredit.domain.Apply;
import com.pujjr.carcredit.po.WorkflowProcessResultPo;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.service.IQueryService;
import com.pujjr.carcredit.service.ITaskService;
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
	@RequestMapping(value="/applyList",method=RequestMethod.GET)
	public PageVo queryApplyList(QueryParamApplyVo param) throws ParseException
	{
		if(param.getApplyStartDate()!=null)
		{
			String tmp = param.getApplyStartDate().replace("Z", " UTC");//注意是空格+UTC
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
			param.setApplyStartDate(Utils.getFormatDate(format.parse(tmp), "yyyy-MM-dd"));
		}
		if(param.getApplyEndDate()!=null)
		{
			String tmp = param.getApplyStartDate().replace("Z", " UTC");//注意是空格+UTC
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
			param.setApplyEndDate(Utils.getFormatDate(format.parse(tmp), "yyyy-MM-dd"));
		}
		if(param.getApplyStartDate()!=null)
		{
			String tmp = param.getApplyStartDate().replace("Z", " UTC");//注意是空格+UTC
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
			param.setApproveStartDate(Utils.getFormatDate(format.parse(tmp), "yyyy-MM-dd"));
		}
		if(param.getApproveEndDate()!=null)
		{
			String tmp = param.getApplyStartDate().replace("Z", " UTC");//注意是空格+UTC
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
			param.setApproveEndDate(Utils.getFormatDate(format.parse(tmp), "yyyy-MM-dd"));
		}
		if(param.getLoanStartDate()!=null)
		{
			String tmp = param.getApplyStartDate().replace("Z", " UTC");//注意是空格+UTC
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
			param.setLoanStartDate(Utils.getFormatDate(format.parse(tmp), "yyyy-MM-dd"));
		}
		if(param.getLoanEndDate()!=null)
		{
			String tmp = param.getApplyStartDate().replace("Z", " UTC");//注意是空格+UTC
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
			param.setLoanEndDate(Utils.getFormatDate(format.parse(tmp), "yyyy-MM-dd"));
		}
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
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
}
