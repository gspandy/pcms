package com.pujjr.base.domain;

public class CarStyle {
    private String id;

	private String carSerialId;

	private String carStyleName;

	private Double displacement;

	private Double guidePrice;

	private String indexStr;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCarSerialId() {
		return carSerialId;
	}

	public void setCarSerialId(String carSerialId) {
		this.carSerialId = carSerialId;
	}

	public String getCarStyleName() {
		return carStyleName;
	}

	public void setCarStyleName(String carStyleName) {
		this.carStyleName = carStyleName;
	}

	public Double getDisplacement() {
		return displacement;
	}

	public void setDisplacement(Double displacement) {
		this.displacement = displacement;
	}

	public Double getGuidePrice() {
		return guidePrice;
	}

	public void setGuidePrice(Double guidePrice) {
		this.guidePrice = guidePrice;
	}

	public String getIndexStr() {
		return indexStr;
	}

	public void setIndexStr(String indexStr) {
		this.indexStr = indexStr;
	}

	//扩展属性品牌
	private CarBrand carBrand;
	//扩展属性车系
	private CarSerial carSerial;

	public CarBrand getCarBrand() {
		return carBrand;
	}

	public void setCarBrand(CarBrand carBrand) {
		this.carBrand = carBrand;
	}

	public CarSerial getCarSerial() {
		return carSerial;
	}

	public void setCarSerial(CarSerial carSerial) {
		this.carSerial = carSerial;
	}
}