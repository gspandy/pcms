package com.pujjr.carcredit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.carcredit.domain.RuleDealer;
import com.pujjr.carcredit.domain.RuleFinanceAmount;
import com.pujjr.carcredit.domain.RuleProduct;
import com.pujjr.carcredit.po.WorkgroupRulePo;
@Service
public interface IRuleService 
{
	public WorkgroupRulePo getWorkgroupRule(String workgroupId);
	
	public RuleFinanceAmount getWorkgroupFinanceAmountRule(String workgroupId);
	
	public List<RuleDealer> getWorkgroupDealerRule(String workgroupId);
	
	public List<RuleProduct> getWorkgroupProductRule(String workgroupId);
}
