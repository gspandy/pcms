package com.pujjr.carcredit.domain;

public class ApplyFinance {
    private String id;

	private String appId;

	private Integer seq;

	private String carStyleId;

	private Double salePrice;

	private Double initPayPercent;

	private Boolean isFinanceGps;

	private String gpsLvlId;

	private Double gpsFee;

	private Boolean isPurchaseTax;

	private Double purchaseTax;

	private Boolean isServiceFee;

	private Double serviceFee;

	private Boolean isInsuranceFee;

	private Double insuranceFee;

	private Boolean isDelayInsuranceFee;

	private Double delayInsuranceFee;

	private Boolean isTransferFee;

	private Double transferFee;

	private Boolean isAddonFee;

	private Double addonFee;

	private Double assessFee;

	private String carColor;

	private String carManu;

	private String carVin;

	private String carEngineNo;

	private Double assessPrice;

	private Double collateral;

	private Double initPayAmount;

	private Double financeAmount;

	private Double financeFee;

	private String comment;

	private String carType;

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

	public String getCarStyleId() {
		return carStyleId;
	}

	public void setCarStyleId(String carStyleId) {
		this.carStyleId = carStyleId;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getInitPayPercent() {
		return initPayPercent;
	}

	public void setInitPayPercent(Double initPayPercent) {
		this.initPayPercent = initPayPercent;
	}

	public Boolean getIsFinanceGps() {
		return isFinanceGps;
	}

	public void setIsFinanceGps(Boolean isFinanceGps) {
		this.isFinanceGps = isFinanceGps;
	}

	public String getGpsLvlId() {
		return gpsLvlId;
	}

	public void setGpsLvlId(String gpsLvlId) {
		this.gpsLvlId = gpsLvlId;
	}

	public Double getGpsFee() {
		return gpsFee;
	}

	public void setGpsFee(Double gpsFee) {
		this.gpsFee = gpsFee;
	}

	public Boolean getIsPurchaseTax() {
		return isPurchaseTax;
	}

	public void setIsPurchaseTax(Boolean isPurchaseTax) {
		this.isPurchaseTax = isPurchaseTax;
	}

	public Double getPurchaseTax() {
		return purchaseTax;
	}

	public void setPurchaseTax(Double purchaseTax) {
		this.purchaseTax = purchaseTax;
	}

	public Boolean getIsServiceFee() {
		return isServiceFee;
	}

	public void setIsServiceFee(Boolean isServiceFee) {
		this.isServiceFee = isServiceFee;
	}

	public Double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(Double serviceFee) {
		this.serviceFee = serviceFee;
	}

	public Boolean getIsInsuranceFee() {
		return isInsuranceFee;
	}

	public void setIsInsuranceFee(Boolean isInsuranceFee) {
		this.isInsuranceFee = isInsuranceFee;
	}

	public Double getInsuranceFee() {
		return insuranceFee;
	}

	public void setInsuranceFee(Double insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	public Boolean getIsDelayInsuranceFee() {
		return isDelayInsuranceFee;
	}

	public void setIsDelayInsuranceFee(Boolean isDelayInsuranceFee) {
		this.isDelayInsuranceFee = isDelayInsuranceFee;
	}

	public Double getDelayInsuranceFee() {
		return delayInsuranceFee;
	}

	public void setDelayInsuranceFee(Double delayInsuranceFee) {
		this.delayInsuranceFee = delayInsuranceFee;
	}

	public Boolean getIsTransferFee() {
		return isTransferFee;
	}

	public void setIsTransferFee(Boolean isTransferFee) {
		this.isTransferFee = isTransferFee;
	}

	public Double getTransferFee() {
		return transferFee;
	}

	public void setTransferFee(Double transferFee) {
		this.transferFee = transferFee;
	}

	public Boolean getIsAddonFee() {
		return isAddonFee;
	}

	public void setIsAddonFee(Boolean isAddonFee) {
		this.isAddonFee = isAddonFee;
	}

	public Double getAddonFee() {
		return addonFee;
	}

	public void setAddonFee(Double addonFee) {
		this.addonFee = addonFee;
	}

	public Double getAssessFee() {
		return assessFee;
	}

	public void setAssessFee(Double assessFee) {
		this.assessFee = assessFee;
	}

	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public String getCarManu() {
		return carManu;
	}

	public void setCarManu(String carManu) {
		this.carManu = carManu;
	}

	public String getCarVin() {
		return carVin;
	}

	public void setCarVin(String carVin) {
		this.carVin = carVin;
	}

	public String getCarEngineNo() {
		return carEngineNo;
	}

	public void setCarEngineNo(String carEngineNo) {
		this.carEngineNo = carEngineNo;
	}

	public Double getAssessPrice() {
		return assessPrice;
	}

	public void setAssessPrice(Double assessPrice) {
		this.assessPrice = assessPrice;
	}

	public Double getCollateral() {
		return collateral;
	}

	public void setCollateral(Double collateral) {
		this.collateral = collateral;
	}

	public Double getInitPayAmount() {
		return initPayAmount;
	}

	public void setInitPayAmount(Double initPayAmount) {
		this.initPayAmount = initPayAmount;
	}

	public Double getFinanceAmount() {
		return financeAmount;
	}

	public void setFinanceAmount(Double financeAmount) {
		this.financeAmount = financeAmount;
	}

	public Double getFinanceFee() {
		return financeFee;
	}

	public void setFinanceFee(Double financeFee) {
		this.financeFee = financeFee;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}
}