package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.SysRole;

public interface SysRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
    
    List<SysRole> selectAll();
    
    int deleteRoleMenuList(@Param("roleId")String roleId);
    
    List<SysRole> selectAccountRoleList(String accountId);
    
    int deleteAccountRole(@Param("accountId")String accountId);
}