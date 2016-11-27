package com.pujjr.postloan.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mchange.v2.beans.BeansUtils;
import com.pujjr.postloan.dao.GeneralLedgerMapper;
import com.pujjr.postloan.dao.RepayPlanMapper;
import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.enumeration.EInterestMode;
import com.pujjr.postloan.enumeration.RepayStatus;
import com.pujjr.postloan.po.RepaySchedulePo;
import com.pujjr.postloan.service.IInterestAlgorithm;
import com.pujjr.postloan.service.IPlanService;
import com.pujjr.postloan.vo.RepayPlanVo;
import com.pujjr.postloan.vo.RepayScheduleVo;
import com.pujjr.utils.Utils;

/**
 * @author tom
 *
 */
@Service
public class PlanServiceImpl implements IPlanService {
	@Autowired
	private IInterestAlgorithm interestAlgorithmImpl;
	@Autowired
	private RepayPlanMapper repayPlanMapper;
	@Autowired 
	private GeneralLedgerMapper generalLedgeMapper;
	@Override
	public void generateRepayPlan(String appId,double fianceAmt, double monthRate, int period, Date valueDate, Enum eInterestMode) {
		RepaySchedulePo rsp = new RepaySchedulePo();
		switch(eInterestMode.name()){
		case "CPM":
			rsp = interestAlgorithmImpl.cpmInterest(fianceAmt, monthRate, period, valueDate);
//			rsp = new InterestAlgorithmImpl().cpmInterest(fianceAmt, monthRate, period, valueDate);
			break;
		case "CONST":
			rsp = interestAlgorithmImpl.constInterest(fianceAmt, monthRate, period, valueDate);
//			rsp = new InterestAlgorithmImpl().constInterest(fianceAmt, monthRate, period, valueDate);
			break;
		case "ONETIME":
			rsp = interestAlgorithmImpl.onetimeInterest(fianceAmt, monthRate, period, valueDate);
//			rsp = new InterestAlgorithmImpl().onetimeInterest(fianceAmt, monthRate, period, valueDate);
			break;
		case "MONTLY":
			rsp = interestAlgorithmImpl.monthlyIntetrest(fianceAmt, monthRate, period, valueDate);
//			rsp = new InterestAlgorithmImpl().monthlyIntetrest(fianceAmt, monthRate, period, valueDate);
			break;
		}
		List<RepayPlan> repayPlanList = rsp.getRepayPlanList();
		for (RepayPlan repayPlan : repayPlanList) {
			repayPlan.setId(Utils.get16UUID());
			repayPlan.setAppId(appId);
			repayPlan.setRepayStatus(RepayStatus.Repaying.getName());
			repayPlanMapper.insert(repayPlan);
		}
	}

	@Override
	public List<RepayPlanVo> selectRepayPlanList(String appId,int period) {
		List<RepayPlanVo> voList = new ArrayList<RepayPlanVo>();
		List<RepayPlan> poList = new ArrayList<RepayPlan>();
		poList = repayPlanMapper.selectRepayPlanList(appId,period);
		for (RepayPlan repayPlan : poList) {
			RepayPlanVo repayPlanVo = new RepayPlanVo();
			Utils.copyProperties(repayPlan, repayPlanVo);
			voList.add(repayPlanVo);
		}
		return voList;
	}

	@Override
	public RepayPlanVo selectRepayPlay(String appId, int period) {
		RepayPlanVo repayPlanVo = new RepayPlanVo();
		RepayPlan repayPlan = new RepayPlan();
		repayPlan = repayPlanMapper.selectRepayPlan(appId, period);
		Utils.copyProperties(repayPlan, repayPlanVo);
		return repayPlanVo;
	}


	@Override
	public RepayScheduleVo selectRepaySchedule(String appId, int period) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RepayPlan> getRepayPlanList(String appId, int beginPeriod, int endPeriod) {
		// TODO Auto-generated method stub
		return repayPlanMapper.selectSpecialRepayPlanList(appId, beginPeriod, endPeriod);
	}

	@Override
	public List<RepayPlan> selectRefreshRepayPlanList(String appId, double fianceAmt, double monthRate, int period,
			Date valueDate, int currPeriod) {
		RepaySchedulePo rsp = new RepaySchedulePo();
		GeneralLedger generalLedge = generalLedgeMapper.selectByAppId(appId);
		String repayMode = generalLedge.getRepayMode();
		EInterestMode eInterestMode = this.getInterestMode(appId);
		List<RepayPlan> repayPlanList = this.selectRefreshRepayPlanList(appId, fianceAmt, monthRate, period, valueDate, eInterestMode, currPeriod);
		return repayPlanList;
	}
	
	@Override
	public List<RepayPlan> selectRefreshRepayPlanList(String appId, double fianceAmt, double monthRate, int period,
			Date valueDate, Enum eInterestMode, int currPeriod) {
		RepaySchedulePo rsp = new RepaySchedulePo();
		switch(eInterestMode.name()){
		case "CPM":
			rsp = interestAlgorithmImpl.cpmInterest(fianceAmt, monthRate, period, valueDate);
			break;
		case "CONST":
			rsp = interestAlgorithmImpl.constInterest(fianceAmt, monthRate, period, valueDate);
			break;
		case "ONETIME":
			rsp = interestAlgorithmImpl.onetimeInterest(fianceAmt, monthRate, period, valueDate);
			break;
		case "MONTLY":
			rsp = interestAlgorithmImpl.monthlyIntetrest(fianceAmt, monthRate, period, valueDate);
			break;
		}
		/** 这里为啥把之前的合并？？之前的还款计划已经处理了，再合并进去就多了
//		1、获取第1期至当前还款周期还款计划
		List<RepayPlan> repayPlanList = new LinkedList<RepayPlan>();
		if(currPeriod >= 1){
			repayPlanList = repayPlanMapper.selectSpecialRepayPlanList(appId, 1, currPeriod);
		}**/
//		2、生成当期后还款计划，合并
		List<RepayPlan> repayPlanListAfter = rsp.getRepayPlanList();
		for(int i = 0 ;i<repayPlanListAfter.size();i++)
		{
			RepayPlan repayPlan = repayPlanListAfter.get(i);
			repayPlan.setId(Utils.get16UUID());
			repayPlan.setAppId(appId);
			repayPlan.setPeriod(repayPlan.getPeriod() + currPeriod);
			repayPlanListAfter.set(i, repayPlan);
		}
		return rsp.getRepayPlanList();
	}

	@Override
	public void refreshRepayPlan(String appId, double financeAmt, double monthRate, int period, Date valueDate,
			Enum eInterestMode, int currPeriod) {
		List<RepayPlan> newRepayPlanList = this.selectRefreshRepayPlanList(appId, financeAmt, monthRate, period, valueDate, eInterestMode, currPeriod);
//		System.out.println("newRepayPlanList:"+newRepayPlanList);
		List<RepayPlan> oldRepayPlanList = repayPlanMapper.selectSpecialRepayPlanList(appId, 1, 0);
//		删除旧还款计划
		for (RepayPlan repayPlan : oldRepayPlanList) {
			repayPlanMapper.deleteByPrimaryKey(repayPlan.getId());
		}
//		插入新还款计划
		for (RepayPlan repayPlan : newRepayPlanList) {
			repayPlanMapper.insert(repayPlan);
		}
	}

	@Override
	public void refreshRepayPlan(String appId, double financeAmt, double monthRate, int period, Date valueDate,
			int currPeriod) {
		GeneralLedger generalLedger = generalLedgeMapper.selectByAppId(appId);
		String repayMode = generalLedger.getRepayMode();
		EInterestMode eInterestMode = this.getInterestMode(appId);
		this.refreshRepayPlan(appId, financeAmt, monthRate, period, valueDate, eInterestMode, currPeriod);
	}

	@Override
	public EInterestMode getInterestMode(String appId) {
		GeneralLedger generalLedger = generalLedgeMapper.selectByAppId(appId);
		String repayMode = generalLedger.getRepayMode();
		EInterestMode eInterestMode = null;
		switch(repayMode){
		case "hkfs01":
			eInterestMode = EInterestMode.CPM;
			break;
		case "hkfs02":
			eInterestMode = EInterestMode.CONST;
			break;
		case "hkfs03":
			eInterestMode = EInterestMode.ONETIME;
			break;
		case "hkfs04":
			eInterestMode = EInterestMode.MONTLY;
			break;
		}
		return eInterestMode;
	}

	@Override
	public List<RepayPlan> selectRefreshRepayPlanList(String appId, double financeAmt, int currPeriod,int lastPeriod) {
		List<RepayPlan> repayPlanListNew = new LinkedList<RepayPlan>();
		List<RepayPlan> repayPlanListOld = repayPlanMapper.selectSpecialRepayPlanList(appId, currPeriod+1, lastPeriod);
		if(lastPeriod > currPeriod){
			BigDecimal bgAmt = new BigDecimal(financeAmt/lastPeriod-currPeriod);
			double repayCaptital =  Utils.formateDouble2Double(bgAmt, 2);
			for (RepayPlan repayPlan : repayPlanListOld) {
				repayPlan.setRepayCapital(repayCaptital);
				repayPlan.setRepayInterest(0.00);
				repayPlanListNew.add(repayPlan);
			}
		}
		System.out.println("repayPlanListNew:"+repayPlanListNew);
		return repayPlanListNew;
	}

	@Override
	public List<RepayPlan> selectRefreshRepayPlanListExtendPeriod(String appId, double fianceAmt, double monthRate,
			int period, Date valueDate, Enum eInterestMode, int currPeriod) {
		RepaySchedulePo rsp = new RepaySchedulePo();
		switch(eInterestMode.name()){
		case "CPM":
			rsp = interestAlgorithmImpl.cpmInterest(fianceAmt, monthRate, period, valueDate);
			break;
		case "CONST":
			rsp = interestAlgorithmImpl.constInterest(fianceAmt, monthRate, period, valueDate);
			break;
		case "ONETIME":
			rsp = interestAlgorithmImpl.onetimeInterest(fianceAmt, monthRate, period, valueDate);
			break;
		case "MONTLY":
			rsp = interestAlgorithmImpl.monthlyIntetrest(fianceAmt, monthRate, period, valueDate);
			break;
		}
//		1、获取第1期至当前还款周期还款计划
		List<RepayPlan> repayPlanList = new LinkedList<RepayPlan>();
//		2、生成当期后还款计划
		List<RepayPlan> repayPlanListAfter = rsp.getRepayPlanList();
		for (RepayPlan repayPlan : repayPlanListAfter) {
			repayPlan.setId(Utils.get16UUID());
			repayPlan.setAppId(appId);
			repayPlan.setPeriod(repayPlan.getPeriod() + currPeriod);
			repayPlanList.add(repayPlan);
		}
		return repayPlanList;
	}
}
