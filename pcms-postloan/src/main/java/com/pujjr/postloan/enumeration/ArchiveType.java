package com.pujjr.postloan.enumeration;
/**
 * 归档类型
 * **/
public enum ArchiveType 
{
	LoanComplete("gdlx01","放款完成归档","fkwcgd"),
	ExtendPriod("gdlx02","展期归档","zqgd"),
	AlterRepayDate("gdlx03","还款日变更归档","hkrbggd"),
	NormalSettle("gdlx04","正常结清归档","zcjqgd"),
	ForwardSettle("gdlx05","提前结清归档","tqjqgd"),
	TelCollection("gdlx06","电话催收归档","dhcsgd"),
	VisitCollection("gdlx07","上门催收归档","smcsgd"),
	OutCollection("gdlx08","委外催收归档","wwcsgd"),
	RecoverCollection("gdlx09","委外收车归档","wwscgd"),
	RefundCollection("gdlx10","车辆退回归档","clthgd"),
	DisposeCollection("gdlx11","资产处置归档","zcccgd"),
	LawsuitCollection("gdlx12","诉讼归档","ssgd");
	
	private String name;
	private String text;
	private String type;
	
	public String getName() 
	{
		return name;
	}

	public String getText() 
	{
		return text;
	}
	
	public String getType()
	{
		return type;
	}
	private ArchiveType(String name,String text,String type)
	{
		this.name = name;
		this.text = text;
		this.type = type;
	}
}
