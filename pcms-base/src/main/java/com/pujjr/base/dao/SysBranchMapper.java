package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.SysBranch;

public interface SysBranchMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysBranch record);

    int insertSelective(SysBranch record);

    SysBranch selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysBranch record);

    int updateByPrimaryKey(SysBranch record);
    
    List<SysBranch> selectAll();
    
    SysBranch selectSysBranch(@Param("id")String id,@Param("branchCode")String branchCode);
    
    List<SysBranch> selectListByBranchType(@Param("branchType")String branchType);
    
    List<SysBranch> selectListByParentId(@Param("parentId")String parentId);
}