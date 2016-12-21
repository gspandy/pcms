package com.pujjr.sms.dao;

import com.pujjr.sms.domain.SmsWaitSend;

public interface SmsWaitSendMapper {
    int deleteByPrimaryKey(String id);

    int insert(SmsWaitSend record);

    int insertSelective(SmsWaitSend record);

    SmsWaitSend selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SmsWaitSend record);

    int updateByPrimaryKey(SmsWaitSend record);
}