package com.pujjr.base.dao;

import java.util.HashMap;
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
    
    List<SysWorkgroup> selectListByParentId(@Param("parentId")String parentId);
    
    List<HashMap> selectMatchRuleAccountList(@Param("productCode")String productCode,
    									   			@Param("financeAmount") double financeAmount, 
    									   			@Param("dealerId")String dealerId,
    									   			@Param("groups")List<SysWorkgroup> groups,
    									   			@Param("candidateAccounts")List<String> candidateAccounts);
    List<HashMap> selectWorkgroupOnlineAccountList(@Param("groups")List<SysWorkgroup> groups,@Param("checkOnline")boolean checkOnline);
    
    SysWorkgroup selectByName(@Param("workgroupName")String workgroupName);
}