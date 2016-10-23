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
