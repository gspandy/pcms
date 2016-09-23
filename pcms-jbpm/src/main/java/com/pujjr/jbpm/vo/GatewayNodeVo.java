package com.pujjr.jbpm.vo;

import java.util.ArrayList;
import java.util.List;

public class GatewayNodeVo 
{
	private String nodeId;
	
	private String workflowVersionId;
	
	private String nodeName;
	
	private List<OutTransNodeVo> outTransNodes = new ArrayList<OutTransNodeVo>();

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getWorkflowVersionId() {
		return workflowVersionId;
	}

	public void setWorkflowVersionId(String workflowVersionId) {
		this.workflowVersionId = workflowVersionId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public List<OutTransNodeVo> getOutTransNodes() {
		return outTransNodes;
	}

	public void setOutTransNodes(List<OutTransNodeVo> outTransNodes) {
		this.outTransNodes = outTransNodes;
	}
	
	
}
