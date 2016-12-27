package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.domain.GpsLvl;
import com.pujjr.base.domain.GpsRule;
import com.pujjr.base.domain.GpsSupplier;
@Service
@Transactional(rollbackFor=Exception.class)
public interface IGpsService 
{
	public List<GpsRule> getAllGpsRuleList();
	
	public void addGpsRule(GpsRule record);
	
	public void modifyGspRule(GpsRule record);
	
	public void deleteGpsRuleById(String id) throws Exception;
	
	public List<GpsLvl> getAllGpsLvlList();
	
	public void addGpsLvl(GpsLvl record);
	
	public void modifyGpsLvl(GpsLvl  record);
	
	public void deleteGpsLvlById(String id) throws Exception;
	
	public List<GpsSupplier> getAllGpsSuppilerList();
	
	public void addGpsSupplier(GpsSupplier record);
	
	public void modifyGpsSupplier(GpsSupplier record);
	
	public void deleteGpsSupplierById(String id);
	
	public List<GpsSupplier> getAllEnabledGpsSupplierList();
	
	public List<GpsLvl> getGpsLvlListByBranchIdAndAmt(String branchId,double amt);
	
	public List<GpsLvl> getGpsLvlListByBranchId(String branchId);
	
	public List<GpsLvl> getGpsLvlListByBranchIdAndRuleId(String branchId,String ruleId);
	
	
	
	
	
}
