package com.pujjr.assetsmanage.enumeration;
/**
 * 变更客户信息类型
 * **/
public enum AlterCustInfoType
{
	Tenant("bgxxlx01","承租人"),
	Colessee("bgxxlx02","共租人"),
	Linkman("bgxxlx03","联系人"),
	BankInfo("bgxxlx04","银行卡");
	
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

	private AlterCustInfoType(String name,String text)
	{
		this.name = name;
		this.text = text;
	}
}
