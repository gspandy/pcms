package com.pujjr.base.po;

import com.pujjr.base.domain.InsuranceHis;

public class InsuranceHisPo extends InsuranceHis 
{
	private String insuranceCompanyName;
	
	private String insTypeDesc;

	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}

	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}

	public String getInsTypeDesc() {
		return insTypeDesc;
	}

	public void setInsTypeDesc(String insTypeDesc) {
		this.insTypeDesc = insTypeDesc;
	}
	
}
