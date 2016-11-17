package com.pujjr.postloan.enumeration;
/**
 * 贷后申请状态
 * **/
public enum LoanApplyStatus 
{
	WaitingApprove("dhsqzt01","待审批"),
	Approving("dhsqzt02","审批中"),
	ApprovePass("dhsqzt03","审批通过"),
	ApproveRefuse("dhsqzt04","审批拒绝"),
	WaitingRemissionApprove("dhsqzt05","待减免审批"),
	RemissionApproving("dhsqzt06","减免审批中"),
	RemissionApprovePass("dhsqzt07","减免审批通过"),
	RemissionApproveRefuse("dhsqzt08","减免审批拒绝"),
	WaitingConfirm("dhsqzt09","待确认"),
	Confirmed("dhsqzt10","已确认"),
	Canceled("dhsqzt11","已取消");
	
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

	private LoanApplyStatus(String name,String text)
	{
		this.name = name;
		this.text = text;
	}
}
