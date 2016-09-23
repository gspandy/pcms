package com.pujjr.carcredit.dao;

import com.pujjr.carcredit.domain.ApplyTenant;
import com.pujjr.carcredit.vo.ApplyTenantVo;

public interface ApplyTenantMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApplyTenant record);

    int insertSelective(ApplyTenant record);

    ApplyTenant selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplyTenant record);

    int updateByPrimaryKey(ApplyTenant record);
    
    ApplyTenantVo selectByAppId(String appId);
}