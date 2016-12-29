package com.pujjr.base.vo;

import java.util.Date;

/**
 * 假日新增对象
 * @author 150032
 *
 */
public class HolidayAddVo 
{
	//假日开始日期
	private Date holidayStartDate;
	//假日结束日期
	private Date holidayEndDate;
	//假日描述
	private String holidayDesc;
	
	public Date getHolidayStartDate() {
		return holidayStartDate;
	}
	public void setHolidayStartDate(Date holidayStartDate) {
		this.holidayStartDate = holidayStartDate;
	}
	public Date getHolidayEndDate() {
		return holidayEndDate;
	}
	public void setHolidayEndDate(Date holidayEndDate) {
		this.holidayEndDate = holidayEndDate;
	}
	public String getHolidayDesc() {
		return holidayDesc;
	}
	public void setHolidayDesc(String holidayDesc) {
		this.holidayDesc = holidayDesc;
	}
}
