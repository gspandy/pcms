package com.pujjr.postloan.enumeration;
/**
 * 还款项目
 * **/
public enum ChargeItem 
{
	CAPITAL("hkxm01","本金"),
	INTEREST("hkxm02","利息"),
	OVERDUEAMT("hkxm03","罚息"),
	OTHERFEE("hkxm04","其他费用本金"),
	OTHEROVERDUEAMT("hkxm05","其他费用罚息");
	
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

	private ChargeItem(String name,String text)
	{
		this.name = name;
		this.text = text;
	}
}
