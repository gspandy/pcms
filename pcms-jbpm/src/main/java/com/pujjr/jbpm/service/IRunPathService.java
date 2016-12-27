package com.pujjr.jbpm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.jbpm.domain.WorkflowRunPath;
@Service
@Transactional(rollbackFor=Exception.class)
public interface IRunPathService 
{
	/**
	 * 获取指定节点最远路径
	 * @param procInstId 流程实例ID
	 * @param actId 活动ID
	 * @return 路径节点
	 * **/
	public WorkflowRunPath getFarestRunPathByActId(String procInstId,String actId);
	/**
	 * 创建流程路径
	 * @param path 路径信息
	 * @return void
	 * **/
	public void createWorkflowRunPath(WorkflowRunPath path);
	/**
	 * 更新流程路径
	 * @param path 路径信息
	 * @return void
	 * **/
	public void updateWorkflowRunPath(WorkflowRunPath path);
	/**
	 * 获取流程实例历史轨迹列表
	 * @param procInstId 流程实例ID
	 * @return List<WorkflowRunPah>
	 * **/
	public List<WorkflowRunPath> getRunPathList(String procInstId);
	/**
	 * 根据路径ID获取路径信息
	 * @param pathId 路径ID
	 * @return 路径信息
	 * **/
	public WorkflowRunPath getRunPathById(String pathId);
	/**更新路径任务执行时间
	 * @param taskId 任务ID
	 * **/
	public void updateRunPathProcessTimeByTaskId(String taskId);
}
