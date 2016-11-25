package com.pujjr.base.dao;

import com.pujjr.base.domain.Holiday;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface HolidayMapper {
    int deleteByPrimaryKey(String id);

	int insert(Holiday record);

	int insertSelective(Holiday record);

	Holiday selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Holiday record);

	int updateByPrimaryKey(Holiday record);
	
	Holiday seleteByHolidayDate(@Param("holidayDate")Date holidayDate);
    
    List<Holiday> selectAll();
    
    void deleteHoliday(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
}