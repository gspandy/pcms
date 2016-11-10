package com.pujjr.postloan.domain;

import java.util.Date;

public class RepayPlan {
    private String id;

    private String appId;

    private Integer period;

    private Double repayCapital;

    private Double repayInterest;

    private Date valueDate;

    private Date closingDate;

    private Double remainCapital;

    private Double repayTotalAmount;

    private Integer addupOverdueDay;

    private Double addupOverdueAmount;

    private String repayStatus;

    private String settleMode;

    private Date settleDate;

    private String reserver1;

    private String reserver2;

    private Date reserver3;

    private Date reserver4;

    private Double reserver5;

    private Double reserver6;

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

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Double getRepayCapital() {
        return repayCapital;
    }

    public void setRepayCapital(Double repayCapital) {
        this.repayCapital = repayCapital;
    }

    public Double getRepayInterest() {
        return repayInterest;
    }

    public void setRepayInterest(Double repayInterest) {
        this.repayInterest = repayInterest;
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

    public Double getRemainCapital() {
        return remainCapital;
    }

    public void setRemainCapital(Double remainCapital) {
        this.remainCapital = remainCapital;
    }

    public Double getRepayTotalAmount() {
        return repayTotalAmount;
    }

    public void setRepayTotalAmount(Double repayTotalAmount) {
        this.repayTotalAmount = repayTotalAmount;
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

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }

    public String getSettleMode() {
        return settleMode;
    }

    public void setSettleMode(String settleMode) {
        this.settleMode = settleMode;
    }

    public Date getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(Date settleDate) {
        this.settleDate = settleDate;
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

    public Date getReserver3() {
        return reserver3;
    }

    public void setReserver3(Date reserver3) {
        this.reserver3 = reserver3;
    }

    public Date getReserver4() {
        return reserver4;
    }

    public void setReserver4(Date reserver4) {
        this.reserver4 = reserver4;
    }

    public Double getReserver5() {
        return reserver5;
    }

    public void setReserver5(Double reserver5) {
        this.reserver5 = reserver5;
    }

    public Double getReserver6() {
        return reserver6;
    }

    public void setReserver6(Double reserver6) {
        this.reserver6 = reserver6;
    }
}