package com.pujjr.postloan.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.po.OfferInfoPo;

/**
 * 贷款查询Dao处理
 * @author 150032
 *
 */
public interface LoanQueryMapper 
{
	/**
	 * 查询贷款客户列表
	 * @return
	 */
	public List<HashMap<String,Object>> selectLoanCustList();
	/**
	 * 查询贷款客户申请基本信息
	 * @param appId
	 * @return
	 */
	public HashMap<String,Object> selectLoanCustApplyInfo(@Param("appId")String appId);
	/**
	 * 查询还款记录
	 * @param appId申请单号
	 * @return
	 */
	public List<HashMap<String,Object>> selectLoanCustRepayLog(@Param("appId")String appId);
	/**
	 * 查询扣款记录
	 * @param appId
	 * @return
	 */
	public List<HashMap<String,Object>> selectLoanCustChargeLog(@Param("appId")String appId);
	/**
	 * 查询贷后代办任务列表
	 * @param assignee 任务执行人
	 * @return
	 */
	public List<HashMap<String,Object>> selectLoanToDoTaskList(@Param("assignee")String assignee);
	/**
	 * 查询其他费用信息
	 * @param appId申请单号
	 * @return
	 */
	public List<HashMap<String,Object>> selectOtherFeeList(@Param("appId")String appId);
	/**
	 * 查询正在执行的贷后任务笔数
	 * @param appId
	 * @param taskType
	 * @return
	 */
	public int selectProcessingLoanTaskCnt(@Param("appId")String appId,@Param("taskKey")String taskKey);
}
