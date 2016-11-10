package com.pujjr.postloan.domain;

import java.util.Date;

public class StayAccountLog {
    private String id;

    private String stayId;

    private Double stayAmount;

    private Date stayTime;

    private String staySource;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStayId() {
        return stayId;
    }

    public void setStayId(String stayId) {
        this.stayId = stayId;
    }

    public Double getStayAmount() {
        return stayAmount;
    }

    public void setStayAmount(Double stayAmount) {
        this.stayAmount = stayAmount;
    }

    public Date getStayTime() {
        return stayTime;
    }

    public void setStayTime(Date stayTime) {
        this.stayTime = stayTime;
    }

    public String getStaySource() {
        return staySource;
    }

    public void setStaySource(String staySource) {
        this.staySource = staySource;
    }
}