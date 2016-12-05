package com.pujjr.carcredit.constant;
/**
 * 保险类型
 * **/
public enum InsuranceType 
{
	JQX("bxlx01","交强险"),
	SYX("bxlx02","商业险"),
	LYX("bxlx03","履约险");
	
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

	private InsuranceType(String name,String text)
	{
		this.name = name;
		this.text = text;
	}
}
