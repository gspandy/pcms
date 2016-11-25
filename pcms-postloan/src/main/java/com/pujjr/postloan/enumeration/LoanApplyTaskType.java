package com.pujjr.postloan.enumeration;
/**
 * 贷后任务类型
 * **/
public  enum  LoanApplyTaskType
{
	PublicRepay("dhsqlx01","申请对公还款","DGHK"),
	Remission("dhsqlx02","申请减免","JIANMIAN"),
	Refund("dhsqlx03","申请退款","TUIKUAN"),
	Settle("dhsqlx04","申请提前结清","TQJQ"),
	ExtendPeriod("dhsqlx05","申请展期","ZHANQI"),
	AlterRepayDate("dhsqlx06","申请变更还款日","HKRBG"),
	OtherFee("dhsqlx07","其他费用","OTHERFEE");
	
	private String name;
	private String text;
	private String workflowKey;
	public String getName() 
	{
		return name;
	}

	public String getText() 
	{
		return text;
	}

	public String getWorkflowKey()
	{
		return workflowKey;
	}
	
	private LoanApplyTaskType(String name,String text,String workflowKey)
	{
		this.name = name;
		this.text = text;
		this.workflowKey = workflowKey;
	}
}
