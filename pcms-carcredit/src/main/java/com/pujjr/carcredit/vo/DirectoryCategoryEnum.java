package com.pujjr.carcredit.vo;

public enum DirectoryCategoryEnum 
{
	APPLY("申请资料", "apply"),
	CHECK("审核资料", "check"),
	SIGN("签约", "sign"), 
	LOANCHECK("放款复核", "loancheck");
	
	private String comment;
	private String key;
	
	private DirectoryCategoryEnum(String comment,String key)
	{
		this.key =key;
		this.comment=comment;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
