package com.pujjr.postloan.enumeration;
/**
 * 贷后任务类型
 * **/
public  enum  LoanApplyTaskType
{
	PublicRepay("dhsqlx01","对公还款"),
	Remission("dhsqlx02","减免"),
	Refund("dhsqlx03","退款"),
	Settle("dhsqlx04","提前结清"),
	ExtendPeriod("dhsqlx05","展期"),
	AlterRepayDate("dhsqlx06","变更还款日");
	
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

	private LoanApplyTaskType(String name,String text)
	{
		this.name = name;
		this.text = text;
	}
}
