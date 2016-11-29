package com.pujjr.jbpm.service;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.jbpm.domain.WorkflowDefine;
import com.pujjr.jbpm.domain.WorkflowGateWayParam;
import com.pujjr.jbpm.domain.WorkflowGlobalParam;
import com.pujjr.jbpm.domain.WorkflowNodeAssignee;
import com.pujjr.jbpm.domain.WorkflowNodes;
import com.pujjr.jbpm.domain.WorkflowType;
import com.pujjr.jbpm.domain.WorkflowVersion;
import com.pujjr.jbpm.vo.GatewayNodeVo;
import com.pujjr.jbpm.vo.WorkflowGlobalParamVo;
import com.pujjr.jbpm.vo.WorkflowNodeAssigneeVo;
import com.pujjr.jbpm.vo.WorkflowNodeFormVo;
import com.pujjr.jbpm.vo.WorkflowNodeParamVo;
import com.pujjr.jbpm.vo.WorkflowNodeVo;
@Service
public interface IConfigWorkflowService 
{
	/**
	 * 通过流程版本ID获取流程全局配置参数
	 * @param workflowVersionId 流程版本ID
	 * @return 流程全局配置参数
	 * **/
	public WorkflowGlobalParamVo getWorkflowGlobalParamByVersionId(String workflowVersionId);
	/**
	 * 保存流程全局参数
	 * @param workflowVersionId 流程版本ID
	 * @param WorkflowGlobalParamVo 全局参数
	 * @return 
	 * **/
	public void saveWorkflowGlobalParam(String workflowVersionId,WorkflowGlobalParamVo vo);
	/**
	 * 根据节点类型获取流程节点列表
	 * @param workflowVersionId 流程版本ID
	 * @param nodeType 节点类型
	 * @return 节点信息列表
	 * **/
	public List<WorkflowNodeVo> getWorkflowNodesByNodeType(String workflowVersionId,String nodeType);
	/**
	 * 获取流程所有人工任务节点表单信息
	 * @param workflowVersionId 流程版本ID
	 * @return 表单参数列表信息
	 * **/
	public List<WorkflowNodeFormVo> getWorkflowUserTaskNodeForms(String workflowVersionId); 
	/**
	 * 保存流程所有人工任务节点表单信息
	 * @param workflowVersionId 流程版本ID
	 * @param List<WorkflowNodeFormVo> 表单参数
	 * @return
	 * **/
	public void saveWorkflowUserTaskNodeForms(String workflowVersionId,List<WorkflowNodeFormVo> vos);
	/**
	 * 获取流程指定节点参数配置信息
	 * @param workflowVersionId 流程版本ID
	 * @param nodeId 节点ID
	 * @return 节点参数信息
	 * **/
	
	public WorkflowNodeParamVo getWorkflowNodeParam(String workflowVersionId,String nodeId);
	/**
	 * 保存流程节点参数配置信息
	 * @param workflowVersionId 流程版本ID
	 * @param WorkflowNodeParamVo 节点参数
	 * @return
	 * **/
	public void saveWorkflowNodeParam(String workflowVersionId,WorkflowNodeParamVo vo);
	/**
	 * 根据流程定义ID创建节点信息
	 * **/
	public void createWorkflowNodes(String workflowVersionId);
	/**
	 * 获取指定流程版本对应发布的图片资源
	 * @param workflowVersionId 流程版本ID
	 * @return 图片二进制信息
	 * **/
	public InputStream getWorkflowImg(String workflowVersionId);
	/**
	 * 获取网关参数
	 * @param workflowVersionId 流程版本ID
	 * @param nodeId 节点ID
	 * @throws Exception 
	 * **/
	public GatewayNodeVo getWorkflowGatewayNodeParam(String workflowVersionId,String nodeId) throws Exception;
	/**
	 * 保存网关参数配置
	 * **/
	public void saveWorkflowGatewayNodeParam(String workflowVersionId,GatewayNodeVo param);
	
	/**
	 * 创建流程并返回对应模型ID
	 * **/
	public void addWorkflowDefine(WorkflowDefine record) throws UnsupportedEncodingException;
	/**
	 * 根据流程类型ID获取流程定义列表
	 * @param workflowTypeId 流程类型ID
	 * @return List<HashMap> 流程定义列表
	 * **/
	public List<HashMap> getWorkflowDefineListByTypeId(String workflowTypeId);
	/**
	 * 根据流程版本ID获取对应流程定义相关信息
	 * @param versionId 版本ID
	 * **/
	public WorkflowDefine getWorkflowDefineByVersionId(String versionId);
	/**
	 * 根据流程定义key获取对应流程定义相关信息
	 * @param workflowKey 流程定义key
	 * **/
	public WorkflowDefine getWorkflowDefineByDefineKey(String workflowKey);
	/**
	 * 更新流程定义信息
	 * @param define 流程定义实体
	 * **/
	public void updateWorkflowDefine(WorkflowDefine define);
	/**
	 * 设置流程主版本信息
	 * @param defineId 流程定义ID
	 * @param versionId 版本ID
	 * **/
	public void setActivateVersion(String defineId,String versionId);
	/**
	 * 获取流程类型定义列表
	 * @return 流程定义列表信息
	 * **/
	public List<WorkflowType> getWorkflowTypeList();
	/**
	 * 创建流程类型
	 * @param record 流程定义实体
	 * **/
	public void createWorkflowType(WorkflowType record);
	/**
	 * 更新流程类型定义
	 * **/
	public void updateWorkflowType(WorkflowType record);
	/**
	 * 通过流程定义ID获取版本列表
	 * @param defineId 流程定义ID
	 * @return 版本信息列表
	 * **/
	public List<HashMap<String,Object>> getWorkflowVersionListByDefineId(String defineId);
	/**
	 * 通过模型ID获取版本列表
	 * @param defineId 流程定义ID
	 * @return 版本信息列表
	 * **/
	public WorkflowVersion getWorkflowVersionByModelId(String modelId);
	/**
	 * 通过版本ID查询版本信息
	 * @param versionId 版本ID
	 * **/
	public WorkflowVersion getWorkflowVersionByVersionId(String versionId);
	/**
	 * 更新版本信息
	 * @param  version 版本信息实体
	 * **/
	public void updateWorkflowVersion(WorkflowVersion version);
	/**
	 * 创建版本信息
	 * @param  version 版本信息实体
	 * **/
	public void createWorkflowVersion(WorkflowVersion version);
	/**
	 * 根据流程定义ID获取激活主版本信息
	 * **/
	public WorkflowVersion getActivateVersionByDefineId(String defineId);
	/**
	 * 获取流程全局参数
	 *@param versionId 流程版本ID
	 *@return WorkflowGlobalParam 流程全局参数
	 * **/
	public WorkflowGlobalParam getGlobalParamByVersionId(String versionId);
	/**
	 * 获取网关节点配置参数
	 * @param versionId 版本ID
	 * @param nodeId节点ID
	 * **/
	public List<WorkflowGateWayParam> getGateWayParamListByNodeId(String versionId,String nodeId);
	/**
	 * 获取节点审批人设置参数
	 * @param versionID 版本ID
	 * @return 审批人参数列表
	 * **/
	public List<WorkflowNodeAssigneeVo> getWorkflowUserTaskNodeAssignee(String versionId);
	/**
	 * 保存流程审批人参数设置信息
	 * @param workflowVersionId 流程版本ID
	 * @param List<WorkflowNodeAssigneeVo> 节点审批参数
	 * @return
	 * **/
	public void saveWorkflowUserTaskNodeAssignee(String workflowVersionId,List<WorkflowNodeAssigneeVo> vos);
	/**
	 * 查询指定流程版本指定节点审批人设置
	 * @param workflowVersionId 流程版本ID
	 * @param nodeId 节点ID
	 * @return WorkflowNodeAssignee 节点审批人参数
	 * **/
	public WorkflowNodeAssignee getNodeAssignee(String workflowVersionId,String nodeId);
}
