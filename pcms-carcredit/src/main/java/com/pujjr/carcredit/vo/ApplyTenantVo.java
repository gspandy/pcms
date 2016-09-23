package com.pujjr.carcredit.vo;

import java.util.List;

import com.pujjr.carcredit.domain.ApplyTenant;
import com.pujjr.carcredit.domain.ApplyTenantCar;
/**
 * 承租人信息
 * **/
import com.pujjr.carcredit.domain.ApplyTenantHouse;
public class ApplyTenantVo extends  ApplyTenant
{
	private List<ApplyTenantHouse> tenantHouses;
	private List<ApplyTenantCar> tenantCars;
	public List<ApplyTenantHouse> getTenantHouses() {
		return tenantHouses;
	}
	public void setTenantHouses(List<ApplyTenantHouse> tenantHouses) {
		this.tenantHouses = tenantHouses;
	}
	public List<ApplyTenantCar> getTenantCars() {
		return tenantCars;
	}
	public void setTenantCars(List<ApplyTenantCar> tenantCars) {
		this.tenantCars = tenantCars;
	}
}
