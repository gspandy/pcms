package com.pujjr.base.dao;

import com.pujjr.base.domain.SysAccountRoleKey;

public interface SysAccountRoleMapper {
    int deleteByPrimaryKey(SysAccountRoleKey key);

    int insert(SysAccountRoleKey record);

    int insertSelective(SysAccountRoleKey record);
}