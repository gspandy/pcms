package com.pujjr.base.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.dao.SysAreaMapper;
import com.pujjr.base.domain.SysArea;
import com.pujjr.base.service.ISysAreaService;
import com.pujjr.utils.Utils;
@Service
public class SysAreaServiceImpl implements ISysAreaService {

	@Autowired
	private SysAreaMapper sysAreaDao;
	
	public List<SysArea> getSysAreaList() {
		// TODO Auto-generated method stub
		return sysAreaDao.selectAll();
	}

	public void addSysArea(SysArea record) {
		// TODO Auto-generated method stub
		record.setId(Utils.get16UUID());
		record.setCreateTime(new Date());
		record.setCreateId("admin");
		sysAreaDao.insert(record);
	}

	public void deleteSysAreaById(String id) {
		// TODO Auto-generated method stub
		sysAreaDao.deleteByPrimaryKey(id);
	}

	public void modifySysArea(SysArea record) {
		// TODO Auto-generated method stub
		record.setUpdateTime(new Date());
		record.setUpdateId("admin");
		sysAreaDao.updateByPrimaryKey(record);
	}

	public List<SysArea> getSysAreaListByParentId(String parentId) {
		// TODO Auto-generated method stub
		return sysAreaDao.selectAllByParentId(parentId);
	}

	@Override
	public List<SysArea> getProvinceList() {
		// TODO Auto-generated method stub
		return sysAreaDao.selectAllByParentIdAndAreaType("0001", "qylx01");
	}

	@Override
	public List<SysArea> getCityByProvinceId(String provinceId) {
		// TODO Auto-generated method stub
		return sysAreaDao.selectAllByParentIdAndAreaType(provinceId, "qylx02");
	}

	@Override
	public List<SysArea> getCountyByCityId(String cityId) {
		// TODO Auto-generated method stub
		return sysAreaDao.selectAllByParentIdAndAreaType(cityId, "qylx03");
	}

	@Override
	public String getAreaNameById(String id) {
		// TODO Auto-generated method stub
		return sysAreaDao.selectByPrimaryKey(id).getAreaName();
	}

}
