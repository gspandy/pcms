package com.pujjr.postloan.domain;

public class OtherFeeDetail {
    private String id;

	private String itemName;

	private String otherfeeApplyId;

	private Double itemAmount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getOtherfeeApplyId() {
		return otherfeeApplyId;
	}

	public void setOtherfeeApplyId(String otherfeeApplyId) {
		this.otherfeeApplyId = otherfeeApplyId;
	}

	public Double getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(Double itemAmount) {
		this.itemAmount = itemAmount;
	}
}