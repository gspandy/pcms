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
	 * ͨ�����̰汾ID��ȡ����ȫ�����ò���
	 * @param workflowVersionId ���̰汾ID
	 * @return ����ȫ�����ò���
	 * **/
	public WorkflowGlobalParamVo getWorkflowGlobalParamByVersionId(String workflowVersionId);
	/**
	 * ��������ȫ�ֲ���
	 * @param workflowVersionId ���̰汾ID
	 * @param WorkflowGlobalParamVo ȫ�ֲ���
	 * @return 
	 * **/
	public void saveWorkflowGlobalParam(String workflowVersionId,WorkflowGlobalParamVo vo);
	/**
	 * ���ݽڵ����ͻ�ȡ���̽ڵ��б�
	 * @param workflowVersionId ���̰汾ID
	 * @param nodeType �ڵ�����
	 * @return �ڵ���Ϣ�б�
	 * **/
	public List<WorkflowNodeVo> getWorkflowNodesByNodeType(String workflowVersionId,String nodeType);
	/**
	 * ��ȡ���������˹�����ڵ����Ϣ
	 * @param workflowVersionId ���̰汾ID
	 * @return �������б���Ϣ
	 * **/
	public List<WorkflowNodeFormVo> getWorkflowUserTaskNodeForms(String workflowVersionId); 
	/**
	 * �������������˹�����ڵ����Ϣ
	 * @param workflowVersionId ���̰汾ID
	 * @param List<WorkflowNodeFormVo> ������
	 * @return
	 * **/
	public void saveWorkflowUserTaskNodeForms(String workflowVersionId,List<WorkflowNodeFormVo> vos);
	/**
	 * ��ȡ����ָ���ڵ����������Ϣ
	 * @param workflowVersionId ���̰汾ID
	 * @param nodeId �ڵ�ID
	 * @return �ڵ������Ϣ
	 * **/
	
	public WorkflowNodeParamVo getWorkflowNodeParam(String workflowVersionId,String nodeId);
	/**
	 * �������̽ڵ����������Ϣ
	 * @param workflowVersionId ���̰汾ID
	 * @param WorkflowNodeParamVo �ڵ����
	 * @return
	 * **/
	public void saveWorkflowNodeParam(String workflowVersionId,WorkflowNodeParamVo vo);
	/**
	 * �������̶���ID�����ڵ���Ϣ
	 * **/
	public void createWorkflowNodes(String workflowVersionId);
	/**
	 * ��ȡָ�����̰汾��Ӧ������ͼƬ��Դ
	 * @param workflowVersionId ���̰汾ID
	 * @return ͼƬ��������Ϣ
	 * **/
	public InputStream getWorkflowImg(String workflowVersionId);
	/**
	 * ��ȡ���ز���
	 * @param workflowVersionId ���̰汾ID
	 * @param nodeId �ڵ�ID
	 * @throws Exception 
	 * **/
	public GatewayNodeVo getWorkflowGatewayNodeParam(String workflowVersionId,String nodeId) throws Exception;
	/**
	 * �������ز�������
	 * **/
	public void saveWorkflowGatewayNodeParam(String workflowVersionId,GatewayNodeVo param);
	
	/**
	 * �������̲����ض�Ӧģ��ID
	 * **/
	public void addWorkflowDefine(WorkflowDefine record) throws UnsupportedEncodingException;
	/**
	 * ������������ID��ȡ���̶����б�
	 * @param workflowTypeId ��������ID
	 * @return List<HashMap> ���̶����б�
	 * **/
	public List<HashMap> getWorkflowDefineListByTypeId(String workflowTypeId);
	/**
	 * �������̰汾ID��ȡ��Ӧ���̶��������Ϣ
	 * @param versionId �汾ID
	 * **/
	public WorkflowDefine getWorkflowDefineByVersionId(String versionId);
	/**
	 * �������̶���key��ȡ��Ӧ���̶��������Ϣ
	 * @param workflowKey ���̶���key
	 * **/
	public WorkflowDefine getWorkflowDefineByDefineKey(String workflowKey);
	/**
	 * �������̶�����Ϣ
	 * @param define ���̶���ʵ��
	 * **/
	public void updateWorkflowDefine(WorkflowDefine define);
	/**
	 * �����������汾��Ϣ
	 * @param defineId ���̶���ID
	 * @param versionId �汾ID
	 * **/
	public void setActivateVersion(String defineId,String versionId);
	/**
	 * ��ȡ�������Ͷ����б�
	 * @return ���̶����б���Ϣ
	 * **/
	public List<WorkflowType> getWorkflowTypeList();
	/**
	 * ������������
	 * @param record ���̶���ʵ��
	 * **/
	public void createWorkflowType(WorkflowType record);
	/**
	 * �����������Ͷ���
	 * **/
	public void updateWorkflowType(WorkflowType record);
	/**
	 * ͨ�����̶���ID��ȡ�汾�б�
	 * @param defineId ���̶���ID
	 * @return �汾��Ϣ�б�
	 * **/
	public List<HashMap<String,Object>> getWorkflowVersionListByDefineId(String defineId);
	/**
	 * ͨ��ģ��ID��ȡ�汾�б�
	 * @param defineId ���̶���ID
	 * @return �汾��Ϣ�б�
	 * **/
	public WorkflowVersion getWorkflowVersionByModelId(String modelId);
	/**
	 * ͨ���汾ID��ѯ�汾��Ϣ
	 * @param versionId �汾ID
	 * **/
	public WorkflowVersion getWorkflowVersionByVersionId(String versionId);
	/**
	 * ���°汾��Ϣ
	 * @param  version �汾��Ϣʵ��
	 * **/
	public void updateWorkflowVersion(WorkflowVersion version);
	/**
	 * �����汾��Ϣ
	 * @param  version �汾��Ϣʵ��
	 * **/
	public void createWorkflowVersion(WorkflowVersion version);
	/**
	 * �������̶���ID��ȡ�������汾��Ϣ
	 * **/
	public WorkflowVersion getActivateVersionByDefineId(String defineId);
	/**
	 * ��ȡ����ȫ�ֲ���
	 *@param versionId ���̰汾ID
	 *@return WorkflowGlobalParam ����ȫ�ֲ���
	 * **/
	public WorkflowGlobalParam getGlobalParamByVersionId(String versionId);
	/**
	 * ��ȡ���ؽڵ����ò���
	 * @param versionId �汾ID
	 * @param nodeId�ڵ�ID
	 * **/
	public List<WorkflowGateWayParam> getGateWayParamListByNodeId(String versionId,String nodeId);
	/**
	 * ��ȡ�ڵ����������ò���
	 * @param versionID �汾ID
	 * @return �����˲����б�
	 * **/
	public List<WorkflowNodeAssigneeVo> getWorkflowUserTaskNodeAssignee(String versionId);
	/**
	 * �������������˲���������Ϣ
	 * @param workflowVersionId ���̰汾ID
	 * @param List<WorkflowNodeAssigneeVo> �ڵ���������
	 * @return
	 * **/
	public void saveWorkflowUserTaskNodeAssignee(String workflowVersionId,List<WorkflowNodeAssigneeVo> vos);
	/**
	 * ��ѯָ�����̰汾ָ���ڵ�����������
	 * @param workflowVersionId ���̰汾ID
	 * @param nodeId �ڵ�ID
	 * @return WorkflowNodeAssignee �ڵ������˲���
	 * **/
	public WorkflowNodeAssignee getNodeAssignee(String workflowVersionId,String nodeId);
}
