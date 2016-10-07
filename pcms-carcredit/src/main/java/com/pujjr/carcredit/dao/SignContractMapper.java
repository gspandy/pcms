package com.pujjr.carcredit.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.carcredit.domain.SignContract;

public interface SignContractMapper {
    int deleteByPrimaryKey(String id);

    int insert(SignContract record);

    int insertSelective(SignContract record);

    SignContract selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SignContract record);

    int updateByPrimaryKey(SignContract record);
    
    SignContract getByAppId(@Param("appId")String appId);
}