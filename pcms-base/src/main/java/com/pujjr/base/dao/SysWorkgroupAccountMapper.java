package com.pujjr.base.dao;

import com.pujjr.base.domain.SysWorkgroupAccountKey;

public interface SysWorkgroupAccountMapper {
    int deleteByPrimaryKey(SysWorkgroupAccountKey key);

    int insert(SysWorkgroupAccountKey record);

    int insertSelective(SysWorkgroupAccountKey record);
}