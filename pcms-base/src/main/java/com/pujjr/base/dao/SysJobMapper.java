package com.pujjr.base.dao;

import java.util.List;

import com.pujjr.base.domain.SysJob;

public interface SysJobMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysJob record);

    int insertSelective(SysJob record);

    SysJob selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysJob record);

    int updateByPrimaryKey(SysJob record);
    
    List<SysJob> selectAll();
}