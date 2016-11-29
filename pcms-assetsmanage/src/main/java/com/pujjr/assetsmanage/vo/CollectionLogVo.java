package com.pujjr.assetsmanage.vo;

import com.pujjr.assetsmanage.domain.CollectionLog;

public class CollectionLogVo extends CollectionLog 
{
	//委外单位
	private String outUnitName;
	//收车单位
	private String recoverUnitName;
	//诉讼单位
	private String lawsuitUnitName;
	
	public String getOutUnitName() {
		return outUnitName;
	}
	public void setOutUnitName(String outUnitName) {
		this.outUnitName = outUnitName;
	}
	public String getRecoverUnitName() {
		return recoverUnitName;
	}
	public void setRecoverUnitName(String recoverUnitName) {
		this.recoverUnitName = recoverUnitName;
	}
	public String getLawsuitUnitName() {
		return lawsuitUnitName;
	}
	public void setLawsuitUnitName(String lawsuitUnitName) {
		this.lawsuitUnitName = lawsuitUnitName;
	}
	
}
