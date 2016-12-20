package com.pujjr.postloan.enumeration;
/**
 * 还款记录状态
 * **/
public enum RepayLogStatus 
{
	Normal("hkjlzt01","正常"),
	Canceled("hkjlzt02","已取消");
	
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

	private RepayLogStatus(String name,String text)
	{
		this.name = name;
		this.text = text;
	}
}
