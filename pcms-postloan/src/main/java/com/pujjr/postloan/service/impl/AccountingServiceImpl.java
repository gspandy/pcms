package com.pujjr.postloan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;

import com.pujjr.base.service.IProductService;
import com.pujjr.enumeration.EIntervalMode;
import com.pujjr.postloan.dao.GeneralLedgerMapper;
import com.pujjr.postloan.dao.RepayPlanMapper;
import com.pujjr.postloan.dao.StayAccountMapper;
import com.pujjr.postloan.dao.WaitingChargeMapper;
import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.domain.StayAccount;
import com.pujjr.postloan.domain.WaitingCharge;
import com.pujjr.postloan.enumeration.FeeType;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.postloan.vo.RepayingFeeItemVo;
import com.pujjr.utils.Utils;

public class AccountingServiceImpl implements IAccountingService {

	@Autowired
	private RepayPlanMapper repayPlanDao;
	@Autowired
	private GeneralLedgerMapper ledgerDao;
	@Autowired
	private WaitingChargeMapper waitingChargeDao;
	@Autowired
	private IProductService productService;
	@Autowired
	private StayAccountMapper stayAccountDao;
	
	@Override
	public RepayPlan getCurrentPeriodRepayPlan(String appId) 
	{
		// TODO Auto-generated method stub
		return repayPlanDao.selectCurrentPeriodRepayPlan(appId, new Date());
	}	

	@Override
	public RepayPlan getLatestPeriodRepayPlan(String appId) {
		// TODO Auto-generated method stub
		GeneralLedger record = ledgerDao.selectByAppId(appId);
		return repayPlanDao.selectRepayPlan(appId, record.getPeriod());
	}

	@Override
	public double getRemainCaptial(String appId) {
		// TODO Auto-generated method stub
		return ledgerDao.selectByAppId(appId).getRemainCapital();
	}

	@Override
	public double getSettleReate(String appId) 
	{
		// TODO Auto-generated method stub
		//查询代扣明细表是否有逾期还款计划，有则以最早一期逾期期数获取对应的结清违约金率，否则以当前期数查询提前结清违约金率
		GeneralLedger ledgerRecord = ledgerDao.selectByAppId(appId);
		List<WaitingCharge> waitingChargeList = this.getWaitingChargeTypePlan(appId);
		int period = 0;
		String productCode = ledgerRecord.getProductCode();
		if(waitingChargeList.size()>0)
		{
			WaitingCharge earliestItem  =  waitingChargeList.get(0);
			RepayPlan earliestRepayPlan = repayPlanDao.selectByPrimaryKey(earliestItem.getFeeRefId());
			period = earliestRepayPlan.getPeriod();
		}
		else
		{
			RepayPlan currentPeriod = this.getCurrentPeriodRepayPlan(appId);
			period = currentPeriod.getPeriod();
		}
		return productService.getProductSettleByPeriod(productCode, period);
	}

	@Override
	public RepayingFeeItemVo getRepayingFeeItems(String appId, boolean isCalOverdueAmount, Date overdueEndDate,boolean isReduceStayAmount) 
	{
		// TODO Auto-generated method stub
		double repayTotalCapital = 0.00;
		double repayTotalInterest = 0.00;
		double repayTotalOverdueAmt = 0.00;
		double otherTotalFee = 0.00;
		double otherTotalOverdueAmt = 0.00;
		
		//获取截止日期与当前日期间隔天数
		long spaceDay = Utils.getTimeInterval(new Date(), overdueEndDate, EIntervalMode.DAYS);
		//获取日罚息率
		GeneralLedger ledgerRecord = ledgerDao.selectByAppId(appId);
		double dayLateRate = ledgerRecord.getDayLateRate();
		
		//获取代扣明细表计划还款明细
		List<WaitingCharge> waitingChargePlanList = this.getWaitingChargeTypePlan(appId);
		for(WaitingCharge item : waitingChargePlanList)
		{
			repayTotalCapital += item.getRepayCapital();
			repayTotalInterest += item.getRepayInterest();
			//判断是否计算额外罚息
			if(isCalOverdueAmount)
			{
				//计算罚息 = 应还罚息+截止天数产生的额外罚息
				repayTotalOverdueAmt += item.getRepayOverdueAmount()+(Math.round((item.getRepayCapital()+item.getRepayInterest())*dayLateRate*spaceDay*100)*0.01d);
			}
			else
			{
				repayTotalOverdueAmt += item.getRepayOverdueAmount();
			}
		}
		//获取代扣明细表其他费用明细
		List<WaitingCharge> waitingChargeOtherList = this.getWaitingChargeTypeOther(appId);
		for(WaitingCharge item : waitingChargeOtherList)
		{
			otherTotalFee += item.getRepayCapital();
			//判断是否计算额外罚息
			if(isCalOverdueAmount)
			{
				//计算罚息 = 应还罚息+截止天数产生的额外罚息
				otherTotalOverdueAmt += item.getRepayOverdueAmount()+(Math.round((item.getRepayCapital()+item.getRepayInterest())*dayLateRate*spaceDay*100)*0.01d);
			}
			else
			{
				otherTotalOverdueAmt += item.getRepayOverdueAmount();
			}
		}
		
		double remainCapital = this.getRemainCaptial(appId);
		
		//挂账金额的处理
		if(isReduceStayAmount)
		{
			StayAccount stayAccount = stayAccountDao.selectByAppId(appId);
			if(stayAccount != null)
			{
				double stayAmount = stayAccount.getStayAmount();
				//有挂账，减其他费用罚息
				if(stayAmount>0)
				{
					if(otherTotalOverdueAmt>0)
					{
						if(Double.compare(otherTotalOverdueAmt, stayAmount)>=0)
						{
							otherTotalOverdueAmt=otherTotalOverdueAmt-stayAmount;
							stayAmount=0.00;
						}
						else
						{
							stayAmount = stayAmount - otherTotalOverdueAmt;
							otherTotalOverdueAmt = 0.00;
						}
					}
				}
				//有挂账减其他费用
				if(stayAmount>0)
				{
					if(otherTotalFee>0)
					{
						if(Double.compare(otherTotalFee, stayAmount)>=0)
						{
							otherTotalFee -=stayAmount;
							stayAmount = 0.00;
						}
						else
						{
							stayAmount-=otherTotalFee;
							otherTotalFee=0.00;
						}
					}
				}
				//有挂账减应还罚息
				if(stayAmount>0)
				{
					if(repayTotalOverdueAmt>0)
					{
						if(Double.compare(repayTotalOverdueAmt, stayAmount)>=0)
						{
							repayTotalOverdueAmt-=stayAmount;
							stayAmount = 0.00;
						}
						else
						{
							stayAmount-=repayTotalOverdueAmt;
							otherTotalFee=0.00;
						}
					}
				}
				//有挂账减应还利息
				if(stayAmount>0)
				{
					if(repayTotalInterest>0)
					{
						if(Double.compare(repayTotalInterest, stayAmount)>=0)
						{
							repayTotalInterest-=stayAmount;
							stayAmount = 0.00;
						}
						else
						{
							stayAmount-=repayTotalInterest;
							otherTotalFee=0.00;
						}
					}
				}
				//有挂账减应还本金
				if(stayAmount>0)
				{
					if(repayTotalCapital>0)
					{
						if(Double.compare(repayTotalCapital, stayAmount)>=0)
						{
							repayTotalCapital-=stayAmount;
							stayAmount = 0.00;
						}
						else
						{
							stayAmount-=repayTotalCapital;
							otherTotalFee=0.00;
						}
					}
				}
				//有挂账减本金
				if(stayAmount>0)
				{
					if(remainCapital>0)
					{
						if(Double.compare(remainCapital, stayAmount)>=0)
						{
							remainCapital-=stayAmount;
							stayAmount = 0.00;
						}
						else
						{
							stayAmount-=remainCapital;
							otherTotalFee=0.00;
						}
					}
				}
				
			}
		}
		
		RepayingFeeItemVo vo = new RepayingFeeItemVo();
		vo.setRepayCapital(repayTotalCapital);
		vo.setRepayInterest(repayTotalInterest);
		vo.setRepayOverdueAmount(repayTotalOverdueAmt);
		vo.setOtherAmount(otherTotalFee);
		vo.setOtherOverdueAmount(otherTotalOverdueAmt);
		vo.setRemainCapital(remainCapital);
		
		return vo;
	}

	@Override
	public List<WaitingCharge> getWaitingChargeTypePlan(String appId) {
		// TODO Auto-generated method stub
		return waitingChargeDao.selectListOrderByGentimeAsc(appId, FeeType.Plan.getName());
	}

	@Override
	public List<WaitingCharge> getWaitingChargeTypeOther(String appId) {
		// TODO Auto-generated method stub
		return waitingChargeDao.selectListOrderByGentimeAsc(appId, FeeType.Other.getName());
	}

	@Override
	public List<RepayPlan> getAfterCurrentPeriodRepayPlan(String appId, int queryPeriod) {
		// TODO Auto-generated method stub
		return repayPlanDao.selectRepayPlanList(appId, queryPeriod+1,0);

	}

	@Override
	public List<Integer> getAfterCurrentPeriodRemainPeroidList(String appId) {
		// TODO Auto-generated method stub

		RepayPlan currentRepayPlan = this.getCurrentPeriodRepayPlan(appId);
		List<RepayPlan> list = this.getAfterCurrentPeriodRepayPlan(appId, currentRepayPlan.getPeriod());
		List<Integer> tmpList = new ArrayList<Integer>();
		for(RepayPlan l : list)
		{
			tmpList.add(l.getPeriod());
		}
		return tmpList;
	}

	@Override
	public void repayReverseAccounting(String appId, double repayAmount, Date repayDate,String repayMode) {

	}

	

	@Override

	public void repayReverseAccountingUserNewWaitingChargeTable(String appId, double repayAmount, Date repayDate,String repayMode) {

	}

}
