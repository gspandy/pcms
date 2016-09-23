package com.pujjr.jbpm.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.deploy.DeploymentCache;
import org.activiti.engine.impl.persistence.deploy.DeploymentManager;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pujjr.base.controller.BaseController;
import com.pujjr.jbpm.domain.ActGeBytearray;
import com.pujjr.jbpm.domain.WorkflowDefine;
import com.pujjr.jbpm.domain.WorkflowVersion;
import com.pujjr.jbpm.service.IConfigWorkflowService;
import com.pujjr.jbpm.service.IWorkflowModelService;
import com.pujjr.utils.Utils;

@RestController
public class ModelController extends BaseController
{
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private IWorkflowModelService modelService;
	@Autowired
	private IConfigWorkflowService configWorkflowService;
	@Autowired
	private ProcessEngineConfiguration processEngineConfiguration;

	/**
	 * ��������
	 * **/
	@RequestMapping(value = "/model/{modelId}/deploy", method = RequestMethod.PUT)
	public void deployModel(@PathVariable String modelId, @RequestBody MultiValueMap<String, String> values)
			throws JsonProcessingException, IOException, TranscoderException 
	{
		WorkflowVersion wfVersion = configWorkflowService.getWorkflowVersionByModelId(modelId);
		WorkflowDefine wfDefine = configWorkflowService.getWorkflowDefineByVersionId(wfVersion.getId());
		
		if (wfVersion.getVersionStatus().equals("INIT")) 
		{
			// ���浱ǰ����
			modelService.saveModel(modelId, values);
			//��������
			String deploymentId = modelService.deployModel(modelId);
			// ���°汾��Ϣ������״̬Ϊ�ѷ���
			ProcessDefinition procDefine = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId)
					.singleResult();
			wfVersion.setActivitiDeployId(deploymentId);
			wfVersion.setActivitiProcdefId(procDefine.getId());
			wfVersion.setVersionStatus("Deployed");
			configWorkflowService.updateWorkflowVersion(wfVersion);	
			  //�������̽ڵ���Ϣ
			configWorkflowService.createWorkflowNodes(wfVersion.getId());
		} 
		else 
		{
			//����Ϊ�°汾
			HashMap<String,String> map = modelService.deployAsNewModel(modelId, values);
			ProcessDefinition procDefine = repositoryService.createProcessDefinitionQuery().deploymentId(map.get("depolymentId"))
					.singleResult();
			//�����汾��Ϣ
			WorkflowVersion version = new WorkflowVersion();
			String versionId = Utils.get16UUID();
			version.setId(versionId);
	        version.setWorkflowDefineId(wfDefine.getId());
	        version.setActivitiModelId(map.get("modelId"));
	        version.setActivitiDeployId(procDefine.getDeploymentId());
	        version.setActivitiProcdefId(procDefine.getId());
	        version.setVersion(procDefine.getVersion());
	        version.setVersionStatus("Deployed");
	        configWorkflowService.createWorkflowVersion(version);
	        //�������̶������汾��Ϣ, ��ʱ�����£���һ���˲��ø�
	        //wfDefine.setActivateVersionId(versionId);
	        //workflowDefineService.updateWorkflowDefine(wfDefine);
	        //�������̽ڵ���Ϣ
			configWorkflowService.createWorkflowNodes(versionId);
			
		}
	}
	
	/**
	 * ���·�������
	 * **/
	@RequestMapping(value = "/model/{modelId}/updateDeploy", method = RequestMethod.PUT)
	public void updateDeployModel(@PathVariable String modelId, @RequestBody MultiValueMap<String, String> values)
			throws JsonProcessingException, IOException, TranscoderException 
	{
		WorkflowVersion wfVersion = configWorkflowService.getWorkflowVersionByModelId(modelId);
		WorkflowDefine wfDefine = configWorkflowService.getWorkflowDefineByVersionId(wfVersion.getId());
		
		//�������Ϊδ��������ֱ�ӷ�������
		if (wfVersion.getVersionStatus().equals("INIT")) 
		{
			// ���浱ǰ����
			modelService.saveModel(modelId, values);
			//��������
			String deploymentId = modelService.deployModel(modelId);
			// ���°汾��Ϣ������״̬Ϊ�ѷ���
			ProcessDefinition procDefine = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId)
					.singleResult();
			wfVersion.setActivitiDeployId(deploymentId);
			wfVersion.setActivitiProcdefId(procDefine.getId());
			wfVersion.setVersionStatus("Deployed");
			configWorkflowService.updateWorkflowVersion(wfVersion);	
			  //�������̽ڵ���Ϣ
			configWorkflowService.createWorkflowNodes(wfVersion.getId());
		}
		//��������ѷ������򱣴����̺󣬸���ģ�͵Ķ�������Ϣ�����������У�Ȼ����ջ���
		else 
		{
			// ���浱ǰ����
			modelService.saveModel(modelId, values);
			String processDeployId = wfVersion.getActivitiDeployId();
			HashMap<String,Object>  modelData = modelService.getModelByModelId(modelId);
			String editorSourceId = modelData.get("editorSourceId").toString();
			String editorSourceExtraId = modelData.get("editorSourceExtraId").toString();
			//��ȡmodel��BNPM��Դ
			ActGeBytearray source = modelService.getActGeBytearrayById(editorSourceId);
			ActGeBytearray sourceExtra = modelService.getActGeBytearrayById(editorSourceExtraId);
			
			ObjectNode modelNode = (ObjectNode) new ObjectMapper()
					.readTree(repositoryService.getModelEditorSource(modelId));
			//ת��JSONΪXML
			BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
			byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
			String bpmnString = new String(bpmnBytes,"UTF-8");
			
			//���·�����BNPM��Դ
			ActGeBytearray deployedBnpm = modelService.getBnpmByteByDeploymentId(processDeployId);
			ActGeBytearray deployedPng = modelService.getPngByteByDeploymentId(processDeployId);
			
			deployedBnpm.setBytes(bpmnString.getBytes("UTF-8"));
			modelService.updateActGeBytearray(deployedBnpm);
			deployedPng.setBytes(sourceExtra.getBytes());
			modelService.updateActGeBytearray(deployedPng);
			
			//������̶��建��
			DeploymentManager delpoymentManager = ((ProcessEngineConfigurationImpl)processEngineConfiguration).getDeploymentManager();
			DeploymentCache<ProcessDefinitionEntity> processDefinitionCache = delpoymentManager.getProcessDefinitionCache();
			processDefinitionCache.remove(wfVersion.getActivitiProcdefId());
			
			
			  //�������̽ڵ���Ϣ
			configWorkflowService.createWorkflowNodes(wfVersion.getId());
		}
	}

	
	
}
