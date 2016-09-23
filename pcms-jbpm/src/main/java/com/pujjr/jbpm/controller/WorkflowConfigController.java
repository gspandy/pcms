package com.pujjr.jbpm.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.controller.BaseController;
import com.pujjr.jbpm.domain.WorkflowDefine;
import com.pujjr.jbpm.domain.WorkflowNodeAssignee;
import com.pujjr.jbpm.domain.WorkflowType;
import com.pujjr.jbpm.domain.WorkflowVersion;
import com.pujjr.jbpm.service.IConfigWorkflowService;
import com.pujjr.jbpm.vo.GatewayNodeVo;
import com.pujjr.jbpm.vo.WorkflowDefineVo;
import com.pujjr.jbpm.vo.WorkflowGlobalParamVo;
import com.pujjr.jbpm.vo.WorkflowNodeAssigneeVo;
import com.pujjr.jbpm.vo.WorkflowNodeFormVo;
import com.pujjr.jbpm.vo.WorkflowNodeParamVo;
import com.pujjr.jbpm.vo.WorkflowNodeVo;
import com.pujjr.utils.Utils;

@RestController 
public class WorkflowConfigController extends BaseController
{
	@Autowired
	private IConfigWorkflowService configWorkflowService;
	/**
	 * ��ѯ���̷����б�
	 * **/
	@RequestMapping(value="/workflowtype",method=RequestMethod.GET)
	public List<WorkflowType> getWorkflowTypeList()
	{
		return configWorkflowService.getWorkflowTypeList();
	}
	/**
	 * �������̷���
	 * **/
	@RequestMapping(value="/workflowtype",method=RequestMethod.POST)
	public void createWorkflowType(@RequestBody WorkflowType record)
	{
		record.setId(Utils.get16UUID());
		record.setCreateId("admin");
		record.setCreateTime(new Date());
		record.setUpdateId("admin");
		record.setUpdateTime(new Date());
		configWorkflowService.createWorkflowType(record);
	}
	/**
	 * �������̷���
	 * **/
	@RequestMapping(value="/workflowtype/{id}",method=RequestMethod.PUT)
	public void updateWorkflowType(@RequestBody WorkflowType record)
	{
		record.setUpdateId("admin");
		record.setUpdateTime(new Date());
		configWorkflowService.updateWorkflowType(record);
	}
	/**
	 * �������̷���ID��ѯ��������
	 * **/
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/workflow/list/{workflowTypeId}",method=RequestMethod.GET)
	public List<HashMap> getWorkflowDefineListByType(@PathVariable String workflowTypeId)
	{
		return configWorkflowService.getWorkflowDefineListByTypeId(workflowTypeId);
	}
	/**
	 * ��������
	 * **/
	@RequestMapping(value="/workflow/create",method=RequestMethod.POST)
	public void createWorkflow(@RequestBody WorkflowDefine workflowDefine) throws Exception
	{
		if(workflowDefine.getWorkflowName().equals(null)||workflowDefine.getWorkflowName()=="")
		{
			throw new Exception("�������Ʋ���Ϊ��");
		}
		workflowDefine.setId(Utils.get16UUID());
		workflowDefine.setActivateVersionId(" ");
		workflowDefine.setCreateId("admin");
		workflowDefine.setCreateTime(new Date());
		workflowDefine.setUpdateId("admin");
		workflowDefine.setUpdateTime(new Date());
		configWorkflowService.addWorkflowDefine(workflowDefine);
	}
	
	/**
	 * ��ѯָ�����̵İ汾��Ϣ
	 * **/
	@RequestMapping(value="/workflowVersion/list",method=RequestMethod.GET)
	public List<HashMap<String,Object>> getWorkflowVersionListByDefineId(String defineId)
	{
		return configWorkflowService.getWorkflowVersionListByDefineId(defineId);
	}
	/**
	 * �����������汾��Ϣ
	 * **/
	@RequestMapping(value="/workflow/{defineId}/setActivateVersion/{versionId}",method=RequestMethod.GET)
	public void setActivateVersion(@PathVariable String defineId,@PathVariable String versionId)
	{
		configWorkflowService.setActivateVersion(defineId, versionId);
	}
	/**
	 * �������̰汾ID��ѯ����ȫ�ֲ�������
	 * **/
	@RequestMapping(value="/workflow/config/globalparam/{workflowVersionId}",method=RequestMethod.GET)
	public WorkflowGlobalParamVo getWorkflowGlobalParamByVersionId(@PathVariable String workflowVersionId)
	{
		return configWorkflowService.getWorkflowGlobalParamByVersionId(workflowVersionId);
	}
	/**
	 * ��������ȫ�ֲ���
	 * **/
	@RequestMapping(value="/workflow/config/globalparam/{workflowVersionId}",method=RequestMethod.POST)
	public void saveWorkflowGlobalParam(@PathVariable String workflowVersionId,@RequestBody WorkflowGlobalParamVo globalParam)
	{
		globalParam.setCreateId("admin");
		globalParam.setCreateTime(new Date());
		globalParam.setUpdateId("admin");
		globalParam.setUpdateTime(new Date());
		configWorkflowService.saveWorkflowGlobalParam(workflowVersionId, globalParam);
	}
	/**
	 * �������̰汾ID��ѯ�����˹���������б�
	 * **/
	@RequestMapping(value="/workflow/config/nodeforms/{workflowVersionId}",method=RequestMethod.GET)
	public List<WorkflowNodeFormVo> getWorkflowUserTaskNodeForms(@PathVariable String workflowVersionId)
	{
		return configWorkflowService.getWorkflowUserTaskNodeForms(workflowVersionId);
	}
	
	/**
	 * ����ڵ������
	 * **/
	@RequestMapping(value="/workflow/config/nodeforms/{workflowVersionId}",method=RequestMethod.POST)
	public void saveWorkflowUsertaskNodeForms(@PathVariable String workflowVersionId,@RequestBody List<WorkflowNodeFormVo> nodeForms)
	{
		configWorkflowService.saveWorkflowUserTaskNodeForms(workflowVersionId, nodeForms);
	}
	/**
	 * �������̰汾ID��ѯ���̽ڵ�
	 * **/
	@RequestMapping(value="/workflow/config/nodes/{workflowVersionId}",method=RequestMethod.GET)
	public List<WorkflowNodeVo> getWorkflowAllNodes(@PathVariable String workflowVersionId)
	{
		return configWorkflowService.getWorkflowNodesByNodeType(workflowVersionId, null);
	}
	/**
	 * ��ѯָ��������ָ���ڵ������Ϣ
	 * **/
	@RequestMapping(value="/workflow/config/nodeparam/{workflowVersionId}/{nodeId}",method=RequestMethod.GET)
	public WorkflowNodeParamVo getWorkflowNodeParam(@PathVariable String workflowVersionId, @PathVariable String nodeId)
	{
		return configWorkflowService.getWorkflowNodeParam(workflowVersionId, nodeId);
	}
	/**
	 * ����ڵ����
	 * **/
	@RequestMapping(value="/workflow/config/nodeparam/{workflowVersionId}",method=RequestMethod.POST)
	public void saveWorkflowNodeParam(@PathVariable String workflowVersionId,@RequestBody WorkflowNodeParamVo vo)
	{
		configWorkflowService.saveWorkflowNodeParam(workflowVersionId, vo);
	}
	/**
	 * ��ȡ���ؽڵ����
	 * @throws Exception 
	 * **/
	@RequestMapping(value="/workflow/config/gatewayparam/{workflowVersionId}/{nodeId}",method=RequestMethod.GET)
	public GatewayNodeVo getWorkflowGatewayParam(@PathVariable String workflowVersionId,@PathVariable String nodeId) throws Exception
	{
		return configWorkflowService.getWorkflowGatewayNodeParam(workflowVersionId, nodeId);
	}
	/**
	 * �������ؽڵ����
	 * **/
	@RequestMapping(value="/workflow/config/gatewayparam/{workflowVersionId}",method=RequestMethod.POST)
	public void saveWorkflowGatewayParam(@PathVariable String workflowVersionId,@RequestBody GatewayNodeVo vo)
	{
		configWorkflowService.saveWorkflowGatewayNodeParam(workflowVersionId, vo);
	}
	/**
	 * ��ȡ����ͼƬ��Դ
	 * @throws IOException 
	 * **/
	@RequestMapping(value="/workflow/config/img/{workflowVersionId}",method=RequestMethod.GET)
	public void getWorkflowImg(@PathVariable String workflowVersionId,
								HttpServletRequest request,
								HttpServletResponse response) throws IOException
	{
		InputStream imgStream = configWorkflowService.getWorkflowImg(workflowVersionId);
		OutputStream stream = response.getOutputStream();
		int len = 0;
	    byte[] b = new byte[1024];
	    while ((len = imgStream.read(b, 0, 1024)) != -1) 
	    {
	        stream.write(b, 0, len);
	    }
	    stream.flush();
        stream.close();
	}
	/**
	 * ��ѯ����ָ���汾������Ϣ
	 * **/
	@RequestMapping(value="/workflow/config/baseinfo/{workflowVersionId}",method=RequestMethod.GET)
	public WorkflowDefineVo getWorkflowBaseInfoByVersionId(@PathVariable String workflowVersionId)
	{
		WorkflowDefineVo vo = new WorkflowDefineVo();
		
		WorkflowDefine define = configWorkflowService.getWorkflowDefineByVersionId(workflowVersionId);
		WorkflowVersion version = configWorkflowService.getWorkflowVersionByVersionId(workflowVersionId);
		
		BeanUtils.copyProperties(define, vo);
		BeanUtils.copyProperties(version, vo);
		return vo;
	}
	/**
	 * ��ѯ�����ƶ��汾�ڵ�������������Ϣ
	 * **/
	@RequestMapping(value="/workflow/config/assignee/{workflowVersionId}",method=RequestMethod.GET)
	public List<WorkflowNodeAssigneeVo> getWorkflowNodeAssigneeList(@PathVariable String workflowVersionId)
	{
		return configWorkflowService.getWorkflowUserTaskNodeAssignee(workflowVersionId);
	}
	/**
	 * ����ڵ�������������Ϣ
	 * **/
	@RequestMapping(value="/workflow/config/assignee/{workflowVersionId}",method=RequestMethod.POST)
	public void saveWorkflowNodeAssignee(@PathVariable String workflowVersionId,@RequestBody List<WorkflowNodeAssigneeVo> vos)
	{
		configWorkflowService.saveWorkflowUserTaskNodeAssignee(workflowVersionId, vos);
	}
	
}
