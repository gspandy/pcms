package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.SysMenu;

public interface SysMenuMapper {
    int deleteByPrimaryKey(String id);

	int insert(SysMenu record);

	int insertSelective(SysMenu record);

	SysMenu selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(SysMenu record);

	int updateByPrimaryKey(SysMenu record);
    
    List<SysMenu> selectAllSysMenuList();
    
    List<SysMenu> selectChildSysMenuList(@Param("parentId")String parentId);
    
    List<SysMenu> selectRoleMenuList(@Param("roleId")String roleId);
}