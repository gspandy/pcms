package com.pujjr.base.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.dao.SysAccountMapper;
import com.pujjr.base.dao.SysWorkgroupAccountMapper;
import com.pujjr.base.dao.SysWorkgroupMapper;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.domain.SysWorkgroup;
import com.pujjr.base.domain.SysWorkgroupAccountKey;
import com.pujjr.base.service.ISysWorkgroupService;
import com.pujjr.utils.Utils;
@Service
public class SysWorkgroupServiceImpl implements ISysWorkgroupService {
	
	@Autowired
	private SysWorkgroupMapper workgroupDao;
	@Autowired
	private SysAccountMapper sysAccountDao;
	@Autowired
	private SysWorkgroupAccountMapper sysWorkgroupAccountDao;

	public List<SysWorkgroup> getSysWorkgroupList() {
		// TODO Auto-generated method stub
		return workgroupDao.selectAll();
	}

	public void addWorkgroup(SysWorkgroup record) {
		// TODO Auto-generated method stub
		record.setId(Utils.get16UUID());
		record.setCreateId("admin");
		record.setCreateTime(new Date());
		workgroupDao.insert(record);
	}

	public void deleteWorkgroupById(String id) {
		// TODO Auto-generated method stub
		workgroupDao.deleteByPrimaryKey(id);
	}

	public SysWorkgroup getWorkgroupById(String id) {
		// TODO Auto-generated method stub
		return workgroupDao.selectByPrimaryKey(id);
	}

	public List<SysAccount> getWorkgroupSysAccounts(String id) {
		// TODO Auto-generated method stub
		return sysAccountDao.selectAllByWorkgroupId(id);
	}

	public void addSysAccountToWorkgroup(String workgroupId, String sysAccountId) {
		// TODO Auto-generated method stub
		SysWorkgroupAccountKey key = new SysWorkgroupAccountKey();
		key.setWorkgroupId(workgroupId);
		key.setSysaccountId(sysAccountId);
		sysWorkgroupAccountDao.insert(key);
	}

	public void removeSysAccountFromWorkgroup(String workgroupId, String sysAccountId) {
		// TODO Auto-generated method stub
		SysWorkgroupAccountKey key = new SysWorkgroupAccountKey();
		key.setWorkgroupId(workgroupId);
		key.setSysaccountId(sysAccountId);
		sysWorkgroupAccountDao.deleteByPrimaryKey(key);
	}

	public void modifyWorkgroup(SysWorkgroup record) {
		// TODO Auto-generated method stub
		record.setUpdateId("admin");
		record.setUpdateTime(new Date());
		workgroupDao.updateByPrimaryKey(record);
	}

	public void batchAddSysAccountToWorkgroup(String workgroupId, List<SysAccount> records) {
		// TODO Auto-generated method stub
		workgroupDao.deleteWorkgroupRelateSysAccounts(workgroupId);
		for(int i =0;i<records.size();i++)
		{
			this.addSysAccountToWorkgroup(workgroupId, records.get(i).getId());
		}
	}

}
