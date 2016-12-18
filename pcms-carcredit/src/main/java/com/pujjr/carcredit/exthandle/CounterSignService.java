package com.pujjr.carcredit.exthandle;

import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.carcredit.dao.TaskMultiProcessResultMapper;
import com.pujjr.carcredit.domain.TaskMultiProcessResult;
import com.pujjr.jbpm.domain.WorkflowRunPath;
import com.pujjr.jbpm.service.IConfigWorkflowService;
import com.pujjr.jbpm.service.IRunPathService;
import com.pujjr.jbpm.vo.WorkflowNodeParamVo;

@Service
public class CounterSignService 
{
	@Autowired
	private IRunPathService runPathService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IConfigWorkflowService configWorkflowService;
	@Autowired
	private TaskMultiProcessResultMapper taskMultiResultDao;
	
	public boolean isComplete(ActivityExecution execution)
	{
		//节点ID
		String activitiId = execution.getCurrentActivityId();
		//执行活动启动脚本
		String workflowVersionId = runtimeService.getVariable(execution.getId(), "workflowVersionId").toString();
		WorkflowNodeParamVo nodeParam = configWorkflowService.getWorkflowNodeParam(workflowVersionId, activitiId);
		//通过模式
		String multiPassMode = nodeParam.getMultiPassMode();
		//通过条件
		String multiPassCond = nodeParam.getMultiPassCond();
		//已完成实例数
		int nrOfCompletedInstances = Integer.valueOf(execution.getVariable("nrOfCompletedInstances").toString());
		//总的实例数
		int nrOfInstances = Integer.valueOf(execution.getVariable("nrOfInstances").toString());
		//获取多任务处理结果
		WorkflowRunPath runPath = runPathService.getFarestRunPathByActId(execution.getProcessInstanceId(), activitiId);
		//获取处理结果
		List<TaskMultiProcessResult> resultList = taskMultiResultDao.selectByRunPathId(runPath.getId());
		int passedCnt = 0 ;
		for(TaskMultiProcessResult item : resultList)
		{
			if(item.getProcessResult().equals("tjlx501"))
			{
				passedCnt++;
			}
		}
		boolean isCounterSignPassed = false;
		//按投票数来定义通过结果
		if(multiPassMode.equals("hqfs01")&&passedCnt>Integer.valueOf(multiPassCond))
		{
			isCounterSignPassed = true;
		}
		//如果按百分比投票
		if(multiPassMode.equals("hqfs02")&&Double.compare(passedCnt/nrOfInstances, Double.valueOf(multiPassCond))>0)
		{
			isCounterSignPassed = true;
		}
		//放入流程变量中
		runtimeService.setVariable(execution.getId(), "isCounterSignPassed", isCounterSignPassed);
		
		if(nrOfInstances == nrOfCompletedInstances)
		{
			//如果已经全部完成提交则强制计算通过率，对应的结果放入流程变量isCounterSignPassed
			return true;
		}
		else
		{
			if(isCounterSignPassed==true)
			{
				return true;
			}
			return false;
		}
	}
}
