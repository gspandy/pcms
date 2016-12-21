package com.pujjr.sms.dao;

import com.pujjr.sms.domain.SmsHis;

public interface SmsHisMapper {
    int deleteByPrimaryKey(String id);

    int insert(SmsHis record);

    int insertSelective(SmsHis record);

    SmsHis selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SmsHis record);

    int updateByPrimaryKey(SmsHis record);
}