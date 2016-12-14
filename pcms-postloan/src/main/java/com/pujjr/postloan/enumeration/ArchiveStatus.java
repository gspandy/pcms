package com.pujjr.postloan.enumeration;
/**
 * 档案状态
 * **/
public enum ArchiveStatus 
{
	WaitingPrint("dazt01","待打印"),
	WaitingPost("dazt02","待邮寄"),
	WaitingCommit("dazt03","待提交归档"),
	WaitingProcess("dazt04","待归档"),
	ArchiveOverdue("dazt05","归档逾期"),
	ArchiveBack("dazt06","归档退回"),
	Archived("dazt07","已归档"),
	ArchiveSupply("dazt08","待补充资料");
	
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

	private ArchiveStatus(String name,String text)
	{
		this.name = name;
		this.text = text;
	}
}
