package com.pujjr.postloan.enumeration;
/**
 * 还款方式
 * **/
public enum RepayMode 
{
	Remission("hkfs01","减免还款"),
	UnionFile("hkfs02","批量文件代扣"),
	UnionRealTime("hkfs03","银联单笔代扣"),
	StayAccounting("hkfs04","挂账还款");
	
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

	private RepayMode(String name,String text)
	{
		this.name = name;
		this.text = text;
	}
}
