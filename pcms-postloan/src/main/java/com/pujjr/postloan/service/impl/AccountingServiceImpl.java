package com.pujjr.postloan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.service.IProductService;
import com.pujjr.postloan.dao.ApplyAlterRepayDateMapper;
import com.pujjr.postloan.dao.ApplyExtendPeriodMapper;
import com.pujjr.postloan.dao.ApplySettleMapper;
import com.pujjr.postloan.dao.GeneralLedgerMapper;
import com.pujjr.postloan.dao.LoanQueryMapper;
import com.pujjr.postloan.dao.OfferSummaryMapper;
import com.pujjr.postloan.dao.OtherFeeMapper;
import com.pujjr.postloan.dao.RemissionItemMapper;
import com.pujjr.postloan.dao.RepayLogItemMapper;
import com.pujjr.postloan.dao.RepayLogMapper;
import com.pujjr.postloan.dao.RepayPlanMapper;
import com.pujjr.postloan.dao.ServiceFeeMapper;
import com.pujjr.postloan.dao.StayAccountLogMapper;
import com.pujjr.postloan.dao.StayAccountMapper;
import com.pujjr.postloan.dao.WaitingChargeMapper;
import com.pujjr.postloan.dao.WaitingChargeNewMapper;
import com.pujjr.postloan.domain.ApplyAlterRepayDate;
import com.pujjr.postloan.domain.ApplyExtendPeriod;
import com.pujjr.postloan.domain.ApplySettle;
import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.domain.OtherFee;
import com.pujjr.postloan.domain.RemissionItem;
import com.pujjr.postloan.domain.RepayLog;
import com.pujjr.postloan.domain.RepayLogItem;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.domain.ServiceFee;
import com.pujjr.postloan.domain.StayAccount;
import com.pujjr.postloan.domain.StayAccountLog;
import com.pujjr.postloan.domain.WaitingCharge;
import com.pujjr.postloan.domain.WaitingChargeNew;
import com.pujjr.postloan.enumeration.FeeType;
import com.pujjr.postloan.enumeration.LoanApplyTaskType;
import com.pujjr.postloan.enumeration.RepayItem;
import com.pujjr.postloan.enumeration.RepayLogStatus;
import com.pujjr.postloan.enumeration.RepayMode;
import com.pujjr.postloan.enumeration.RepayStatus;
import com.pujjr.postloan.enumeration.SettleMode;
import com.pujjr.postloan.enumeration.SettleType;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.postloan.vo.RemissionFeeItemVo;
import com.pujjr.postloan.vo.RepayingFeeItemVo;
import com.pujjr.utils.Utils;
@Service
@Transactional(rollbackFor=Exception.class)
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
	private RepayLogItemMapper repayLogItemDao;
	@Autowired
	private StayAccountLogMapper stayAccountLogDao;
	@Autowired
	private WaitingChargeNewMapper waitingchargeNewDao;
	@Autowired
	private RemissionItemMapper remissionItemDao;
	@Autowired
	private ApplySettleMapper applySettleDao;
	@Autowired
	private ApplyExtendPeriodMapper applyExtendPeriodDao;
	@Autowired
	private ApplyAlterRepayDateMapper applyAlterRepayDateDao;
	@Autowired
	private ServiceFeeMapper serviceFeeDao;
	@Autowired
	private LoanQueryMapper loanQueryDao;
	@Autowired
	private OfferSummaryMapper offerSummaryDao;
	
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
		int spaceDay = Utils.getSpaceDay(new Date(), overdueEndDate);
		//获取日罚息率
		GeneralLedger ledgerRecord = ledgerDao.selectByAppId(appId);
		double dayLateRate = ledgerRecord.getDayLateRate();
		
		//获取代扣明细表计划还款明细(不包含当期)
		List<WaitingCharge> waitingChargePlanList = this.getWaitingChargeTypePlan(appId, true);
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

	private void saveRepayLogItem(String repayLogId,FeeType feeType,String feeRefId,RepayItem repayItem,double repayAmount)
	{
		//记录还款记录还款项目信息
		RepayLogItem repayLogItem = new RepayLogItem();
		repayLogItem.setId(Utils.get16UUID());
		repayLogItem.setRepayLogId(repayLogId);
		repayLogItem.setFeeType(feeType.getName());
		repayLogItem.setFeeRefId(feeRefId);
		repayLogItem.setRepayAmount(repayAmount);
		repayLogItem.setRepayItem(repayItem.getName());
		repayLogItemDao.insert(repayLogItem);
		
	}
	@Override
	public void repayReverseAccounting(String appId, double repayAmount, Date repayDate,RepayMode repayMode,RemissionFeeItemVo refundItem) 
	{
		//生成还款记录ID
		String repayLogId = Utils.get16UUID();
		double totalOtherOverdueAmt = 0.00; //记录总的其他费用罚息的总额
		double totalOtherFee = 0.00; //记录总的其他费用还款总额
		double totalCapital = 0.00;  //记录总的还款本金额
		double totalInterest = 0.00; //记录总的还款利息金额
		double totalOverdueAmt = 0.00; //记录总的还款罚息金额
		/**
		 * 第一阶段冲其他费用，先冲其他费用罚息，再冲本金
		 */
		List<WaitingCharge> otherFeeList = waitingChargeDao.selectListOrderByGentimeAsc(appId, FeeType.Other.getName());
		
		/**
		 * 冲其他费用罚息，如果是减免则还款金额为减免的其他费用罚息
		 */
		double repayOtherOverdueAmt = repayAmount;
		if(repayMode.equals(RepayMode.Remission))
		{
			repayOtherOverdueAmt = refundItem.getOtherOverdueAmount();
		}
		for(int i = 0 ; i<otherFeeList.size();i++)
		{
			WaitingCharge otherFeeItem = otherFeeList.get(i);
			if(Double.compare(repayOtherOverdueAmt,0.00)<=0)
				break;
			double otherFeeOverdueAmt = otherFeeItem.getRepayOverdueAmount();
			if(Double.compare(repayOtherOverdueAmt, 0.00)>0 && Double.compare(otherFeeOverdueAmt, 0.00)>0)	
			{
				if(Double.compare(otherFeeOverdueAmt, repayOtherOverdueAmt)>=0)
				{
					otherFeeOverdueAmt-=repayOtherOverdueAmt;
					totalOtherOverdueAmt += repayOtherOverdueAmt;
					this.saveRepayLogItem(repayLogId, FeeType.Other, otherFeeItem.getFeeRefId(), RepayItem.OTHEROVERDUEAMT, repayOtherOverdueAmt);
					repayOtherOverdueAmt=0.00;
				}
				else
				{
					repayOtherOverdueAmt-=otherFeeOverdueAmt;
					totalOtherOverdueAmt += otherFeeOverdueAmt;
					this.saveRepayLogItem(repayLogId,FeeType.Other,otherFeeItem.getFeeRefId(),RepayItem.OTHEROVERDUEAMT,otherFeeOverdueAmt);
					otherFeeOverdueAmt=0.00;
				}
			}
			otherFeeItem.setRepayOverdueAmount(otherFeeOverdueAmt);
			otherFeeList.set(i, otherFeeItem);
		}
		/**
		 * 冲其他费用，如果是减免则还款金额为减免的其他费用
		 */
		double repayOtherFee = repayOtherOverdueAmt;
		if(repayMode.equals(RepayMode.Remission))
		{
			repayOtherFee = refundItem.getOtherFee();
		}
		for(int i = 0 ; i<otherFeeList.size();i++)
		{
			WaitingCharge otherFeeItem = otherFeeList.get(i);
			if(Double.compare(repayOtherFee,0.00)<=0)
				break;
			double otherFee = otherFeeItem.getRepayCapital();
			if(Double.compare(repayOtherFee, 0.00)>0 && Double.compare(otherFee, 0.00)>0)	
			{
				if(Double.compare(otherFee, repayOtherFee)>=0)
				{
					otherFee-=repayOtherFee;
					totalOtherFee += repayOtherFee;
					this.saveRepayLogItem(repayLogId, FeeType.Other, otherFeeItem.getFeeRefId(), RepayItem.OTHERFEE, repayOtherFee);
					repayOtherFee=0.00;
				}
				else
				{
					repayOtherFee-=otherFee;
					totalOtherFee += otherFee;
					this.saveRepayLogItem(repayLogId,FeeType.Other,otherFeeItem.getFeeRefId(),RepayItem.OTHERFEE,otherFee);
					otherFee=0.00;
				}
			}
			otherFeeItem.setRepayCapital(otherFee);
			otherFeeList.set(i, otherFeeItem);
		}
		/**
		 * 检查是否其他费用已结清，并更改状态 
		 */
		for(int i=0 ; i<otherFeeList.size();i++)
		{
			WaitingCharge otherFeeItem = otherFeeList.get(i);
			if(Double.compare(otherFeeItem.getRepayCapital(), 0.00)==0 && Double.compare(otherFeeItem.getRepayOverdueAmount(), 0.00)==0)
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
				otherFeeItem.setRepayCapital(otherFeeItem.getRepayCapital());
				otherFeeItem.setRepayOverdueAmount(otherFeeItem.getRepayOverdueAmount());
				waitingChargeDao.updateByPrimaryKey(otherFeeItem);
			}
		}
		/**
		 * 第二阶段冲还款计划，先冲罚息，再冲利息，最后冲本金
		 */
		List<WaitingCharge> planRepayList = waitingChargeDao.selectListOrderByGentimeAsc(appId, FeeType.Plan.getName());
		/**
		 * 冲还款计划罚息
		 */
		double repayOverdueAmt  = repayOtherFee;
		if(repayMode.equals(RepayMode.Remission))
		{
			repayOverdueAmt = refundItem.getOverdueAmount();
		}
		for(int i = 0 ; i<planRepayList.size() ; i++)
		{
			WaitingCharge planItem = planRepayList.get(i);
			if(Double.compare(repayOverdueAmt,0.00)<=0)
				break;
			double overdueAmt = planItem.getRepayOverdueAmount();
			if(Double.compare(repayOverdueAmt,0.00)>0  && Double.compare(overdueAmt,0.00)>0)
			{
				if(Double.compare(overdueAmt, repayOverdueAmt)>=0)
				{
					overdueAmt-=repayOverdueAmt;
					totalOverdueAmt+=repayOverdueAmt;
					this.saveRepayLogItem(repayLogId, FeeType.Plan, planItem.getFeeRefId(), RepayItem.OVERDUEAMT, repayOverdueAmt);
					repayOverdueAmt=0.00;
				}
				else
				{
					repayOverdueAmt-=overdueAmt;
					totalOverdueAmt+=overdueAmt;
					this.saveRepayLogItem(repayLogId, FeeType.Plan, planItem.getFeeRefId(), RepayItem.OVERDUEAMT, overdueAmt);
					overdueAmt=0.00;
				}
			}
			planItem.setRepayOverdueAmount(overdueAmt);
			planRepayList.set(i, planItem);
		}
		/**
		 * 冲还款计划利息
		 */
		double repayInterest  = repayOverdueAmt;
		if(repayMode.equals(RepayMode.Remission))
		{
			repayInterest = refundItem.getInterest();
		}
		for(int i = 0 ; i<planRepayList.size() ; i++)
		{
			WaitingCharge planItem = planRepayList.get(i);
			if(Double.compare(repayInterest,0.00)<=0)
				break;
			double interest  = planItem.getRepayInterest();
			if(Double.compare(repayInterest,0.00)>0  && Double.compare(interest,0.00)>0)
			{
				if(Double.compare(interest, repayInterest)>=0)
				{
					interest-=repayInterest;
					totalInterest+=repayInterest;
					this.saveRepayLogItem(repayLogId, FeeType.Plan, planItem.getFeeRefId(), RepayItem.INTEREST, repayInterest);
					repayInterest=0.00;
				}
				else
				{
					repayInterest-=interest;
					totalInterest+=interest;
					this.saveRepayLogItem(repayLogId, FeeType.Plan, planItem.getFeeRefId(), RepayItem.INTEREST, interest);
					interest=0.00;
				}
			}
			planItem.setRepayInterest(interest);
			planRepayList.set(i, planItem);
		}
		/**
		 * 冲还款计划本金
		 */
		double repayCapital  = repayInterest;
		if(repayMode.equals(RepayMode.Remission))
		{
			repayCapital = refundItem.getCapital();
		}
		for(int i = 0 ; i<planRepayList.size() ; i++)
		{
			WaitingCharge planItem = planRepayList.get(i);
			if(Double.compare(repayCapital,0.00)<=0)
				break;
			double capital  = planItem.getRepayCapital();
			if(Double.compare(repayCapital,0.00)>0  && Double.compare(capital,0.00)>0)
			{
				if(Double.compare(capital, repayCapital)>=0)
				{
					capital-=repayCapital;
					totalCapital+=repayCapital;
					this.saveRepayLogItem(repayLogId, FeeType.Plan, planItem.getFeeRefId(), RepayItem.CAPITAL, repayCapital);
					repayCapital=0.00;
				}
				else
				{
					repayCapital-=capital;
					totalCapital+=capital;
					this.saveRepayLogItem(repayLogId, FeeType.Plan, planItem.getFeeRefId(), RepayItem.CAPITAL, capital);
					capital=0.00;
				}
			}
			planItem.setRepayCapital(capital);
			planRepayList.set(i, planItem);
		}
		/**
		 * 检查还款计划是否已结清，并更新状态
		 */
		for(int i = 0 ; i<planRepayList.size() ; i++)
		{
			WaitingCharge planItem = planRepayList.get(i);
			if(Double.compare(planItem.getRepayCapital(),0.00)==0 && Double.compare(planItem.getRepayInterest(),0.00)==0 && Double.compare(planItem.getRepayOverdueAmount(),0.00) ==0)
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
				planItem.setRepayCapital(planItem.getRepayCapital());
				planItem.setRepayInterest(planItem.getRepayInterest());
				planItem.setRepayOverdueAmount(planItem.getRepayOverdueAmount());
				waitingChargeDao.updateByPrimaryKey(planItem);
			}
		}	
		
		/**
		 * 如果还款金额还大于0，挂账处理,如果本身就是挂账冲账，则剩余挂账金额为0
		 * **/
		double remainRepayAmount = repayCapital;
		if(remainRepayAmount>0)
		{
			StayAccount stayAccount = stayAccountDao.selectByAppId(appId);
			//如果是挂账还款则更新挂账表记录,否则将多余的余额挂账处理并做挂账记录
			if(repayMode.equals(RepayMode.StayAccounting))
			{
				stayAccount.setStayAmount(remainRepayAmount);
				stayAccountDao.updateByPrimaryKey(stayAccount);
				remainRepayAmount=0.00;
			}
			else
			{
				if(stayAccount!=null)
				{
					stayAccount.setStayAmount(stayAccount.getStayAmount()+remainRepayAmount);
					stayAccountDao.updateByPrimaryKey(stayAccount);
				}
				else
				{
					stayAccount = new StayAccount();
					stayAccount.setId(Utils.get16UUID());
					stayAccount.setAppId(appId);
					stayAccount.setStayAmount(remainRepayAmount);
					stayAccountDao.insert(stayAccount);
				}
				StayAccountLog stayLog = new StayAccountLog();
				stayLog.setId(Utils.get16UUID());
				stayLog.setStayId(stayAccount.getId());
				stayLog.setStayAmount(remainRepayAmount);
				stayLog.setStaySource(repayMode.getName());
				stayLog.setStayDate(new Date());
				stayAccountLogDao.insert(stayLog);
			}
		}
		/**
		 * 记录还款记录
		 */
		RepayLog repayLog = new RepayLog();
		repayLog.setId(repayLogId);
		repayLog.setAppId(appId);
		int cnt = repayLogDao.selectRepayLogCntByAppId(appId);
		repayLog.setSeq(++cnt);
		repayLog.setRepayMode(repayMode.getName());
		repayLog.setRepayAmount(repayAmount);
		repayLog.setRepayTime(repayDate);
		repayLog.setLogStatus(RepayLogStatus.Normal.getName());
		repayLog.setCapital(totalCapital);
		repayLog.setInterest(totalInterest);
		repayLog.setOverdueAmount(totalOverdueAmt);
		repayLog.setOtherFee(totalOtherFee);
		repayLog.setOtherOverdueAmount(totalOtherOverdueAmt);
		repayLog.setExtendFee(0.00);
		repayLog.setLateFee(0.00);
		repayLog.setStageAmount(remainRepayAmount);
		repayLogDao.insert(repayLog);
		/**
		 * 更新总账相关信息,刷新总账还款状态,如果还款计划都结清并且待扣款明细不存在应还明细，则更新为已结清
		 */
		GeneralLedger ledgerPo = ledgerDao.selectByAppId(appId);
		ledgerPo.setRemainCapital(ledgerPo.getRemainCapital()-totalCapital);
		List<WaitingCharge> waitingChargeList = waitingChargeDao.selectListOrderByGentimeAsc(appId, null);
		List<RepayPlan> repayPlanList = repayPlanDao.selectNotSettleRepayPlanList(appId);
		if(waitingChargeList.size()==0 && repayPlanList.size()==0)
		{
			ledgerPo.setRepayStatus(RepayStatus.Settled.getName());
		}
		if(waitingChargeList.size()==0 && repayPlanList.size()>0)
		{
			ledgerPo.setRepayStatus(RepayStatus.Repaying.getName());
		}
		ledgerDao.updateByPrimaryKey(ledgerPo);
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
	public void repayReverseAccountingUserNewWaitingChargeTable(String applyId, LoanApplyTaskType applyType,String appId) 
	{
		// TODO Auto-generated method stub
		/**
		 * 获取申请信息，创建手续费应收项
		 * **/
		ApplySettle  settlePo = new ApplySettle();
		ApplyExtendPeriod extendPeriodPo = new ApplyExtendPeriod();
		ApplyAlterRepayDate alterRepayDatePo = new ApplyAlterRepayDate();
		Date chargeDate = new Date();
		//先删除手续费信息，再创建
		serviceFeeDao.deleteByApplyId(applyId);
		if(applyType.equals(LoanApplyTaskType.Settle))
		{
			settlePo = applySettleDao.selectByPrimaryKey(applyId);
			chargeDate = settlePo.getApplyEndDate();
			//创建提前结清违约金
			ServiceFee serviceFee = new ServiceFee();
			serviceFee.setId(Utils.get16UUID());
			serviceFee.setApplyId(applyId);
			serviceFee.setFeeType(FeeType.LateFee.getName());
			serviceFee.setAmount(settlePo.getLateFee());
			serviceFee.setRepayStatus(RepayStatus.Repaying.getName());
			serviceFeeDao.insert(serviceFee);
			
		}
		if(applyType.equals(LoanApplyTaskType.ExtendPeriod))
		{
			extendPeriodPo = applyExtendPeriodDao.selectByPrimaryKey(applyId);
			chargeDate = extendPeriodPo.getCreateDate();
			//创建展期费用
			ServiceFee serviceFee = new ServiceFee();
			serviceFee.setId(Utils.get16UUID());
			serviceFee.setApplyId(applyId);
			serviceFee.setFeeType(FeeType.ExtendFee.getName());
			serviceFee.setAmount(extendPeriodPo.getExtendFee());
			serviceFee.setRepayStatus(RepayStatus.Repaying.getName());
			serviceFeeDao.insert(serviceFee);
		}
		/**
		 * 先获取需结清的信息,含其他费用、计划还款、展期费或结清违约金
		 */
		List<WaitingChargeNew> settleOtherNewWaitingChargeList = waitingchargeNewDao.selectList(applyType.getName(), applyId, FeeType.Other.getName(),true);
		List<WaitingChargeNew> settlePlanNewWatingChargeList = waitingchargeNewDao.selectList(applyType.getName(), applyId, FeeType.Plan.getName(),true);
		ServiceFee serviceFee = serviceFeeDao.selectByApplyId(applyId);
		
		/**
		 * 获取挂账金额，为减少循环直接在减免处理后如果剩余本金、利息、罚息的金额大于0并且为提前结清的情况下，直接用挂账金额处理
		 * 先冲账其他费用罚息、本金，再冲账计划还款罚息、利息、本金，最后冲账手续费
		 */
		double totalRemissionCapital = 0.00,remissionCapital = 0.00;
		double totalRemissionInterest = 0.00,remissionInterest = 0.00;
		double totalRemissionOverdueAmt = 0.00,remissionOverdueAmt = 0.00;
		double totalRemissionOtherFee = 0.00,remissionOtherFee = 0.00;
		double totalRemissionOtherOverdueAmt = 0.00,remissionOtherOverdueAmt = 0.00;
		double totalRemissionLateFee = 0.00,remissionLateFee = 0.00;
		Date remissionDate = new Date();
		double totalRemissionAmt = 0.00;
		boolean isDoRemission = false;
		RemissionItem remissionItem = remissionItemDao.selectByApplyId(applyType.getName(), applyId);
		if(remissionItem!=null)
		{
			totalRemissionCapital =remissionCapital = remissionItem.getCapital();
			totalRemissionInterest =remissionInterest = remissionItem.getInterest();
			totalRemissionOverdueAmt =remissionOverdueAmt = remissionItem.getOverdueAmount();
			totalRemissionOtherFee =remissionOtherFee = remissionItem.getOtherFee();
			totalRemissionOtherOverdueAmt =remissionOtherOverdueAmt = remissionItem.getOtherOverdueAmount();
			totalRemissionLateFee =remissionLateFee = remissionItem.getLateFee();
			remissionDate = remissionItem.getRemissionDate();
			totalRemissionAmt = remissionCapital+remissionInterest+remissionOverdueAmt+remissionOtherFee+remissionOtherOverdueAmt+remissionLateFee;
		}
		
		//保存挂账冲账的各项总金额
		double totalStayCaptial = 0.00;
		double totalStayInterest = 0.00;
		double totalStayOverdueAmt = 0.00;
		double totalStayOtherOverdueAmt = 0.00;
		double totalStayOtherFee = 0.00;
		double totalStayLateFee = 0.00;
		boolean isDoStay = false;
		//获取挂账金额
		StayAccount stayAccount = stayAccountDao.selectByAppId(appId);
		double stayAmount = stayAccount!=null ? stayAccount.getStayAmount():0.00;
		
		//保存还款的各项总金额
		double totalRepayCapital = 0.00;
		double totalRepayInterest = 0.00;
		double totalRepayOverdueAmt = 0.00;
		double totalRepayOtherOverdueAmt = 0.00;
		double totalRepayOtherFee = 0.00;
		double totalRepayLateFee = 0.00;
		boolean isDoRepay = false;
	
		/*********************************************开始减免和挂账处理其他费用罚息*************************************************/
		String remissionRepayLogId = Utils.get16UUID();
		String stayRepayLogId = Utils.get16UUID();
		String repayLogId = Utils.get16UUID();
		for(int i =0 ;i <settleOtherNewWaitingChargeList.size();i++)
		{
			WaitingChargeNew item = settleOtherNewWaitingChargeList.get(i);
			//其他费用罚息
			double otherFeeOverdueAmt = item.getRepayOverdueAmount();
			//减免其他费用罚息
			if(Double.compare(remissionOtherOverdueAmt, 0.00)>0 && Double.compare(otherFeeOverdueAmt, 0.00)>0)
			{
				if(Double.compare(otherFeeOverdueAmt, remissionOtherOverdueAmt)>=0)
				{
					otherFeeOverdueAmt-=remissionOtherOverdueAmt;
					this.saveRepayLogItem(remissionRepayLogId, FeeType.Other, item.getFeeRefId(), RepayItem.OTHEROVERDUEAMT, remissionOtherOverdueAmt);
					remissionOtherOverdueAmt=0.00;
				}
				else
				{
					remissionOtherOverdueAmt-=otherFeeOverdueAmt;
					this.saveRepayLogItem(remissionRepayLogId,FeeType.Other,item.getFeeRefId(),RepayItem.OTHEROVERDUEAMT,otherFeeOverdueAmt);
					otherFeeOverdueAmt=0.00;
				}
				isDoRemission = true;
			}
			//挂账冲账剩余的其他费用罚息
			if(Double.compare(stayAmount, 0.00)>0 && applyType.equals(LoanApplyTaskType.Settle) && Double.compare(otherFeeOverdueAmt, 0.00)>0)
			{
				if(Double.compare(otherFeeOverdueAmt, stayAmount)>=0)
				{
					otherFeeOverdueAmt-=stayAmount;
					totalStayOtherOverdueAmt += stayAmount;
					this.saveRepayLogItem(stayRepayLogId, FeeType.Other, item.getFeeRefId(), RepayItem.OTHEROVERDUEAMT, stayAmount);
					stayAmount=0.00;
				}
				else
				{
					stayAmount-=otherFeeOverdueAmt;
					totalStayOtherOverdueAmt += otherFeeOverdueAmt;
					this.saveRepayLogItem(stayRepayLogId,FeeType.Other,item.getFeeRefId(),RepayItem.OTHEROVERDUEAMT,otherFeeOverdueAmt);
					otherFeeOverdueAmt=0.00;
				}
				isDoStay = true;
			}
			//对公还款冲账
			if(Double.compare(otherFeeOverdueAmt, 0.00)>0)
			{
				totalRepayOtherOverdueAmt += otherFeeOverdueAmt;
				this.saveRepayLogItem(repayLogId,FeeType.Other,item.getFeeRefId(),RepayItem.OTHEROVERDUEAMT,otherFeeOverdueAmt);
				isDoRepay=true;
			}
			item.setRepayOverdueAmount(otherFeeOverdueAmt);
			settleOtherNewWaitingChargeList.set(i, item);
		}
		/*********************************************开始减免和挂账其他费用本金*************************************************/
		for(int i =0 ;i <settleOtherNewWaitingChargeList.size();i++)
		{
			WaitingChargeNew item = settleOtherNewWaitingChargeList.get(i);
			//其他费用
			double otherFee = item.getRepayCapital();
			//减免其他费用
			if(Double.compare(remissionOtherFee, 0.00)>0 && Double.compare(otherFee, 0.00)>0)
			{
				if(Double.compare(otherFee, remissionOtherFee)>=0)
				{
					otherFee-=remissionOtherFee;
					this.saveRepayLogItem(remissionRepayLogId, FeeType.Other, item.getFeeRefId(), RepayItem.OTHERFEE, remissionOtherFee);
					remissionOtherOverdueAmt=0.00;
				}
				else
				{
					remissionOtherFee-=otherFee;
					this.saveRepayLogItem(remissionRepayLogId,FeeType.Other,item.getFeeRefId(),RepayItem.OTHERFEE,otherFee);
					otherFee=0.00;
				}
				isDoRemission = true;
			}
			//挂账冲账其他费用
			if(Double.compare(stayAmount, 0.00)>0 && applyType.equals(LoanApplyTaskType.Settle) && Double.compare(otherFee, 0.00)>0)
			{
				if(Double.compare(otherFee, stayAmount)>=0)
				{
					otherFee-=stayAmount;
					totalStayOtherFee += stayAmount;
					this.saveRepayLogItem(stayRepayLogId, FeeType.Other, item.getFeeRefId(), RepayItem.OTHERFEE, stayAmount);
					stayAmount=0.00;
				}
				else
				{
					stayAmount-=otherFee;
					totalStayOtherFee += otherFee;
					this.saveRepayLogItem(stayRepayLogId,FeeType.Other,item.getFeeRefId(),RepayItem.OTHERFEE,otherFee);
					otherFee=0.00;
				}
				isDoStay = true;
			}
			//对公还款冲账
			if(Double.compare(otherFee, 0.00)>0)
			{
				totalRepayOtherFee += otherFee;
				this.saveRepayLogItem(repayLogId,FeeType.Other,item.getFeeRefId(),RepayItem.OTHERFEE,otherFee);
				isDoRepay=true;
			}
			item.setRepayCapital(otherFee);
			settleOtherNewWaitingChargeList.set(i, item);
		}
		/*********************************************开始减免和挂账计划还款罚息*************************************************/
		for(int i =0 ;i <settlePlanNewWatingChargeList.size();i++)
		{
			WaitingChargeNew item = settlePlanNewWatingChargeList.get(i);
			//罚息
			double overdueAmt = item.getRepayOverdueAmount();
			//减免罚息
			if(Double.compare(remissionOverdueAmt, 0.00)>0 && Double.compare(overdueAmt, 0.00)>0)
			{
				if(Double.compare(overdueAmt, remissionOverdueAmt)>=0)
				{
					overdueAmt-=remissionOverdueAmt;
					this.saveRepayLogItem(remissionRepayLogId, FeeType.Plan, item.getFeeRefId(), RepayItem.OVERDUEAMT, remissionOverdueAmt);
					remissionOverdueAmt=0.00;
				}
				else
				{
					remissionOverdueAmt-=overdueAmt;
					this.saveRepayLogItem(remissionRepayLogId,FeeType.Plan,item.getFeeRefId(),RepayItem.OVERDUEAMT,overdueAmt);
					overdueAmt=0.00;
				}
				isDoRemission = true;
			}
			//挂账冲账罚息
			if(Double.compare(stayAmount, 0.00)>0 && applyType.equals(LoanApplyTaskType.Settle) && Double.compare(overdueAmt, 0.00)>0)
			{
				if(Double.compare(overdueAmt, stayAmount)>=0)
				{
					overdueAmt-=stayAmount;
					totalStayOverdueAmt +=stayAmount;
					this.saveRepayLogItem(stayRepayLogId, FeeType.Plan, item.getFeeRefId(), RepayItem.OVERDUEAMT, stayAmount);
					stayAmount=0.00;
				}
				else
				{
					stayAmount-=overdueAmt;
					totalStayOverdueAmt +=overdueAmt;
					this.saveRepayLogItem(stayRepayLogId,FeeType.Plan,item.getFeeRefId(),RepayItem.OVERDUEAMT,overdueAmt);
					overdueAmt=0.00;
				}
				isDoStay = true;
			}
			//对公还款冲账
			if(Double.compare(overdueAmt, 0.00)>0)
			{
				totalRepayOverdueAmt += overdueAmt;
				this.saveRepayLogItem(repayLogId,FeeType.Plan,item.getFeeRefId(),RepayItem.OVERDUEAMT,overdueAmt);
				isDoRepay=true;
			}
			item.setRepayOverdueAmount(overdueAmt);
			settlePlanNewWatingChargeList.set(i, item);
		}
		/*********************************************开始减免和挂账计划还款利息*************************************************/
		for(int i =0 ;i <settlePlanNewWatingChargeList.size();i++)
		{
			WaitingChargeNew item = settlePlanNewWatingChargeList.get(i);
			//利息
			double interest = item.getRepayInterest();
			 //减免利息
			if(Double.compare(remissionInterest, 0.00)>0 && Double.compare(interest, 0.00)>0)
			{
				if(Double.compare(interest, remissionInterest)>=0)
				{
					interest-=remissionInterest;
					this.saveRepayLogItem(remissionRepayLogId, FeeType.Plan, item.getFeeRefId(), RepayItem.INTEREST, remissionInterest);
					remissionOverdueAmt=0.00;
				}
				else
				{
					remissionInterest-=interest;
					this.saveRepayLogItem(remissionRepayLogId, FeeType.Plan, item.getFeeRefId(), RepayItem.INTEREST, interest);
					interest=0.00;
				}
				isDoRemission = true;
			}
			//挂账冲账利息
			if(Double.compare(stayAmount, 0.00)>0 && applyType.equals(LoanApplyTaskType.Settle) && Double.compare(interest, 0.00)>0)
			{
				if(Double.compare(interest, stayAmount)>=0)
				{
					interest-=stayAmount;
					totalStayInterest +=stayAmount;
					this.saveRepayLogItem(stayRepayLogId, FeeType.Plan, item.getFeeRefId(), RepayItem.INTEREST, stayAmount);
					stayAmount=0.00;
				}
				else
				{
					stayAmount-=interest;
					totalStayInterest +=interest;
					this.saveRepayLogItem(stayRepayLogId,FeeType.Plan,item.getFeeRefId(),RepayItem.INTEREST,interest);
					interest=0.00;
				}
				isDoStay = true;
			}
			//对公还款冲账
			if(Double.compare(interest, 0.00)>0)
			{
				totalRepayInterest += interest;
				this.saveRepayLogItem(repayLogId,FeeType.Plan,item.getFeeRefId(),RepayItem.INTEREST,interest);
				isDoRepay = true;
			}
			item.setRepayInterest(interest);
			settlePlanNewWatingChargeList.set(i, item);
		}
		/*********************************************开始减免和挂账计划还款本金*************************************************/
		for(int i =0 ;i <settlePlanNewWatingChargeList.size();i++)
		{
			WaitingChargeNew item = settlePlanNewWatingChargeList.get(i);
			//本金
			double capital = item.getRepayCapital();
			//减免本金
			if(Double.compare(remissionCapital, 0.00)>0 && Double.compare(capital, 0.00)>0)
			{
				if(Double.compare(capital, remissionCapital)>=0)
				{
					capital-=remissionCapital;
					this.saveRepayLogItem(remissionRepayLogId, FeeType.Plan, item.getFeeRefId(), RepayItem.CAPITAL, capital);
					remissionCapital=0.00;
				}
				else
				{
					remissionCapital-=capital;
					this.saveRepayLogItem(remissionRepayLogId, FeeType.Plan, item.getFeeRefId(), RepayItem.CAPITAL, capital);
					capital=0.00;
				}
				isDoRemission = true;
			}
			//挂账冲账本金
			if(Double.compare(stayAmount, 0.00)>0 && applyType.equals(LoanApplyTaskType.Settle) && Double.compare(capital, 0.00)>0)
			{
				if(Double.compare(capital, stayAmount)>=0)
				{
					capital-=stayAmount;
					totalStayCaptial +=stayAmount;
					this.saveRepayLogItem(stayRepayLogId, FeeType.Plan, item.getFeeRefId(), RepayItem.CAPITAL, stayAmount);
					stayAmount=0.00;
				}
				else
				{
					stayAmount-=capital;
					totalStayCaptial +=capital;
					this.saveRepayLogItem(stayRepayLogId,FeeType.Plan,item.getFeeRefId(),RepayItem.CAPITAL,capital);
					capital=0.00;
				}
				isDoStay = true;
			}
			if(Double.compare(capital, 0.00)>0)
			{
				totalRepayCapital += capital;
				this.saveRepayLogItem(repayLogId,FeeType.Plan,item.getFeeRefId(),RepayItem.CAPITAL,capital);
				isDoRepay = true;
			}
			item.setRepayCapital(capital);
			settlePlanNewWatingChargeList.set(i, item);
		}
		/*********************************************开始减免和挂账展期费用或结清违约金*************************************************/
		//展期费或提前结清违约金
		double sf = serviceFee.getAmount();
		 //减免
		if(Double.compare(remissionLateFee, 0.00)>0 && Double.compare(sf, 0.00)>0)
		{
			if(Double.compare(sf, remissionLateFee)>0)
			{
				sf-=remissionLateFee;
				this.saveRepayLogItem(remissionRepayLogId, serviceFee.getFeeType().equals("fylx03")?FeeType.ExtendFee:FeeType.LateFee, serviceFee.getId(), RepayItem.CAPITAL, remissionLateFee);
				remissionLateFee=0.00;
			}
			else
			{
				remissionLateFee-=sf;
				this.saveRepayLogItem(remissionRepayLogId, serviceFee.getFeeType().equals("fylx03")?FeeType.ExtendFee:FeeType.LateFee, serviceFee.getId(), RepayItem.CAPITAL, sf);
				sf=0.00;
			}
			isDoRemission = true;
			serviceFee.setAmount(sf);
		}
		//挂账冲账
		if(Double.compare(stayAmount, 0.00)>0 && applyType.equals(LoanApplyTaskType.Settle) && Double.compare(sf, 0.00)>0)
		{
			if(Double.compare(sf, stayAmount)>=0)
			{
				sf-=stayAmount;
				totalStayLateFee += stayAmount;
				this.saveRepayLogItem(stayRepayLogId, serviceFee.getFeeType().equals("fylx03")?FeeType.ExtendFee:FeeType.LateFee, serviceFee.getId(), RepayItem.CAPITAL, stayAmount);
				stayAmount=0.00;
			}
			else
			{
				stayAmount-=sf;
				totalStayLateFee += stayAmount;
				this.saveRepayLogItem(stayRepayLogId,serviceFee.getFeeType().equals("fylx03")?FeeType.ExtendFee:FeeType.LateFee, serviceFee.getId(),RepayItem.CAPITAL,sf);
				sf=0.00;
			}
			isDoStay = true;
			serviceFee.setAmount(sf);
		}
		//对公还款
		if(Double.compare(sf, 0.00)>0)
		{
			totalRepayLateFee += sf;
			this.saveRepayLogItem(repayLogId,serviceFee.getFeeType().equals("fylx03")?FeeType.ExtendFee:FeeType.LateFee, serviceFee.getId(),RepayItem.CAPITAL,sf);
			isDoRepay = true;
		}
		//记录减免还款记录
		if(isDoRemission)
		{
			RepayLog repayLog = new RepayLog();
			repayLog.setId(remissionRepayLogId);
			repayLog.setAppId(appId);
			int cnt = repayLogDao.selectRepayLogCntByAppId(appId);
			repayLog.setSeq(++cnt);
			repayLog.setRepayMode(RepayMode.Remission.getName());
			repayLog.setRepayAmount(totalRemissionAmt);
			repayLog.setRepayTime(chargeDate);
			repayLog.setLogStatus(RepayLogStatus.Normal.getName());
			repayLog.setCapital(totalRemissionCapital);
			repayLog.setInterest(totalRemissionInterest);
			repayLog.setOverdueAmount(totalRemissionOverdueAmt);
			repayLog.setOtherFee(totalRemissionOtherFee);
			repayLog.setOtherOverdueAmount(totalRemissionOtherOverdueAmt);
			if(applyType.equals(LoanApplyTaskType.Settle))
			{
				repayLog.setLateFee(totalRemissionLateFee);
				repayLog.setExtendFee(0.00);
			}
			else
			{
				repayLog.setExtendFee(totalRemissionLateFee);
				repayLog.setLateFee(0.00);
			}
			repayLog.setStageAmount(0.00);
			repayLogDao.insert(repayLog);
		}
		//记录挂账冲账记录
		if(isDoStay)
		{
			RepayLog repayLog = new RepayLog();
			repayLog.setId(stayRepayLogId);
			repayLog.setAppId(appId);
			int cnt = repayLogDao.selectRepayLogCntByAppId(appId);
			repayLog.setSeq(++cnt);
			repayLog.setRepayMode(RepayMode.StayAccounting.getName());
			double totalStayAmt = totalStayCaptial + totalStayInterest+totalStayOverdueAmt+totalStayOtherOverdueAmt+totalStayOtherFee+totalStayLateFee;
			repayLog.setRepayAmount(totalStayAmt);
			repayLog.setRepayTime(chargeDate);
			repayLog.setLogStatus(RepayLogStatus.Normal.getName());
			repayLog.setCapital(totalStayCaptial);
			repayLog.setInterest(totalStayInterest);
			repayLog.setOverdueAmount(totalStayOverdueAmt);
			repayLog.setOtherFee(totalStayOtherFee);
			repayLog.setOtherOverdueAmount(totalStayOtherOverdueAmt);
			if(applyType.equals(LoanApplyTaskType.Settle))
			{
				repayLog.setLateFee(totalStayLateFee);
				repayLog.setExtendFee(0.00);
			}
			else
			{
				repayLog.setExtendFee(totalStayLateFee);
				repayLog.setLateFee(0.00);
			}
			repayLog.setStageAmount(0.00);
			repayLogDao.insert(repayLog);
		}
		//记录对公还款记录
		if(isDoRepay)
		{
			RepayLog repayLog = new RepayLog();
			repayLog.setId(repayLogId);
			repayLog.setAppId(appId);
			int cnt = repayLogDao.selectRepayLogCntByAppId(appId);
			repayLog.setSeq(++cnt);
			repayLog.setRepayMode(RepayMode.PublicRepay.getName());
			double totalRepayAmt = totalRepayCapital + totalRepayInterest+totalRepayOverdueAmt+totalRepayOtherOverdueAmt+totalRepayOtherFee+totalRepayLateFee;
			repayLog.setRepayAmount(totalRepayAmt);
			repayLog.setRepayTime(chargeDate);
			repayLog.setLogStatus(RepayLogStatus.Normal.getName());
			repayLog.setCapital(totalRepayCapital);
			repayLog.setInterest(totalRepayInterest);
			repayLog.setOverdueAmount(totalRepayOverdueAmt);
			repayLog.setOtherFee(totalRepayOtherFee);
			repayLog.setOtherOverdueAmount(totalRepayOtherOverdueAmt);
			if(applyType.equals(LoanApplyTaskType.Settle))
			{
				repayLog.setLateFee(totalRepayLateFee);
				repayLog.setExtendFee(0.00);
			}
			else
			{
				repayLog.setExtendFee(totalRepayLateFee);
				repayLog.setLateFee(0.00);
			}
			repayLog.setStageAmount(0.00);
			repayLogDao.insert(repayLog);
		}
		//如果之前有挂账，则挂账冲账后更新挂账信息
		if(stayAccount!=null)
		{
			stayAccount.setStayAmount(stayAmount);
			stayAccountDao.updateByPrimaryKey(stayAccount);
		}
		 
		/**
		 * 获取所有还款计划信息，刷新新的还款信息
		 */
		List<WaitingChargeNew> otherNewWaitingCharge = waitingchargeNewDao.selectList(applyType.getName(), applyId, FeeType.Other.getName(),false);
		for(WaitingChargeNew item : otherNewWaitingCharge)
		{
			//获取对应其他费用信息
			OtherFee otherFee  = otherFeeDao.selectByPrimaryKey(item.getFeeRefId());
			//获取对应其他费用还款记录
			List<RepayLogItem> otherFeeList = repayLogItemDao.selectList(FeeType.Other.getName(), item.getFeeRefId(), RepayItem.OTHERFEE.getName());
			//获取对应其他费用罚息还款记录
			List<RepayLogItem> otherOverdueList = repayLogItemDao.selectList(FeeType.Other.getName(), item.getFeeRefId(), RepayItem.OTHEROVERDUEAMT.getName());
			//删除代扣款明细,用新的数据替换原始还款数据
			WaitingCharge waitingChargePo = waitingChargeDao.selectByFeeInfo(item.getFeeType(), item.getFeeRefId());
			if(waitingChargePo!=null)
			{
				waitingChargeDao.deleteByPrimaryKey(waitingChargePo.getId());
			}
			otherFee.setAddupOverdueDay(item.getAddupOverdueDay());
			//将已扣款金额加上去为最终的应还款金额
			double tempOtherOverdueAmt = item.getAddupOverdueAmount();
			for(RepayLogItem otherOverdueItem : otherOverdueList)
			{
				tempOtherOverdueAmt += otherOverdueItem.getRepayAmount();
			}
			otherFee.setAddupOverdueAmount(tempOtherOverdueAmt);
			double tempOtherFeeAmt = item.getRepayCapital();
			
			for(RepayLogItem otherFeeItem : otherFeeList)
			{
				tempOtherFeeAmt+=otherFeeItem.getRepayAmount();
			}
			otherFee.setFeeTotalAmount(tempOtherFeeAmt);
			otherFee.setRepayStatus(RepayStatus.Settled.getName());
			otherFee.setSettleDate(chargeDate);
			otherFeeDao.updateByPrimaryKey(otherFee);
		}
		
		List<WaitingChargeNew> planNewWaitingCharge = waitingchargeNewDao.selectList(applyType.getName(), applyId, FeeType.Plan.getName(),false);
		for(WaitingChargeNew item : planNewWaitingCharge)
		{
			RepayPlan oldPlan = repayPlanDao.selectRepayPlan(appId, item.getPeriod());
			List<RepayLogItem> planOverdueList  = new ArrayList<RepayLogItem>();
			List<RepayLogItem> planInterestList = new ArrayList<RepayLogItem>();
			List<RepayLogItem> planCapitalList = new ArrayList<RepayLogItem>();
			if(oldPlan!=null)
			{
				//获取对应的罚息还款记录
				planOverdueList = repayLogItemDao.selectList(FeeType.Plan.getName(), item.getFeeRefId(), RepayItem.OVERDUEAMT.getName());
				//获取对应的应还利息
				planInterestList = repayLogItemDao.selectList(FeeType.Plan.getName(), item.getFeeRefId(), RepayItem.INTEREST.getName());
				//获取对应的应还本金
				planCapitalList = repayLogItemDao.selectList(FeeType.Plan.getName(), item.getFeeRefId(), RepayItem.CAPITAL.getName());
			}
			if(item.getDoSettle())
			{
				oldPlan.setAddupOverdueDay(item.getAddupOverdueDay());
				oldPlan.setValueDate(item.getValueDate());
				oldPlan.setClosingDate(item.getClosingDate());
				double tempOverdueAmount = 0.00;
				for(RepayLogItem log: planOverdueList)
				{
					tempOverdueAmount += log.getRepayAmount();
				}
				oldPlan.setAddupOverdueAmount(tempOverdueAmount);
				double tempInterestAmount = 0.00;
				for(RepayLogItem log : planInterestList)
				{
					tempInterestAmount += log.getRepayAmount();
				}
				oldPlan.setRepayInterest(tempInterestAmount);
				double tempCapitalAmount = 0.00;
				for(RepayLogItem log : planCapitalList)
				{
					tempCapitalAmount += log.getRepayAmount();
				}
				oldPlan.setRepayCapital(tempCapitalAmount);
				oldPlan.setRepayStatus(RepayStatus.Settled.getName());
				oldPlan.setRepayTotalAmount(tempOverdueAmount+tempInterestAmount+tempCapitalAmount);
				oldPlan.setSettleDate(chargeDate);
				repayPlanDao.updateByPrimaryKey(oldPlan);
				/******************************************************删除对应待扣款明细**********************************/
				WaitingCharge waitingChargePo = waitingChargeDao.selectByFeeInfo(item.getFeeType(), item.getFeeRefId());
				if(waitingChargePo!=null)
				{
					waitingChargeDao.deleteByPrimaryKey(waitingChargePo.getId());
				}
			}
			else
			{
				/**
				 * 如不做结清处理，这种情况只发生在产生新的还款计划，如部分结清、展期、变更还款日，此时还没有待扣款明细产生，则直接更新原始还款计划或插入新的还款计划
				 */
				if(oldPlan!=null)
				{
					oldPlan.setRepayCapital(item.getRepayCapital());
					oldPlan.setRepayInterest(item.getRepayInterest());
					oldPlan.setRepayTotalAmount(item.getRepayCapital()+item.getRepayInterest());
					oldPlan.setAddupOverdueAmount(item.getAddupOverdueAmount());
					oldPlan.setAddupOverdueDay(item.getAddupOverdueDay());
					oldPlan.setValueDate(item.getValueDate());
					oldPlan.setClosingDate(item.getClosingDate());
					oldPlan.setRepayStatus(RepayStatus.Repaying.getName());
					repayPlanDao.updateByPrimaryKey(oldPlan);
				}
				else
				{
					RepayPlan newPlan = new RepayPlan();
					newPlan.setId(Utils.get16UUID());
					newPlan.setAppId(appId);
					newPlan.setPeriod(item.getPeriod());
					newPlan.setRepayCapital(item.getRepayCapital());
					newPlan.setRepayInterest(item.getRepayInterest());
					newPlan.setValueDate(item.getValueDate());
					newPlan.setClosingDate(item.getClosingDate());
					newPlan.setRemainCapital(0.00);
					newPlan.setRepayTotalAmount(0.00);
					newPlan.setAddupOverdueAmount(0.00);
					newPlan.setAddupOverdueDay(0);
					newPlan.setRepayStatus(RepayStatus.Repaying.getName());
					repayPlanDao.insert(newPlan);
				}
			}
		}
		
		/*************************************************修改总账相关数据,执行一些后续处理***********************************************************/
		GeneralLedger ledgerPo = ledgerDao.selectByAppId(appId);
		if(applyType.equals(LoanApplyTaskType.Settle))
		{
			ledgerPo.setRemainCapital(settlePo.getSettleAfterCapital());
			if(settlePo.getSettleType().equals(SettleType.AllSettle.getName()))
			{
				ledgerPo.setRepayStatus(RepayStatus.Settled.getName());
				ledgerPo.setSettleDate(chargeDate);
			}
			else
			{
				ledgerPo.setRepayStatus(RepayStatus.Repaying.getName());
			}
		}
		if(applyType.equals(LoanApplyTaskType.ExtendPeriod))
		{
			//展期需要更新总账剩余本金、还款方式、新的还款期数
			ledgerPo.setRepayMode(extendPeriodPo.getNewRepayMode());
			ledgerPo.setPeriod(ledgerPo.getPeriod()+extendPeriodPo.getExtendPeriod());
			ledgerPo.setRemainCapital(extendPeriodPo.getNewCapital());
			ledgerPo.setRepayStatus(RepayStatus.Repaying.getName());
		}
		if(applyType.equals(LoanApplyTaskType.AlterRepayDate))
		{
			ledgerPo.setRepayStatus(RepayStatus.Repaying.getName());
		}
		ledgerDao.updateByPrimaryKey(ledgerPo);
	}

	@Override
	public void checkCandoPublicRepay(String appId) throws Exception {
		// TODO Auto-generated method stub
		/**
		 * 在对公还款时涉及到对应还项进行操作的交易情况下都不能执行
		 */
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.Settle.getWorkflowKey())>0)
		{
			throw new Exception("正在申请结清，不能执行对公还款");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.ExtendPeriod.getWorkflowKey())>0)
		{
			throw new Exception("正在申请展期，不能执行对公还款");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.AlterRepayDate.getWorkflowKey())>0)
		{
			throw new Exception("正在申请变更还款日，不能执行对公还款");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.Remission.getWorkflowKey())>0)
		{
			throw new Exception("正在申请减免，不能执行对公还款");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.Refund.getWorkflowKey())>0)
		{
			throw new Exception("正在申请退款，不能执行对公还款");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.PublicRepay.getWorkflowKey())>0)
		{
			throw new Exception("正在申请对公还款，不能执行对公还款");
		}
	}

	@Override
	public void checkCandoSettle(String appId) throws Exception {
		// TODO Auto-generated method stub
		/**
		 * 结清时不能有任何对账务有影响的交易
		 */
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.Settle.getWorkflowKey())>0)
		{
			throw new Exception("正在申请结清，不能执行结清");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.ExtendPeriod.getWorkflowKey())>0)
		{
			throw new Exception("正在申请展期，不能执行结清");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.AlterRepayDate.getWorkflowKey())>0)
		{
			throw new Exception("正在申请变更还款日，不能执行结清");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.Remission.getWorkflowKey())>0)
		{
			throw new Exception("正在申请减免，不能执行结清");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.Refund.getWorkflowKey())>0)
		{
			throw new Exception("正在申请退款，不能执行结清");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.PublicRepay.getWorkflowKey())>0)
		{
			throw new Exception("正在申请对公还款，不能执行结清");
		}
		if(offerSummaryDao.selectOfferingCnt(appId)>0)
		{
			throw new Exception("正在报盘中，不能执行结清");
		}
	}

	@Override
	public void checkCandoRefund(String appId) throws Exception {
		// TODO Auto-generated method stub
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.Settle.getWorkflowKey())>0)
		{
			throw new Exception("正在申请结清，不能执行退款");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.ExtendPeriod.getWorkflowKey())>0)
		{
			throw new Exception("正在申请展期，不能执行退款");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.AlterRepayDate.getWorkflowKey())>0)
		{
			throw new Exception("正在申请变更还款日，不能执行退款");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.Remission.getWorkflowKey())>0)
		{
			throw new Exception("正在申请减免，不能执行退款");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.Refund.getWorkflowKey())>0)
		{
			throw new Exception("正在申请退款，不能执行退款");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.PublicRepay.getWorkflowKey())>0)
		{
			throw new Exception("正在申请对公还款，不能执行退款");
		}
		if(offerSummaryDao.selectOfferingCnt(appId)>0)
		{
			throw new Exception("正在报盘中，不能执行退款");
		}
	}

	@Override
	public void checkCandoExtendPeriod(String appId) throws Exception {
		// TODO Auto-generated method stub
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.Settle.getWorkflowKey())>0)
		{
			throw new Exception("正在申请结清，不能执行展期");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.ExtendPeriod.getWorkflowKey())>0)
		{
			throw new Exception("正在申请展期，不能执行展期");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.AlterRepayDate.getWorkflowKey())>0)
		{
			throw new Exception("正在申请变更还款日，不能执行展期");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.Remission.getWorkflowKey())>0)
		{
			throw new Exception("正在申请减免，不能执行展期");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.Refund.getWorkflowKey())>0)
		{
			throw new Exception("正在申请退款，不能执行展期");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.PublicRepay.getWorkflowKey())>0)
		{
			throw new Exception("正在申请对公还款，不能执行展期");
		}
		if(offerSummaryDao.selectOfferingCnt(appId)>0)
		{
			throw new Exception("正在报盘中，不能执行展期");
		}
	}

	@Override
	public void checkCandoAlterRepayDate(String appId) throws Exception {
		// TODO Auto-generated method stub
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.Settle.getWorkflowKey())>0)
		{
			throw new Exception("正在申请结清，不能执行变更还款日");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.ExtendPeriod.getWorkflowKey())>0)
		{
			throw new Exception("正在申请展期，不能执行变更还款日");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.AlterRepayDate.getWorkflowKey())>0)
		{
			throw new Exception("正在申请变更还款日，不能执行变更还款日");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.Remission.getWorkflowKey())>0)
		{
			throw new Exception("正在申请减免，不能执行变更还款日");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.Refund.getWorkflowKey())>0)
		{
			throw new Exception("正在申请退款，不能执行变更还款日");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.PublicRepay.getWorkflowKey())>0)
		{
			throw new Exception("正在申请对公还款，不能执行变更还款日");
		}
		if(offerSummaryDao.selectOfferingCnt(appId)>0)
		{
			throw new Exception("正在报盘中，不能执行变更还款日");
		}
	}

	@Override
	public void checkCandoRemission(String appId) throws Exception {
		// TODO Auto-generated method stub
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.Settle.getWorkflowKey())>0)
		{
			throw new Exception("正在申请结清，不能执行减免");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.ExtendPeriod.getWorkflowKey())>0)
		{
			throw new Exception("正在申请展期，不能执行减免");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.AlterRepayDate.getWorkflowKey())>0)
		{
			throw new Exception("正在申请变更还款日，不能执行减免");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.Remission.getWorkflowKey())>0)
		{
			throw new Exception("正在申请减免，不能执行减免");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.Refund.getWorkflowKey())>0)
		{
			throw new Exception("正在申请退款，不能执行减免");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.PublicRepay.getWorkflowKey())>0)
		{
			throw new Exception("正在申请对公还款，不能执行减免");
		}
		if(offerSummaryDao.selectOfferingCnt(appId)>0)
		{
			throw new Exception("正在报盘中，不能执行减免");
		}
	}

	@Override
	public void checkCandoOtherFee(String appId) throws Exception {
		// TODO Auto-generated method stub
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.Settle.getWorkflowKey())>0)
		{
			throw new Exception("正在申请结清，不能执行减免");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.ExtendPeriod.getWorkflowKey())>0)
		{
			throw new Exception("正在申请展期，不能执行减免");
		}
		if(loanQueryDao.selectProcessingLoanTaskCnt(appId, LoanApplyTaskType.AlterRepayDate.getWorkflowKey())>0)
		{
			throw new Exception("正在申请变更还款日，不能执行减免");
		}
	}

	@Override
	public RepayingFeeItemVo getSettleRepayingFeeItems(String appId, boolean isCalOverdueAmount, Date overdueEndDate,
			boolean isReduceStayAmount, boolean isContainCurPeriod) {
		// TODO Auto-generated method stub
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
				int spaceDay = Utils.getSpaceDay(new Date(), overdueEndDate);
				//获取日罚息率
				GeneralLedger ledgerRecord = ledgerDao.selectByAppId(appId);
				double dayLateRate = ledgerRecord.getDayLateRate();
				
				//获取代扣明细表计划还款明细(不包含当期)
				List<WaitingCharge> waitingChargePlanList = this.getWaitingChargeTypePlan(appId, false);
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
				//获取当期还款信息，如果当前结清日期不是还款日，则结清的当期本金+利息从还款计划获取，反之则从待扣款记录获取
				if(isContainCurPeriod)
				{
					RepayPlan curRepayPlan = this.getCurrentPeriodRepayPlan(appId);
					if(curRepayPlan.getWatingCharge()==null)
					{
						repayTotalCapital += curRepayPlan.getRepayCapital();
						repayTotalInterest += curRepayPlan.getRepayInterest();	
					}
					else
					{
						repayTotalCapital += curRepayPlan.getWatingCharge().getRepayCapital();
						repayTotalInterest += curRepayPlan.getWatingCharge().getRepayInterest();
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
}
