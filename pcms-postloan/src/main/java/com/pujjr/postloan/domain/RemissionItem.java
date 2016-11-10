package com.pujjr.postloan.domain;

public class RemissionItem {
    private String id;

    private String applyType;

    private String applyRefId;

    private Double capital;

    private Double interest;

    private Double overdueAmount;

    private Double otherFee;

    private Double otherOverdueAmount;

    private Double lateFee;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getApplyRefId() {
        return applyRefId;
    }

    public void setApplyRefId(String applyRefId) {
        this.applyRefId = applyRefId;
    }

    public Double getCapital() {
        return capital;
    }

    public void setCapital(Double capital) {
        this.capital = capital;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(Double overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public Double getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(Double otherFee) {
        this.otherFee = otherFee;
    }

    public Double getOtherOverdueAmount() {
        return otherOverdueAmount;
    }

    public void setOtherOverdueAmount(Double otherOverdueAmount) {
        this.otherOverdueAmount = otherOverdueAmount;
    }

    public Double getLateFee() {
        return lateFee;
    }

    public void setLateFee(Double lateFee) {
        this.lateFee = lateFee;
    }
}