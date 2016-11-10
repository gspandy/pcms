package com.pujjr.postloan.domain;

import java.util.Date;

public class OtherFee {
    private String id;

    private String appId;

    private Double feeTotalAmount;

    private Date valueDate;

    private Date closingDate;

    private Integer addupOverdueDay;

    private Double addupOverdueAmount;

    private String applyStatus;

    private String repayStatus;

    private Date settleDate;

    private String settleMode;

    private String applyComment;

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

    public Double getFeeTotalAmount() {
        return feeTotalAmount;
    }

    public void setFeeTotalAmount(Double feeTotalAmount) {
        this.feeTotalAmount = feeTotalAmount;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public Integer getAddupOverdueDay() {
        return addupOverdueDay;
    }

    public void setAddupOverdueDay(Integer addupOverdueDay) {
        this.addupOverdueDay = addupOverdueDay;
    }

    public Double getAddupOverdueAmount() {
        return addupOverdueAmount;
    }

    public void setAddupOverdueAmount(Double addupOverdueAmount) {
        this.addupOverdueAmount = addupOverdueAmount;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }

    public Date getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(Date settleDate) {
        this.settleDate = settleDate;
    }

    public String getSettleMode() {
        return settleMode;
    }

    public void setSettleMode(String settleMode) {
        this.settleMode = settleMode;
    }

    public String getApplyComment() {
        return applyComment;
    }

    public void setApplyComment(String applyComment) {
        this.applyComment = applyComment;
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