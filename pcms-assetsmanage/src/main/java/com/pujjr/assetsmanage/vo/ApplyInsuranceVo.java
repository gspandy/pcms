package com.pujjr.assetsmanage.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class ApplyInsuranceVo {
	private String insType;

	private String insCompanyId;

	private String insPolicyNo;

	private Double insAmount;
	@DateTimeFormat(iso = ISO.DATE)
	private Date insStartDate;
	@DateTimeFormat(iso = ISO.DATE)
	private Date insEndDate;

	private Boolean isDamage;

	private Double damageAmount;

	private Boolean isThird;

	private Double thirdAmount;

	private Boolean isTheft;

	private Double theftAmount;

	private Boolean isAvoid;

	private Double avoidAmount;

	public String getInsType() {
		return insType;
	}

	public void setInsType(String insType) {
		this.insType = insType;
	}

	public String getInsCompanyId() {
		return insCompanyId;
	}

	public void setInsCompanyId(String insCompanyId) {
		this.insCompanyId = insCompanyId;
	}

	public String getInsPolicyNo() {
		return insPolicyNo;
	}

	public void setInsPolicyNo(String insPolicyNo) {
		this.insPolicyNo = insPolicyNo;
	}

	public Double getInsAmount() {
		return insAmount;
	}

	public void setInsAmount(Double insAmount) {
		this.insAmount = insAmount;
	}


	public Date getInsStartDate() {
		return insStartDate;
	}

	public void setInsStartDate(Date insStartDate) {
		this.insStartDate = insStartDate;
	}

	public Date getInsEndDate() {
		return insEndDate;
	}

	public void setInsEndDate(Date insEndDate) {
		this.insEndDate = insEndDate;
	}

	public Boolean getIsDamage() {
		return isDamage;
	}

	public void setIsDamage(Boolean isDamage) {
		this.isDamage = isDamage;
	}

	public Double getDamageAmount() {
		return damageAmount;
	}

	public void setDamageAmount(Double damageAmount) {
		this.damageAmount = damageAmount;
	}

	public Boolean getIsThird() {
		return isThird;
	}

	public void setIsThird(Boolean isThird) {
		this.isThird = isThird;
	}

	public Double getThirdAmount() {
		return thirdAmount;
	}

	public void setThirdAmount(Double thirdAmount) {
		this.thirdAmount = thirdAmount;
	}

	public Boolean getIsTheft() {
		return isTheft;
	}

	public void setIsTheft(Boolean isTheft) {
		this.isTheft = isTheft;
	}

	public Double getTheftAmount() {
		return theftAmount;
	}

	public void setTheftAmount(Double theftAmount) {
		this.theftAmount = theftAmount;
	}

	public Boolean getIsAvoid() {
		return isAvoid;
	}

	public void setIsAvoid(Boolean isAvoid) {
		this.isAvoid = isAvoid;
	}

	public Double getAvoidAmount() {
		return avoidAmount;
	}

	public void setAvoidAmount(Double avoidAmount) {
		this.avoidAmount = avoidAmount;
	}
	
}
