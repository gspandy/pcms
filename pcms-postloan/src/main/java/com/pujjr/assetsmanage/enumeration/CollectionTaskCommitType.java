package com.pujjr.assetsmanage.enumeration;
/**
 * 催收任务提交类型
 * **/
public enum CollectionTaskCommitType
{
	Settleed("csrwtjlx01","结案已结清"),
	NotOverdue("csrwtjlx02","结案未逾期"),
	ApplyNewTask("csrwtjlx03","申请新催收方式"),
	ReAssignee("csrwtjlx04","重新调配");
	
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

	private CollectionTaskCommitType(String name,String text)
	{
		this.name = name;
		this.text = text;
	}
}
