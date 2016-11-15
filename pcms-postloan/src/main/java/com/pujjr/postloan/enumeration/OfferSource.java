package com.pujjr.postloan.enumeration;
/**
 * 报盘来源
 * **/
public enum OfferSource 
{
	SYSTEM("bply01","系统报盘"),
	MANUAL("bply02","人手报盘");
	
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

	private OfferSource(String name,String text)
	{
		this.name = name;
		this.text = text;
	}
}
