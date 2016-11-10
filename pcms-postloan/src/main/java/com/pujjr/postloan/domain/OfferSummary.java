package com.pujjr.postloan.domain;

import java.util.Date;

public class OfferSummary {
    private String id;

    private String appId;

    private String offerSource;

    private String chargeMode;

    private String merchantNo;

    private Double offerAmount;

    private Date offerTime;

    private String chargeStatus;

    private String reserver1;

    private String reserver2;

    private Date reserver3;

    private Date reserver4;

    private Double reserver5;

    private Double reserver6;

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

    public String getOfferSource() {
        return offerSource;
    }

    public void setOfferSource(String offerSource) {
        this.offerSource = offerSource;
    }

    public String getChargeMode() {
        return chargeMode;
    }

    public void setChargeMode(String chargeMode) {
        this.chargeMode = chargeMode;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public Double getOfferAmount() {
        return offerAmount;
    }

    public void setOfferAmount(Double offerAmount) {
        this.offerAmount = offerAmount;
    }

    public Date getOfferTime() {
        return offerTime;
    }

    public void setOfferTime(Date offerTime) {
        this.offerTime = offerTime;
    }

    public String getChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(String chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

    public String getReserver1() {
        return reserver1;
    }

    public void setReserver1(String reserver1) {
        this.reserver1 = reserver1;
    }

    public String getReserver2() {
        return reserver2;
    }

    public void setReserver2(String reserver2) {
        this.reserver2 = reserver2;
    }

    public Date getReserver3() {
        return reserver3;
    }

    public void setReserver3(Date reserver3) {
        this.reserver3 = reserver3;
    }

    public Date getReserver4() {
        return reserver4;
    }

    public void setReserver4(Date reserver4) {
        this.reserver4 = reserver4;
    }

    public Double getReserver5() {
        return reserver5;
    }

    public void setReserver5(Double reserver5) {
        this.reserver5 = reserver5;
    }

    public Double getReserver6() {
        return reserver6;
    }

    public void setReserver6(Double reserver6) {
        this.reserver6 = reserver6;
    }
}