package com.pujjr.base.domain;

import java.util.Date;

public class ArchiveTask {
    private String id;

    private String archiveType;

    private String archiveNo;

    private String appId;

    private String archiveStatus;

    private String checkId;

    private Date checkTime;

    private String postId;

    private Date postTime;

    private String commitId;

    private Date commitTime;

    private String processId;

    private Date processTime;

    private Date archiveEndDate;

    private Boolean isDelay;

    private Date delayEndDate;

    private String expressCompany;

    private String expressNo;

    private String comment;

    private String procInstId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArchiveType() {
        return archiveType;
    }

    public void setArchiveType(String archiveType) {
        this.archiveType = archiveType;
    }

    public String getArchiveNo() {
        return archiveNo;
    }

    public void setArchiveNo(String archiveNo) {
        this.archiveNo = archiveNo;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getArchiveStatus() {
        return archiveStatus;
    }

    public void setArchiveStatus(String archiveStatus) {
        this.archiveStatus = archiveStatus;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public Date getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }

    public Date getArchiveEndDate() {
        return archiveEndDate;
    }

    public void setArchiveEndDate(Date archiveEndDate) {
        this.archiveEndDate = archiveEndDate;
    }

    public Boolean getIsDelay() {
        return isDelay;
    }

    public void setIsDelay(Boolean isDelay) {
        this.isDelay = isDelay;
    }

    public Date getDelayEndDate() {
        return delayEndDate;
    }

    public void setDelayEndDate(Date delayEndDate) {
        this.delayEndDate = delayEndDate;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }
}