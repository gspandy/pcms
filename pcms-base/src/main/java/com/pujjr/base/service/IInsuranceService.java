package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.domain.InsuranceCompany;
@Service
@Transactional(rollbackFor=Exception.class)
public interface IInsuranceService {

	public List<InsuranceCompany> getInsuranceCompanyList(boolean enabled);
	
	public void addInsuranceCompany(InsuranceCompany record);
	
	public void modifyInsuranceCompany(InsuranceCompany record);
	
	public void deleteInsuranceCompanyById(String id);
}
