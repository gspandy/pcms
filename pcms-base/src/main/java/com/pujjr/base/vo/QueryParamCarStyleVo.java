package com.pujjr.base.vo;

public class QueryParamCarStyleVo  extends QueryParamPageVo
{
	private String carSerialId;
	
	private String carBrandId;
	
	private String indexStr;
	
	private String appId;
	
	private String carTemplateId;

	public String getCarTemplateId() {
		return carTemplateId;
	}

	public void setCarTemplateId(String carTemplateId) {
		this.carTemplateId = carTemplateId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getIndexStr() {
		return indexStr;
	}

	public void setIndexStr(String indexStr) {
		this.indexStr = indexStr;
	}

	public String getCarSerialId() {
		return carSerialId;
	}

	public void setCarSerialId(String carSerialId) {
		this.carSerialId = carSerialId;
	}

	public String getCarBrandId() {
		return carBrandId;
	}

	public void setCarBrandId(String carBrandId) {
		this.carBrandId = carBrandId;
	}
	
	
}
