package com.pujjr.base.dao;

import java.util.List;

import com.pujjr.base.domain.SysBranch;

public interface SysBranchMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysBranch record);

    int insertSelective(SysBranch record);

    SysBranch selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysBranch record);

    int updateByPrimaryKey(SysBranch record);
    
    List<SysBranch> selectAll();
}