package com.pujjr.carcredit.domain;

import java.util.List;

public class ApplyTenant {
    private String id;

    private String appId;

    private String name;

    private String idType;

    private String idNo;

    private Integer age;

    private String sex;

    private String mobile;

    private String qq;

    private String weixin;

    private String education;

    private String marryStatus;

    private String addrProvince;

    private String addrCity;

    private String addrCounty;

    private String addrExt;

    private String houseOwner;

    private String houseMate;

    private String houseHold;

    private String familyMember;

    private String liveTime;

    private String unitName;

    private String unitType;

    private String unitIndustry;

    private String unitTel;

    private String rank;

    private String ranName;

    private String unitAddrProvince;

    private String unitAddrCity;

    private String unitAddrCounty;

    private String unitAddrExt;

    private Double montyIncome;

    private Double yearIncome;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMarryStatus() {
        return marryStatus;
    }

    public void setMarryStatus(String marryStatus) {
        this.marryStatus = marryStatus;
    }

    public String getAddrProvince() {
        return addrProvince;
    }

    public void setAddrProvince(String addrProvince) {
        this.addrProvince = addrProvince;
    }

    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    public String getAddrCounty() {
        return addrCounty;
    }

    public void setAddrCounty(String addrCounty) {
        this.addrCounty = addrCounty;
    }

    public String getAddrExt() {
        return addrExt;
    }

    public void setAddrExt(String addrExt) {
        this.addrExt = addrExt;
    }

    public String getHouseOwner() {
        return houseOwner;
    }

    public void setHouseOwner(String houseOwner) {
        this.houseOwner = houseOwner;
    }

    public String getHouseMate() {
        return houseMate;
    }

    public void setHouseMate(String houseMate) {
        this.houseMate = houseMate;
    }

    public String getHouseHold() {
        return houseHold;
    }

    public void setHouseHold(String houseHold) {
        this.houseHold = houseHold;
    }

    public String getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(String familyMember) {
        this.familyMember = familyMember;
    }

    public String getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(String liveTime) {
        this.liveTime = liveTime;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getUnitIndustry() {
        return unitIndustry;
    }

    public void setUnitIndustry(String unitIndustry) {
        this.unitIndustry = unitIndustry;
    }

    public String getUnitTel() {
        return unitTel;
    }

    public void setUnitTel(String unitTel) {
        this.unitTel = unitTel;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRanName() {
        return ranName;
    }

    public void setRanName(String ranName) {
        this.ranName = ranName;
    }

    public String getUnitAddrProvince() {
        return unitAddrProvince;
    }

    public void setUnitAddrProvince(String unitAddrProvince) {
        this.unitAddrProvince = unitAddrProvince;
    }

    public String getUnitAddrCity() {
        return unitAddrCity;
    }

    public void setUnitAddrCity(String unitAddrCity) {
        this.unitAddrCity = unitAddrCity;
    }

    public String getUnitAddrCounty() {
        return unitAddrCounty;
    }

    public void setUnitAddrCounty(String unitAddrCounty) {
        this.unitAddrCounty = unitAddrCounty;
    }

    public String getUnitAddrExt() {
        return unitAddrExt;
    }

    public void setUnitAddrExt(String unitAddrExt) {
        this.unitAddrExt = unitAddrExt;
    }

    public Double getMontyIncome() {
        return montyIncome;
    }

    public void setMontyIncome(Double montyIncome) {
        this.montyIncome = montyIncome;
    }

    public Double getYearIncome() {
        return yearIncome;
    }

    public void setYearIncome(Double yearIncome) {
        this.yearIncome = yearIncome;
    }
    
    private List<ApplyTenantHouse>  tenantHouses;
    
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