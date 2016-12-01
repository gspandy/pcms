package com.pujjr.schedule.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pujjr.assetsmanage.service.ICollectionService;
import com.pujjr.base.service.IHolidayService;
import com.pujjr.postloan.dao.GeneralLedgerMapper;
import com.pujjr.postloan.dao.OtherFeeMapper;
import com.pujjr.postloan.dao.OverdueLogMapper;
import com.pujjr.postloan.dao.RepayPlanMapper;
import com.pujjr.postloan.dao.StayAccountMapper;
import com.pujjr.postloan.dao.WaitingChargeMapper;
import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.domain.OtherFee;
import com.pujjr.postloan.domain.OverdueLog;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.domain.StayAccount;
import com.pujjr.postloan.domain.WaitingCharge;
import com.pujjr.postloan.enumeration.FeeType;
import com.pujjr.postloan.enumeration.OfferStatus;
import com.pujjr.postloan.enumeration.RepayMode;
import com.pujjr.postloan.enumeration.RepayStatus;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.utils.Utils;

public class CutOffService
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
	@Autowired
	private OverdueLogMapper overdueLogDao;
	@Autowired
	private ICollectionService collectionService;
	/**
	 * 日切账务处理
	 * @throws ParseException 
	 */
	private void handleAccounting() throws ParseException 
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
		 * 挂账冲账处理阶段
		 */
		//找出挂账表金额大于0并且存在对应代扣明细记录的挂账记录
		List<StayAccount> stayAccountList = stayAccountDao.selectNeedReserveList();
		//按照申请单次序循环冲账
		for(StayAccount item : stayAccountList)
		{
			String appId = item.getAppId();
			double stayAmount = item.getStayAmount();
			accountingService.repayReverseAccounting(appId, stayAmount, curDate, RepayMode.StayAccounting,null);
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
			/**
			 * 计算产生的罚息,如果还款日与结账日不是同一天则这段时间差为节假日，
			**计算罚息时，如果当前日期为还款日后一天则应将节假日这几天计算罚息，否则只需要计算一天的罚息
			**/
			//计算罚息天数
			int overdueDays = 1;
			if(Utils.getSpaceDay(item.getRepayDate(), curDate)==1)
			{
				overdueDays = Utils.getSpaceDay(item.getClosingDate(), curDate);
			}
			double genOverdueAmount = Math.round((capital+interest)*dayLateFee*100*overdueDays)*0.01d;
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
		}
		
		/**
		 * 总账逾期状态刷新及逾期日志记录
		 */
		List<String> overdueAppList = waitingChargeDao.selectHasOverdueAppIdList(curDate);
		for(String appId : overdueAppList)
		{
			//更新总账逾期天数
			GeneralLedger ledgerPo = ledgerDao.selectByAppId(appId);
			ledgerPo.setAddupOverdueDay(ledgerPo.getAddupOverdueDay()+1);
			
			//总账已经处于逾期状态，则获取最近逾期记录，更新最后逾期日期和逾期次数，否则新增逾期记录
			OverdueLog latesetOverdueLog = overdueLogDao.selectLatesetLog(appId);
			if(ledgerPo.getRepayStatus().equals(RepayStatus.Overdue.getName()))
			{
				latesetOverdueLog.setEndDate(curDate);
				latesetOverdueLog.setAddupOverdueDay(latesetOverdueLog.getAddupOverdueDay()+1);
				overdueLogDao.updateByPrimaryKey(latesetOverdueLog);
			}
			else
			{
				//总账之前未逾期，则设置扣款状态为逾期，逾期次数+1
				ledgerPo.setRepayStatus(RepayStatus.Overdue.getName());
				ledgerPo.setAddupOverdueTime(ledgerPo.getAddupOverdueTime()+1);
				if(latesetOverdueLog!=null)
				{
					//逾期记录不是第一次则新的序号为最近一次+1
					OverdueLog newOverdueLog = new OverdueLog();
					newOverdueLog.setId(Utils.get16UUID());
					newOverdueLog.setSeq(latesetOverdueLog.getSeq()+1);
					newOverdueLog.setStartDate(curDate);
					newOverdueLog.setEndDate(curDate);
					newOverdueLog.setAddupOverdueDay(1);
					overdueLogDao.insert(newOverdueLog);
				}
				else
				{
					//第一次逾期创建新的逾期记录
					OverdueLog newOverdueLog = new OverdueLog();
					newOverdueLog.setId(Utils.get16UUID());
					newOverdueLog.setSeq(1);
					newOverdueLog.setStartDate(curDate);
					newOverdueLog.setEndDate(curDate);
					newOverdueLog.setAddupOverdueDay(1);
					overdueLogDao.insert(newOverdueLog);
				}
			}
			ledgerDao.updateByPrimaryKey(ledgerPo);
			
			//判断是否已经发起催收任务，如果没有则发起电话催收任务
			if(collectionService.checkHasCollectionTask(appId)==false)
			{
				collectionService.createPhoneCollectionTask("admin", appId, "系统提示：客户已逾期，请进行电话催收");
			}
			
		}
		System.out.println("结束日切处理");
	}

	public void run() throws ParseException {
		// TODO Auto-generated method stub
		//日切账务处理
		handleAccounting();
	}

}
