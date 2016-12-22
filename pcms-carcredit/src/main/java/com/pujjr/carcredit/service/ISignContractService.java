package com.pujjr.carcredit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.carcredit.domain.SignContract;
import com.pujjr.carcredit.domain.SignFinanceDetail;
@Service
public interface ISignContractService 
{
	public SignContract getSignContractByAppId(String appId);
	
	public SignFinanceDetail getSignFinanceDetailByApplyFinanceId(String applyFinanceId);
	
	public List<SignFinanceDetail> getSignFinanceDetailByAppId(String appId);
	
	public void addSignContract(SignContract record);
	
	public void deleteSignFinanceDetailByAppId(String appId);
	
	public void modifySignContract(SignContract record);
	
	public void addSignFinanceDetail(SignFinanceDetail record);
	
	public void modifySignFinanceDetail(SignFinanceDetail record);
	
	public SignFinanceDetail getSignFinanceDetailById(String id);
}
