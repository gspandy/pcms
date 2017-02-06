package com.pujjr.schedule.service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pujjr.assetsmanage.service.ICollectionService;
import com.pujjr.assetsmanage.service.IInsuranceManageService;
import com.pujjr.assetsmanage.service.ITelInterviewService;
import com.pujjr.base.dao.ArchiveTaskMapper;
import com.pujjr.base.dao.InsuranceHisMapper;
import com.pujjr.base.domain.ArchiveTask;
import com.pujjr.base.domain.BankInfo;
import com.pujjr.base.domain.Holiday;
import com.pujjr.base.domain.InsuranceHis;
import com.pujjr.base.domain.Sequence;
import com.pujjr.base.domain.SysParam;
import com.pujjr.base.service.IBankService;
import com.pujjr.base.service.IHolidayService;
import com.pujjr.base.service.ISequenceService;
import com.pujjr.base.service.ISysParamService;
import com.pujjr.carcredit.constant.InsuranceType;
import com.pujjr.carcredit.domain.ApplyTenant;
import com.pujjr.carcredit.domain.SignContract;
import com.pujjr.carcredit.domain.SignFinanceDetail;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.service.ISignContractService;
import com.pujjr.postloan.dao.GeneralLedgerMapper;
import com.pujjr.postloan.dao.OtherFeeMapper;
import com.pujjr.postloan.dao.OverdueLogMapper;
import com.pujjr.postloan.dao.OverdueTodayMapper;
import com.pujjr.postloan.dao.RepayPlanMapper;
import com.pujjr.postloan.dao.StayAccountMapper;
import com.pujjr.postloan.dao.WaitingChargeMapper;
import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.domain.OtherFee;
import com.pujjr.postloan.domain.OverdueLog;
import com.pujjr.postloan.domain.OverdueToday;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.domain.StayAccount;
import com.pujjr.postloan.domain.WaitingCharge;
import com.pujjr.postloan.enumeration.ArchiveStatus;
import com.pujjr.postloan.enumeration.ArchiveType;
import com.pujjr.postloan.enumeration.FeeType;
import com.pujjr.postloan.enumeration.OfferStatus;
import com.pujjr.postloan.enumeration.RepayMode;
import com.pujjr.postloan.enumeration.RepayStatus;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.postloan.service.IPlanService;
import com.pujjr.postloan.service.IPostLoanSmsService;
import com.pujjr.postloan.vo.RepayPlanVo;
import com.pujjr.sms.service.ISmsService;
import com.pujjr.sms.vo.SmsMessageVo;
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
	@Autowired
	private ISequenceService  seqService;
	@Autowired
	private ArchiveTaskMapper archiveTaskDao;
	@Autowired
	private ITelInterviewService telInterviewService;
	@Autowired
	private InsuranceHisMapper insHisDao;
	@Autowired
	private IInsuranceManageService insManageService;
	@Autowired
	private ISmsService smsService;
	@Autowired
	private IApplyService applyService;
	@Autowired
	private ISignContractService signService;
	@Autowired
	private ISysParamService sysParamService;
	@Autowired
	private IPlanService planService;
	@Autowired
	private IBankService bankService;
	@Autowired
	private IPostLoanSmsService postLoanSmsService;
	@Autowired
	private OverdueTodayMapper overdueTodayDao;
	/**
	 * 日切账务处理
	 * @throws ParseException 
	 */
	private void handleAccounting(Date execDate) throws ParseException 
	{
		// TODO Auto-generated method stub
		//获取当前日期及下一个工作日
		System.out.println("开始日切处理");
		Date curDate = Utils.str82date(Utils.getFormatDate(execDate, "yyyyMMdd"));
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
			waitingChargePo.setGenTime(execDate);
			waitingChargePo.setVersionId(1);
			waitingChargePo.setBatchTaskId("1");
			//上一次罚息
			waitingChargePo.setReserver5(0.00);
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
		//清空当日逾期信息
		overdueTodayDao.deleteAll();
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
			double lastOverdueAmt = 0.00;
			//罚息值保留6位精度
			if(item.getReserver5()!=null)
			{
				lastOverdueAmt=item.getReserver5();
			}
			double newOverdueAmount = (capital+interest)*dayLateFee*overdueDays;
			double genOverdueAmount = newOverdueAmount+lastOverdueAmt;
			item.setReserver5(Utils.formateDouble2Double(genOverdueAmount,6));
			double s2OverdueAmount = Utils.formateDouble2Double(genOverdueAmount, 2);
			item.setRepayOverdueAmount(s2OverdueAmount);
			//更新待扣款明细表为新的逾期数据
			waitingChargeDao.updateByPrimaryKey(item);
			
			//更新对应关联的费用数据的汇总逾期天数、逾期金额并设置还款状态为已逾期
			if(item.getFeeType().equals(FeeType.Plan.getName()))
			{
				RepayPlan planPo = repayPlanDao.selectByPrimaryKey(item.getFeeRefId());
				int tmpAddupOverdueDay = 0;
				if(planPo.getAddupOverdueDay()!=null)
				{
					tmpAddupOverdueDay=planPo.getAddupOverdueDay();
				}
				planPo.setAddupOverdueDay(tmpAddupOverdueDay+overdueDays);
				double tmpAddupOverdueAmount = 0.00;
				if(planPo.getAddupOverdueAmount()!=null)
				{
					tmpAddupOverdueAmount = planPo.getAddupOverdueAmount();
				}
				planPo.setAddupOverdueAmount(Utils.formateDouble2Double(tmpAddupOverdueAmount+newOverdueAmount,6));
				planPo.setRepayStatus(RepayStatus.Overdue.getName());
				repayPlanDao.updateByPrimaryKey(planPo);
				
			}
			if(item.getFeeType().equals(FeeType.Other.getName()))
			{
				OtherFee otherFeePo = otherFeeDao.selectByPrimaryKey(item.getFeeRefId());
				int tmpAddupOverdueDay = 0;
				if(otherFeePo.getAddupOverdueDay()!=null)
				{
					tmpAddupOverdueDay=otherFeePo.getAddupOverdueDay();
				}
				double tmpAddupOverdueAmount = 0.00;
				if(otherFeePo.getAddupOverdueAmount()!=null)
				{
					tmpAddupOverdueAmount = otherFeePo.getAddupOverdueAmount();
				}
				otherFeePo.setAddupOverdueDay(tmpAddupOverdueDay+overdueDays);
				otherFeePo.setAddupOverdueAmount(Utils.formateDouble2Double(tmpAddupOverdueAmount+newOverdueAmount,6));
				otherFeePo.setRepayStatus(RepayStatus.Overdue.getName());
				otherFeeDao.updateByPrimaryKey(otherFeePo);
			}
			
			//记录申请单单日逾期信息，如果存在申请单逾期记录，则以逾期天数最大为准
			OverdueToday ot = overdueTodayDao.selectByAppId(item.getAppId());
			if(ot==null)
			{
				ot = new OverdueToday();
				ot.setId(Utils.get16UUID());
				ot.setAppId(item.getAppId());
				ot.setOverdueDay(overdueDays);
				if(overdueDays>1)
				{
					ot.setStartDate(item.getClosingDate());
					ot.setEndDate(curDate);
				}
				else
				{
					ot.setStartDate(curDate);
					ot.setEndDate(curDate);
				}
				overdueTodayDao.insert(ot);
			}
			else
			{
				if(ot.getOverdueDay()<overdueDays)
				{
					ot.setStartDate(item.getClosingDate());
					ot.setEndDate(curDate);
					ot.setOverdueDay(overdueDays);
					overdueTodayDao.updateByPrimaryKey(ot);
				}
			}
		}
		
		/**
		 * 总账逾期状态刷新及逾期日志记录
		 */
		List<OverdueToday> overdueTodayList = overdueTodayDao.selectList();
		for(OverdueToday item : overdueTodayList)
		{
			String appId = item.getAppId();
			//获取总账
			GeneralLedger ledgerPo = ledgerDao.selectByAppId(appId);
			//获取最近逾期记录
			OverdueLog latesetOverdueLog = overdueLogDao.selectLatesetLog(appId);
			
		}
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
					newOverdueLog.setAppId(appId);
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
					newOverdueLog.setAppId(appId);
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
	/**
	 * 初始化序列号
	 */
	private void resetSequence()
	{
		List<Sequence> seqList = seqService.getAll();
		Calendar cal = Calendar.getInstance();
	    int day = cal.get(Calendar.DATE);
	    int month = cal.get(Calendar.MONTH) + 1;
		for(Sequence item : seqList)
		{
			switch(item.getResetTime())
			{
				case "day" :
					//每天充值
					item.setCurval(1);
					seqService.modify(item);
					break;
				case "month":
				    //每月1号重置
				    if(day==1)
				    {
				    	item.setCurval(1);
				    	seqService.modify(item);
				    }
				    break;
				case "year":
				    if(month == 1 && day == 1)
				    {
				    	item.setCurval(1);
				    	seqService.modify(item);
				    }
					break;
				default:
					break;
			}
		}
	}
	//计算归档任务逾期
	private void checkArchiveTaskOverdue(Date execDate)
	{
		Date beforeDay = Utils.getDateAfterDay(execDate, -1);
		List<ArchiveTask> overdueTasks = archiveTaskDao.selectArchiveTaskOverdueList(ArchiveType.LoanComplete.getName(), beforeDay);
		for(ArchiveTask item : overdueTasks)
		{
			item.setArchiveStatus(ArchiveStatus.ArchiveOverdue.getName());
			archiveTaskDao.updateByPrimaryKey(item);
		}
	}
	//放款完成后15天自动生成电话回访任务
	private  void generateTelInterviewTask(Date execDate)
	{
		Date beforeDay = Utils.getDateAfterDay(execDate, -15);
		List<GeneralLedger> list = ledgerDao.selectByLoanDate(beforeDay);
		for(GeneralLedger item : list)
		{
			telInterviewService.createTelInterviewTask(item.getAppId(), "admin");
		}
	}
	//商业保险到期前30天生成保险续保任务
	private void checkNeedInsuranceContinue(Date execDate)
	{
		Date afterDay = Utils.getDateAfterDay(execDate, 30);
		List<String> list = insHisDao.selectNeedContinueAppList(InsuranceType.SYX.getName(), afterDay);
		for(String appId : list)
		{
			//创建保险续保任务
			insManageService.createInsuranceContinueTask(appId, "admin");
		}
		List<InsuranceHis> listIns = insHisDao.selectNeedContinueInsuranceList(afterDay);
		for(InsuranceHis item : listIns)
		{
			//短信通知
			SignFinanceDetail signDetail = signService.getSignFinanceDetailById(item.getSignId());
			ApplyTenant tenant = applyService.getApplyTenant(item.getAppId());
			try {
				smsService.sendInsuranceContinueNotice(item.getAppId(), "admin", tenant.getMobile(), tenant.getName(), signDetail.getPlateNo(), Utils.formateDate2String(item.getInsEndDate(), "yyyy年MM月dd日"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	//还款日前短信通知
	private void repayDayNotice(Date execDate)
	{
		//还款日前1天提醒
		Date before1day = Utils.getDateAfterDay(execDate, 1);
		List<RepayPlan> next1DayPlanList =repayPlanDao.selectNeedChargeRepayPlanList(before1day);
		for(RepayPlan item : next1DayPlanList)
		{
			try
			{
				ApplyTenant tenant = applyService.getApplyTenant(item.getAppId());
				smsService.sendRepayDayNormalNotice(item.getAppId(), "admin", tenant.getMobile(), tenant.getName(), Utils.getFormatDate(item.getClosingDate(),"yyyy年MM月dd日"), item.getRepayTotalAmount());
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
		//还款日前2天提醒
		Date before2day = Utils.getDateAfterDay(execDate, 2);
		List<RepayPlan> next10DayPlanList =repayPlanDao.selectNeedChargeRepayPlanList(before2day);
		for(RepayPlan item : next10DayPlanList)
		{
			try
			{
				ApplyTenant tenant = applyService.getApplyTenant(item.getAppId());
				smsService.sendRepayDayNormalNotice(item.getAppId(), "admin", tenant.getMobile(), tenant.getName(), Utils.getFormatDate(item.getClosingDate(),"yyyy年MM月dd日"), item.getRepayTotalAmount());
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
		//还款日前7天提醒
		Date before7day = Utils.getDateAfterDay(execDate, 7);
		List<RepayPlan> next7DayPlanList =repayPlanDao.selectNeedChargeRepayPlanList(before7day);
		for(RepayPlan item : next7DayPlanList)
		{
			try
			{
				ApplyTenant tenant = applyService.getApplyTenant(item.getAppId());
				smsService.sendRepayDayNormalNotice(item.getAppId(), "admin", tenant.getMobile(), tenant.getName(), Utils.getFormatDate(item.getClosingDate(),"yyyy年MM月dd日"), item.getRepayTotalAmount());
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			
		}
		
	}
	//欢迎短信提醒（在放款后15天，系统向客户自动发送）
	private void welcomeNotice(Date execDate) throws Exception
	{
		Date beforeDay = Utils.getDateAfterDay(execDate, -15);
		List<GeneralLedger> list = ledgerDao.selectByLoanDate(beforeDay);
		for(GeneralLedger item : list)
		{
			//发送放款完成短信
			RepayPlanVo repayPlan = planService.selectRepayPlay(item.getAppId(), 1);
			Date repayDate = repayPlan.getClosingDate();
			SignContract signInfo = signService.getSignContractByAppId(item.getAppId());
			BankInfo bankInfo = bankService.getBankInfoById(signInfo.getRepayBankId());
			ApplyTenant tenant  = applyService.getApplyTenant(item.getAppId());
			smsService.sendWelcomNotice(item.getAppId(), "admin", tenant.getMobile(), tenant.getName(), String.valueOf(Utils.getDateDay(repayDate)), repayPlan.getRepayTotalAmount(), signInfo.getRepayAcctNo(), bankInfo.getBankName());
		}
	}
	//发送逾期通知短信
	private void overdueNotice(Date execDate) throws Exception
	{
		//逾期1-7天告知逾期天数及金额短信提醒（逾期第2、5、7天系统自动发送）
		List<GeneralLedger> listOverdueDay2 = ledgerDao.selectByOverdueDay(2);
		for(GeneralLedger item : listOverdueDay2)
		{
			SmsMessageVo vo = postLoanSmsService.genPostLoanSms("overdue1to7", item.getAppId());
			smsService.sendMessage(item.getAppId(), "admin",vo.getMobile(), "overdue1to7", vo.getMessage());
		}
		List<GeneralLedger> listOverdueDay5 = ledgerDao.selectByOverdueDay(5);
		for(GeneralLedger item : listOverdueDay5)
		{
			SmsMessageVo vo = postLoanSmsService.genPostLoanSms("overdue1to7", item.getAppId());
			smsService.sendMessage(item.getAppId(), "admin",vo.getMobile(), "overdue1to7", vo.getMessage());
		}
		List<GeneralLedger> listOverdueDay7 = ledgerDao.selectByOverdueDay(7);
		for(GeneralLedger item : listOverdueDay7)
		{
			SmsMessageVo vo = postLoanSmsService.genPostLoanSms("overdue1to7", item.getAppId());
			smsService.sendMessage(item.getAppId(), "admin",vo.getMobile(), "overdue1to7", vo.getMessage());
		}
		//逾期8-15天告知逾期天数及金额短信提醒（逾期第9、12、15天系统自动发送）
		List<GeneralLedger> listOverdueDay9 = ledgerDao.selectByOverdueDay(9);
		for(GeneralLedger item : listOverdueDay9)
		{
			SmsMessageVo vo = postLoanSmsService.genPostLoanSms("overdue8to15", item.getAppId());
			smsService.sendMessage(item.getAppId(), "admin",vo.getMobile(), "overdue8to15", vo.getMessage());
		}
		List<GeneralLedger> listOverdueDay12 = ledgerDao.selectByOverdueDay(12);
		for(GeneralLedger item : listOverdueDay12)
		{
			SmsMessageVo vo = postLoanSmsService.genPostLoanSms("overdue8to15", item.getAppId());
			smsService.sendMessage(item.getAppId(), "admin",vo.getMobile(), "overdue8to15", vo.getMessage());
		}
		List<GeneralLedger> listOverdueDay15 = ledgerDao.selectByOverdueDay(15);
		for(GeneralLedger item : listOverdueDay15)
		{
			SmsMessageVo vo = postLoanSmsService.genPostLoanSms("overdue8to15", item.getAppId());
			smsService.sendMessage(item.getAppId(), "admin",vo.getMobile(), "overdue8to15", vo.getMessage());
		}
		//逾期16-30天告知逾期天数及金额短信提醒（逾期第17、24、30天系统自动发送）
		List<GeneralLedger> listOverdueDay17 = ledgerDao.selectByOverdueDay(15);
		for(GeneralLedger item : listOverdueDay17)
		{
			SmsMessageVo vo = postLoanSmsService.genPostLoanSms("overdue16to30", item.getAppId());
			smsService.sendMessage(item.getAppId(), "admin",vo.getMobile(), "overdue16to30", vo.getMessage());
		}
		List<GeneralLedger> listOverdueDay24 = ledgerDao.selectByOverdueDay(24);
		for(GeneralLedger item : listOverdueDay24)
		{
			SmsMessageVo vo = postLoanSmsService.genPostLoanSms("overdue16to30", item.getAppId());
			smsService.sendMessage(item.getAppId(), "admin",vo.getMobile(), "overdue16to30", vo.getMessage());
		}
		List<GeneralLedger> listOverdueDay30 = ledgerDao.selectByOverdueDay(30);
		for(GeneralLedger item : listOverdueDay30)
		{
			SmsMessageVo vo = postLoanSmsService.genPostLoanSms("overdue16to30", item.getAppId());
			smsService.sendMessage(item.getAppId(), "admin",vo.getMobile(), "overdue16to30", vo.getMessage());
		}
	}
	private void execCutOff(Date execDate) throws Exception {
		// TODO Auto-generated method stub
		//日切账务处理
		handleAccounting(execDate);
		//计算归档任务逾期
		checkArchiveTaskOverdue(execDate);
		//创建放款电话回访任务
		generateTelInterviewTask(execDate);
		//创建商业保险续保任务
		checkNeedInsuranceContinue(execDate);
		//还款日前短信提醒
		repayDayNotice(execDate);
		//欢迎短信提醒
		welcomeNotice(execDate);
		//逾期短信通知
		overdueNotice(execDate);
		//保存最后执行批处理日期
		SysParam sysParam = sysParamService.getSysParamByParamName("lastBatchExecDate");
		if(sysParam==null)
		{
			sysParam = new SysParam();
			sysParam.setId(Utils.get16UUID());
			sysParam.setParamName("lastBatchExecDate");
			sysParam.setParamValue(Utils.formateDate2String(execDate, "yyyy-MM-dd"));
			sysParam.setParamDesc("日切批处理最后成功执行日期");
			sysParam.setCreateId("admin");
			sysParam.setCreateTime(new Date());
			sysParam.setUpdateId("admin");
			sysParam.setUpdateTime(new Date());
			sysParamService.addSysParam(sysParam);
		}
		else
		{
			sysParam.setParamValue(Utils.formateDate2String(execDate, "yyyy-MM-dd"));
			sysParam.setUpdateId("admin");
			sysParam.setUpdateTime(new Date());
			sysParamService.modifySysParam(sysParam);
		}
	}
	public void runNormalCutOff() throws Exception
	{
		//默认开始运行批处理日期为当前日期
		Date startRunDate = new Date();
		//默认结束运行批处理日期为当前日期
		Date endRunDate = new Date();
		//获取最后日切批处理任务执行日期
		SysParam sysParam = sysParamService.getSysParamByParamName("lastBatchExecDate");
		//如果没有则默认当前日期
		if(sysParam!=null)
		{
			startRunDate = Utils.formateString2Date(sysParam.getParamValue(), "yyyy-MM-dd");
			startRunDate = Utils.getDateAfterDay(startRunDate, 1);
		}
		while(Utils.getSpaceDay(startRunDate, endRunDate)>=0)
		{
			execCutOff(startRunDate);
			startRunDate = Utils.getDateAfterDay(startRunDate, 1);
		}
	}
	public void runOtherCutOff()
	{
		//重置序列号
		resetSequence();
		
	}

}
