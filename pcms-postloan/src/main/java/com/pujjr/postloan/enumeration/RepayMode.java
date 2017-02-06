package com.pujjr.postloan.enumeration;
/**
 * 还款方式
 * **/
public enum RepayMode 
{
	Remission("dhhkfs01","减免还款"),
	UnionFile("dhhkfs02","批量文件代扣"),
	UnionRealTime("dhhkfs03","银联单笔代扣"),
	StayAccounting("dhhkfs04","挂账还款"),
	PublicRepay("dhhkfs05","对公还款"),
	Refund("dhhkfs06","退款");
	
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
