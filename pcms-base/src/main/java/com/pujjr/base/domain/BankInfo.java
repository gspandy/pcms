package com.pujjr.base.domain;

import java.util.Date;

public class BankInfo {
    private String id;

    private String bankName;

    private Boolean supportUnionpay;

    private String unionpayCode;

    private Boolean enabled;

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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Boolean getSupportUnionpay() {
        return supportUnionpay;
    }

    public void setSupportUnionpay(Boolean supportUnionpay) {
        this.supportUnionpay = supportUnionpay;
    }

    public String getUnionpayCode() {
        return unionpayCode;
    }

    public void setUnionpayCode(String unionpayCode) {
        this.unionpayCode = unionpayCode;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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