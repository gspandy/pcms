package com.pujjr.carcredit.vo;
/**查询任务信息**/
public class TaskVo 
{
	//任务ID
	private String id;
	//任务名称
	private String name;
	//任务小提示信息
	private String tips;
	//任务流程定义KEY
	private String procDefId;
	//任务实例ID
	private String procInstId;
	
	public String getProcDefId() {
		return procDefId;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	public String getProcInstId() {
		return procInstId;
	}
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
