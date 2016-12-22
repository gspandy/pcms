package com.pujjr.assetsmanage.vo;

import java.util.List;

public class ArchivePostVo 
{
	//快递公司
	private String expressCompany;
	//快递单号
	private String expressNo;
	//备注
	private String comment;
	//选择邮寄档案ID列表
	private List<String> selArchives;
	
	public String getExpressCompany() {
		return expressCompany;
	}
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public List<String> getSelArchives() {
		return selArchives;
	}
	public void setSelArchives(List<String> selArchives) {
		this.selArchives = selArchives;
	}
	
}
