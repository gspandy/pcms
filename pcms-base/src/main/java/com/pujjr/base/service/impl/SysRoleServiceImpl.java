package com.pujjr.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.dao.SysAccountRoleMapper;
import com.pujjr.base.dao.SysMenuMapper;
import com.pujjr.base.dao.SysRoleMapper;
import com.pujjr.base.dao.SysRoleMenuMapper;
import com.pujjr.base.domain.SysAccountRoleKey;
import com.pujjr.base.domain.SysMenu;
import com.pujjr.base.domain.SysRole;
import com.pujjr.base.domain.SysRoleMenuKey;
import com.pujjr.base.service.ISysRoleService;
@Service
public class SysRoleServiceImpl implements ISysRoleService {

	@Autowired
	private SysRoleMapper sysRoleDao;
	@Autowired
	private SysMenuMapper sysMenuDao;
	@Autowired
	private SysRoleMenuMapper sysRoleMenuDao;
	@Autowired
	private SysAccountRoleMapper sysAccountRoleDao;
	
	@Override
	public List<SysRole> getSysRoleList() {
		// TODO Auto-generated method stub
		return sysRoleDao.selectAll();
	}

	@Override
	public void addSysRole(SysRole record) {
		// TODO Auto-generated method stub
		sysRoleDao.insert(record);
	}

	@Override
	public void modifySysRole(SysRole record) {
		// TODO Auto-generated method stub
		sysRoleDao.updateByPrimaryKey(record);
	}

	@Override
	public void deleteSysRoleById(String id) {
		// TODO Auto-generated method stub
		sysRoleDao.deleteByPrimaryKey(id);
	}

	@Override
	public List<SysMenu> getRoleMenuList(String roleId) {
		// TODO Auto-generated method stub
		return sysMenuDao.selectRoleMenuList(roleId);
	}

	@Override
	public void saveRoleMenuList(String roleId,List<SysMenu> menus) {
		// TODO Auto-generated method stub
		sysRoleDao.deleteRoleMenuList(roleId);
		for(SysMenu menu : menus)
		{
			SysRoleMenuKey key = new SysRoleMenuKey();
			key.setRoleId(roleId);
			key.setMenuId(menu.getId());
			sysRoleMenuDao.insert(key);
		}
	}

	@Override
	public List<SysRole> getAccountRoleList(String accountId) {
		// TODO Auto-generated method stub
		return sysRoleDao.selectAccountRoleList(accountId);
	}

	@Override
	public void saveAccountRoleList(String accountId, List<SysRole> roles) {
		// TODO Auto-generated method stub
		sysRoleDao.deleteAccountRole(accountId);
		for(SysRole role : roles)
		{
			SysAccountRoleKey key = new SysAccountRoleKey();
			key.setAccountId(accountId);
			key.setRoleId(role.getId());
			sysAccountRoleDao.insert(key);
		}
	}

}
