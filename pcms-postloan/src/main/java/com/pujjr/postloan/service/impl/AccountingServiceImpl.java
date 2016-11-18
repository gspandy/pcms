package com.pujjr.postloan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.service.IProductService;
import com.pujjr.enumeration.EIntervalMode;
import com.pujjr.postloan.dao.GeneralLedgerMapper;
import com.pujjr.postloan.dao.OtherFeeMapper;
import com.pujjr.postloan.dao.RepayLogMapper;
import com.pujjr.postloan.dao.RepayPlanMapper;
import com.pujjr.postloan.dao.StayAccountLogMapper;
import com.pujjr.postloan.dao.StayAccountMapper;
import com.pujjr.postloan.dao.WaitingChargeMapper;
import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.domain.OtherFee;
import com.pujjr.postloan.domain.RepayLog;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.domain.StayAccount;
import com.pujjr.postloan.domain.StayAccountLog;
import com.pujjr.postloan.domain.WaitingCharge;
import com.pujjr.postloan.enumeration.ChargeItem;
import com.pujjr.postloan.enumeration.FeeType;
import com.pujjr.postloan.enumeration.RepayMode;
import com.pujjr.postloan.enumeration.RepayStatus;
import com.pujjr.postloan.enumeration.SettleMode;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.postloan.vo.RepayingFeeItemVo;
import com.pujjr.utils.Utils;
@Service
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
	@Autowired
	private OtherFeeMapper otherFeeDao;
	@Autowired
	private RepayLogMapper repayLogDao;
	@Autowired
	private StayAccountLogMapper stayAccountLogDao;
	
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
	public double getSettleRate(String appId) 
	{
		// TODO Auto-generated method stub
		//查询代扣明细表是否有逾期还款计划，有则以最早一期逾期期数获取对应的结清违约金率，否则以当前期数查询提前结清违约金率
		GeneralLedger ledgerRecord = ledgerDao.selectByAppId(appId);
		List<WaitingCharge> waitingChargeList = this.getWaitingChargeTypePlan(appId,true);
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
	public RepayingFeeItemVo getRepayingFeeItems(String appId, boolean isCalOverdueAmount, Date overdueEndDate,boolean isReduceStayAmount,boolean isContainCurPeriod) 
	{
		// TODO Auto-generated method stub
		double repayTotalCapital = 0.00;
		double repayTotalInterest = 0.00;
		double repayTotalOverdueAmt = 0.00;
		double otherTotalFee = 0.00;
		double otherTotalOverdueAmt = 0.00;
		
		//获取截止日期与当前日期间隔天数
		if(overdueEndDate ==null)
		{
			overdueEndDate = new Date();
		}
		long spaceDay = Utils.getTimeInterval(new Date(), overdueEndDate, EIntervalMode.DAYS);
		//获取日罚息率
		GeneralLedger ledgerRecord = ledgerDao.selectByAppId(appId);
		double dayLateRate = ledgerRecord.getDayLateRate();
		
		//获取代扣明细表计划还款明细
		List<WaitingCharge> waitingChargePlanList = this.getWaitingChargeTypePlan(appId, isContainCurPeriod);
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
		
		StayAccount stayAccount = stayAccountDao.selectByAppId(appId);
		double stayAmount; 
		if(stayAccount != null)
		{
			stayAmount = stayAccount.getStayAmount();
		}
		else
		{
			stayAmount = 0;
		}
			
		//挂账金额的处理
		if(isReduceStayAmount)
		{
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
		
		RepayingFeeItemVo vo = new RepayingFeeItemVo();
		vo.setRepayCapital(repayTotalCapital);
		vo.setRepayInterest(repayTotalInterest);
		vo.setRepayOverdueAmount(repayTotalOverdueAmt);
		vo.setOtherAmount(otherTotalFee);
		vo.setOtherOverdueAmount(otherTotalOverdueAmt);
		vo.setRemainCapital(remainCapital);
		vo.setStayAmount(stayAmount);
		
		return vo;
	}

	@Override
	public List<WaitingCharge> getWaitingChargeTypePlan(String appId,boolean isContainCurPeriod) {
		// TODO Auto-generated method stub
		return waitingChargeDao.selectListTypePlanOrderByGentimeAsc(appId, isContainCurPeriod, new Date());
	}

	@Override
	public List<WaitingCharge> getWaitingChargeTypeOther(String appId) {
		// TODO Auto-generated method stub
		return waitingChargeDao.selectListOrderByGentimeAsc(appId, FeeType.Other.getName());
	}

	@Override
	public List<RepayPlan> getAfterCurrentPeriodRepayPlan(String appId, int queryPeriod) {
		// TODO Auto-generated method stub
		return repayPlanDao.selectSpecialRepayPlanList(appId, queryPeriod+1,0);

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

	private void saveRepayLog(String appId,FeeType feeType,String feeRefId,ChargeItem chargeItem,double chargeAmount,Date chargeDate,String chargeMode)
	{
		//记录还款记录
		RepayLog repayLog = new RepayLog();
		repayLog.setId(Utils.get16UUID());
		repayLog.setAppId(appId);
		repayLog.setFeeType(feeType.getName());
		repayLog.setFeeRefId(feeRefId);
		repayLog.setChargeItem(chargeItem.getName());
		repayLog.setChargeAmount(chargeAmount);
		repayLog.setChargeTime(new Date());
		repayLog.setChargeMode(chargeMode);
		repayLogDao.insert(repayLog);
	}
	@Override
	public void repayReverseAccounting(String appId, double repayAmount, Date repayDate,String repayMode) 
	{
		/**
		 * 冲其他费用
		 */
		List<WaitingCharge> otherFeeList = waitingChargeDao.selectListOrderByGentimeAsc(appId, FeeType.Other.getName());
		for(WaitingCharge otherFeeItem : otherFeeList)
		{
			if(repayAmount<=0)
				break;
			double otherFeeCapital = otherFeeItem.getRepayCapital();
			double otherFeeOverdueAmt = otherFeeItem.getRepayOverdueAmount();
			//先冲罚息
			if(repayAmount>0)
			{
				if(otherFeeOverdueAmt>0)
				{
					if(Double.compare(otherFeeOverdueAmt, repayAmount)>=0)
					{
						otherFeeOverdueAmt-=repayAmount;
						repayAmount=0.00;
						saveRepayLog(appId,FeeType.Other,otherFeeItem.getId(),ChargeItem.OTHEROVERDUEAMT,repayAmount,repayDate,repayMode);
					}
					else
					{
						repayAmount-=otherFeeOverdueAmt;
						otherFeeOverdueAmt=0.00;
						saveRepayLog(appId,FeeType.Other,otherFeeItem.getId(),ChargeItem.OTHEROVERDUEAMT,otherFeeOverdueAmt,repayDate,repayMode);
					}
					
				}
			}
			//再冲本金
			if(repayAmount>0)
			{
				if(otherFeeCapital>0)
				{
					if(Double.compare(otherFeeCapital, repayAmount)>=0)
					{
						otherFeeCapital-=repayAmount;
						repayAmount=0.00;
						saveRepayLog(appId,FeeType.Other,otherFeeItem.getId(),ChargeItem.OTHERFEE,repayAmount,repayDate,repayMode);
					}
					else
					{
						repayAmount-=otherFeeCapital;
						otherFeeCapital=0.00;
						saveRepayLog(appId,FeeType.Other,otherFeeItem.getId(),ChargeItem.OTHERFEE,otherFeeCapital,repayDate,repayMode);
					}
				}
			}
			//如果冲账后本金和利息都为0，则为结清状态，否则只更新代扣款明细相关数据
			if(otherFeeCapital==0 && otherFeeOverdueAmt==0)
			{
				OtherFee otherFeePo = otherFeeDao.selectByPrimaryKey(otherFeeItem.getFeeRefId());
				otherFeePo.setRepayStatus(RepayStatus.Settled.getName());
				otherFeePo.setSettleDate(new Date());
				otherFeePo.setSettleMode(SettleMode.NORMAL.getName());
				otherFeeDao.updateByPrimaryKey(otherFeePo);
				//删除对应代扣款明细
				waitingChargeDao.deleteByPrimaryKey(otherFeeItem.getId());
			}
			else
			{
				otherFeeItem.setRepayCapital(otherFeeCapital);
				otherFeeItem.setRepayOverdueAmount(otherFeeOverdueAmt);
				waitingChargeDao.updateByPrimaryKey(otherFeeItem);
			}
		}
		/**
		 * 冲计划还款费用
		 */
		//保存还款本金
		double repayLedgercapital=0.00;
		List<WaitingCharge> planRepayList = waitingChargeDao.selectListOrderByGentimeAsc(appId, FeeType.Plan.getName());
		for(WaitingCharge planItem : planRepayList)
		{
			if(repayAmount<=0)
				break;
			double capital = planItem.getRepayCapital();
			double interest = planItem.getRepayInterest();
			double overdueAmt = planItem.getRepayOverdueAmount();
			//先冲罚息
			if(repayAmount>0)
			{
				if(overdueAmt>0)
				{
					if(Double.compare(overdueAmt, repayAmount)>=0)
					{
						overdueAmt-=repayAmount;
						saveRepayLog(appId,FeeType.Plan,planItem.getId(),ChargeItem.OVERDUEAMT,repayAmount,repayDate,repayMode);
						repayAmount=0.00;
					}
					else
					{
						repayAmount-=overdueAmt;
						saveRepayLog(appId,FeeType.Plan,planItem.getId(),ChargeItem.OVERDUEAMT,overdueAmt,repayDate,repayMode);
						overdueAmt=0.00;
					}
					
				}
			}
			//再冲利息
			if(repayAmount>0)
			{
				if(interest>0)
				{
					if(Double.compare(interest, repayAmount)>=0)
					{
						interest-=repayAmount;
						saveRepayLog(appId,FeeType.Plan,planItem.getId(),ChargeItem.INTEREST,repayAmount,repayDate,repayMode);
						repayAmount=0.00;
					}
					else
					{
						repayAmount-=interest;
						saveRepayLog(appId,FeeType.Plan,planItem.getId(),ChargeItem.INTEREST,interest,repayDate,repayMode);
						interest=0.00;
					}
				}
			}
			//再冲本金
			
			if(repayAmount>0)
			{
				
				if(capital>0)
				{
					if(Double.compare(capital, repayAmount)>=0)
					{
						capital-=repayAmount;
						repayLedgercapital += repayAmount;
						saveRepayLog(appId,FeeType.Plan,planItem.getId(),ChargeItem.CAPITAL,repayAmount,repayDate,repayMode);
						repayAmount=0.00;
					}
					else
					{
						repayAmount-=capital;
						repayLedgercapital += capital;
						saveRepayLog(appId,FeeType.Plan,planItem.getId(),ChargeItem.CAPITAL,capital,repayDate,repayMode);
						capital=0.00;
					}
				}
			}
			//如果冲账后本金、利息、F爱惜都为0，则为结清状态，否则只更新代扣款明细相关数据
			if(capital==0 && interest==0 && overdueAmt ==0)
			{
				RepayPlan repayPlanPo = repayPlanDao.selectByPrimaryKey(planItem.getFeeRefId());
				repayPlanPo.setRepayStatus(RepayStatus.Settled.getName());
				repayPlanPo.setSettleDate(new Date());
				repayPlanPo.setSettleMode(SettleMode.NORMAL.getName());
				repayPlanDao.updateByPrimaryKey(repayPlanPo);
				//删除对应代扣款明细
				waitingChargeDao.deleteByPrimaryKey(planItem.getId());
			}
			else
			{
				planItem.setRepayCapital(capital);
				planItem.setRepayInterest(interest);
				planItem.setRepayOverdueAmount(overdueAmt);
				waitingChargeDao.updateByPrimaryKey(planItem);
			}
			
			
		}
		
		/**
		 * 更新总账相关信息,刷新总账还款状态,如果还款计划都结清并且待扣款明细不存在应还明细，则更新为已结清
		 */
		GeneralLedger ledgerPo = ledgerDao.selectByAppId(appId);
		ledgerPo.setRemainCapital(ledgerPo.getRemainCapital()-repayLedgercapital);
		List<WaitingCharge> waitingChargeList = waitingChargeDao.selectListOrderByGentimeAsc(appId, null);
		List<RepayPlan> repayPlanList = repayPlanDao.selectNotSettleRepayPlanList(appId);
		if(waitingChargeList.size()==0 && repayPlanList.size()==0)
		{
			ledgerPo.setRepayStatus(RepayStatus.Settled.getName());
		}
		ledgerDao.updateByPrimaryKey(ledgerPo);
		/**
		 * 如果还款金额还大于0，挂账处理
		 * **/
		if(repayAmount>0)
		{
			StayAccount stayAccount = stayAccountDao.selectByAppId(appId);
			if(stayAccount!=null)
			{
				stayAccount.setStayAmount(stayAccount.getStayAmount()+repayAmount);
				stayAccountDao.updateByPrimaryKey(stayAccount);
			}
			else
			{
				stayAccount = new StayAccount();
				stayAccount.setId(Utils.get16UUID());
				stayAccount.setAppId(appId);
				stayAccount.setStayAmount(repayAmount);
				stayAccountDao.insert(stayAccount);
			}
			//如果不是挂账还款需要做挂账记录
			if(!repayMode.equals(RepayMode.StayAccounting.getName()))
			{
				StayAccountLog stayLog = new StayAccountLog();
				stayLog.setId(Utils.get16UUID());
				stayLog.setStayId(stayAccount.getId());
				stayLog.setStayAmount(repayAmount);
				stayLog.setStaySource(repayMode);
				stayLog.setStayDate(new Date());
				stayAccountLogDao.insert(stayLog);
			}
		}
		
	}

	

	@Override

	public void repayReverseAccountingUserNewWaitingChargeTable(String appId, double repayAmount, Date repayDate,String repayMode) {

	}

	@Override
	public double getStayAmount(String appId) {
		// TODO Auto-generated method stub
		StayAccount stayAccount = stayAccountDao.selectByAppId(appId);
		if(stayAccount == null)
		{
			return 0.00;
		}
		else
		{
			return stayAccount.getStayAmount();
		}
		
	}

	@Override
	public boolean checkCandoPublicRepay(String appId) {
		// TODO Auto-generated method stub
		return true;
	}

}
