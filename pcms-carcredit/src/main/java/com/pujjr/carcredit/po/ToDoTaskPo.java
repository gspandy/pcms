package com.pujjr.carcredit.po;

import java.util.Date;

//代办任务
public class ToDoTaskPo 
{
	//任务ID
	private String taskId;
	//任务名称
	private String taskName;
	//任务定义ID
	private String taskDefKey;
	//任务执行人
	private String assignee;
	//业务主键
	private String businessKey;
	//流程实例ID
	private String procInstId;
	//流程定义ID
	private String procDefId;
	//流程定义标识
	private String procDefKey;
	//路径ID
	private String runPathId;
	//分配时间
	private Date startTime;
	//进入流程方式
	private String inJumpType;
	//上级任务名称
	private String parentTaskName;
	//上级任务执行人
	private String parentAssignee;
	//任务路由
	private String taskRouter;
	//产品名称
	private String productName;
	//客户姓名
	private String tenantName;
	//身份证号
	private String tenantIdNo;
	//录入日期
	private String createTime;
	//所属经销商
	private String branchName;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getTenantIdNo() {
		return tenantIdNo;
	}
	public void setTenantIdNo(String tenantIdNo) {
		this.tenantIdNo = tenantIdNo;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskDefKey() {
		return taskDefKey;
	}
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public String getProcInstId() {
		return procInstId;
	}
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	public String getProcDefId() {
		return procDefId;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	public String getProcDefKey() {
		return procDefKey;
	}
	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}
	public String getRunPathId() {
		return runPathId;
	}
	public void setRunPathId(String runPathId) {
		this.runPathId = runPathId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getInJumpType() {
		return inJumpType;
	}
	public void setInJumpType(String inJumpType) {
		this.inJumpType = inJumpType;
	}
	public String getParentAssignee() {
		return parentAssignee;
	}
	public void setParentAssignee(String parentAssignee) {
		this.parentAssignee = parentAssignee;
	}
	public String getTaskRouter() {
		return taskRouter;
	}
	public void setTaskRouter(String taskRouter) {
		this.taskRouter = taskRouter;
	}
	public String getParentTaskName() {
		return parentTaskName;
	}
	public void setParentTaskName(String parentTaskName) {
		this.parentTaskName = parentTaskName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
