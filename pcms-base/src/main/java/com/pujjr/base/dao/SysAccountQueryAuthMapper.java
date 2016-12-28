package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.SysAccountQueryAuthKey;

public interface SysAccountQueryAuthMapper {
    int deleteByPrimaryKey(SysAccountQueryAuthKey key);

    int insert(SysAccountQueryAuthKey record);

    int insertSelective(SysAccountQueryAuthKey record);
    
    int deleteByAccountId(@Param("accountId")String accountId);
    
    List<SysAccountQueryAuthKey> selectListByAccountId(@Param("accountId")String accountId);
}