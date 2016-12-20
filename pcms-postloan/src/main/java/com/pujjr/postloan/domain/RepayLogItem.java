package com.pujjr.postloan.domain;

public class RepayLogItem {
    private String id;

    private String repayLogId;

    private String feeType;

    private String feeRefId;

    private String repayItem;

    private Double repayAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRepayLogId() {
        return repayLogId;
    }

    public void setRepayLogId(String repayLogId) {
        this.repayLogId = repayLogId;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getFeeRefId() {
        return feeRefId;
    }

    public void setFeeRefId(String feeRefId) {
        this.feeRefId = feeRefId;
    }

    public String getRepayItem() {
        return repayItem;
    }

    public void setRepayItem(String repayItem) {
        this.repayItem = repayItem;
    }

    public Double getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(Double repayAmount) {
        this.repayAmount = repayAmount;
    }
}