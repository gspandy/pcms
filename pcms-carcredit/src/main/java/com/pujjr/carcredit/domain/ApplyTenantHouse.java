package com.pujjr.carcredit.domain;

public class ApplyTenantHouse {
    private String id;

	private String appId;

	private Integer seq;

	private String addrProvince;

	private String addrCity;

	private String addrCounty;

	private String addrExt;

	private String status;

	private Double area;

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

	public String getAddrProvince() {
		return addrProvince;
	}

	public void setAddrProvince(String addrProvince) {
		this.addrProvince = addrProvince;
	}

	public String getAddrCity() {
		return addrCity;
	}

	public void setAddrCity(String addrCity) {
		this.addrCity = addrCity;
	}

	public String getAddrCounty() {
		return addrCounty;
	}

	public void setAddrCounty(String addrCounty) {
		this.addrCounty = addrCounty;
	}

	public String getAddrExt() {
		return addrExt;
	}

	public void setAddrExt(String addrExt) {
		this.addrExt = addrExt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
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