package com.pujjr.base.vo;

public class ProductPeriodVo {
	private String productId;

    private Integer period;

    private Double financeFee;
    
    private Boolean selected;

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
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

	public Double getFinanceFee() {
		return financeFee;
	}

	public void setFinanceFee(Double financeFee) {
		this.financeFee = financeFee;
	}
    
}
