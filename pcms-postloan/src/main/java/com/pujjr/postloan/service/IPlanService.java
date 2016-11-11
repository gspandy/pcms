package com.pujjr.postloan.service;

import java.util.Date;
import java.util.List;

import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.vo.RepayPlanVo;
import com.pujjr.postloan.vo.RepayScheduleVo;

/**
 * 还款计划处理服务
 * **/
public interface IPlanService {
	/**
	 * 生成还款计划
	 * tom 2016年11月10日
	 * @param appId 申请单号
	 * @param fianceAmt 融资总金额
	 * @param monthRate 月利率
	 * @param period 期限
	 * @param valueDate 起息日
	 * @param eInterestMode 利息计息方式
	 */
	void generateRepayPlan(String appId,double fianceAmt,double monthRate,int period,Date valueDate,Enum eInterestMode);
	/**
	 * 获取还款计划列表列表
	 * tom 2016年11月10日
	 * @param appId 申请单号
	 * @param period 还款期数
	 * @return
	 */
	List<RepayPlanVo> selectRepayPlanList(String appId,int period);
	/**
	 * 获取单条还款计划
	 * tom 2016年11月10日
	 * @param appId 申请单号
	 * @param period 还款期数
	 * @return
	 */
	RepayPlanVo selectRepayPlay(String appId,int period);
	
	/**获取还款计划(包含总还款、总利息、本金、月供、各期还款计划明细数据)
	 * tom 2016年11月11日
	 * @param appId
	 * @param period
	 * @return
	 */
	RepayScheduleVo selectRepaySchedule(String appId,int period);
}
