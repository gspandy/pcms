package com.pujjr.base.po;

import java.util.List;

import com.pujjr.base.domain.Product;
import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.domain.RuleFinanceAmount;

public class WorkgroupRulePo 
{
	
	private RuleFinanceAmount ruleFinanceAmount;
	
	private List<SysBranch> ruleDealerList;
	
	private List<Product> ruleProductList;

	public RuleFinanceAmount getRuleFinanceAmount() {
		return ruleFinanceAmount;
	}

	public void setRuleFinanceAmount(RuleFinanceAmount ruleFinanceAmount) {
		this.ruleFinanceAmount = ruleFinanceAmount;
	}

	public List<SysBranch> getRuleDealerList() {
		return ruleDealerList;
	}

	public void setRuleDealerList(List<SysBranch> ruleDealerList) {
		this.ruleDealerList = ruleDealerList;
	}

	public List<Product> getRuleProductList() {
		return ruleProductList;
	}

	public void setRuleProductList(List<Product> ruleProductList) {
		this.ruleProductList = ruleProductList;
	}


	
}
