package com.pujjr.assetsmanage.domain;

public class AlterCustInfoDetail {
    private String id;

    private String logId;

    private String alterField;

    private String alterBeforeValue;

    private String alterAfterValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getAlterField() {
        return alterField;
    }

    public void setAlterField(String alterField) {
        this.alterField = alterField;
    }

    public String getAlterBeforeValue() {
        return alterBeforeValue;
    }

    public void setAlterBeforeValue(String alterBeforeValue) {
        this.alterBeforeValue = alterBeforeValue;
    }

    public String getAlterAfterValue() {
        return alterAfterValue;
    }

    public void setAlterAfterValue(String alterAfterValue) {
        this.alterAfterValue = alterAfterValue;
    }
}