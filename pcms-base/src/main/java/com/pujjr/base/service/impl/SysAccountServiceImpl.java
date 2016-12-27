package com.pujjr.base.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.dao.SysAccountMapper;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.service.ISysAccountService;
import com.pujjr.utils.Utils;
@Service
@Transactional(rollbackFor=Exception.class)
public class SysAccountServiceImpl implements ISysAccountService 
{
	@Autowired
	private SysAccountMapper sysAccountDao;
	@Autowired
	private KeyService keyService;
	
	public void addSysAccount(SysAccount record) throws Exception {
		// TODO Auto-generated method stub
		String accountId = record.getAccountId();
		SysAccount item = sysAccountDao.selectByAccountId(accountId);
		if(item != null)
		{
			throw new Exception(accountId+"已存在");
		}
		record.setId(Utils.get16UUID());
		record.setCreateId("admin");
		record.setCreateTime(new Date());
		record.setPassword(keyService.generateEncryptPasswd(record.getAccountId(), "111111"));
		sysAccountDao.insert(record);
	}

	public SysAccount getSysAccountById(String id) {
		// TODO Auto-generated method stub
		return sysAccountDao.selectByPrimaryKey(id);
	}

	public void deleteSysAccountById(String id) {
		// TODO Auto-generated method stub
		sysAccountDao.deleteByPrimaryKey(id);
	}

	public List<SysAccount> getSysAccountListByWorkgroupId(String workgroupId) {
		// TODO Auto-generated method stub
		return sysAccountDao.selectAllByWorkgroupId(workgroupId);
	}

	public List<SysAccount> getSysAccountListByBranchId(String branchId) {
		// TODO Auto-generated method stub
		return sysAccountDao.selectAllByBranchId(branchId);
	}

	public void modifySysAccount(SysAccount record) {
		// TODO Auto-generated method stub
		sysAccountDao.updateByPrimaryKey(record);
	}

	public List<SysAccount> getSysAccount() {
		// TODO Auto-generated method stub
		return sysAccountDao.selectAll();
	}

	@Override
	public SysAccount getSysAccountByAccountId(String accountId) {
		// TODO Auto-generated method stub
		return sysAccountDao.selectByAccountId(accountId);
	}

	@Override
	public void modifySysAccountLoginStatus(String id, String loginStatus, Date loginDate) {
		// TODO Auto-generated method stub
		SysAccount account = new SysAccount();
		account.setId(id);
		account.setLoginStatus(loginStatus);
		account.setLastHeartbeatTime(loginDate);
		sysAccountDao.updateByPrimaryKeySelective(account);
	}

	@Override
	public List<SysAccount> getOnlineSysAccountByWorkgroupId(String workgroupId) {
		// TODO Auto-generated method stub
		return sysAccountDao.selectOnlineListByWorkgroupId(workgroupId, new Date());
	}

	@Override
	public List<HashMap<String, Object>> getAccountAuthMenu(String accountId) {
		// TODO Auto-generated method stub
		return sysAccountDao.selectAccountAuthMenu(accountId);
	}


}
