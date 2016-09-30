package com.pujjr.carcredit.po;

import java.util.List;

import com.pujjr.carcredit.domain.RuleDealer;
import com.pujjr.carcredit.domain.RuleFinanceAmount;
import com.pujjr.carcredit.domain.RuleProduct;

public class WorkgroupRulePo 
{
	private RuleFinanceAmount ruleFinanceAmount;
	
	private List<RuleDealer> ruleDealerList;
	
	private List<RuleProduct> ruleProductList;

	public RuleFinanceAmount getRuleFinanceAmount() {
		return ruleFinanceAmount;
	}

	public void setRuleFinanceAmount(RuleFinanceAmount ruleFinanceAmount) {
		this.ruleFinanceAmount = ruleFinanceAmount;
	}

	public List<RuleDealer> getRuleDealerList() {
		return ruleDealerList;
	}

	public void setRuleDealerList(List<RuleDealer> ruleDealerList) {
		this.ruleDealerList = ruleDealerList;
	}

	public List<RuleProduct> getRuleProductList() {
		return ruleProductList;
	}

	public void setRuleProductList(List<RuleProduct> ruleProductList) {
		this.ruleProductList = ruleProductList;
	}
}
