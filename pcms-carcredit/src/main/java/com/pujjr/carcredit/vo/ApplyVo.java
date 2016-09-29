package com.pujjr.carcredit.vo;

import java.util.List;

import com.pujjr.base.domain.Product;
import com.pujjr.carcredit.domain.Apply;
import com.pujjr.carcredit.domain.ApplyTenant;
/**
 * 申请单对象
 * **/
public class ApplyVo extends Apply
{
	//产品信息
	public Product product;
	//融资信息
	public List<ApplyFinanceVo> finances;
	//承租人信息
	public ApplyTenantVo tenant;
	//配偶信息
	public ApplySpouseVo spouse;
	//共租人信息
	public ApplyCloesseeVo cloessee;
	//联系人信息
	public List<ApplyLinkmanVo> linkmans;
	//家庭负债
	public ApplyFamilyDebtVo familyDebt;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public List<ApplyFinanceVo> getFinances() {
		return finances;
	}
	public void setFinances(List<ApplyFinanceVo> finances) {
		this.finances = finances;
	}
	public ApplyTenantVo getTenant() {
		return tenant;
	}
	public void setTenant(ApplyTenantVo tenant) {
		this.tenant = tenant;
	}
	public ApplySpouseVo getSpouse() {
		return spouse;
	}
	public void setSpouse(ApplySpouseVo spouse) {
		this.spouse = spouse;
	}
	public List<ApplyLinkmanVo> getLinkmans() {
		return linkmans;
	}
	public void setLinkmans(List<ApplyLinkmanVo> linkmans) {
		this.linkmans = linkmans;
	}
	public ApplyFamilyDebtVo getFamilyDebt() {
		return familyDebt;
	}
	public void setFamilyDebt(ApplyFamilyDebtVo familyDebt) {
		this.familyDebt = familyDebt;
	}
	public ApplyCloesseeVo getCloessee() {
		return cloessee;
	}
	public void setCloessee(ApplyCloesseeVo cloessee) {
		this.cloessee = cloessee;
	}
	
	
	
}
