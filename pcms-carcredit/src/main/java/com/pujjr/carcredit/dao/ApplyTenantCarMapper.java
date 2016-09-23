package com.pujjr.carcredit.dao;

import java.util.List;

import com.pujjr.carcredit.domain.ApplyTenantCar;

public interface ApplyTenantCarMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApplyTenantCar record);

    int insertSelective(ApplyTenantCar record);

    ApplyTenantCar selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplyTenantCar record);

    int updateByPrimaryKey(ApplyTenantCar record);
    
    List<ApplyTenantCar> selectByAppId(String appId);
}