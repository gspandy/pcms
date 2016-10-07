package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.base.domain.InsuranceCompany;
@Service
public interface IInsuranceService {

	public List<InsuranceCompany> getInsuranceCompanyList(boolean enabled);
	
	public void addInsuranceCompany(InsuranceCompany record);
	
	public void modifyInsuranceCompany(InsuranceCompany record);
	
	public void deleteInsuranceCompanyById(String id);
}
