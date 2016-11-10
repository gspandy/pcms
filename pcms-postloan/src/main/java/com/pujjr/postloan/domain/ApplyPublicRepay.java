package com.pujjr.postloan.domain;

import java.util.Date;

public class ApplyPublicRepay {
    private String id;

    private String appId;

    private Double chargeAmount;

    private Double chargeDate;

    private String applyStatus;

    private String applyComment;

    private String chargeResult;

    private String createId;

    private Date crateDate;

    private String procInstId;

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

    public Double getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(Double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public Double getChargeDate() {
        return chargeDate;
    }

    public void setChargeDate(Double chargeDate) {
        this.chargeDate = chargeDate;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getApplyComment() {
        return applyComment;
    }

    public void setApplyComment(String applyComment) {
        this.applyComment = applyComment;
    }

    public String getChargeResult() {
        return chargeResult;
    }

    public void setChargeResult(String chargeResult) {
        this.chargeResult = chargeResult;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Date getCrateDate() {
        return crateDate;
    }

    public void setCrateDate(Date crateDate) {
        this.crateDate = crateDate;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }
}