package com.pujjr.postloan.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.pujjr.base.service.ISysAccountService;
import com.pujjr.base.vo.QueryAuthVo;
import com.pujjr.postloan.dao.LoanQueryMapper;
import com.pujjr.postloan.dao.RepayPlanMapper;
import com.pujjr.postloan.dao.WaitingChargeNewMapper;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.domain.WaitingChargeNew;
import com.pujjr.postloan.service.ILoanQueryService;
import com.pujjr.postloan.vo.QueryParamLoanVo;

@Service
public class LoanQueryServiceImpl implements ILoanQueryService {

	@Autowired
	private LoanQueryMapper loanQueryDao; 
	@Autowired
	private WaitingChargeNewMapper waitingChargeNewDao;
	@Autowired
	private ISysAccountService sysAccountService;
	@Autowired
	private RepayPlanMapper repayPlanDao;
	@Override
	public List<HashMap<String, Object>> getLoanCustList(QueryParamLoanVo queryParam) {
		// TODO Auto-generated method stub
		QueryAuthVo authVo = sysAccountService.getAccountQueryAuth(queryParam.getQueryAccountId());
		queryParam.setQueryAuth(authVo);
		PageHelper.startPage(Integer.parseInt(queryParam.getCurPage()), Integer.parseInt(queryParam.getPageSize()),true);
		return loanQueryDao.selectLoanCustList(queryParam);
	}
	@Override
	public HashMap<String,Object> getLoanCustApplyInfo(String appId) {
		// TODO Auto-generated method stub
		HashMap<String,Object> map = new HashMap<String,Object>();
		map =  loanQueryDao.selectLoanCustApplyInfo(appId);
		//获取已还期数
		List<RepayPlan> settleRepayPlan = repayPlanDao.selectSettleRepayPlanList(appId);
		map.put("settlePeriod", settleRepayPlan.size());
		return map;
	}
	@Override
	public List<HashMap<String,Object>> getLoanCustRepayLog(String appId)
	{
		return loanQueryDao.selectLoanCustRepayLog(appId);
	}
	@Override
	public List<HashMap<String, Object>> getLoanCustChargeLog(String appId) {
		// TODO Auto-generated method stub
		return loanQueryDao.selectLoanCustChargeLog(appId);
	}
	@Override
	public List<HashMap<String, Object>> getLoanToDoTaskList(String assignee) {
		// TODO Auto-generated method stub
		return loanQueryDao.selectLoanToDoTaskList(assignee);
	}
	@Override
	public List<WaitingChargeNew> getWaitingChargeNewList(String applyId, String applyType, String feeType,boolean containSettleRecord) {
		// TODO Auto-generated method stub
		return waitingChargeNewDao.selectList(applyType, applyId, feeType,containSettleRecord);
	}
	@Override
	public List<HashMap<String, Object>> getOtherFeeList(String appId) {
		// TODO Auto-generated method stub
		return loanQueryDao.selectOtherFeeList(appId);
	}
	

 	
}
