package com.pujjr.base.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.domain.Product;
import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.domain.SysWorkgroup;
import com.pujjr.base.service.IProductService;
import com.pujjr.base.service.ISysBranchService;
import com.pujjr.base.service.ISysWorkgroupService;
import com.pujjr.base.domain.RuleFinanceAmount;
import com.pujjr.base.domain.RuleProduct;
import com.pujjr.base.domain.RuleRemission;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.po.WorkgroupRulePo;
import com.pujjr.base.service.IRuleService;
import com.pujjr.base.vo.WorkgroupRuleVo;

@RestController
@RequestMapping(value="/rule")
public class RuleController extends BaseController
{
	@Autowired
	private IRuleService ruleService;
	@Autowired
	private ISysWorkgroupService workgroupService;
	@Autowired
	private IProductService productService;
	@Autowired
	private ISysBranchService sysBranchService;
	
	@RequestMapping(value="/getWorkgroupRule/{workgroupId}",method=RequestMethod.GET)
	public WorkgroupRuleVo getWorkgroupRule(@PathVariable String workgroupId)
	{
		WorkgroupRuleVo vo = new WorkgroupRuleVo();
		WorkgroupRulePo po = ruleService.getWorkgroupRule(workgroupId);
		BeanUtils.copyProperties(po, vo);
		return vo;
	}
	@RequestMapping(value="/getParentWorkgroupFinanceAmountRule/{workgroupId}",method=RequestMethod.GET)
	public RuleFinanceAmount getParentWorkgroupFinanceAmountRule(@PathVariable String workgroupId)
	{
		SysWorkgroup workgroup = workgroupService.getWorkgroupById(workgroupId);
		RuleFinanceAmount item = ruleService.getWorkgroupFinanceAmountRule(workgroup.getParentId());
		return item;
	}
	@RequestMapping(value="/getParentWorkgroupDealerRule/{workgroupId}",method=RequestMethod.GET)
	public List<SysBranch> getParentWorkgroupSysBranchRuleList(@PathVariable String workgroupId)
	{
		SysWorkgroup workgroup = workgroupService.getWorkgroupById(workgroupId);
		List<SysBranch> branchList = ruleService.getWorkgroupDealerRule(workgroup.getParentId());
		if(branchList.size()==0)
		{
			branchList = sysBranchService.getSysBranchList();
		}
		return branchList;
	}
	@RequestMapping(value="/getParentWorkgroupProductRule/{workgroupId}",method=RequestMethod.GET)
	public List<Product> getParentWorkgroupProductRuleList(@PathVariable String workgroupId)
	{
		SysWorkgroup workgroup = workgroupService.getWorkgroupById(workgroupId);
		List<Product> productList = ruleService.getWorkgroupProductRule(workgroup.getParentId());
		if(productList.size()==0)
		{
			productList = productService.getAllEnableProductList();
		}
		return productList;
	}
	@RequestMapping(value="/saveWorkgroupRule/{workgroupId}",method=RequestMethod.POST)
	public void saveWorkgroupRule(@PathVariable String workgroupId,@RequestBody WorkgroupRuleVo vo)
	{
		WorkgroupRulePo po = new WorkgroupRulePo();
		BeanUtils.copyProperties(vo, po);
		ruleService.saveWorkgroupRule(workgroupId, po);
	}
	@RequestMapping(value="/batchSetTaskCnt/{workgroupId}/{maxAssigneeTaskCnt}",method=RequestMethod.POST)
	public void batchSaveAccountAssigneeTaskCnt(@PathVariable String workgroupId,@PathVariable int maxAssigneeTaskCnt,@RequestBody List<SysAccount> accounts)
	{
		List<String> accountIds = new ArrayList<String>();
		for(SysAccount account : accounts)
		{
			accountIds.add(account.getId());
		}
		ruleService.batchSetAssigneeTaskCnt(workgroupId, maxAssigneeTaskCnt, accountIds);
	}
	
	@RequestMapping(value="/saveWorkgroupRuleRemission/{workgroupId}",method=RequestMethod.POST)
	public void saveWorkgroupRuleRemission(@PathVariable String workgroupId,@RequestBody RuleRemission record)
	{
		ruleService.addWorkgroupRemissionRule(workgroupId, record);
	}
	
	@RequestMapping(value="/getWorkgroupRuleRemission/{workgroupId}",method=RequestMethod.GET)
	public RuleRemission getWorkgroupRuleRemission(@PathVariable String workgroupId)
	{
		return ruleService.getWorkgroupRemissionRule(workgroupId);
	}
	
	@RequestMapping(value="/getParentWorkgroupRuleRemission/{workgroupId}",method=RequestMethod.GET)
	public RuleRemission getParentWorkgroupRuleRemission(@PathVariable String workgroupId)
	{
		SysWorkgroup workgroup = workgroupService.getWorkgroupById(workgroupId);
		RuleRemission parentRule = ruleService.getWorkgroupRemissionRule(workgroup.getParentId());
		if(parentRule ==null)
		{
			parentRule = new RuleRemission();
			parentRule.setMaxCapital(10000000.00);
			parentRule.setMaxInterest(10000000.00);
			parentRule.setMaxLateFee(10000000.00);
			parentRule.setMaxOverdueAmt(10000000.00);
			parentRule.setMaxLateFee(10000000.00);
		}
		return parentRule;
	}
}
