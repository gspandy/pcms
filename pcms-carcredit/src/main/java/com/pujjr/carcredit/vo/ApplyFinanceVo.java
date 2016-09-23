package com.pujjr.carcredit.vo;

import com.pujjr.base.domain.CarStyle;
import com.pujjr.base.domain.GpsLvl;
import com.pujjr.carcredit.domain.ApplyFinance;
/**
 * 融资信息
 * **/
public class ApplyFinanceVo extends ApplyFinance
{
	//是否选择车辆
	private boolean select;
	//融资车辆信息
	private CarStyle carStyle;
	//GPS价格档位
	private GpsLvl gpsLvl;
	
	public GpsLvl getGpsLvl() {
		return gpsLvl;
	}

	public void setGpsLvl(GpsLvl gpsLvl) {
		this.gpsLvl = gpsLvl;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public CarStyle getCarStyle() {
		return carStyle;
	}

	public void setCarStyle(CarStyle carStyle) {
		this.carStyle = carStyle;
	}
	
	public Object clone() throws CloneNotSupportedException{
		Object finance = super.clone();
		return finance;
	}
}
