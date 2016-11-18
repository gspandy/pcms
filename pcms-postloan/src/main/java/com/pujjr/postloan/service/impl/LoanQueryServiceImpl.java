package com.pujjr.postloan.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.postloan.dao.LoanQueryMapper;
import com.pujjr.postloan.service.ILoanQueryService;

@Service
public class LoanQueryServiceImpl implements ILoanQueryService {

	@Autowired
	private LoanQueryMapper loanQueryDao; 
	@Override
	public List<HashMap<String, Object>> getLoanCustList() {
		// TODO Auto-generated method stub
		return loanQueryDao.selectLoanCustList();
	}
	@Override
	public HashMap<String,Object> getLoanCustApplyInfo(String appId) {
		// TODO Auto-generated method stub
		return loanQueryDao.selectLoanCustApplyInfo(appId);
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
	
}
