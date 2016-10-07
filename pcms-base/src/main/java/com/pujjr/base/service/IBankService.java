package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.base.domain.BankInfo;
@Service
public interface IBankService 
{
	public List<BankInfo> getBankInfoList();
	
	public void addBankInfo(BankInfo record);
	
	public void modifyBankInfo(BankInfo record);
	
	public void deleteBankInfoById(String id);
	
	public List<BankInfo> getAllEnabledBankInfoList();
	
	public List<BankInfo> getAllUnionpayBankInfoList();
	
	public BankInfo getBankInfoById(String id);
}
