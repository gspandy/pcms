package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.base.domain.SysMenu;
@Service
public interface ISysMenuService 
{
	public List<SysMenu> getAllSysMenuList();
	
	public void addSysMenu(SysMenu record);
	
	public void modifySysMenu(SysMenu record);
	
	public void deleteSysMenuById(String id) throws Exception;
	
	public List<SysMenu> getChildSysMenuList(String parentId);
}
