package com.pujjr.carcredit.domain;

import java.util.Date;

public class ApplyTenantCar {
    private String id;

	private String appId;

	private Integer seq;

	private String carBrand;

	private String carDetail;

	private String carStatus;

	private Date registerDate;

	private Double loanAmount;

	private Double loanBalance;

	private Double monthRent;

	private String comment;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getCarBrand() {
		return carBrand;
	}

	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}

	public String getCarDetail() {
		return carDetail;
	}

	public void setCarDetail(String carDetail) {
		this.carDetail = carDetail;
	}

	public String getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Double getLoanBalance() {
		return loanBalance;
	}

	public void setLoanBalance(Double loanBalance) {
		this.loanBalance = loanBalance;
	}

	public Double getMonthRent() {
		return monthRent;
	}

	public void setMonthRent(Double monthRent) {
		this.monthRent = monthRent;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}