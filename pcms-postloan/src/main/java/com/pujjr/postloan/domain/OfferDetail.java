package com.pujjr.postloan.domain;

public class OfferDetail {
    private String id;

    private String offerId;

    private String feeType;

    private String feeRefId;

    private Double chargeCapital;

    private Double chargeInterest;

    private Double chargeOverdueAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
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

    public Double getChargeCapital() {
        return chargeCapital;
    }

    public void setChargeCapital(Double chargeCapital) {
        this.chargeCapital = chargeCapital;
    }

    public Double getChargeInterest() {
        return chargeInterest;
    }

    public void setChargeInterest(Double chargeInterest) {
        this.chargeInterest = chargeInterest;
    }

    public Double getChargeOverdueAmount() {
        return chargeOverdueAmount;
    }

    public void setChargeOverdueAmount(Double chargeOverdueAmount) {
        this.chargeOverdueAmount = chargeOverdueAmount;
    }
}