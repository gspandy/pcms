package com.pujjr.postloan.enumeration;
/**
 * 还款状态
 * **/
public enum RepayStatus 
{
	Repaying("hkzt01","还款中"),
	Settled("hkzt02","已结清"),
	Overdue("hkzt03","逾期");
	
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

	private RepayStatus(String name,String text)
	{
		this.name = name;
		this.text = text;
	}
}
