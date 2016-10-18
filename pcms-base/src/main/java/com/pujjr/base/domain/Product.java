package com.pujjr.base.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Product {
    private String id;

	private String productTypeId;

	private String productName;

	private String productCode;

	private String loanCondition;

	private Double minLoanAmount;

	private Double maxLoanAmount;

	private Double yearRate;

	private Double dayLateRate;

	private Double dayExtendRate;

	private String repayMode;

	private String productRuleId;

	private String directoryTemplateId;

	private Boolean enable;

	private Date createTime;

	private String createId;

	private Date updateTime;

	private String updateId;

	private String formTemplateId;



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getLoanCondition() {
		return loanCondition;
	}

	public void setLoanCondition(String loanCondition) {
		this.loanCondition = loanCondition;
	}

	public Double getMinLoanAmount() {
		return minLoanAmount;
	}

	public void setMinLoanAmount(Double minLoanAmount) {
		this.minLoanAmount = minLoanAmount;
	}

	public Double getMaxLoanAmount() {
		return maxLoanAmount;
	}

	public void setMaxLoanAmount(Double maxLoanAmount) {
		this.maxLoanAmount = maxLoanAmount;
	}

	public Double getYearRate() {
		return yearRate;
	}

	public void setYearRate(Double yearRate) {
		this.yearRate = yearRate;
	}

	public Double getDayLateRate() {
		return dayLateRate;
	}

	public void setDayLateRate(Double dayLateRate) {
		this.dayLateRate = dayLateRate;
	}

	public Double getDayExtendRate() {
		return dayExtendRate;
	}

	public void setDayExtendRate(Double dayExtendRate) {
		this.dayExtendRate = dayExtendRate;
	}

	public String getRepayMode() {
		return repayMode;
	}

	public void setRepayMode(String repayMode) {
		this.repayMode = repayMode;
	}

	public String getProductRuleId() {
		return productRuleId;
	}

	public void setProductRuleId(String productRuleId) {
		this.productRuleId = productRuleId;
	}

	public String getDirectoryTemplateId() {
		return directoryTemplateId;
	}

	public void setDirectoryTemplateId(String directoryTemplateId) {
		this.directoryTemplateId = directoryTemplateId;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public String getFormTemplateId() {
		return formTemplateId;
	}

	public void setFormTemplateId(String formTemplateId) {
		this.formTemplateId = formTemplateId;
	}

    
    private SysDictData repayModeDict;

	public SysDictData getRepayModeDict() {
		return repayModeDict;
	}

	public void setRepayModeDict(SysDictData repayModeDict) {
		this.repayModeDict = repayModeDict;
	}
	//产品日罚�?
	private List<ProductSettle> productSettleList = new ArrayList<ProductSettle>();

	public List<ProductSettle> getProductSettleList() {
		return productSettleList;
	}

	public void setProductSettleList(List<ProductSettle> productSettleList) {
		this.productSettleList = productSettleList;
	}
	//产品可�?期数
	private List<ProductPeriod> productPeriodList = new ArrayList<ProductPeriod>();

	public List<ProductPeriod> getProductPeriodList() {
		return productPeriodList;
	}

	public void setProductPeriodList(List<ProductPeriod> productPeriodList) {
		this.productPeriodList = productPeriodList;
	}
	//产品规则
	private ProductRule productRule;

	public ProductRule getProductRule() {
		return productRule;
	}

	public void setProductRule(ProductRule productRule) {
		this.productRule = productRule;
	}
	//产品分类名称
	private String productTypeName;

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	
	
	
	
	
	
	
}