package com.pujjr.base.dao;

import com.pujjr.base.domain.SysParam;

public interface SysParamMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysParam record);

    int insertSelective(SysParam record);

    SysParam selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysParam record);

    int updateByPrimaryKey(SysParam record);
}