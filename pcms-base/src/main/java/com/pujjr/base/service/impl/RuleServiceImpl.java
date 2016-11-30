package com.pujjr.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.domain.Product;
import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.dao.RuleDealerMapper;
import com.pujjr.base.dao.RuleFinanceAmountMapper;
import com.pujjr.base.dao.RuleMemberTaskCntMapper;
import com.pujjr.base.dao.RuleProductMapper;
import com.pujjr.base.dao.RuleRemissionMapper;
import com.pujjr.base.domain.RuleDealer;
import com.pujjr.base.domain.RuleFinanceAmount;
import com.pujjr.base.domain.RuleMemberTaskCnt;
import com.pujjr.base.domain.RuleProduct;
import com.pujjr.base.domain.RuleRemission;
import com.pujjr.base.po.WorkgroupRulePo;
import com.pujjr.base.service.IRuleService;
import com.pujjr.utils.Utils;
@Service
public class RuleServiceImpl implements IRuleService {

	@Autowired
	private RuleDealerMapper ruleDealerDao;
	@Autowired
	private RuleProductMapper ruleProductDao;
	@Autowired
	private RuleFinanceAmountMapper ruleFinanceAmountDao;
	@Autowired
	private RuleMemberTaskCntMapper ruleMemberTaskCntDao;
	@Autowired
	private RuleRemissionMapper ruleRemissionDao;
	
	@Override
	public WorkgroupRulePo getWorkgroupRule(String workgroupId) {
		// TODO Auto-generated method stub
		
		List<SysBranch> ruleDealerList =  ruleDealerDao.selectListByWorkgroupId(workgroupId);
		List<Product> ruleProductList = ruleProductDao.selectListByWorkgroupId(workgroupId);
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
	public List<SysBranch> getWorkgroupDealerRule(String workgroupId) {
		// TODO Auto-generated method stub
		return ruleDealerDao.selectListByWorkgroupId(workgroupId);
	}
	@Override
	public List<Product> getWorkgroupProductRule(String workgroupId) {
		// TODO Auto-generated method stub
		return ruleProductDao.selectListByWorkgroupId(workgroupId);
	}
	@Override
	public void saveWorkgroupRule(String workgroupId, WorkgroupRulePo po) {
		// TODO Auto-generated method stub
		List<SysBranch> ruleDealerList = po.getRuleDealerList();
		ruleDealerDao.deleteByWorkgroupId(workgroupId);
		for(SysBranch item:ruleDealerList)
		{
			RuleDealer record = new RuleDealer();
			record.setId(Utils.get16UUID());
			record.setSysBranchId(item.getId());
			record.setWorkgroupId(workgroupId);
			ruleDealerDao.insert(record);
		}
		
		List<Product> ruleProductList = po.getRuleProductList();
		ruleProductDao.deleteByWorkgroupId(workgroupId);
		for(Product item:ruleProductList)
		{
			RuleProduct record = new RuleProduct();
			record.setId(Utils.get16UUID());
			record.setProductCode(item.getProductCode());
			record.setWorkgroupId(workgroupId);
			ruleProductDao.insert(record);
		}
		
		RuleFinanceAmount ruleFinanceAmount = po.getRuleFinanceAmount();
		ruleFinanceAmountDao.deleteByWorkgroupId(workgroupId);
		ruleFinanceAmount.setId(Utils.get16UUID());
		ruleFinanceAmount.setWorkgroupId(workgroupId);
		ruleFinanceAmountDao.insert(ruleFinanceAmount);
	}
	@Override
	public void batchSetAssigneeTaskCnt(String workgroupId, int maxAssigneeTaskCnt, List<String> accountIds) {
		// TODO Auto-generated method stub
		for(String accountId:accountIds)
		{
			ruleMemberTaskCntDao.deleteByWorkgroupIdAndAccountId(workgroupId, accountId);
			RuleMemberTaskCnt record = new RuleMemberTaskCnt();
			record.setId(Utils.get16UUID());
			record.setAccountId(accountId);
			record.setMaxTaskCnt(maxAssigneeTaskCnt);
			record.setWorkgroupId(workgroupId);
			ruleMemberTaskCntDao.insert(record);
		}
		
	}
	@Override
	public void addWorkgroupRemissionRule(String workgroupId, RuleRemission record) {
		// TODO Auto-generated method stub
		ruleRemissionDao.deleteByWorkgroupId(workgroupId);
		record.setWorkgroupId(workgroupId);
		record.setId(Utils.get16UUID());
		ruleRemissionDao.insert(record);
	}
	@Override
	public RuleRemission getWorkgroupRemissionRule(String workgroupId) {
		// TODO Auto-generated method stub
		return ruleRemissionDao.selectByWorkgroupId(workgroupId);
	}

}
