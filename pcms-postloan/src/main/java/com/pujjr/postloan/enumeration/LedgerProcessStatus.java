package com.pujjr.postloan.enumeration;
/**
 * 总账处理状态
 * **/
public enum LedgerProcessStatus 
{
	ApplyRemission("zzclzt01","申请减免"),
	ApplySettle("zzclzt02","申请提前结清"),
	ApplyPartSettle("zzclzt03","申请部分提前结清"),
	ApplyExtendPeriod("zzclzt04","申请展期"),
	ApplyPublicRepay("zzclzt05","申请对公还款"),
	ApplyRefund("zzclzt06","申请退款"),
	ApplyAlterRepayDate("zzclzt07","申请还款日变更");
	
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

	private LedgerProcessStatus(String name,String text)
	{
		this.name = name;
		this.text = text;
	}
}
