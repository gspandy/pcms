package com.pujjr.postloan.domain;

import java.util.Date;

public class ApplyExtendPeriod {
    private String id;

    private String appId;

    private Double repayCapital;

    private Double repayInterest;

    private Double repayOverdueAmount;

    private Double otherFee;

    private Double otherOverdueAmount;

    private Double extendFee;

    private Double newOldInterest;

    private String oldRepayMode;

    private String newRepayMode;

    private Double remainCapital;

    private Double newCapital;

    private String applyComment;

    private String applyStatus;

    private Date applyEndDate;

    private String createId;

    private Date createDate;

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

    public Double getRepayCapital() {
        return repayCapital;
    }

    public void setRepayCapital(Double repayCapital) {
        this.repayCapital = repayCapital;
    }

    public Double getRepayInterest() {
        return repayInterest;
    }

    public void setRepayInterest(Double repayInterest) {
        this.repayInterest = repayInterest;
    }

    public Double getRepayOverdueAmount() {
        return repayOverdueAmount;
    }

    public void setRepayOverdueAmount(Double repayOverdueAmount) {
        this.repayOverdueAmount = repayOverdueAmount;
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

    public Double getExtendFee() {
        return extendFee;
    }

    public void setExtendFee(Double extendFee) {
        this.extendFee = extendFee;
    }

    public Double getNewOldInterest() {
        return newOldInterest;
    }

    public void setNewOldInterest(Double newOldInterest) {
        this.newOldInterest = newOldInterest;
    }

    public String getOldRepayMode() {
        return oldRepayMode;
    }

    public void setOldRepayMode(String oldRepayMode) {
        this.oldRepayMode = oldRepayMode;
    }

    public String getNewRepayMode() {
        return newRepayMode;
    }

    public void setNewRepayMode(String newRepayMode) {
        this.newRepayMode = newRepayMode;
    }

    public Double getRemainCapital() {
        return remainCapital;
    }

    public void setRemainCapital(Double remainCapital) {
        this.remainCapital = remainCapital;
    }

    public Double getNewCapital() {
        return newCapital;
    }

    public void setNewCapital(Double newCapital) {
        this.newCapital = newCapital;
    }

    public String getApplyComment() {
        return applyComment;
    }

    public void setApplyComment(String applyComment) {
        this.applyComment = applyComment;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public Date getApplyEndDate() {
        return applyEndDate;
    }

    public void setApplyEndDate(Date applyEndDate) {
        this.applyEndDate = applyEndDate;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }
}