package com.pujjr.assetsmanage.enumeration;
/**
 * 来电处理状态
 * **/
public enum TelIncomeProcessStatus
{
	NotPorcess("ldclzt02","未处理"),
	Processed("ldclzt01","已处理");
	
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

	private TelIncomeProcessStatus(String name,String text)
	{
		this.name = name;
		this.text = text;
	}
}
