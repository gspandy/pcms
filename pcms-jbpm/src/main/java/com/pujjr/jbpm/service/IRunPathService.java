package com.pujjr.jbpm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.jbpm.domain.WorkflowRunPath;
@Service
public interface IRunPathService 
{
	/**
	 * ��ȡָ���ڵ���Զ·��
	 * @param procInstId ����ʵ��ID
	 * @param actId �ID
	 * @return ·���ڵ�
	 * **/
	public WorkflowRunPath getFarestRunPathByActId(String procInstId,String actId);
	/**
	 * ��������·��
	 * @param path ·����Ϣ
	 * @return void
	 * **/
	public void createWorkflowRunPath(WorkflowRunPath path);
	/**
	 * ��������·��
	 * @param path ·����Ϣ
	 * @return void
	 * **/
	public void updateWorkflowRunPath(WorkflowRunPath path);
	/**
	 * ��ȡ����ʵ����ʷ�켣�б�
	 * @param procInstId ����ʵ��ID
	 * @return List<WorkflowRunPah>
	 * **/
	public List<WorkflowRunPath> getRunPathList(String procInstId);
	/**
	 * ����·��ID��ȡ·����Ϣ
	 * @param pathId ·��ID
	 * @return ·����Ϣ
	 * **/
	public WorkflowRunPath getRunPathById(String pathId);
	/**����·������ִ��ʱ��
	 * @param taskId ����ID
	 * **/
	public void updateRunPathProcessTimeByTaskId(String taskId);
}
