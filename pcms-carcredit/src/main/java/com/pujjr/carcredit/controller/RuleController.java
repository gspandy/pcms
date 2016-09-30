package com.pujjr.carcredit.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.domain.SysWorkgroup;
import com.pujjr.base.service.IProductService;
import com.pujjr.base.service.ISysWorkgroupService;
import com.pujjr.carcredit.domain.RuleFinanceAmount;
import com.pujjr.carcredit.domain.RuleProduct;
import com.pujjr.carcredit.po.WorkgroupRulePo;
import com.pujjr.carcredit.service.IRuleService;
import com.pujjr.carcredit.vo.WorkgroupRuleVo;

@RestController
@RequestMapping(value="/rule")
public class RuleController 
{
	@Autowired
	private IRuleService ruleService;
	@Autowired
	private ISysWorkgroupService workgroupService;
	@Autowired
	private IProductService productService;
	
	@RequestMapping(value="/getWorkgroupRule/{workgroupId}",method=RequestMethod.GET)
	public WorkgroupRuleVo getWorkgroupRule(String workgroupId)
	{
		WorkgroupRuleVo vo = new WorkgroupRuleVo();
		WorkgroupRulePo po = ruleService.getWorkgroupRule(workgroupId);
		BeanUtils.copyProperties(po, vo);
		return vo;
	}
	@RequestMapping(value="/getParentWorkgroupFinanceAmountRule/{workgroupId}",method=RequestMethod.GET)
	public RuleFinanceAmount getParentWorkgroupFinanceAmountRule(String workgroupId)
	{
		SysWorkgroup workgroup = workgroupService.getWorkgroupById(workgroupId);
		RuleFinanceAmount item = ruleService.getWorkgroupFinanceAmountRule(workgroup.getParentId());
		return item;
	}
}
