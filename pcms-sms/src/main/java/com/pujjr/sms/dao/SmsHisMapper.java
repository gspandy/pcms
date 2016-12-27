package com.pujjr.sms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.sms.domain.SmsHis;
import com.pujjr.sms.vo.SmsHisQueryParamVo;

public interface SmsHisMapper {
    int deleteByPrimaryKey(String id);

	int insert(SmsHis record);

	int insertSelective(SmsHis record);

	SmsHis selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(SmsHis record);

	int updateByPrimaryKey(SmsHis record);
    
    List<SmsHis> selectList(@Param("queryParam")SmsHisQueryParamVo queryParam);
}