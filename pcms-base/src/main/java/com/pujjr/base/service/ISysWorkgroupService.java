package com.pujjr.base.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.domain.SysWorkgroup;
@Service
@Transactional(rollbackFor=Exception.class)
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
	
	public List<SysWorkgroup> getSysWorkgroupListByParentId(String parentId);
	
	public List<HashMap> getMatchRuleAccountList(String productCode, double financeAmount, String dealerId,List<SysWorkgroup> groups,List<String> candidateAccounts);
	/**
	 * 按照产品编码、经销商、逾期天数查询满足条件工作组ID
	 * @param productCode
	 * @param dealerId
	 * @param groups
	 * @param candidateAccounts
	 * @return
	 */
	public List<HashMap> getCollectionTaskMatchRuleAccountList(String productCode,String dealerId,int overdueDay,List<SysWorkgroup> groups,List<String> candidateAccounts);
	/**
	 * 按照产品编码、经销商、逾期天数、催收任务类型查询满足条件工作组ID
	 * @param productCode
	 * @param dealerId
	 * @param overdueDay
	 * @param taskType
	 * @param groups
	 * @param candidateAccounts
	 * @return
	 */
	public List<HashMap> getCollectionDeployTaskMatchRuleAccountList(String productCode,String dealerId,int overdueDay,String taskType,List<SysWorkgroup> groups,List<String> candidateAccounts);
	
	public List<HashMap> getWorkgroupOnlineAccountList(List<SysWorkgroup> groups,boolean checkOnline);
	
	public SysWorkgroup getWorkgroupByName(String workgroupName);
	
	public List<SysWorkgroup> getChildWorkgroup(String workgroupId,boolean includeSelf );
}
