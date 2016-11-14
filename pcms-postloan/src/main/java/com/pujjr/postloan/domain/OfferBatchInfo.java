package com.pujjr.postloan.domain;

import java.util.Date;

public class OfferBatchInfo {
    private String id;

    private String offerBatchNo;

    private String offerFilePath;

    private String retofferBatchNo;

    private String retofferFilePath;

    private String retofferStatus;

    private Date retofferTime;

    private String retofferId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOfferBatchNo() {
        return offerBatchNo;
    }

    public void setOfferBatchNo(String offerBatchNo) {
        this.offerBatchNo = offerBatchNo;
    }

    public String getOfferFilePath() {
        return offerFilePath;
    }

    public void setOfferFilePath(String offerFilePath) {
        this.offerFilePath = offerFilePath;
    }

    public String getRetofferBatchNo() {
        return retofferBatchNo;
    }

    public void setRetofferBatchNo(String retofferBatchNo) {
        this.retofferBatchNo = retofferBatchNo;
    }

    public String getRetofferFilePath() {
        return retofferFilePath;
    }

    public void setRetofferFilePath(String retofferFilePath) {
        this.retofferFilePath = retofferFilePath;
    }

    public String getRetofferStatus() {
        return retofferStatus;
    }

    public void setRetofferStatus(String retofferStatus) {
        this.retofferStatus = retofferStatus;
    }

    public Date getRetofferTime() {
        return retofferTime;
    }

    public void setRetofferTime(Date retofferTime) {
        this.retofferTime = retofferTime;
    }

    public String getRetofferId() {
        return retofferId;
    }

    public void setRetofferId(String retofferId) {
        this.retofferId = retofferId;
    }
}