package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.SysWorkgroup;

public interface SysWorkgroupMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysWorkgroup record);

    int insertSelective(SysWorkgroup record);

    SysWorkgroup selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysWorkgroup record);

    int updateByPrimaryKey(SysWorkgroup record);
    
    List<SysWorkgroup> selectAll();
    
    int deleteWorkgroupRelateSysAccounts(@Param("workgroupId")String workgroupId);
}