package com.pujjr.carcredit.dao;

import com.pujjr.carcredit.domain.Apply;
import com.pujjr.carcredit.domain.ApplySpouse;
import com.pujjr.carcredit.domain.ApplyTenantCar;
import com.pujjr.carcredit.domain.ApplyTenantHouse;
import com.pujjr.carcredit.vo.ApplyCloesseeVo;
import com.pujjr.carcredit.vo.ApplyFamilyDebtVo;
import com.pujjr.carcredit.vo.ApplyFinanceVo;
import com.pujjr.carcredit.vo.ApplyLinkmanVo;
import com.pujjr.carcredit.vo.ApplySpouseVo;
import com.pujjr.carcredit.vo.ApplyTenantVo;

public interface IApplyDao {
	public void insertApply(Apply apply,String accountId);
	public void updApply(Apply apply,String accountId);
	public void insertApplyFinance(ApplyFinanceVo afv,String accountId);	
	public void updApplyFinance(ApplyFinanceVo afv,String accountId);	
	public void insertApplySpouse(ApplySpouseVo asv,String accountId);	
	public void updApplySpouse(ApplySpouseVo asv,String accountId);
	public void insertApplyCloessee(ApplyCloesseeVo acv,String accountId);	
	public void updApplyCloessee(ApplyCloesseeVo acv,String accountId);
	public void insertApplyLinkman(ApplyLinkmanVo alv,String accountId);	
	public void updApplyLinkman(ApplyLinkmanVo alv,String accountId);
	public void insertApplyFamilyDebt(ApplyFamilyDebtVo afdv,String account);	
	public void updApplyFamilyDebt(ApplyFamilyDebtVo afdv,String accountId);
	public void insertApplyTenant(ApplyTenantVo atv,String accountId);
	public void updApplyTenant(ApplyTenantVo atv,String accountId);
	public void insertApplyTenantHouse(ApplyTenantHouse telantHouse,String accountId);
	public void updApplyTenantHouse(ApplyTenantHouse telantHouse,String accountId);
	public void insertApplyTenantCar(ApplyTenantCar telantCar,String accountId);
	public void updApplyTenantCar(ApplyTenantCar telantCar,String accountId);

}
