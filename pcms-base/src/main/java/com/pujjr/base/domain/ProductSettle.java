package com.pujjr.base.domain;

public class ProductSettle {
    private String id;

    private String productId;

    private Integer period;

    private Double reate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Double getReate() {
        return reate;
    }

    public void setReate(Double reate) {
        this.reate = reate;
    }
}