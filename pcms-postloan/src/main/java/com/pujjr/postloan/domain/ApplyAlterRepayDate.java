package com.pujjr.postloan.domain;

import java.util.Date;

public class ApplyAlterRepayDate {
    private String id;

    private String appId;

    private Date oldClosingDate;

    private Date newClosingDate;

    private Integer alterDay;

    private Double alterInterest;

    private String applyComment;

    private String applyStatus;

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

    public Date getOldClosingDate() {
        return oldClosingDate;
    }

    public void setOldClosingDate(Date oldClosingDate) {
        this.oldClosingDate = oldClosingDate;
    }

    public Date getNewClosingDate() {
        return newClosingDate;
    }

    public void setNewClosingDate(Date newClosingDate) {
        this.newClosingDate = newClosingDate;
    }

    public Integer getAlterDay() {
        return alterDay;
    }

    public void setAlterDay(Integer alterDay) {
        this.alterDay = alterDay;
    }

    public Double getAlterInterest() {
        return alterInterest;
    }

    public void setAlterInterest(Double alterInterest) {
        this.alterInterest = alterInterest;
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