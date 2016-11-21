package com.pujjr.postloan.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.enumeration.EIntervalMode;
import com.pujjr.postloan.dao.GeneralLedgerMapper;
import com.pujjr.postloan.domain.ApplyAlterRepayDate;
import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.postloan.service.IAlterRepayDateService;
import com.pujjr.postloan.vo.AlterRepayDateFeeItemVo;
import com.pujjr.postloan.vo.AlterRepayDateTaskVo;
import com.pujjr.postloan.vo.ApplyAlterRepayDateVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.NewRepayPlanVo;
import com.pujjr.utils.Utils;
@Service
public class AlterRepayDateServiceImpl implements IAlterRepayDateService {

	@Autowired
	private GeneralLedgerMapper ledgerDao;
	@Autowired
	private IAccountingService accountingService;
	
	@Override
	public AlterRepayDateFeeItemVo getAlterRepayDateFeeItem(String appId, Date oldClosingDate,Date newClosingDate) {
		// TODO Auto-generated method stub
		AlterRepayDateFeeItemVo vo = new AlterRepayDateFeeItemVo();
		//变更天数
		int alterDay = new Long(Utils.getTimeInterval(oldClosingDate, newClosingDate, EIntervalMode.DAYS)).intValue();
		vo.setAlterDay(alterDay);
		GeneralLedger ledgerPo = ledgerDao.selectByAppId(appId);
		DecimalFormat  nf = new DecimalFormat("#.00");
		//变更利息
		double alterInterest = Double.valueOf(nf.format(ledgerPo.getRemainCapital()*ledgerPo.getYearRate()/360*(alterDay)));
		vo.setAlterInterest(alterInterest);
		
		/******************************生成新的还款计划************************************/
		List<NewRepayPlanVo> newRepayPlanList = new ArrayList<NewRepayPlanVo>();
		//处理当期还款计划，记账日设置为新的结账日，产生的变更利息加在当期上
		RepayPlan curPeriodRepayPlan  = accountingService.getCurrentPeriodRepayPlan(appId);
		NewRepayPlanVo tempCur = new NewRepayPlanVo();
		tempCur.setPeriod(curPeriodRepayPlan.getPeriod());
		tempCur.setRepayCapital(curPeriodRepayPlan.getRepayCapital());
		tempCur.setRepayCnterest(curPeriodRepayPlan.getRepayInterest()+alterInterest);
		tempCur.setRemainCapital(curPeriodRepayPlan.getRemainCapital());
		tempCur.setValueDate(curPeriodRepayPlan.getValueDate());
		tempCur.setClosingDate(newClosingDate);
		tempCur.setRepayTotalAmount(curPeriodRepayPlan.getRepayTotalAmount());
		newRepayPlanList.add(tempCur);
		//处理当期后续还款计划
		List<RepayPlan> nextRepayPlanList = accountingService.getAfterCurrentPeriodRepayPlan(appId, curPeriodRepayPlan.getPeriod());
		int i = 1;
		Date newValueDate = newClosingDate;
		for(RepayPlan item : nextRepayPlanList)
		{
			NewRepayPlanVo tempVo = new NewRepayPlanVo();
			tempVo.setPeriod(item.getPeriod());
			tempVo.setRepayCapital(item.getRepayCapital());
			tempVo.setRepayCnterest(item.getRepayInterest());
			tempVo.setRemainCapital(item.getRemainCapital());
			tempVo.setRepayTotalAmount(item.getRepayTotalAmount());
			tempVo.setValueDate(newValueDate);
			tempVo.setClosingDate(Utils.getDateAfterMonth(newClosingDate, i));
			newValueDate = Utils.getDateAfterMonth(newClosingDate, i);
			i++;
			newRepayPlanList.add(tempVo);
			
		}
		
		return vo;
	}

	@Override
	public void commitApplyAlterRepayDateTask(String appId, ApplyAlterRepayDateVo vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void commitApproveAlterRepayDateTask(String taskId, ApproveResultVo vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void commitApplyConfirmAlterRepayDateTask(String taskId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void commitConfirmAlterRepayDateTask(String taskId, ApproveResultVo vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelAlterRepayDateTask(String taskId, String operId, String cancelComment) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<AlterRepayDateTaskVo> getApplyExtendPeriodTaskList(String createId, List<String> applyStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplyAlterRepayDate getApplyAlterRepayDateTaskById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modifyApplyAlterRepayDateInfo(ApplyAlterRepayDate record) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteApplyAlterRepayDateInfoById(String id) {
		// TODO Auto-generated method stub

	}

}
