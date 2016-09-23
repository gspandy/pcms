package com.pujjr.base.domain;

public class CarStyle {
    private String id;

	private String carSerialId;

	private String carStyleName;

	private Double displacement;

	private Double guidePrice;

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
	//扩展属性-车辆所属品牌
	private CarBrand carBrand;
	//扩展属性-车辆所属车系
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