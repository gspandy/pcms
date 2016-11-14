package com.pujjr.postloan.service;

import org.springframework.stereotype.Service;

@Service
public interface IScheduleService 
{
	/**
	 * 贷后日切计划任务，完成账务核算
	 * **/
	public void CutOff();
}
