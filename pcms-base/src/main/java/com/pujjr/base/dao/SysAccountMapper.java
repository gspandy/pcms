package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.SysAccount;

public interface SysAccountMapper {
    int deleteByPrimaryKey(String id);

	int insert(SysAccount record);

	int insertSelective(SysAccount record);

	SysAccount selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(SysAccount record);

	int updateByPrimaryKey(SysAccount record);
    
    List<SysAccount> selectAll();
    
    SysAccount selectByAccountId(@Param("accountId")String accountId);
    
    List<SysAccount> selectAllByWorkgroupId(@Param("workgroupId")String workgroupId);
    
    List<SysAccount> selectAllByBranchId(@Param("branchId")String branchId);
    
}