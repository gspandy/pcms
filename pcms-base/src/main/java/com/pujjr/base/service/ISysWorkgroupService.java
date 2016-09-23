package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.domain.SysWorkgroup;
@Service
public interface ISysWorkgroupService 
{
	public List<SysWorkgroup> getSysWorkgroupList();
	
	public void addWorkgroup(SysWorkgroup record);
	
	public void deleteWorkgroupById(String id);
	
	public SysWorkgroup getWorkgroupById(String id);
	
	public void modifyWorkgroup(SysWorkgroup record);

	public List<SysAccount> getWorkgroupSysAccounts(String id);
	
	public void addSysAccountToWorkgroup(String workgroupId,String sysAccountId);
	
	public void batchAddSysAccountToWorkgroup(String workgroupId,List<SysAccount> records);
	
	public void removeSysAccountFromWorkgroup(String workgroupId,String sysAccountId);
}
