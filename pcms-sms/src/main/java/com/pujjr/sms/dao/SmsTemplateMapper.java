package com.pujjr.sms.dao;

import com.pujjr.sms.domain.SmsTemplate;

public interface SmsTemplateMapper {
    int deleteByPrimaryKey(String id);

    int insert(SmsTemplate record);

    int insertSelective(SmsTemplate record);

    SmsTemplate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SmsTemplate record);

    int updateByPrimaryKey(SmsTemplate record);
}