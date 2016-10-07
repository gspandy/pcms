package com.pujjr.carcredit.vo;

import com.pujjr.carcredit.domain.SignFinanceDetail;
import com.pujjr.carcredit.vo.ApplyFinanceVo;

public class SignFinanceDetailVo 
{
	//融资信息
	private ApplyFinanceVo applyFinance;
	//签约融资信息
	private SignFinanceDetail signFinanceDetail;
	
	public ApplyFinanceVo getApplyFinance() {
		return applyFinance;
	}
	public void setApplyFinance(ApplyFinanceVo applyFinance) {
		this.applyFinance = applyFinance;
	}
	public SignFinanceDetail getSignFinanceDetail() {
		return signFinanceDetail;
	}
	public void setSignFinanceDetail(SignFinanceDetail signFinanceDetail) {
		this.signFinanceDetail = signFinanceDetail;
	}
	
	
	
}
