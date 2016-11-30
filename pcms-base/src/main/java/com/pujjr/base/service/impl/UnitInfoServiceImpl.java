package com.pujjr.base.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.dao.UnitInfoMapper;
import com.pujjr.base.domain.UnitInfo;
import com.pujjr.base.service.IUnitInfoService;

@Service
@Transactional
public class UnitInfoServiceImpl implements IUnitInfoService {

	@Autowired
	private UnitInfoMapper unitInfoDao;
	
	@Override
	public List<HashMap<String,Object>> getUnitInfoList(boolean enabled,String unitType) {
		// TODO Auto-generated method stub
		return unitInfoDao.selectAll(enabled,unitType);
	}

	@Override
	public void addUnitInfo(UnitInfo record) {
		// TODO Auto-generated method stub
		unitInfoDao.insert(record);
	}

	@Override
	public void modifyUnitInfo(UnitInfo record) {
		// TODO Auto-generated method stub
		unitInfoDao.updateByPrimaryKey(record);
	}

	@Override
	public void deleteUnitInfoById(String id) {
		// TODO Auto-generated method stub
		unitInfoDao.deleteByPrimaryKey(id);
	}

	@Override
	public UnitInfo getUnitInfoById(String id) {
		// TODO Auto-generated method stub
		return unitInfoDao.selectByPrimaryKey(id);
	}

}
