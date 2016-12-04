package com.pujjr.base.po;

import java.util.List;

import com.pujjr.base.domain.Product;
import com.pujjr.base.domain.RuleCollectionTask;
import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.domain.RuleFinanceAmount;
import com.pujjr.base.domain.RuleOverdueDay;

public class WorkgroupRulePo 
{
	
	private RuleFinanceAmount ruleFinanceAmount;
	
	private List<SysBranch> ruleDealerList;
	
	private List<Product> ruleProductList;

	private RuleOverdueDay ruleOverdueDay;
	
	private List<RuleCollectionTask> ruleCollectioTaskList;
	
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

	public RuleOverdueDay getRuleOverdueDay() {
		return ruleOverdueDay;
	}

	public void setRuleOverdueDay(RuleOverdueDay ruleOverdueDay) {
		this.ruleOverdueDay = ruleOverdueDay;
	}

	public List<RuleCollectionTask> getRuleCollectioTaskList() {
		return ruleCollectioTaskList;
	}

	public void setRuleCollectioTaskList(List<RuleCollectionTask> ruleCollectioTaskList) {
		this.ruleCollectioTaskList = ruleCollectioTaskList;
	}



	
}
