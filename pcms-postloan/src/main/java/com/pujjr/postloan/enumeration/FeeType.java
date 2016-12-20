package com.pujjr.postloan.enumeration;
/**
 * 费用类型
 * **/
public enum FeeType 
{
	Plan("fylx01","计划还款"),
	Other("fylx02","其他费用"),
	ExtendFee("fylx03","展期费"),
	LateFee("fylx04","提前结清违约金");
	
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

	private FeeType(String name,String text)
	{
		this.name = name;
		this.text = text;
	}
}
