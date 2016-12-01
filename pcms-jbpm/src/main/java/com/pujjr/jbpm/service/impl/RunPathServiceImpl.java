package com.pujjr.jbpm.service.impl;

import java.util.Date;
import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.jbpm.dao.WorkflowRunPathMapper;
import com.pujjr.jbpm.domain.WorkflowRunPath;
import com.pujjr.jbpm.service.IRunPathService;

@Service
@Transactional
public class RunPathServiceImpl implements IRunPathService 
{
	@Autowired
	private WorkflowRunPathMapper workflowRunPathDao;
	@Autowired
	private TaskService actTaskService;
	/**
	 * 获取指定节点最远路径
	 * @param procInstId 流程实例ID
	 * @param actId 活动ID
	 * @return 路径节点
	 * **/
	public WorkflowRunPath getFarestRunPathByActId(String procInstId,String actId)
	{
		//获取流程运行路径树的叶子节点
		List<WorkflowRunPath> paths = workflowRunPathDao.selectAllByProcInstIdAndActId(procInstId, actId);
		if(paths.size()>0)
		{
			return paths.get(0);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 创建流程路径
	 * @param path 路径信息
	 * @return void
	 * **/
	public void createWorkflowRunPath(WorkflowRunPath path)
	{
		workflowRunPathDao.insert(path);
	}
	/**
	 * 更新流程路径
	 * @param path 路径信息
	 * @return void
	 * **/
	public void updateWorkflowRunPath(WorkflowRunPath path)
	{
		workflowRunPathDao.updateByPrimaryKey(path);
	}
	
	/**
	 * 获取流程实例历史轨迹列表
	 * @param procInstId 流程实例ID
	 * @return List<WorkflowRunPah>
	 * **/
	public List<WorkflowRunPath> getRunPathList(String procInstId)
	{
		return workflowRunPathDao.selectAllByProcInstId(procInstId);
	}
	
	/**
	 * 根据路径ID获取路径信息
	 * @param pathId 路径ID
	 * @return 路径信息
	 * **/
	public WorkflowRunPath getRunPathById(String pathId)
	{
		return workflowRunPathDao.selectByPrimaryKey(pathId);
	}

	@Override
	public void updateRunPathProcessTimeByTaskId(String taskId) {
		// TODO Auto-generated method stub
		Task task =  actTaskService.createTaskQuery().taskId(taskId).singleResult();
		WorkflowRunPath runPath = this.getFarestRunPathByActId(task.getProcessInstanceId(), task.getTaskDefinitionKey());
		runPath.setProcessTime(new Date());
		workflowRunPathDao.updateByPrimaryKey(runPath);
		
	}
}
