package com.pujjr.postloan.enumeration;
/**
 * 批量任务处理状态
 * **/
public enum BatchProcessStatus 
{
	Waiting("plrwclzt01","待处理"),
	Processing("plrwclzt02","处理中");
	
	private String name;
	private String text;
	
	public String getName() 
	{
		return name;
	}

	public String getText() 
	{
		return text;
	}

	private BatchProcessStatus(String name,String text)
	{
		this.name = name;
		this.text = text;
	}
}
