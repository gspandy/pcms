package com.pujjr.jbpm.service.impl;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pujjr.jbpm.dao.WorkflowDefineMapper;
import com.pujjr.jbpm.dao.WorkflowGateWayParamMapper;
import com.pujjr.jbpm.dao.WorkflowGlobalParamMapper;
import com.pujjr.jbpm.dao.WorkflowNodeAssigneeMapper;
import com.pujjr.jbpm.dao.WorkflowNodeFormMapper;
import com.pujjr.jbpm.dao.WorkflowNodeParamMapper;
import com.pujjr.jbpm.dao.WorkflowNodesMapper;
import com.pujjr.jbpm.dao.WorkflowTypeMapper;
import com.pujjr.jbpm.dao.WorkflowVersionMapper;
import com.pujjr.jbpm.domain.WorkflowDefine;
import com.pujjr.jbpm.domain.WorkflowGateWayParam;
import com.pujjr.jbpm.domain.WorkflowGlobalParam;
import com.pujjr.jbpm.domain.WorkflowNodeAssignee;
import com.pujjr.jbpm.domain.WorkflowNodeForm;
import com.pujjr.jbpm.domain.WorkflowNodeParam;
import com.pujjr.jbpm.domain.WorkflowNodes;
import com.pujjr.jbpm.domain.WorkflowType;
import com.pujjr.jbpm.domain.WorkflowVersion;
import com.pujjr.jbpm.service.IConfigWorkflowService;
import com.pujjr.jbpm.vo.GatewayNodeVo;
import com.pujjr.jbpm.vo.OutTransNodeVo;
import com.pujjr.jbpm.vo.WorkflowGlobalParamVo;
import com.pujjr.jbpm.vo.WorkflowNodeAssigneeVo;
import com.pujjr.jbpm.vo.WorkflowNodeFormVo;
import com.pujjr.jbpm.vo.WorkflowNodeParamVo;
import com.pujjr.jbpm.vo.WorkflowNodeVo;
import com.pujjr.utils.Utils;
@Service
public class ConfigWorkflowServiceImpl implements IConfigWorkflowService {

	@Autowired
	private WorkflowGlobalParamMapper workflowGlobalParamDao;
	@Autowired
	private WorkflowNodesMapper workflowNodesDao;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private WorkflowVersionMapper workflowVersionDao;
	@Autowired
	private WorkflowNodeFormMapper workflowNodeFormDao;
	@Autowired
	private WorkflowNodeParamMapper workflowNodeParamDao;
	@Autowired
	private WorkflowGateWayParamMapper workflowGatewayParamDao;
	@Autowired
	private WorkflowDefineMapper workflowDefineDao;
	@Autowired
	private WorkflowTypeMapper workflowTypeDao;
	@Autowired
	private WorkflowGateWayParamMapper gatewayParamDao;
	@Autowired
	private WorkflowNodeAssigneeMapper workflowNodeAssigneeDao;
	
	 
	public WorkflowGlobalParamVo getWorkflowGlobalParamByVersionId(String workflowVersionId) {
		// TODO Auto-generated method stub
		WorkflowGlobalParam item = workflowGlobalParamDao.selectByWorkflowVersionId(workflowVersionId);
		WorkflowGlobalParamVo vo = new WorkflowGlobalParamVo();
		if(item!=null)
			BeanUtils.copyProperties(item, vo);
		return vo;
	}

	public void saveWorkflowGlobalParam(String workflowVersionId,WorkflowGlobalParamVo vo)
	{
		workflowGlobalParamDao.deleteByWorkflowVersionId(workflowVersionId);
		vo.setId(Utils.get16UUID());
		vo.setWorkflowVersionId(workflowVersionId);
		WorkflowGlobalParam po = new WorkflowGlobalParam();
		BeanUtils.copyProperties(vo, po);
		workflowGlobalParamDao.insert(po);
	}
	public List<WorkflowNodeVo> getWorkflowNodesByNodeType(String workflowVersionId, String nodeType) {
		// TODO Auto-generated method stub
		List<WorkflowNodes> nodes = workflowNodesDao.selectAllByNodeType(workflowVersionId,nodeType);
		List<WorkflowNodeVo> nodesVo = new ArrayList<WorkflowNodeVo>();
		for(WorkflowNodes node:nodes)
		{
			WorkflowNodeVo nodeVo = new WorkflowNodeVo();
			BeanUtils.copyProperties(node, nodeVo);
			nodesVo.add(nodeVo);
		}
		return nodesVo;
	}

	public List<WorkflowNodeFormVo> getWorkflowUserTaskNodeForms(String workflowVersionId) {
		// TODO Auto-generated method stub
		List<HashMap<String,Object>> list = workflowNodesDao.selectWorkflowUserTaskNodeForms(workflowVersionId);
		List<WorkflowNodeFormVo> formVos = new ArrayList<WorkflowNodeFormVo>();
		for(HashMap<String,Object> l:list)
		{
			WorkflowNodeFormVo vo = new WorkflowNodeFormVo();
			vo.setId(l.get("id").toString());
			vo.setWorkflowVersionId(l.containsKey("workflowVersion")?l.get("workflowVersinId").toString():"");
			vo.setSeq(l.containsKey("seq")?Integer.valueOf(l.get("seq").toString()):0);
			vo.setNodeId(l.containsKey("nodeId")?l.get("nodeId").toString():"");
			vo.setNodeName(l.containsKey("nodeName")?l.get("nodeName").toString():"");
			vo.setNodeType(l.containsKey("nodeType")?l.get("nodeType").toString():"");
			vo.setFormName(l.containsKey("formName")?l.get("formName").toString():"");
			vo.setCreateId(l.containsKey("createId")?l.get("createId").toString():"");
			vo.setCreateTime(l.containsKey("createTime")?(Date)l.get("createTime"):null);
			formVos.add(vo);
		}
		return formVos;
	}
	
	public void saveWorkflowUserTaskNodeForms(String workflowVersionId,List<WorkflowNodeFormVo> vos)
	{
		workflowNodeFormDao.deleteByWorkflowVersionId(workflowVersionId);
		for(WorkflowNodeFormVo vo:vos)
		{
			WorkflowNodeForm po = new WorkflowNodeForm();
			po.setId(Utils.get16UUID());
			po.setWorkflowVersionId(workflowVersionId);
			po.setNodeId(vo.getNodeId());
			po.setFormName(vo.getFormName());
			po.setCreateId("admin");
			po.setCreateTime(new Date());
			workflowNodeFormDao.insert(po);
		}
	}
	
	public WorkflowNodeParamVo getWorkflowNodeParam(String workflowVersionId, String nodeId) {
		// TODO Auto-generated method stub
		HashMap<String,Object> po = workflowNodesDao.selectWorkflowNodeParam(workflowVersionId, nodeId);
		WorkflowNodeParamVo vo = new WorkflowNodeParamVo();
		vo.setId(po.containsKey("id")?po.get("id").toString():"");
		vo.setWorkflowVersionId(po.containsKey("workflowVersinId")?po.get("workflowVersinId").toString():"");
		vo.setNodeId(po.containsKey("nodeId")?po.get("nodeId").toString():"");
		vo.setSeq(po.containsKey("seq")?Integer.valueOf(po.get("seq").toString()):0);
		vo.setNodeName(po.containsKey("nodeName")?po.get("nodeName").toString():"");
		vo.setNodeType(po.containsKey("nodeType")?po.get("nodeType").toString():"");
		vo.setActStartScript(po.containsKey("actStartScript")?po.get("actStartScript").toString():"");
		vo.setActEndScript(po.containsKey("actEndScript")?po.get("actEndScript").toString():"");
		vo.setTaskCreatePrehandle(po.containsKey("taskCreatePrehandle")?po.get("taskCreatePrehandle").toString():"");
		vo.setTaskCreateAfterhandle(po.containsKey("taskCreateAfterhandle")?po.get("taskCreateAfterhandle").toString():"");
		vo.setTaskCompleteHandle(po.containsKey("taskCompleteHandle")?po.get("taskCompleteHandle").toString():"");
		vo.setTaskCreateScript(po.containsKey("taskCreateScript")?po.get("taskCreateScript").toString():"");
		vo.setTaskCompleteScript(po.containsKey("taskCompleteScript")?po.get("taskCompleteScript").toString():"");
		vo.setOnSiteNotice(po.containsKey("onSiteNotice")?(Boolean)po.get("onSiteNotice"):false);
		vo.setRecommitMode(po.containsKey("recommitMode")?po.get("recommitMode").toString():"");
		vo.setBackNodeId(po.containsKey("backNodeId")?po.get("backNodeId").toString():"");
		vo.setOnMsgNotice(po.containsKey("onMsgNotice")?(Boolean)po.get("onMsgNotice"):false);
		vo.setOnMailNotice(po.containsKey("onMailNotice")?(Boolean)po.get("onMailNotice"):false);
		vo.setOnWeixinNotice(po.containsKey("onWeixinNotice")?(Boolean)po.get("onWeixinNotice"):false);
		return vo;
	}
	
	public void saveWorkflowNodeParam(String workflowVersionId, WorkflowNodeParamVo vo) {
		// TODO Auto-generated method stub
		workflowNodeParamDao.deleteByNodeId(workflowVersionId, vo.getNodeId());
		
		WorkflowNodeParam po = new WorkflowNodeParam();
		po.setId(Utils.get16UUID());
		po.setWorkflowVersionId(workflowVersionId);
		po.setNodeId(vo.getNodeId());
		po.setActStartScript(vo.getActStartScript());
		po.setActEndScript(vo.getActEndScript());
		po.setTaskCreatePrehandle(vo.getTaskCreatePrehandle());
		po.setTaskCreateAfterhandle(vo.getTaskCreateAfterhandle());
		po.setTaskCompleteHandle(vo.getTaskCompleteHandle());
		po.setTaskCreateScript(vo.getTaskCreateScript());
		po.setTaskCompleteScript(vo.getTaskCompleteScript());
		po.setOnSiteNotice(vo.getOnSiteNotice());
		po.setOnMailNotice(vo.getOnMailNotice());
		po.setOnMsgNotice(vo.getOnMsgNotice());
		po.setOnWeixinNotice(vo.getOnWeixinNotice());
		po.setRecommitMode(vo.getRecommitMode());
		po.setBackNodeId(vo.getBackNodeId());
		po.setCreateId("admin");
		po.setCreateTime(new Date());
		workflowNodeParamDao.insert(po);
	}

	public void createWorkflowNodes(String workflowVersionId) {
		// TODO Auto-generated method stub
		//获取流程版本信息
		WorkflowVersion workflowVersion = workflowVersionDao.selectByPrimaryKey(workflowVersionId);
		//获取流程定义
		ProcessDefinitionEntity def =  (ProcessDefinitionEntity)repositoryService.getProcessDefinition(workflowVersion.getActivitiProcdefId());
		//删除流程节点信息
		workflowNodesDao.deleteNodesByVersionId(workflowVersionId);
		//创建节点信息
		List<ActivityImpl> acts = def.getActivities();
		int seq = 0;
		for(ActivityImpl act : acts)
		{
			WorkflowNodes node = new WorkflowNodes();
			node.setId(Utils.get16UUID());
			node.setWorkflowVersionId(workflowVersionId);
			node.setNodeId(act.getId());
			String nodeName = null;
			if(act.getProperty("name") == null)
			{
				nodeName = act.getProperty("type").toString();
			}
			else
			{
				nodeName = act.getProperty("name").toString();
			}
			node.setNodeName(nodeName);
			node.setNodeType(act.getProperty("type").toString());
			node.setSeq(++seq);
			workflowNodesDao.insert(node);
		}
	}

	public InputStream getWorkflowImg(String workflowVersionId) {
		// TODO Auto-generated method stub
		
		WorkflowVersion version = workflowVersionDao.selectByPrimaryKey(workflowVersionId);
		String processDefineId = version.getActivitiProcdefId();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefineId).singleResult();
		String diagramResourceName = processDefinition.getDiagramResourceName();  
		InputStream imageStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), diagramResourceName);
		return imageStream;
	}

	public GatewayNodeVo getWorkflowGatewayNodeParam(String workflowVersionId, String nodeId) throws Exception 
	{
		// TODO Auto-generated method stub
		WorkflowNodes node = workflowNodesDao.selectWorkflowNodeByNodeId(workflowVersionId, nodeId);
		if(node == null)
		{
			throw new Exception("节点不存在");
		}
		WorkflowVersion workflowVersion = workflowVersionDao.selectByPrimaryKey(node.getWorkflowVersionId());
		ProcessDefinitionEntity def =  (ProcessDefinitionEntity)repositoryService.getProcessDefinition(workflowVersion.getActivitiProcdefId());
		ActivityImpl act = def.findActivity(nodeId);
		GatewayNodeVo vo = new GatewayNodeVo();
		vo.setNodeId(nodeId);
		vo.setWorkflowVersionId(workflowVersionId);
		vo.setNodeName(node.getNodeName());
		
		List<PvmTransition> outTrans = act.getOutgoingTransitions();
		List<OutTransNodeVo> outTransVos = new ArrayList<OutTransNodeVo>();
		for(PvmTransition o:outTrans)
		{
			String outNodeId = o.getDestination().getId();
			//获取出口节点信息
			WorkflowNodes outNode = workflowNodesDao.selectWorkflowNodeByNodeId(workflowVersionId, outNodeId);
			OutTransNodeVo outVo = new OutTransNodeVo();
			outVo.setNodeId(outNodeId);
			outVo.setNodeName(outNode.getNodeName());
			//获取出口参数信息
			WorkflowGateWayParam param = workflowGatewayParamDao.selectByNodeId(workflowVersionId, nodeId, outNodeId);
			if(param != null)
			{
				outVo.setOutScript(param.getOutScript());
			}
			outTransVos.add(outVo);
		}
		vo.setOutTransNodes(outTransVos);
		return vo;
	}

	public void saveWorkflowGatewayNodeParam(String workflowVersionId, GatewayNodeVo param) {
		// TODO Auto-generated method stub
		workflowGatewayParamDao.deleteByNodeId(workflowVersionId,param.getNodeId());
		for(OutTransNodeVo vo:param.getOutTransNodes())
		{
			WorkflowGateWayParam  po = new WorkflowGateWayParam();
			po.setId(Utils.get16UUID());
			po.setNodeId(param.getNodeId());
			po.setOutNodeId(vo.getNodeId());
			po.setWorkflowVersionId(workflowVersionId);
			po.setOutScript(vo.getOutScript());
			po.setCreateId("admin");
			po.setCreateTime(new Date());
			workflowGatewayParamDao.insert(po);
		}
	}
	
	public void addWorkflowDefine(WorkflowDefine record) throws UnsupportedEncodingException
	{
		//创建流程信息
		workflowDefineDao.insert(record);
		//创建流程模型
		ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        ObjectNode properNode = objectMapper.createObjectNode();
        properNode.put("process_id", record.getWorkflowKey());
        properNode.put("process_author", "admin");
        properNode.put("process_version", "1");
        editorNode.put("properties", properNode);
        Model modelData = repositoryService.newModel();
     
        ObjectNode modelObjectNode = objectMapper.createObjectNode();
        modelObjectNode.put("name", record.getWorkflowName());
        modelObjectNode.put("revision", 1);
        modelData.setMetaInfo(modelObjectNode.toString());
        modelData.setName(record.getWorkflowName());
        modelData.setKey(record.getWorkflowKey());
        repositoryService.saveModel(modelData);
        repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
        
        //创建流程版本
        WorkflowVersion version = new WorkflowVersion();
        version.setId(Utils.get16UUID());
        version.setWorkflowDefineId(record.getId());
        version.setActivitiModelId(modelData.getId());
        version.setVersion(1);
        version.setVersionStatus("INIT");
        workflowVersionDao.insert(version);
        
        //再次刷新流程信息
        record.setActivateVersionId(version.getId());
        workflowDefineDao.updateByPrimaryKey(record);
	}

	public List<HashMap> getWorkflowDefineListByTypeId(String workflowTypeId)
	{
		return workflowDefineDao.selectListByTypeId(workflowTypeId);
	}
	
	public WorkflowDefine getWorkflowDefineByVersionId(String versionId)
	{
		return workflowDefineDao.selectByVersionId(versionId);
	}
	
	public void updateWorkflowDefine(WorkflowDefine define)
	{
		workflowDefineDao.updateByPrimaryKey(define);
	}
	
	public void setActivateVersion(String defineId,String versionId)
	{
		workflowDefineDao.setActivateVersion(defineId, versionId);
	}
	
	public List<WorkflowType> getWorkflowTypeList()
	{
		return workflowTypeDao.selectAll();
	}

	public void createWorkflowType(WorkflowType record)
	{
		workflowTypeDao.insert(record);
	}
	
	public List<HashMap<String,Object>> getWorkflowVersionListByDefineId(String defineId)
	{
		return workflowVersionDao.selectListByDefineId(defineId);
	}
	
	public WorkflowVersion getWorkflowVersionByModelId(String modelId)
	{
		return workflowVersionDao.selectByModelId(modelId);
	}
	
	public void updateWorkflowVersion(WorkflowVersion version)
	{
		workflowVersionDao.updateByPrimaryKey(version);
	}
	public void createWorkflowVersion(WorkflowVersion version)
	{
		workflowVersionDao.insert(version);
	}
	
	public WorkflowVersion getActivateVersionByDefineId(String defineId)
	{
		return workflowVersionDao.selectActivateVersionByDefineId(defineId);
	}

	public WorkflowGlobalParam getGlobalParamByVersionId(String versionId) {
		// TODO Auto-generated method stub
		return workflowGlobalParamDao.selectByWorkflowVersionId(versionId);
	}
	public List<WorkflowGateWayParam> getGateWayParamListByNodeId(String versionId,String nodeId)
	{
		return gatewayParamDao.selectGatewayParamsByNodeId(versionId, nodeId);
	}

	public WorkflowDefine getWorkflowDefineByDefineKey(String workflowKey) {
		// TODO Auto-generated method stub
		return workflowDefineDao.selectByDefineKey(workflowKey);
	}

	public WorkflowVersion getWorkflowVersionByVersionId(String versionId) {
		// TODO Auto-generated method stub
		return workflowVersionDao.selectByPrimaryKey(versionId);
	}

	public void updateWorkflowType(WorkflowType record) {
		// TODO Auto-generated method stub
		workflowTypeDao.updateByPrimaryKey(record);
	}

	public List<WorkflowNodeAssigneeVo> getWorkflowUserTaskNodeAssignee(String versionId) {
		// TODO Auto-generated method stub
		List<WorkflowNodeAssigneeVo> assigneeVos = new ArrayList<WorkflowNodeAssigneeVo>();
		
		List<HashMap<String,Object>> list =  workflowNodesDao.selectWorkflowUserTaskNodeAssignee(versionId);
		
		for(HashMap<String,Object> l:list)
		{
			WorkflowNodeAssigneeVo vo = new WorkflowNodeAssigneeVo();
			vo.setId(l.get("id").toString());
			vo.setWorkflowVersionId(l.containsKey("workflowVersion")?l.get("workflowVersinId").toString():"");
			vo.setSeq(l.containsKey("seq")?Integer.valueOf(l.get("seq").toString()):0);
			vo.setNodeId(l.containsKey("nodeId")?l.get("nodeId").toString():"");
			vo.setNodeName(l.containsKey("nodeName")?l.get("nodeName").toString():"");
			vo.setNodeType(l.containsKey("nodeType")?l.get("nodeType").toString():"");
			vo.setAssigneeType(l.containsKey("assigneeType")?l.get("assigneeType").toString():"");
			vo.setAssigneeParam(l.containsKey("assigneeParam")?l.get("assigneeParam").toString():"");
			vo.setAssigneeHandle(l.containsKey("assigneeHandle")?l.get("assigneeHandle").toString():"");
			assigneeVos.add(vo);
		}
		return assigneeVos;
	}

	public void saveWorkflowUserTaskNodeAssignee(String workflowVersionId, List<WorkflowNodeAssigneeVo> vos) {
		// TODO Auto-generated method stub
		workflowNodeAssigneeDao.deleteByWorkflowVersionId(workflowVersionId);
		for(WorkflowNodeAssigneeVo vo : vos)
		{
			WorkflowNodeAssignee item = new WorkflowNodeAssignee();
			item.setId(Utils.get16UUID());
			item.setWorkflowVersionId(workflowVersionId);
			item.setNodeId(vo.getNodeId());
			item.setAssigneeType(vo.getAssigneeType());
			item.setAssigneeParam(vo.getAssigneeParam());
			item.setAssigneeHandle(vo.getAssigneeHandle());
			workflowNodeAssigneeDao.insert(item);
		}
	}

	public WorkflowNodeAssignee getNodeAssignee(String workflowVersionId, String nodeId) {
		// TODO Auto-generated method stub
		return workflowNodeAssigneeDao.selectNodeAssignee(workflowVersionId, nodeId);
	}
}
