package com.pujjr.base.domain;

public class ArchiveSupplyDetail {
    private String id;

    private String supplyId;

    private String fileName;

    private Integer supplyFileCnt;

    private Integer postFileCnt;

    private Integer recvFileCnt;

    private String comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(String supplyId) {
        this.supplyId = supplyId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getSupplyFileCnt() {
        return supplyFileCnt;
    }

    public void setSupplyFileCnt(Integer supplyFileCnt) {
        this.supplyFileCnt = supplyFileCnt;
    }

    public Integer getPostFileCnt() {
        return postFileCnt;
    }

    public void setPostFileCnt(Integer postFileCnt) {
        this.postFileCnt = postFileCnt;
    }

    public Integer getRecvFileCnt() {
        return recvFileCnt;
    }

    public void setRecvFileCnt(Integer recvFileCnt) {
        this.recvFileCnt = recvFileCnt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}