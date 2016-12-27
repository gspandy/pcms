package com.pujjr.jbpm.service.impl;

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
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pujjr.jbpm.dao.ActGeBytearrayMapper;
import com.pujjr.jbpm.domain.ActGeBytearray;
import com.pujjr.jbpm.service.IWorkflowModelService;

@Service
@Transactional(rollbackFor=Exception.class)
public class WorkflowModelServiceImpl implements ModelDataJsonConstants,IWorkflowModelService
{
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ActGeBytearrayMapper  actGeBytearrayDao;
	
	private Model modelData;
	
	public void saveModel(String modelId , MultiValueMap<String, String> values) throws JsonProcessingException, IOException, TranscoderException
	{
		Model model = repositoryService.getModel(modelId);
		ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
		modelJson.put(MODEL_NAME, values.getFirst("name"));
		modelJson.put(MODEL_DESCRIPTION, values.getFirst("description"));
		model.setMetaInfo(modelJson.toString());
		model.setName(values.getFirst("name"));

		repositoryService.saveModel(model);

		repositoryService.addModelEditorSource(model.getId(), values.getFirst("json_xml").getBytes("utf-8"));

		InputStream svgStream = new ByteArrayInputStream(values.getFirst("svg_xml").getBytes("utf-8"));
		TranscoderInput input = new TranscoderInput(svgStream);

		PNGTranscoder transcoder = new PNGTranscoder();
		// Setup output
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		TranscoderOutput output = new TranscoderOutput(outStream);

		// Do the transformation
		transcoder.transcode(input, output);
		final byte[] result = outStream.toByteArray();
		repositoryService.addModelEditorSourceExtra(model.getId(), result);
		outStream.close();
	}
	
	public String deployModel(String modelId) throws JsonProcessingException, IOException
	{
		ObjectNode modelNode = (ObjectNode) new ObjectMapper()
				.readTree(repositoryService.getModelEditorSource(modelId));
		modelData = repositoryService.getModel(modelId);
		String deploymentId = deployModelerModel(modelNode);
		return deploymentId;
	}
	
	public void updateDeployedModel(String modelId)
	{
		
	}
	
	private String deployModelerModel(ObjectNode modelNode) throws UnsupportedEncodingException {
		BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
		byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
		String bpmnString = new String(bpmnBytes, "UTF-8");
		String processName = modelData.getName() + ".bpmn20.xml";
		Deployment deployment = this.repositoryService.createDeployment().name(modelData.getName())
				.addString(processName, bpmnString).deploy();
		return deployment.getId();
	}
	
	public String createModel(String workflowName,String workflowKey,String author,String version) throws UnsupportedEncodingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        ObjectNode properNode = objectMapper.createObjectNode();
        properNode.put("process_id", workflowKey);
        properNode.put("process_author", author);
        properNode.put("process_version", version);
        editorNode.put("properties", properNode);
        Model modelData = repositoryService.newModel();
     
        ObjectNode modelObjectNode = objectMapper.createObjectNode();
        modelObjectNode.put("name", workflowName);
        modelObjectNode.put("revision", 1);
        modelData.setMetaInfo(modelObjectNode.toString());
        modelData.setName(workflowName);
        modelData.setKey(workflowKey);
        repositoryService.saveModel(modelData);
        repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
        
        return modelData.getId();
	}
	
	public HashMap<String,String>  deployAsNewModel(String  modelId, MultiValueMap<String, String> values) throws JsonProcessingException, IOException, TranscoderException
	{
		modelData = repositoryService.getModel(modelId);
		String workflowName = modelData.getName();
		String workflowKey = modelData.getKey();
		String newModelId = createModel(workflowName,workflowKey,"admin","1");
		saveModel(newModelId,values);
		String deploymentId = deployModel(newModelId);
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("depolymentId", deploymentId);
		map.put("modelId", newModelId);
		
		return map;
	}
	
	public HashMap<String,Object> getModelByModelId(String modelId)
	{
		return actGeBytearrayDao.selectModelByModelId(modelId);
	}
	
	public ActGeBytearray getBnpmByteByDeploymentId(String deploymentId)
	{
		return actGeBytearrayDao.selectBnpmByteByDeploymentId(deploymentId);
	}
	
	public ActGeBytearray getPngByteByDeploymentId(String deploymentId)
	{
		return actGeBytearrayDao.selectPngByteByDeploymentId(deploymentId);
	}
	
	public ActGeBytearray getActGeBytearrayById(String id)
	{
		return actGeBytearrayDao.selectByPrimaryKey(id);
	}
	
	public void updateActGeBytearray(ActGeBytearray record)
	{
		actGeBytearrayDao.updateByPrimaryKeyWithBLOBs(record);
	}
}
