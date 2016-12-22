package com.pujjr.assetsmanage.enumeration;
/**
 * 催收任务类型
 * **/
public enum CollectionTaskType
{
	PhoneCollection("csrwlx01","电话催收","DHCS"),
	VisitCollection("csrwlx02","上门催收","SMCS"),
	OutCollection("csrwlx03","委外催收","WWCS"),
	RecoverCollection("csrwlx04","委外收车","WWSC"),
	RefundCollection("csrwlx05","车辆退回","CLTH"),
	DisposeCollection("csrwlx06","资产处置","ZCCZ"),
	LawsuitCollection("csrwlx07","诉讼","SS");
	
	private String name;
	private String text;
	private String taskkey;
	
	public String getName() 
	{
		return name;
	}

	public String getText() 
	{
		return text;
	}

	public String getTaskKey()
	{
		return taskkey;
	}
	private CollectionTaskType(String name,String text,String taskKey)
	{
		this.name = name;
		this.text = text;
		this.taskkey = taskKey;
	}
}
