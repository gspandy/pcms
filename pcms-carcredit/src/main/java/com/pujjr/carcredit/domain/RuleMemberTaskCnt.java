package com.pujjr.carcredit.domain;

public class RuleMemberTaskCnt {
    private String id;

    private String workgroupId;

    private String accountId;

    private Integer maxTaskCnt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkgroupId() {
        return workgroupId;
    }

    public void setWorkgroupId(String workgroupId) {
        this.workgroupId = workgroupId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Integer getMaxTaskCnt() {
        return maxTaskCnt;
    }

    public void setMaxTaskCnt(Integer maxTaskCnt) {
        this.maxTaskCnt = maxTaskCnt;
    }
}