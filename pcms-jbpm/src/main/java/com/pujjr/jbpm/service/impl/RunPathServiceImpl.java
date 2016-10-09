package com.pujjr.jbpm.service.impl;

import java.util.Date;
import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.jbpm.dao.WorkflowRunPathMapper;
import com.pujjr.jbpm.domain.WorkflowRunPath;
import com.pujjr.jbpm.service.IRunPathService;

@Service
public class RunPathServiceImpl implements IRunPathService 
{
	@Autowired
	private WorkflowRunPathMapper workflowRunPathDao;
	@Autowired
	private TaskService actTaskService;
	/**
	 * ��ȡָ���ڵ���Զ·��
	 * @param procInstId ����ʵ��ID
	 * @param actId �ID
	 * @return ·���ڵ�
	 * **/
	public WorkflowRunPath getFarestRunPathByActId(String procInstId,String actId)
	{
		//��ȡ��������·������Ҷ�ӽڵ�
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
	 * ��������·��
	 * @param path ·����Ϣ
	 * @return void
	 * **/
	public void createWorkflowRunPath(WorkflowRunPath path)
	{
		workflowRunPathDao.insert(path);
	}
	/**
	 * ��������·��
	 * @param path ·����Ϣ
	 * @return void
	 * **/
	public void updateWorkflowRunPath(WorkflowRunPath path)
	{
		workflowRunPathDao.updateByPrimaryKey(path);
	}
	
	/**
	 * ��ȡ����ʵ����ʷ�켣�б�
	 * @param procInstId ����ʵ��ID
	 * @return List<WorkflowRunPah>
	 * **/
	public List<WorkflowRunPath> getRunPathList(String procInstId)
	{
		return workflowRunPathDao.selectAllByProcInstId(procInstId);
	}
	
	/**
	 * ����·��ID��ȡ·����Ϣ
	 * @param pathId ·��ID
	 * @return ·����Ϣ
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
