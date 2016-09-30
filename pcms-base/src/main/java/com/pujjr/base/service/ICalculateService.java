package com.pujjr.base.service;

public interface  ICalculateService 
{
	//计算月租金
	public double calMonthRent(double financeAmout,int period,double monthRate,String repayMode);
}
