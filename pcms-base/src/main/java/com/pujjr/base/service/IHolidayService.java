package com.pujjr.base.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.domain.Holiday;
import com.pujjr.base.vo.HolidayAddVo;
/**
 * 节假日管理
 * **/
@Service
@Transactional(rollbackFor=Exception.class)
public interface IHolidayService 
{
	//查询节假日信息列表
	public List<Holiday> getHolidayList();
	//根据日期查询节假日信息
	public Holiday getHolidayByDate(String id);
	//增加节假日
	public void addHoliday(Date holidayStartDate,Date holidayEndDate,String holidayDesc,String createId) throws Exception;
	//修改节假日
	public void modifyHoliday(Holiday record);
	//删除节假日
	public void deleteHolidayById(String id);
	//获取指定日期工作日
	public Date getWorkDate(Date date);
	//初始年周末
	public void initHoliday(String year, String operId) throws ParseException;
}
