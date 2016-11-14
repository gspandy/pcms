package com.pujjr.base.domain;

import java.util.Date;

public class Holiday {
    private String id;

	private Date holidayDate;

	private String holidayDesc;

	private String createId;

	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getHolidayDate() {
		return holidayDate;
	}

	public void setHolidayDate(Date holidayDate) {
		this.holidayDate = holidayDate;
	}

	public String getHolidayDesc() {
		return holidayDesc;
	}

	public void setHolidayDesc(String holidayDesc) {
		this.holidayDesc = holidayDesc;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}