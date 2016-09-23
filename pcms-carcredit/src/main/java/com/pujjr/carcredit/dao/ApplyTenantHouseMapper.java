package com.pujjr.carcredit.dao;

import java.util.List;

import com.pujjr.carcredit.domain.ApplyTenantCar;
import com.pujjr.carcredit.domain.ApplyTenantHouse;

public interface ApplyTenantHouseMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApplyTenantHouse record);

    int insertSelective(ApplyTenantHouse record);

    ApplyTenantHouse selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplyTenantHouse record);

    int updateByPrimaryKey(ApplyTenantHouse record);
    
    List<ApplyTenantHouse> selectByAppId(String appId);
}