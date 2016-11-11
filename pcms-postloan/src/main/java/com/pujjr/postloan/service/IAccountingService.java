package com.pujjr.postloan.service;

import java.util.Date;
import java.util.List;

import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.domain.WaitingCharge;
import com.pujjr.postloan.vo.RepayFeeItemVo;
import com.pujjr.postloan.vo.RepayingFeeItemVo;

/**
 * 账务核心服务
 * **/
public interface IAccountingService 
{
	/**
	 * 功能：查询客户当期还款计划
	 * 参数：
	 * 	appId-申请单号
	 * 返回：
	 * 	当期还款计划
	 * **/
	public RepayPlan getCurrentPeriodRepayPlan(String appId);
	/**
	 * 功能：查询客户最后一期还款计划
	 * 参数：
	 * 	appId-申请单号
	 * 返回：
	 * 	最后一期还款计划
	 * **/
	public RepayPlan getLatestPeriodRepayPlan(String appId);
	/**
	 * 功能：查询客户剩余本金
	 * 参数：
	 * 	appId-申请单号
	 * 返回：
	 * 	剩余本金
	 * **/
	public double getRemainCaptial(String appId);
	/**
	 * 功能：查询提前结清违约金率
	 * @param 
	 * 	appId-申请单号
	 * @return
	 * 	提前结清违约金率
	 * **/
	public double getSettleReate(String appId);
	/**
	 * 功能：查询客户当前正在还款的分类汇总费用信息
	 * @param 
	 * 	appId-申请单号
	 * 	isCalOverdueAmount-是否计算逾期金额
	 * 	overdueEndDate-计算逾期截止日期
	 * 	isReduceStayAmount-是否扣除挂账金额
	 * @return
	 * 	正在还款的分类汇总费用信息
	 * **/
	public RepayingFeeItemVo getRepayingFeeItems(String appId,boolean isCalOverdueAmount,Date overdueEndDate,boolean isReduceStayAmount);
	/**
	 * 功能：查询当前正在扣款中的计划还款信息
	 * @param
	 * 	appId-申请单号
	 * @return
	 * 	计划还款待扣款明细
	 * **/
	public List<WaitingCharge> getWaitingChargeTypePlan(String appId);
	/**
	 * 功能：查询当前正在扣款中的其他费用还款信息
	 * @param
	 * 	appId-申请单号
	 * @return
	 * 	其他费用待扣款明细
	 * **/
	public List<WaitingCharge> getWaitingChargeTypeOther(String appId);
	/**
	 * 功能：查询当前期数后续指定期数，为queryPeriod=0是查询后续所有期数
	 * @param
	 * 	appId-申请单号
	 * 	queryPeriod-查询期数
	 * @return
	 * 	返回还款计划信息
	 * **/
	public List<RepayPlan> getAfterCurrentPeriodRepayPlan(String appId,int queryPeriod);
	/**
	 * 功能：查询当前期数后续期数信息
	 * @param
	 * 	appId-申请单号
	 * @return
	 * 	剩余期数信息列表
	 * **/
	public List<Integer> getAfterCurrentPeriodRemainPeroidList(String appId);
	/**
	 * 功能：还款冲账处理
	 * @param
	 * 	appId-申请单号
	 * 	repayAmount-还款金额
	 * 	repayMode-还款方式
	 * @return
	 * **/
	public void repayReverseAccounting(String appId,double repayAmount,String repayMode);
	/**
	 * 功能：用新的代扣明细表冲账处理
	 * @param
	 * 	appId-申请单号
	 * 	repayAmount-还款金额
	 * 	repayMode-还款方式
	 * @return
	 * **/
	public void repayReverseAccountingUserNewWaitingChargeTable(String appId,double repayAmount,String repayMode);
}