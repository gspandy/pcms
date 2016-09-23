package com.pujjr.carcredit.domain;

public class RejectRecommit {
    private String id;

    private String appId;

    private String rejectReason;

    private String recommitReason;

    private String recommitComment;

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

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getRecommitReason() {
        return recommitReason;
    }

    public void setRecommitReason(String recommitReason) {
        this.recommitReason = recommitReason;
    }

    public String getRecommitComment() {
        return recommitComment;
    }

    public void setRecommitComment(String recommitComment) {
        this.recommitComment = recommitComment;
    }
}