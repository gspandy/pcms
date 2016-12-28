package com.pujjr.base.enumeration;
/**
 * 查询授权检查类型
 * **/
public enum CheckAuthType
{
	FilterByBranch("0","根据机构过滤"),
	FilterByAcctId("1","根据用户ID");
	
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

	private CheckAuthType(String name,String text)
	{
		this.name = name;
		this.text = text;
	}
}
