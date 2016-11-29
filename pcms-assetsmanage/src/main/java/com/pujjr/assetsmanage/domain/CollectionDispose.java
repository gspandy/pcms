package com.pujjr.assetsmanage.domain;

import java.util.Date;

public class CollectionDispose {
    private String id;

    private String applyId;

    private String disposeUnitId;

    private Date refundTime;

    private Double refundEvalPrice;

    private Double resalePrice;

    private Date resaleTime;

    private Double curEvalPrice;

    private Double km;

    private String degree;

    private Integer ageLimit;

    private Boolean isDamage;

    private String disposeReason;

    private String disposeComment;

    private String reserver1;

    private String reserver2;

    private String reserver3;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getDisposeUnitId() {
        return disposeUnitId;
    }

    public void setDisposeUnitId(String disposeUnitId) {
        this.disposeUnitId = disposeUnitId;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public Double getRefundEvalPrice() {
        return refundEvalPrice;
    }

    public void setRefundEvalPrice(Double refundEvalPrice) {
        this.refundEvalPrice = refundEvalPrice;
    }

    public Double getResalePrice() {
        return resalePrice;
    }

    public void setResalePrice(Double resalePrice) {
        this.resalePrice = resalePrice;
    }

    public Date getResaleTime() {
        return resaleTime;
    }

    public void setResaleTime(Date resaleTime) {
        this.resaleTime = resaleTime;
    }

    public Double getCurEvalPrice() {
        return curEvalPrice;
    }

    public void setCurEvalPrice(Double curEvalPrice) {
        this.curEvalPrice = curEvalPrice;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Integer getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(Integer ageLimit) {
        this.ageLimit = ageLimit;
    }

    public Boolean getIsDamage() {
        return isDamage;
    }

    public void setIsDamage(Boolean isDamage) {
        this.isDamage = isDamage;
    }

    public String getDisposeReason() {
        return disposeReason;
    }

    public void setDisposeReason(String disposeReason) {
        this.disposeReason = disposeReason;
    }

    public String getDisposeComment() {
        return disposeComment;
    }

    public void setDisposeComment(String disposeComment) {
        this.disposeComment = disposeComment;
    }

    public String getReserver1() {
        return reserver1;
    }

    public void setReserver1(String reserver1) {
        this.reserver1 = reserver1;
    }

    public String getReserver2() {
        return reserver2;
    }

    public void setReserver2(String reserver2) {
        this.reserver2 = reserver2;
    }

    public String getReserver3() {
        return reserver3;
    }

    public void setReserver3(String reserver3) {
        this.reserver3 = reserver3;
    }
}