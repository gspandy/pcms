package com.pujjr.base.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.domain.SysAccountQueryAuthKey;
import com.pujjr.base.vo.AccountAuthVo;
import com.pujjr.base.vo.QueryAuthVo;
@Service
@Transactional(rollbackFor=Exception.class)
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
	
	public void modifySysAccountLoginStatus(String id,String loginStatus,Date loginDate);
	
	public List<SysAccount>  getOnlineSysAccountByWorkgroupId(String workgroupId);
	
	public List<HashMap<String,Object>> getAccountAuthMenu(String accountId);
	
	/**
	 * 保存用户权限
	 * @param accountId 用户ID
	 * @param authVo 权限信息
	 */
	public void saveAccountAuth(String accountId,AccountAuthVo authVo);
	/**
	 * 查询用户查询授权信息
	 * @param accountId
	 * @return
	 */
	List<SysAccountQueryAuthKey> getAccountQueryAuthList(String accountId);
	/**
	 * 根据用户编码获取查询授权信息
	 * @param accountId
	 * @return
	 */
	QueryAuthVo getAccountQueryAuth(String accountId);
}
