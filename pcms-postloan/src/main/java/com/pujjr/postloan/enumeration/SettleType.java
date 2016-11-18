package com.pujjr.postloan.enumeration;
/**
 * 结清类型
 * **/
public enum SettleType
{
	AllSettle("jqlx01","提前结清"),
	PartSettle("jqlx02","部分提前结清");
	
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

	private SettleType(String name,String text)
	{
		this.name = name;
		this.text = text;
	}
}
