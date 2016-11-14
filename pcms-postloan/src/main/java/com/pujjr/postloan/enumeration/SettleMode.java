package com.pujjr.postloan.enumeration;
/**
 * 还款状态
 * **/
public enum SettleMode 
{
	NORMAL("jqfs01","正常结清"),
	FORWARD("jqfs02","提前结清");
	
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

	private SettleMode(String name,String text)
	{
		this.name = name;
		this.text = text;
	}
}
