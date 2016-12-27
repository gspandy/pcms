package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.domain.Product;
import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.domain.RuleDealer;
import com.pujjr.base.domain.RuleFinanceAmount;
import com.pujjr.base.domain.RuleProduct;
import com.pujjr.base.domain.RuleRemission;
import com.pujjr.base.po.WorkgroupRulePo;
@Service
@Transactional(rollbackFor=Exception.class)
public interface IRuleService 
{
	public WorkgroupRulePo getWorkgroupRule(String workgroupId);
	
	public RuleFinanceAmount getWorkgroupFinanceAmountRule(String workgroupId);
	
	public List<SysBranch> getWorkgroupDealerRule(String workgroupId);
	
	public List<Product> getWorkgroupProductRule(String workgroupId);
	
	public void saveWorkgroupRule(String workgroupId,WorkgroupRulePo po);
	
	public void batchSetAssigneeTaskCnt(String workgroupId,int maxAssigneeTaskCnt,List<String> accountIds);
	
	public void addWorkgroupRemissionRule(String workgroupId,RuleRemission record);
	
	public RuleRemission getWorkgroupRemissionRule(String workgroupId);
}
