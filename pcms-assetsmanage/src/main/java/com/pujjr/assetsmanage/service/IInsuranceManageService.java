package com.pujjr.assetsmanage.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.domain.InsuranceHis;
@Service
@Transactional
public interface IInsuranceManageService 
{
	public void createInsuranceContinueTask(String appId,String operId);
	/**
	 * 查询指定类型保险购买记录
	 * @param appId 申请单号
	 * @return
	 */
	public List<HashMap<String,Object>> getInsuranceHisList(String appId);
	/**
	 * 增加保险
	 * @param appId 申请单号
	 * @param signId 签约ID
	 * @param vo  新增保险信息
	 */
	public void addInsurance(String appId,String signId,String insType,InsuranceHis vo);
	/**
	 * 提交保险续保任务
	 * @param taskId
	 */
	public void commitInsuranceContinue(String taskId);
}
