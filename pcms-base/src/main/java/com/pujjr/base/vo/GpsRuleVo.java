package com.pujjr.base.vo;

import java.util.List;

import com.pujjr.base.domain.GpsLvl;
import com.pujjr.base.domain.GpsRule;

public class GpsRuleVo extends GpsRule
{
	private List<GpsLvl> gpsLvls;

	public List<GpsLvl> getGpsLvls() {
		return gpsLvls;
	}

	public void setGpsLvls(List<GpsLvl> gpsLvls) {
		this.gpsLvls = gpsLvls;
	}
	
	
}
