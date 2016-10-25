package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.base.domain.SysRole;
@Service
public interface ISysRoleService 
{
	public List<SysRole> getSysRoleList();
	
	public void addSysRole(SysRole record);
	
	public void modifySysRole(SysRole record);
	
	public void deleteSysRoleById(String id);
}
