package com.pujjr.postloan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mchange.v2.beans.BeansUtils;
import com.pujjr.postloan.dao.RepayPlanMapper;
import com.pujjr.postloan.domain.RepayPlan;
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

}
