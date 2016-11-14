package com.pujjr.postloan.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.domain.WaitingCharge;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.postloan.vo.RepayingFeeItemVo;

/**
 * @author tom
 *
 */
@Service
public class AccountingServiceImpl implements IAccountingService {

	@Override
	public RepayPlan getCurrentPeriodRepayPlan(String appId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RepayPlan getLatestPeriodRepayPlan(String appId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getRemainCaptial(String appId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getSettleRate(String appId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RepayingFeeItemVo getRepayingFeeItems(String appId, boolean isCalOverdueAmount, Date overdueEndDate,
			boolean isReduceStayAmount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WaitingCharge> getWaitingChargeTypePlan(String appId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WaitingCharge> getWaitingChargeTypeOther(String appId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RepayPlan> getAfterCurrentPeriodRepayPlan(String appId, int queryPeriod) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getAfterCurrentPeriodRemainPeroidList(String appId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void repayReverseAccounting(String appId, double repayAmount, String repayMode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void repayReverseAccountingUserNewWaitingChargeTable(String appId, double repayAmount, String repayMode) {
		// TODO Auto-generated method stub

	}

}
