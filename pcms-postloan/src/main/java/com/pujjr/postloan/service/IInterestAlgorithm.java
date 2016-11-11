package com.pujjr.postloan.service;

import java.util.Date;

import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.po.RepaySchedulePo;

/**还款算法
 * @author tom
 */
public interface IInterestAlgorithm {
	/**
	 * 等额本息
	 * tom 2016年11月10日
	 * @param fianceAmt 融资总金额
	 * @param monthRate 月利率
	 * @param period 期限
	 * @param valueDate 起息日
	 * @return
	 */
	RepaySchedulePo cpmInterest(double fianceAmt,double monthRate,int period,Date valueDate);
	/**
	 * 按月付息到期还本
	 * tom 2016年11月10日
	 * @param fianceAmt 融资总金额
	 * @param monthRate 月利率
	 * @param period 期限
	 * @param valueDate 起息日
	 * @return
	 */
	RepaySchedulePo monthlyIntetrest(double fianceAmt,double monthRate,int period,Date valueDate);
	/**
	 * 一次付息按月还本
	 * tom 2016年11月10日
	 * @param fianceAmt 融资总金额
	 * @param monthRate 月利率
	 * @param period 期限
	 * @param valueDate 起息日
	 * @return
	 */
	RepaySchedulePo onetimeInterest(double fianceAmt,double monthRate,int period,Date valueDate);
	/**
	 * 等额利息按月还款
	 * tom 2016年11月10日
	 * @param fianceAmt 融资总金额
	 * @param monthRate 月利率
	 * @param period 期限
	 * @param valueDate 起息日
	 * @return
	 */
	RepaySchedulePo constInterest(double fianceAmt,double monthRate,int period,Date valueDate);
}
