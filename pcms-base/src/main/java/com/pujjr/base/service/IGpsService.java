package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.base.domain.GpsLvl;
import com.pujjr.base.domain.GpsRule;
@Service
public interface IGpsService 
{
	public List<GpsRule> getAllGpsRuleList();
	
	public List<GpsLvl> getGpsLvlListByBranchIdAndAmt(String branchId,double amt);
	
	public List<GpsLvl> getGpsLvlListByBranchId(String branchId);
	
	public List<GpsLvl> getGpsLvlListByBranchIdAndRuleId(String branchId,String ruleId);
	
	public List<GpsLvl> getAllGpsLvlList();
	
	
}
