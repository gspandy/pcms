package com.pujjr.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.dao.InsuranceCompanyMapper;
import com.pujjr.base.domain.InsuranceCompany;
import com.pujjr.base.service.IInsuranceService;
@Service
public class InsuranceServiceImpl implements IInsuranceService {

	@Autowired
	private InsuranceCompanyMapper insuranceCompanyDao;
	@Override
	public List<InsuranceCompany> getInsuranceCompanyList(boolean enabled) {
		// TODO Auto-generated method stub
		return insuranceCompanyDao.selectAll(enabled);
	}

	@Override
	public void addInsuranceCompany(InsuranceCompany record) {
		// TODO Auto-generated method stub
		insuranceCompanyDao.insert(record);
	}

	@Override
	public void modifyInsuranceCompany(InsuranceCompany record) {
		// TODO Auto-generated method stub
		insuranceCompanyDao.updateByPrimaryKey(record);
	}

	@Override
	public void deleteInsuranceCompanyById(String id) {
		// TODO Auto-generated method stub
		insuranceCompanyDao.deleteByPrimaryKey(id);
	}

}
