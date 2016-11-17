package com.pujjr.postloan.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ILoanQueryService 
{
	/**
	 * 查询贷款客户列表
	 */
	public List<HashMap<String,Object>> getLoanCustList();
	/**
	 * 查询贷款客户申请信息
	 * @param appId
	 * @return
	 */
	public HashMap<String,Object> getLoanCustApplyInfo(String appId);
	
	/**
	 * 查询贷款客户还款记录
	 * @param appId
	 * @return
	 */
	public List<HashMap<String,Object>> getLoanCustRepayLog(String appId);
	
	/**
	 * 查询贷款客户扣款记录
	 * @param appId
	 * @return
	 */
	public List<HashMap<String,Object>> getLoanCustChargeLog(String appId);
	/**
	 * 查询贷后代办任务
	 * @param assignee任务执行人
	 * @return
	 */
	public List<HashMap<String,Object>> getLoanToDoTaskList(String assignee);
}
