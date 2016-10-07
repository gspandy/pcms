package com.pujjr.carcredit.domain;

import java.util.Date;

public class SignContract {
    private String id;

    private String appId;

    private String contractNo;

    private String repayBankId;

    private String repayAcctName;

    private String repayAcctNo;

    private Date commitSignDate;

    private String signComment;

    private Integer signStep;

    private String createId;

    private Date createTime;

    private String updateId;

    private Date updateTime;

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

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getRepayBankId() {
        return repayBankId;
    }

    public void setRepayBankId(String repayBankId) {
        this.repayBankId = repayBankId;
    }

    public String getRepayAcctName() {
        return repayAcctName;
    }

    public void setRepayAcctName(String repayAcctName) {
        this.repayAcctName = repayAcctName;
    }

    public String getRepayAcctNo() {
        return repayAcctNo;
    }

    public void setRepayAcctNo(String repayAcctNo) {
        this.repayAcctNo = repayAcctNo;
    }

    public Date getCommitSignDate() {
        return commitSignDate;
    }

    public void setCommitSignDate(Date commitSignDate) {
        this.commitSignDate = commitSignDate;
    }

    public String getSignComment() {
        return signComment;
    }

    public void setSignComment(String signComment) {
        this.signComment = signComment;
    }

    public Integer getSignStep() {
        return signStep;
    }

    public void setSignStep(Integer signStep) {
        this.signStep = signStep;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}