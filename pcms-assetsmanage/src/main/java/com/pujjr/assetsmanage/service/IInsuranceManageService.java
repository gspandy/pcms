package com.pujjr.assetsmanage.service;

import java.util.HashMap;
import java.util.List;

import com.pujjr.base.domain.InsuranceHis;

public interface IInsuranceManageService 
{
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
	public void addInsurance(String appId,String signId,InsuranceHis vo);
}
