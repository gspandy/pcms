package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.domain.SysMenu;
import com.pujjr.base.domain.SysRole;
@Service
@Transactional(rollbackFor=Exception.class)
public interface ISysRoleService 
{
	public List<SysRole> getSysRoleList();
	
	public void addSysRole(SysRole record);
	
	public void modifySysRole(SysRole record);
	
	public void deleteSysRoleById(String id);
	
	public List<SysMenu> getRoleMenuList(String roleId);
	
	public void saveRoleMenuList(String roleId,List<SysMenu> menus);
	
	public List<SysRole> getAccountRoleList(String accountId);
	
	public void saveAccountRoleList(String accountId,List<SysRole> roles);
}
