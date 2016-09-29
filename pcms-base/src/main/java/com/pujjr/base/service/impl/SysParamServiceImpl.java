package com.pujjr.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.dao.SysParamMapper;
import com.pujjr.base.domain.SysParam;
import com.pujjr.base.service.ISysParamService;
@Service
public class SysParamServiceImpl implements ISysParamService {

	@Autowired
	private SysParamMapper sysParamDao;
	
	@Override
	public List<SysParam> getSysParamList(String paramName) {
		// TODO Auto-generated method stub
		return sysParamDao.selectList(paramName);
	}

	@Override
	public void addSysParam(SysParam record) {
		// TODO Auto-generated method stub
		sysParamDao.insert(record);
	}

	@Override
	public void modifySysParam(SysParam record) {
		// TODO Auto-generated method stub
		sysParamDao.updateByPrimaryKey(record);
	}

	@Override
	public void deleteSysParam(String id) {
		// TODO Auto-generated method stub
		sysParamDao.deleteByPrimaryKey(id);
	}

	@Override
	public SysParam getSysParamByParamName(String paramName) {
		// TODO Auto-generated method stub
		return sysParamDao.selectByParamName(paramName);
	}

}
