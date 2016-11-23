package com.pujjr.postloan.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.domain.WaitingCharge;
import com.pujjr.postloan.enumeration.ChargeItem;
import com.pujjr.postloan.enumeration.LoanApplyTaskType;
import com.pujjr.postloan.enumeration.RepayMode;
import com.pujjr.postloan.vo.RepayingFeeItemVo;

/**
 * 账务核心服务
 * **/
@Service
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
	public double getSettleRate(String appId);
	/**
	 * 功能：查询客户当前正在还款的分类汇总费用信息
	 * @param appId 申请单号
	 * @param isCalOverdueAmount 是否计算逾期金额
	 * @param overdueEndDate 计算逾期截止日期
	 * @param isReduceStayAmount 是否扣除挂账金额
	 * @param isContainCurPeriod 是否包含当期还款信息
	 * @return
	 * 	正在还款的分类汇总费用信息
	 * **/
	public RepayingFeeItemVo getRepayingFeeItems(String appId,boolean isCalOverdueAmount,Date overdueEndDate,boolean isReduceStayAmount,boolean isContainCurPeriod);
	/**
	 * 功能：查询当前正在扣款中的计划还款信息
	 * @param
	 * 	appId-申请单号
	 * @return
	 * 	计划还款待扣款明细
	 * **/
	public List<WaitingCharge> getWaitingChargeTypePlan(String appId,boolean isContainCurPeriod);
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
	 * @param appId 申请单号
	 * @param queryPeriod 查询期数
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
	 * 功能：还款冲账处理(重要接口)
	 * @param appId-申请单号
	 * @param repayAmount-还款金额
	 * @param repayDate-还款日期 
	 * @param repayMode-还款方式
	 * @param chargeItem-还款费用项目，如果为NULL则按照正常的冲账顺序冲账
	 * @return
	 * **/
	public void repayReverseAccounting(String appId,double repayAmount,Date repayDate,RepayMode repayMode,ChargeItem chargeItem);
	/**
	 * 功能：用新的代扣明细表对提前结清、展期、变更还款日交易进行冲账处理，并用新的还款计划替换原还款计划
	 * @param applyId-申请任务ID
	 * @param applyType
	 * @param appId-关联申请单号
	 * @return
	 * **/
	public void repayReverseAccountingUserNewWaitingChargeTable(String applyId,LoanApplyTaskType applyType,String appId);
	/**
	 * 获取当前挂账金额
	 * @param appId申请单号
	 * @return
	 */
	public double getStayAmount(String appId);
	/**
	 * 检查是否可执行对公还款
	 * @param appId
	 * @return
	 */
	public boolean checkCandoPublicRepay(String appId);
}
