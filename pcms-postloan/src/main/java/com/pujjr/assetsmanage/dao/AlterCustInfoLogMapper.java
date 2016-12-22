package com.pujjr.assetsmanage.dao;

import com.pujjr.assetsmanage.domain.AlterCustInfoLog;

public interface AlterCustInfoLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(AlterCustInfoLog record);

    int insertSelective(AlterCustInfoLog record);

    AlterCustInfoLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AlterCustInfoLog record);

    int updateByPrimaryKey(AlterCustInfoLog record);
}