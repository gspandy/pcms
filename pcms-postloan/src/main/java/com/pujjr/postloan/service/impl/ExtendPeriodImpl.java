package com.pujjr.postloan.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pujjr.enumeration.EIntervalMode;
import com.pujjr.postloan.dao.GeneralLedgerMapper;
import com.pujjr.postloan.dao.WaitingChargeMapper;
import com.pujjr.postloan.domain.ApplyExtendPeriod;
import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.domain.WaitingCharge;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.postloan.service.IExtendPeriodService;
import com.pujjr.postloan.vo.ApplyExtendPeriodVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.ExtendPeriodFeeItemVo;
import com.pujjr.postloan.vo.ExtendPeriodTaskVo;
import com.pujjr.postloan.vo.RemissionFeeItemVo;
import com.pujjr.postloan.vo.RepayFeeItemVo;
import com.pujjr.utils.Utils;

/**
 * @author tom
 *
 */
public class ExtendPeriodImpl implements IExtendPeriodService {
	@Autowired
	private IAccountingService accountingServiceImpl;
	@Autowired
	private WaitingChargeMapper waitingChargeMapper;
	@Autowired
	private GeneralLedgerMapper generalLedgerMapper;
	@Override
	public ExtendPeriodFeeItemVo getExtendPeriodFeeItem(String appId) {
		ExtendPeriodFeeItemVo ev = new ExtendPeriodFeeItemVo();
		Date currDate =	Utils.getDate();
		List<WaitingCharge> waitingChargePlanList = accountingServiceImpl.getWaitingChargeTypePlan(appId, false);
		List<WaitingCharge> waitingChargesOtherList = accountingServiceImpl.getWaitingChargeTypeOther(appId);
		RepayPlan currRepayPlan = accountingServiceImpl.getCurrentPeriodRepayPlan(appId);
		//逾期：计划还款
		double overDueCapital = 0.00;
		double overDueInterest = 0.00;
		double overDueFine = 0.00;
		for (WaitingCharge waitingCharge : waitingChargesOtherList) {
			overDueCapital += waitingCharge.getRepayCapital();
			overDueInterest += waitingCharge.getRepayInterest();
			overDueFine += waitingCharge.getRepayOverdueAmount();
		}
		//当期
		double currCaptital = currRepayPlan.getRepayCapital();
		double currInterest = currRepayPlan.getRepayInterest();
		double remainCapital = currRepayPlan.getRemainCapital();//剩余本金
		overDueCapital += currCaptital;
		overDueInterest += currInterest;
		//逾期：其他费用
		double otherCapital = 0.00;
		double otherInterest = 0.00;
		double otherFine = 0.00;
		for (WaitingCharge waitingCharge : waitingChargesOtherList) {
			otherCapital += waitingCharge.getRepayCapital();
			otherFine += waitingCharge.getRepayOverdueAmount();
		}
		
		//新旧还款日利息
		Date currClosingDate = currRepayPlan.getClosingDate();
		long intervalDay = Utils.getTimeInterval(currClosingDate, currDate, EIntervalMode.DAYS);
		//总账表
		GeneralLedger generalledger = generalLedgerMapper.selectByAppId(appId);
		//日利率
		double dayRate = generalledger.getYearRate()/(12*360);
		//日展期费率
		double extendRate = generalledger.getDayExtendRate();
		//日罚息率
		double lateRate = generalledger.getDayLateRate();
		//新旧还款利息
//		BigDecimal newOldInterest = new BigDecimal(remainCapital * dayRate * intervalDay) ;
		double newOldInterest = remainCapital * dayRate * intervalDay;
		//新计息本金
		double newCapital = otherFine + otherCapital 
				+ overDueInterest + overDueFine + newOldInterest +remainCapital;
		//展期费
		double extendFee = (remainCapital - overDueCapital) * extendRate * intervalDay;
		ev.setNewCapital(newCapital);
		ev.setNewOldInterest(newOldInterest);
		ev.setExtendFee(extendFee);
		ev.setOtherAmount(otherCapital);
		ev.setOtherOverdueAmount(otherFine);
		ev.setRemainCapital(remainCapital);
		ev.setRepayCapital(overDueCapital + currCaptital);
		ev.setRepayInterest(overDueInterest + currInterest);
		ev.setRepayOverdueAmount(overDueCapital + overDueInterest + overDueFine);
		//挂账金额
		double stayAmount = accountingServiceImpl.getStayAmount(appId);
		ev.setStayAmount(stayAmount);
		return ev;
	}

	@Override
	public void commitApplyExtendPeriodTask(String appId, ApplyExtendPeriodVo vo) {
		

	}

	@Override
	public void commitApproveExtendPeriodTask(String taskId, ApproveResultVo vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void commitApproveRemissionTask(String taskId, ApproveResultVo vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void commitApplyConfirmExtendPeriodTask(String taskId, RemissionFeeItemVo vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void commitConfirmExtendPeriodTask(String taskId, ApproveResultVo vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelExtendPeriodTask(String taskId, String operId, String cancelComment) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ExtendPeriodTaskVo> getApplyExtendPeriodTaskList(String createId, List<String> applyStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplyExtendPeriod getApplyExtendPeriodTaskById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modifyApplyExtendPeriodInfo(ApplyExtendPeriod record) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteApplyExtendPeriodInfoById(String id) {
		// TODO Auto-generated method stub

	}

}
