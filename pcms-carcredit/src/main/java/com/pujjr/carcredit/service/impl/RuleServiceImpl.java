package com.pujjr.carcredit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.carcredit.dao.RuleDealerMapper;
import com.pujjr.carcredit.dao.RuleFinanceAmountMapper;
import com.pujjr.carcredit.dao.RuleProductMapper;
import com.pujjr.carcredit.domain.RuleDealer;
import com.pujjr.carcredit.domain.RuleFinanceAmount;
import com.pujjr.carcredit.domain.RuleProduct;
import com.pujjr.carcredit.po.WorkgroupRulePo;
import com.pujjr.carcredit.service.IRuleService;
@Service
public class RuleServiceImpl implements IRuleService {

	@Autowired
	private RuleDealerMapper ruleDealerDao;
	@Autowired
	private RuleProductMapper ruleProductDao;
	@Autowired
	private RuleFinanceAmountMapper ruleFinanceAmountDao;
	@Override
	public WorkgroupRulePo getWorkgroupRule(String workgroupId) {
		// TODO Auto-generated method stub
		
		List<RuleDealer> ruleDealerList =  ruleDealerDao.selectListByWorkgroupId(workgroupId);
		List<RuleProduct> ruleProductList = ruleProductDao.selectListByWorkgroupId(workgroupId);
		RuleFinanceAmount ruleFinanceAmount = ruleFinanceAmountDao.selectByWorkgroupId(workgroupId);
		
		WorkgroupRulePo po = new WorkgroupRulePo();
		po.setRuleDealerList(ruleDealerList);
		po.setRuleFinanceAmount(ruleFinanceAmount);
		po.setRuleProductList(ruleProductList);
		return po;
	}
	@Override
	public RuleFinanceAmount getWorkgroupFinanceAmountRule(String workgroupId) {
		// TODO Auto-generated method stub
		return ruleFinanceAmountDao.selectByWorkgroupId(workgroupId);
	}
	@Override
	public List<RuleDealer> getWorkgroupDealerRule(String workgroupId) {
		// TODO Auto-generated method stub
		return ruleDealerDao.selectListByWorkgroupId(workgroupId);
	}
	@Override
	public List<RuleProduct> getWorkgroupProductRule(String workgroupId) {
		// TODO Auto-generated method stub
		return ruleProductDao.selectListByWorkgroupId(workgroupId);
	}

}
