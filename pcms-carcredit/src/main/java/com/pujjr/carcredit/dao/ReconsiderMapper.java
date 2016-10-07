package com.pujjr.carcredit.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.carcredit.domain.Reconsider;

public interface ReconsiderMapper {
    int deleteByPrimaryKey(String id);

    int insert(Reconsider record);

    int insertSelective(Reconsider record);

    Reconsider selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Reconsider record);

    int updateByPrimaryKey(Reconsider record);
    
    int disabledAllByAppId(@Param("appId")String appId);
    
    Reconsider selectEnabledByAppId(@Param("appId")String appId);
}