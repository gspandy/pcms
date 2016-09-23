package com.pujjr.base.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.SysArea;

public interface SysAreaMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysArea record);

    int insertSelective(SysArea record);

    SysArea selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysArea record);

    int updateByPrimaryKey(SysArea record);
    
    List<SysArea> selectAll();
    
    List<SysArea> selectAllByParentId(@Param("parentId")String parentId);
    
    List<SysArea> selectAllByParentIdAndAreaType(@Param("parentId")String parentId,@Param("areaType")String areaType);
}