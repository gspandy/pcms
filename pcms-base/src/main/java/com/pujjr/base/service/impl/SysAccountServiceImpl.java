package com.pujjr.base.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.dao.SysAccountMapper;
import com.pujjr.base.dao.SysAccountQueryAuthMapper;
import com.pujjr.base.dao.SysAccountRoleMapper;
import com.pujjr.base.dao.SysMenuMapper;
import com.pujjr.base.dao.SysRoleMapper;
import com.pujjr.base.dao.SysRoleMenuMapper;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.domain.SysAccountQueryAuthKey;
import com.pujjr.base.domain.SysAccountRoleKey;
import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.domain.SysRole;
import com.pujjr.base.enumeration.CheckAuthType;
import com.pujjr.base.service.ISysAccountService;
import com.pujjr.base.service.ISysBranchService;
import com.pujjr.base.vo.AccountAuthVo;
import com.pujjr.base.vo.QueryAuthVo;
import com.pujjr.utils.Utils;
@Service
@Transactional(rollbackFor=Exception.class)
public class SysAccountServiceImpl implements ISysAccountService 
{
	@Autowired
	private SysAccountMapper sysAccountDao;
	@Autowired
	private KeyService keyService;
	@Autowired
	private SysRoleMapper sysRoleDao;
	@Autowired
	private SysAccountRoleMapper sysAccountRoleDao;
	@Autowired
	private SysAccountQueryAuthMapper queryAuthDao;
	@Autowired
	private ISysBranchService sysBranchService;
	
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

	@Override
	public void saveAccountAuth(String accountId, AccountAuthVo authVo) {
		// TODO Auto-generated method stub
		//更新用户角色信息
		List<SysRole> roles = authVo.getRoleList();
		sysRoleDao.deleteAccountRole(accountId);
		for(SysRole role : roles)
		{
			SysAccountRoleKey key = new SysAccountRoleKey();
			key.setAccountId(accountId);
			key.setRoleId(role.getId());
			sysAccountRoleDao.insert(key);
		}
		//更新用户数据查询权限
		SysAccount account = sysAccountDao.selectByPrimaryKey(accountId);
		//清除目前的账户可查询机构权限信息
		List<SysBranch> branchs = authVo.getQueryAuthBranchList();
		queryAuthDao.deleteByAccountId(account.getAccountId());
		//设置查询级别
		account.setReserver1(authVo.getQueryAuthLvl());
		sysAccountDao.updateByPrimaryKey(account);
		//如果是自定义则设置机构信息
		if(authVo.getQueryAuthLvl().equals("4"))
		{
			//保存授权查询机构信息
			for(SysBranch branch : branchs)
			{
				SysAccountQueryAuthKey key = new SysAccountQueryAuthKey();
				key.setAccountId(account.getAccountId());
				key.setBranchCode(branch.getBranchCode());
				queryAuthDao.insert(key);
			}
		}
	}

	@Override
	public List<SysAccountQueryAuthKey> getAccountQueryAuthList(String accountId) {
		// TODO Auto-generated method stub
		return queryAuthDao.selectListByAccountId(accountId);
	}

	@Override
	public QueryAuthVo getAccountQueryAuth(String accountId) {
		// TODO Auto-generated method stub
		SysAccount account = sysAccountDao.selectByAccountId(accountId);
		QueryAuthVo vo = new QueryAuthVo();
		List<SysBranch> authBranchList = new ArrayList<SysBranch>();
		//已授权全部
		if(account.getReserver1()==null)
		{
			vo.setNeedCheckAuth(true);
			vo.setCheckAuthType(CheckAuthType.FilterByAcctId.getName());
			vo.setAuthBranch(authBranchList);
			vo.setAuthAcctId(accountId);
			return vo;
		}
		switch(account.getReserver1())
		{
			//授权全部机构
			case "0":
				vo.setNeedCheckAuth(false);
				vo.setCheckAuthType(CheckAuthType.FilterByBranch.getName());
				vo.setAuthBranch(authBranchList);
				break;
			//授权本机构及下属机构
			case "1":
				vo.setNeedCheckAuth(true);
				vo.setCheckAuthType(CheckAuthType.FilterByBranch.getName());
				authBranchList = sysBranchService.getChildBranchList(account.getBranchId(),null,true);
				vo.setAuthBranch(authBranchList);
				break;
			//授权本机构	
			case "2":
				vo.setNeedCheckAuth(true);
				vo.setCheckAuthType(CheckAuthType.FilterByBranch.getName());
				SysBranch branch = sysBranchService.getSysBranch(account.getBranchId(), null);
				authBranchList.add(branch);
				vo.setAuthBranch(authBranchList);
				break;
			//授权查询本人	
			case "3":
				vo.setNeedCheckAuth(true);
				vo.setCheckAuthType(CheckAuthType.FilterByAcctId.getName());
				vo.setAuthBranch(authBranchList);
				vo.setAuthAcctId(accountId);
				break;
			//自定义授权	
			case "4":
				vo.setNeedCheckAuth(true);
				vo.setCheckAuthType(CheckAuthType.FilterByBranch.getName());
				List<SysAccountQueryAuthKey> keys = this.getAccountQueryAuthList(accountId);
				for(SysAccountQueryAuthKey key : keys)
				{
					SysBranch sysBranch = new SysBranch();
					sysBranch.setBranchCode(key.getBranchCode());
					authBranchList.add(sysBranch);
				}
				vo.setAuthBranch(authBranchList);
				break;
			default:
				vo.setNeedCheckAuth(true);
				vo.setCheckAuthType(CheckAuthType.FilterByAcctId.getName());
				vo.setAuthBranch(authBranchList);
				vo.setAuthAcctId(accountId);
				break;
		}
		return vo;
	}


}
