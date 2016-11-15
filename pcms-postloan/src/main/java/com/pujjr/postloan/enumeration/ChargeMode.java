package com.pujjr.postloan.enumeration;
/**
 * 报盘方式
 * **/
public enum ChargeMode 
{
	UnionFile("kkfs01","批量文件代扣"),
	UnionRealTime("kkfs02","银联单笔代扣");
	
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

	private ChargeMode(String name,String text)
	{
		this.name = name;
		this.text = text;
	}
}
