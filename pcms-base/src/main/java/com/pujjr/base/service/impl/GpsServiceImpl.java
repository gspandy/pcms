package com.pujjr.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.dao.GpsLvlMapper;
import com.pujjr.base.dao.GpsRuleMapper;
import com.pujjr.base.dao.GpsSupplierMapper;
import com.pujjr.base.domain.GpsLvl;
import com.pujjr.base.domain.GpsRule;
import com.pujjr.base.domain.GpsSupplier;
import com.pujjr.base.service.IGpsService;
@Service
public class GpsServiceImpl implements IGpsService {
	
	@Autowired
	private GpsRuleMapper gpsRuleDao;
	@Autowired
	private GpsLvlMapper gpsLvlDao;
	@Autowired
	private GpsSupplierMapper gpsSupplierDao;

	public List<GpsRule> getAllGpsRuleList() {
		// TODO Auto-generated method stub
		return gpsRuleDao.selectAll();
	}

	public List<GpsLvl> getGpsLvlListByBranchIdAndAmt(String branchId, double amt) {
		// TODO Auto-generated method stub
		List<GpsLvl> list =new ArrayList<GpsLvl>();
		GpsRule rule = gpsRuleDao.selectByAmt(amt);
		if(rule != null)
		{
			list = gpsLvlDao.selectByBranchIdAndRuleId(branchId, rule.getId());
		}
		return list;
	}

	public List<GpsLvl> getGpsLvlListByBranchId(String branchId) {
		// TODO Auto-generated method stub
		return gpsLvlDao.selectByBranchIdAndRuleId(branchId, null);
	}

	public List<GpsLvl> getGpsLvlListByBranchIdAndRuleId(String branchId, String ruleId) {
		// TODO Auto-generated method stub
		return gpsLvlDao.selectByBranchIdAndRuleId(branchId, ruleId);
	}

	public List<GpsLvl> getAllGpsLvlList() {
		// TODO Auto-generated method stub
		return gpsLvlDao.selectAll();
	}

	@Override
	public List<GpsSupplier> getAllGpsSuppilerList() {
		// TODO Auto-generated method stub
		return gpsSupplierDao.selectAll(false);
	}

	@Override
	public void addGpsSupplier(GpsSupplier record) {
		// TODO Auto-generated method stub
		gpsSupplierDao.insert(record);
	}

	@Override
	public void modifyGpsSupplier(GpsSupplier record) {
		// TODO Auto-generated method stub
		gpsSupplierDao.updateByPrimaryKey(record);
	}

	@Override
	public void deleteGpsSupplierById(String id) {
		// TODO Auto-generated method stub
		gpsSupplierDao.deleteByPrimaryKey(id);
	}

	@Override
	public List<GpsSupplier> getAllEnabledGpsSupplierList() {
		// TODO Auto-generated method stub
		return gpsSupplierDao.selectAll(true);
	}

}
