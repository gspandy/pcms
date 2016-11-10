package com.pujjr.postloan.domain;

import java.util.Date;

public class ChargeLog {
    private String id;

    private String appId;

    private String offerId;

    private String chargeMode;

    private String merchantNo;

    private String orderNo;

    private String openBankNo;

    private String custName;

    private String cardNo;

    private String idType;

    private String idNo;

    private Double chargeAmount;

    private Date chargeTime;

    private String chargeResult;

    private String thirdPartyResult;

    private Date thirdPartyTime;

    private String thirdPartyResultDesc;

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

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOpenBankNo() {
        return openBankNo;
    }

    public void setOpenBankNo(String openBankNo) {
        this.openBankNo = openBankNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public Double getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(Double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public Date getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(Date chargeTime) {
        this.chargeTime = chargeTime;
    }

    public String getChargeResult() {
        return chargeResult;
    }

    public void setChargeResult(String chargeResult) {
        this.chargeResult = chargeResult;
    }

    public String getThirdPartyResult() {
        return thirdPartyResult;
    }

    public void setThirdPartyResult(String thirdPartyResult) {
        this.thirdPartyResult = thirdPartyResult;
    }

    public Date getThirdPartyTime() {
        return thirdPartyTime;
    }

    public void setThirdPartyTime(Date thirdPartyTime) {
        this.thirdPartyTime = thirdPartyTime;
    }

    public String getThirdPartyResultDesc() {
        return thirdPartyResultDesc;
    }

    public void setThirdPartyResultDesc(String thirdPartyResultDesc) {
        this.thirdPartyResultDesc = thirdPartyResultDesc;
    }
}