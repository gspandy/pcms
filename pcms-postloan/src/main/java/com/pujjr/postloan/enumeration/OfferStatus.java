package com.pujjr.postloan.enumeration;
/**
 * 报盘状态
 * **/
public enum OfferStatus 
{
	WaitOffer("bpzt01","待报盘"),
	Offering("bpzt02","报盘中"),
	RetOffer("bpzt03","已回盘");
	
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

	private OfferStatus(String name,String text)
	{
		this.name = name;
		this.text = text;
	}
}
