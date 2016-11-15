package com.pujjr.postloan.enumeration;
/**
 * 扣款状态
 * **/
public enum ChargeStatus
{
	Waiting("kkzt01","待扣款"),
	Charging("kkzt02","扣款中"),
	CompleteCharge("kkzt03","扣款完成");
	
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

	private ChargeStatus(String name,String text)
	{
		this.name = name;
		this.text = text;
	}
}
