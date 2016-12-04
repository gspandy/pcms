package com.pujjr.assetsmanage.domain;

import java.util.Date;

public class AlterCustInfoLog {
    private String id;

    private String appId;

    private String alterType;

    private Date alterTime;

    private String operId;

    private String alterComment;

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

    public String getAlterType() {
        return alterType;
    }

    public void setAlterType(String alterType) {
        this.alterType = alterType;
    }

    public Date getAlterTime() {
        return alterTime;
    }

    public void setAlterTime(Date alterTime) {
        this.alterTime = alterTime;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public String getAlterComment() {
        return alterComment;
    }

    public void setAlterComment(String alterComment) {
        this.alterComment = alterComment;
    }
}