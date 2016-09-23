package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.base.domain.SysAccount;
@Service
public interface ISysAccountService 
{
	public List<SysAccount> getSysAccountListByWorkgroupId(String workgroupId);
	
	public List<SysAccount> getSysAccountListByBranchId(String branchId);
	
	public void  addSysAccount(SysAccount record) throws Exception;
	
	public SysAccount getSysAccountById(String id);

	public void deleteSysAccountById(String id);
	
	public void modifySysAccount(SysAccount record);
	
	public List<SysAccount> getSysAccount();
	
	public SysAccount getSysAccountByAccountId(String accountId);
}
