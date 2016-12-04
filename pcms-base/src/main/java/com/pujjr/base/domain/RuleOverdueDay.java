package com.pujjr.base.domain;

public class RuleOverdueDay {
    private String id;

	private String workgroupId;

	private Integer beginDay;

	private Integer endDay;

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

	public Integer getBeginDay() {
		return beginDay;
	}

	public void setBeginDay(Integer beginDay) {
		this.beginDay = beginDay;
	}

	public Integer getEndDay() {
		return endDay;
	}

	public void setEndDay(Integer endDay) {
		this.endDay = endDay;
	}
}