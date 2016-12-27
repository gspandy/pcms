package com.pujjr.postloan.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.enumeration.EInterestMode;
import com.pujjr.postloan.vo.RepayPlanVo;
import com.pujjr.postloan.vo.RepayScheduleVo;

/**
 * 还款计划处理服务
 * **/
@Service
@Transactional(rollbackFor=Exception.class)
public interface IPlanService {
	
	/**
	 * 获取预刷新后还款计划(展期，指定计息方式)
	 * tom 2016年11月17日
	 * @param appId 申请单号
	 * @param fianceAmt 本金
	 * @param monthRate 月利率
	 * @param period 计息期数
	 * @param valueDate 起息日（首期）
	 * @param eInterestMode 计息方式
	 * @param startPeriod 当期还款周期(新还款计划第一期周期数：startPeriod+1)
	 * @return
	 */
	public List<RepayPlan> selectRefreshRepayPlanListExtendPeriod(String appId,double fianceAmt,double monthRate,int period,Date valueDate,Enum eInterestMode,int currPeriod);
	
	/**获取预刷新后还款计划（全额提前结清）
	 * tom 2016年11月21日
	 * @param appId
	 * @param financeAmt
	 * @param currPeriod
	 * @param lastPeriod
	 * @return
	 */
	public List<RepayPlan> selectRefreshRepayPlanList(String appId,double financeAmt,int currPeriod,int lastPeriod);
	
	/**
	 * 获取预刷新后还款计划（沿用原计息方式）
	 * tom 2016年11月17日
	 * @param appId 申请单号
	 * @param fianceAmt 本金
	 * @param monthRate 月利率
	 * @param period 计息期数
	 * @param valueDate 起息日（首期）
	 * @param startPeriod 当期还款周期
	 * @return
	 */
	public List<RepayPlan> selectRefreshRepayPlanList(String appId,double financeAmt,double monthRate,int period,Date valueDate,int currPeriod);
	
	
	/**
	 * 获取预刷新后还款计划(指定计息方式)
	 * tom 2016年11月17日
	 * @param appId 申请单号
	 * @param fianceAmt 本金
	 * @param monthRate 月利率
	 * @param period 计息期数
	 * @param valueDate 起息日（首期）
	 * @param eInterestMode 计息方式
	 * @param startPeriod 当期还款周期(新还款计划第一期周期数：startPeriod+1)
	 * @return
	 */
	public List<RepayPlan> selectRefreshRepayPlanList(String appId,double fianceAmt,double monthRate,int period,Date valueDate,Enum eInterestMode,int currPeriod);
	
	/**刷新还款计划（沿用原计息方式）
	 * tom 2016年11月17日
	 * @param appId 申请单号
	 * @param fianceAmt 本金
	 * @param monthRate 月利率
	 * @param period 计息期数
	 * @param valueDate 起息日（首期）
	 * @param eInterestMode 计息方 式
	 * @param startPeriod 当期还款周期
	 */
	public void refreshRepayPlan(String appId,double financeAmt,double monthRate,int period,Date valueDate,int currPeriod);
	
	/**刷新还款计划(指定计息方式)
	 * tom 2016年11月17日
	 * @param appId 申请单号
	 * @param fianceAmt 本金
	 * @param monthRate 月利率
	 * @param period 计息期数
	 * @param valueDate 起息日（首期）
	 * @param eInterestMode 计息方 式
	 * @param startPeriod 当期还款周期
	 */
	public void refreshRepayPlan(String appId,double financeAmt,double monthRate,int period,Date valueDate,Enum eInterestMode,int currPeriod);
	/**
	 * 生成还款计划
	 * tom 2016年11月10日
	 * @param appId 申请单号
	 * @param fianceAmt 融资总金额
	 * @param monthRate 月利率
	 * @param period 期限
	 * @param valueDate 起息日（首期）
	 * @param eInterestMode 利息计息方式
	 */
	public void generateRepayPlan(String appId,double fianceAmt,double monthRate,int period,Date valueDate,Enum eInterestMode);
	/**
	 * 获取还款计划列表列表
	 * tom 2016年11月10日
	 * @param appId 申请单号
	 * @param period 还款期数
	 * @return
	 */
	public List<RepayPlanVo> selectRepayPlanList(String appId,int period);
	/**
	 * 获取单条还款计划
	 * tom 2016年11月10日
	 * @param appId 申请单号
	 * @param period 还款期数
	 * @return
	 */
	public RepayPlanVo selectRepayPlay(String appId,int period);
	
	/**获取还款计划(包含总还款、总利息、本金、月供、各期还款计划明细数据)
	 * tom 2016年11月11日
	 * @param appId
	 * @param period
	 * @return
	 */
	public RepayScheduleVo selectRepaySchedule(String appId,int period);
	/**
	 * 获取制定申请单指定期数的还款计划
	 * @param appId-申请单号
	 * @param beginPeriod-开始期数
	 * @param endPeriod-截止期数
	 * @return 还款计划列表
	 * **/
	public List<RepayPlan> getRepayPlanList(String appId,int beginPeriod, int endPeriod);
	
	/**
	 * 查询总账表对应订单计息方式
	 * tom 2016年11月18日
	 * @param appId
	 * @return 计息方式枚举对象
	 */
	public EInterestMode getInterestMode(@Param("appId") String appId);
	
	
}
