package com.pujjr.sms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.sms.domain.SmsTemplate;

public interface SmsTemplateMapper {
    int deleteByPrimaryKey(String id);

    int insert(SmsTemplate record);

    int insertSelective(SmsTemplate record);

    SmsTemplate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SmsTemplate record);

    int updateByPrimaryKey(SmsTemplate record);
    
    List<SmsTemplate>  selectList();
    
    SmsTemplate selectByTplKey(@Param("tplKey")String tplKey);
}