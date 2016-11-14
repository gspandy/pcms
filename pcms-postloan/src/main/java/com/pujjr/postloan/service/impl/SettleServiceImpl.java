package com.pujjr.postloan.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.postloan.domain.ApplySettle;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.postloan.service.ISettleService;
import com.pujjr.postloan.vo.ApplySettleVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.RemissionFeeItemVo;
import com.pujjr.postloan.vo.RepayFeeItemVo;
import com.pujjr.postloan.vo.SettleFeeItemVo;
import com.pujjr.postloan.vo.SettleTaskVo;
import com.pujjr.utils.Utils;

/**
 * @author tom
 *
 */
@Service
public class SettleServiceImpl implements ISettleService{
	@Autowired
	private IAccountingService accountingServiceImpl;
	@Autowired
	private IRunWorkflowService runWorkflowServiceImpl;
	@Override
	public SettleFeeItemVo getAllSettleFeeItem(String appId, Date settleEeffectDate) {
		boolean isCalOverdueAmount = true;
		boolean isReduceStayAmount = true;
		double hangAmount= 0.00;//挂账金额
		SettleFeeItemVo settleFeeItemVo = new SettleFeeItemVo();
		RepayFeeItemVo repayFeeItemVo = accountingServiceImpl.getRepayingFeeItems(appId, isCalOverdueAmount, settleEeffectDate, isReduceStayAmount);
		BeanUtils.copyProperties(repayFeeItemVo, settleFeeItemVo);
		//剩余本金
		double remainCapital = settleFeeItemVo.getRemainCapital();
		//应还本金
		double repayCapital = settleFeeItemVo.getRepayCapital();
		//结清本金
		double settleCapital = remainCapital - repayCapital;
		//当期已起息未结账本金
		double currRepayCapital = accountingServiceImpl.getCurrentPeriodRepayPlan(appId).getRepayCapital();
		double lateRate  = accountingServiceImpl.getSettleRate(appId);
		//违约金
		double lateFee = lateRate * (settleCapital + currRepayCapital);
		//结清金额
		double settleTotalAmt = repayFeeItemVo.getRepayCapital() + repayFeeItemVo.getRepayInterest() + repayFeeItemVo.getOtherOverdueAmount()
				+ repayFeeItemVo.getOtherAmount() + repayFeeItemVo.getOtherOverdueAmount()
				+ settleCapital
				+ lateFee
				- hangAmount;
		//结清后剩余本金
		double settleAfterAmount = 0.00;
		settleFeeItemVo.setLateFee(lateFee);
		settleFeeItemVo.setSettleTotalAmount(settleTotalAmt);
		settleFeeItemVo.setSettleCapital(settleCapital);
		settleFeeItemVo.setSettleAfterAmount(settleAfterAmount);
		return settleFeeItemVo;
	}

	@Override
	public SettleFeeItemVo getPartSettleFeeItem(String appId, int beginPeriod, int endPeriod, Date settleEeffectDate) {
		boolean isCalOverdueAmount = true;
		boolean isReduceStayAmount = true;
		double hangAmount= 0.00;//挂账金额
		SettleFeeItemVo settleFeeItemVo = new SettleFeeItemVo();
		RepayFeeItemVo repayFeeItemVo = accountingServiceImpl.getRepayingFeeItems(appId, isCalOverdueAmount, settleEeffectDate, isReduceStayAmount);
		BeanUtils.copyProperties(repayFeeItemVo, settleFeeItemVo);
		//剩余本金
		double remainCapital = settleFeeItemVo.getRemainCapital();
		//应还本金
		double repayCapital = settleFeeItemVo.getRepayCapital();
		//结清本金
		double settleCapital = 0.00;
		int queryPeriod = endPeriod - beginPeriod;
		List<RepayPlan> repayPlanList = accountingServiceImpl.getAfterCurrentPeriodRepayPlan(appId, queryPeriod);
		for (RepayPlan repayPlan : repayPlanList) {
			settleCapital += repayPlan.getRepayCapital();
		}
		//当期已起息未结账本金
		double currRepayCapital = accountingServiceImpl.getCurrentPeriodRepayPlan(appId).getRepayCapital();
		double lateRate  = accountingServiceImpl.getSettleRate(appId);
		//违约金
		double lateFee = lateRate * (settleCapital + currRepayCapital);
		//结清金额
		double settleTotalAmt = repayFeeItemVo.getRepayCapital() + repayFeeItemVo.getRepayInterest() + repayFeeItemVo.getOtherOverdueAmount()
				+ repayFeeItemVo.getOtherAmount() + repayFeeItemVo.getOtherOverdueAmount()
				+ settleCapital
				+ lateFee
				- hangAmount;
		//结清后剩余本金
		double settleAfterAmount = remainCapital - repayCapital - settleCapital;
		settleFeeItemVo.setLateFee(lateFee);
		settleFeeItemVo.setSettleCapital(settleCapital);
		settleFeeItemVo.setSettleAfterAmount(0.00);
		settleFeeItemVo.setSettleTotalAmount(settleTotalAmt);
		return settleFeeItemVo;
	}

	@Override
	public void commitApplySettleTask(String appId, ApplySettleVo vo) {
		RepayPlan lastRepayPlan = accountingServiceImpl.getLatestPeriodRepayPlan(appId);
		int endPeriod = vo.getEndPeriod();
		ApplySettle as = new ApplySettle();
		as.setId(Utils.get16UUID());
		as.setAppId(appId);
		if(vo.getEndPeriod() < lastRepayPlan.getPeriod()){
			as.setSettleType("");
		}else{
			
		}
		
		
		SettleFeeItemVo settleFeeItemVo = this.getPartSettleFeeItem(appId, vo.getBeginPeriod(), vo.getEndPeriod(), vo.getApplyEffectDate());
		double settleTotalAmt = vo.getFeeItem().getSettleCapital();
		
	}

	@Override
	public void commitApproveSettleTask(String taskId, ApproveResultVo vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commitApproveRemissionTask(String taskId, ApproveResultVo vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commitApplyConfirmSettleTask(String taskId, RemissionFeeItemVo vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commitConfirmSettleTask(String taskId, ApproveResultVo vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelSettleTask(String taskId, String operId, String cancelComment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SettleTaskVo> getApplySettleTaskList(String createId, String settleType, List<String> applyStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplySettle getApplySettleTaskById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modifyApplySettleInfo(ApplySettle record) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteApplySettleInfoById(String id) {
		// TODO Auto-generated method stub
		
	}

	

}
