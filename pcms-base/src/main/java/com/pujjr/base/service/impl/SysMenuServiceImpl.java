package com.pujjr.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.dao.SysMenuMapper;
import com.pujjr.base.domain.SysMenu;
import com.pujjr.base.service.ISysMenuService;
@Service
public class SysMenuServiceImpl implements ISysMenuService {

	@Autowired
	private SysMenuMapper sysMenuDao;
	@Override
	public List<SysMenu> getAllSysMenuList() {
		// TODO Auto-generated method stub
		return sysMenuDao.selectAllSysMenuList();
	}

	@Override
	public void addSysMenu(SysMenu record) {
		// TODO Auto-generated method stub
		sysMenuDao.insert(record);
	}

	@Override
	public void modifySysMenu(SysMenu record) {
		// TODO Auto-generated method stub
		sysMenuDao.updateByPrimaryKey(record);
	}

	@Override
	public void deleteSysMenuById(String id) throws Exception {
		// TODO Auto-generated method stub
		if(sysMenuDao.selectChildSysMenuList(id).size()>0)
		{
			throw new Exception("存在下级子菜单，请先删除后再执行操作");
		}
		sysMenuDao.deleteByPrimaryKey(id);
	}

	@Override
	public List<SysMenu> getChildSysMenuList(String parentId) {
		// TODO Auto-generated method stub
		return sysMenuDao.selectChildSysMenuList(parentId);
	}

}
