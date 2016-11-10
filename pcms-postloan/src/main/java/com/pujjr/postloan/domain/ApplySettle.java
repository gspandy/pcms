package com.pujjr.postloan.domain;

import java.util.Date;

public class ApplySettle {
    private String id;

    private String appId;

    private String settleType;

    private Double repayCapital;

    private Double repayInterest;

    private Double repayOverdueAmount;

    private Double otherFee;

    private Double otherOverdueAmount;

    private Double lateFee;

    private Double settleCapital;

    private Double settleTotalAmount;

    private Double settleAfterCapital;

    private Integer settleStartPeriod;

    private Integer settleEndPeriod;

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

    public String getSettleType() {
        return settleType;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType;
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

    public Double getLateFee() {
        return lateFee;
    }

    public void setLateFee(Double lateFee) {
        this.lateFee = lateFee;
    }

    public Double getSettleCapital() {
        return settleCapital;
    }

    public void setSettleCapital(Double settleCapital) {
        this.settleCapital = settleCapital;
    }

    public Double getSettleTotalAmount() {
        return settleTotalAmount;
    }

    public void setSettleTotalAmount(Double settleTotalAmount) {
        this.settleTotalAmount = settleTotalAmount;
    }

    public Double getSettleAfterCapital() {
        return settleAfterCapital;
    }

    public void setSettleAfterCapital(Double settleAfterCapital) {
        this.settleAfterCapital = settleAfterCapital;
    }

    public Integer getSettleStartPeriod() {
        return settleStartPeriod;
    }

    public void setSettleStartPeriod(Integer settleStartPeriod) {
        this.settleStartPeriod = settleStartPeriod;
    }

    public Integer getSettleEndPeriod() {
        return settleEndPeriod;
    }

    public void setSettleEndPeriod(Integer settleEndPeriod) {
        this.settleEndPeriod = settleEndPeriod;
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