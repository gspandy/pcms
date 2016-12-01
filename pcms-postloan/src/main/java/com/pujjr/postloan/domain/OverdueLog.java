package com.pujjr.postloan.domain;

import java.util.Date;

public class OverdueLog {
    private String id;

    private String appId;

    private Integer seq;

    private Date startDate;

    private Date endDate;

    private Integer addupOverdueDay;

    private String reserver1;

    private Integer reserver2;

    private Date reserver3;

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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getAddupOverdueDay() {
        return addupOverdueDay;
    }

    public void setAddupOverdueDay(Integer addupOverdueDay) {
        this.addupOverdueDay = addupOverdueDay;
    }

    public String getReserver1() {
        return reserver1;
    }

    public void setReserver1(String reserver1) {
        this.reserver1 = reserver1;
    }

    public Integer getReserver2() {
        return reserver2;
    }

    public void setReserver2(Integer reserver2) {
        this.reserver2 = reserver2;
    }

    public Date getReserver3() {
        return reserver3;
    }

    public void setReserver3(Date reserver3) {
        this.reserver3 = reserver3;
    }
}