package com.pujjr.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.dao.SysRoleMapper;
import com.pujjr.base.domain.SysRole;
import com.pujjr.base.service.ISysRoleService;
@Service
public class SysRoleServiceImpl implements ISysRoleService {

	@Autowired
	private SysRoleMapper sysRoleDao;
	
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

}
