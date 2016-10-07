package com.pujjr.base.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.domain.InsuranceCompany;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.service.IInsuranceService;
import com.pujjr.utils.Utils;

@RestController
@RequestMapping(value="/insurancecompany")
public class InsuranceController 
{
	@Autowired
	private IInsuranceService insuranceService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<InsuranceCompany> getInsuranceCompanyList(@RequestParam("enabled")boolean enabled)
	{
		return insuranceService.getInsuranceCompanyList(enabled);
	}
	@RequestMapping(method=RequestMethod.POST)
	public void addInsuranceCompany(@RequestBody InsuranceCompany insuranceCompany,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		insuranceCompany.setId(Utils.get16UUID());
		insuranceCompany.setCreateId(account.getAccountId());
		insuranceCompany.setCreateTime(new Date());
		insuranceService.addInsuranceCompany(insuranceCompany);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public void modifyInsuranceCompany(@RequestBody InsuranceCompany insuranceCompany,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		insuranceCompany.setUpdateId(account.getAccountId());
		insuranceCompany.setUpdateTime(new Date());
		insuranceService.modifyInsuranceCompany(insuranceCompany);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public void deleteBankInfo(@PathVariable String id)
	{
		insuranceService.deleteInsuranceCompanyById(id);
	}
}
