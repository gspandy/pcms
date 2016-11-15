package com.pujjr.postloan.po;

import com.pujjr.postloan.domain.OfferSummary;

/**
 * 报盘信息
 * @author dengpan
 *
 */
public class OfferInfoPo extends OfferSummary 
{
	//报盘来源描述
	private String offerSourceDesc;
	//费用类型描述
	private String feeTypeDesc;
	//姓名
	private String name;
	//身份证号
	private String idNo;
	
	public String getOfferSourceDesc() {
		return offerSourceDesc;
	}
	public void setOfferSourceDesc(String offerSourceDesc) {
		this.offerSourceDesc = offerSourceDesc;
	}
	public String getFeeTypeDesc() {
		return feeTypeDesc;
	}
	public void setFeeTypeDesc(String feeTypeDesc) {
		this.feeTypeDesc = feeTypeDesc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
}
