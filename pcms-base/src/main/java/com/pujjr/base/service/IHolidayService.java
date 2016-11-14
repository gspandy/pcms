package com.pujjr.base.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.base.domain.Holiday;
/**
 * 节假日管理
 * **/
@Service
public interface IHolidayService 
{
	//查询节假日信息列表
	public List<Holiday> getHolidayList();
	//根据日期查询节假日信息
	public Holiday getHolidayByDate(String id);
	//增加节假日
	public void addHoliday(Holiday record) throws Exception;
	//修改节假日
	public void modifyHoliday(Holiday record);
	//删除节假日
	public void deleteHolidayById(String id);
	//获取指定日期工作日
	public Date getWorkDate(Date date);
}
