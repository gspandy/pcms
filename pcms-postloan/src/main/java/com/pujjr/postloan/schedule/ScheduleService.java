package com.pujjr.postloan.schedule;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.service.IHolidayService;
import com.pujjr.postloan.dao.GeneralLedgerMapper;
import com.pujjr.postloan.dao.OtherFeeMapper;
import com.pujjr.postloan.dao.RepayPlanMapper;
import com.pujjr.postloan.dao.StayAccountMapper;
import com.pujjr.postloan.dao.WaitingChargeMapper;
import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.domain.OtherFee;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.domain.StayAccount;
import com.pujjr.postloan.domain.WaitingCharge;
import com.pujjr.postloan.enumeration.FeeType;
import com.pujjr.postloan.enumeration.OfferStatus;
import com.pujjr.postloan.enumeration.RepayStatus;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.utils.Utils;

public class ScheduleService
{
	
	@Autowired
	private WaitingChargeMapper waitingChargeDao;
	@Autowired
	private RepayPlanMapper repayPlanDao;
	@Autowired
	private IHolidayService holidayService;
	@Autowired
	private GeneralLedgerMapper ledgerDao;
	@Autowired
	private OtherFeeMapper otherFeeDao;
	@Autowired
	private StayAccountMapper stayAccountDao;
	@Autowired
	private IAccountingService accountingService;
	
	/**
	 * 日切账务处理
	 * @throws ParseException 
	 */
	private void cutOff() throws ParseException 
	{
		// TODO Auto-generated method stub
		//获取当前日期及下一个工作日
		System.out.println("开始日切处理");
		Date curDate = Utils.str82date(Utils.getFormatDate(new Date(), "yyyyMMdd"));
		Date workDate =  holidayService.getWorkDate(curDate);
		/**
		 * 应还处理阶段
		 */
		//获取当前还款计划已到结账日的还款计划并插入待扣款明细表
		List<RepayPlan> needChargePlanList =repayPlanDao.selectNeedChargeRepayPlanList(curDate);
		for(RepayPlan item : needChargePlanList)
		{
			//保存至待扣款明细表
			WaitingCharge waitingChargePo = new WaitingCharge();
			waitingChargePo.setId(Utils.get16UUID());
			waitingChargePo.setAppId(item.getAppId());
			waitingChargePo.setFeeType(FeeType.Plan.getName());
			waitingChargePo.setFeeRefId(item.getId());
			waitingChargePo.setRepayCapital(item.getRepayCapital());
			waitingChargePo.setRepayInterest(item.getRepayInterest());
			waitingChargePo.setRepayOverdueAmount(0.00);
			waitingChargePo.setClosingDate(item.getClosingDate());
			//还款日设置为下一个工作日期
			waitingChargePo.setRepayDate(workDate);
			waitingChargePo.setOfferStatus(OfferStatus.WaitOffer.getName());
			waitingChargePo.setGenTime(new Date());
			waitingChargePo.setVersionId(1);
			waitingChargePo.setBatchTaskId("1");
			waitingChargeDao.insert(waitingChargePo);
			
			//修改还款计划为还款中
			item.setRepayStatus(RepayStatus.Repaying.getName());
			repayPlanDao.updateByPrimaryKey(item);
		}
		/**
		 * 罚息计算阶段
		 */
		//获取待扣款明细表需要计算罚息的待扣款明细
		List<WaitingCharge> calOverdueAmountList = waitingChargeDao.selectNeedCalOverdueAmountList(curDate);
		for(WaitingCharge item : calOverdueAmountList)
		{
			//应还本金
			double capital = item.getRepayCapital();
			//应还利息
			double interest = item.getRepayInterest();
			//应还罚息
			double overdueAmount = item.getRepayOverdueAmount();
			//获取日罚息率
			GeneralLedger ledgerPo = ledgerDao.selectByAppId(item.getAppId());
			double dayLateFee = ledgerPo.getDayLateRate();
			//计算产生的罚息
			double genOverdueAmount = Math.round((capital+interest)*dayLateFee*100)*0.01d;
			item.setRepayOverdueAmount(overdueAmount+genOverdueAmount);
			//更新待扣款明细表为新的逾期数据
			waitingChargeDao.updateByPrimaryKey(item);
			
			//更新对应关联的费用数据的汇总逾期天数、逾期金额并设置还款状态为已逾期
			if(item.getFeeType().equals(FeeType.Plan.getName()))
			{
				RepayPlan planPo = repayPlanDao.selectByPrimaryKey(item.getFeeRefId());
				planPo.setAddupOverdueDay(planPo.getAddupOverdueDay()+1);
				planPo.setAddupOverdueAmount(planPo.getAddupOverdueAmount()+genOverdueAmount);
				planPo.setRepayStatus(RepayStatus.Overdue.getName());
				repayPlanDao.updateByPrimaryKey(planPo);
			}
			if(item.getFeeType().equals(FeeType.Other.getName()))
			{
				OtherFee otherFeePo = otherFeeDao.selectByPrimaryKey(item.getFeeRefId());
				otherFeePo.setAddupOverdueDay(otherFeePo.getAddupOverdueDay()+1);
				otherFeePo.setAddupOverdueAmount(otherFeePo.getAddupOverdueAmount()+genOverdueAmount);
				otherFeePo.setRepayStatus(RepayStatus.Overdue.getName());
				otherFeeDao.updateByPrimaryKey(otherFeePo);
			}
			//更新总账为已逾期状态
			ledgerPo.setRepayStatus(RepayStatus.Overdue.getName());
			ledgerDao.updateByPrimaryKey(ledgerPo);
		}
		
		/**
		 * 挂账冲账处理阶段
		 */
		//找出挂账表金额大于0并且存在对应代扣明细记录的挂账记录
		List<StayAccount> stayAccountList = stayAccountDao.selectNeedReserveList();
		//按照申请单次序循环冲账
		for(StayAccount item : stayAccountList)
		{
			String appId = item.getAppId();
			double stayAmount = item.getStayAmount();
			accountingService.repayReverseAccounting(appId, stayAmount, curDate, "挂账还款");
		}
		System.out.println("结束日切处理");
	}

	public void dayJob() throws ParseException {
		// TODO Auto-generated method stub
		//日切账务处理
		cutOff();
	}

}
