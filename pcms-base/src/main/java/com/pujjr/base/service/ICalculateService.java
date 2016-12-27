package com.pujjr.base.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor=Exception.class)
public interface  ICalculateService 
{
	//计算月租金
	public double calMonthRent(double financeAmout,int period,double monthRate,String repayMode);
}
