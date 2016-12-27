package com.pujjr.assetsmanage.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.pujjr.assetsmanage.domain.InsuranceClaims;
import com.pujjr.assetsmanage.vo.ApplyInsuranceVo;
import com.pujjr.base.domain.InsuranceHis;
import com.pujjr.base.po.InsuranceHisPo;
@Service
@Transactional(rollbackFor=Exception.class)
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
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws Exception 
	 */
	public void addInsurance(String appId,String signId,String insType,ApplyInsuranceVo vo,MultipartFile[] files,String operId) throws IllegalAccessException, InvocationTargetException, Exception;
	/**
	 * 提交保险续保任务
	 * @param taskId
	 */
	public void commitInsuranceContinue(String taskId);
	/**
	 * 根据保险ID查询保险记录
	 * @param id
	 * @return
	 */
	public InsuranceHisPo getInsuranceHisById(String id);
	/**
	 * 新增保险理赔记录
	 * @param appId 申请单号
	 * @param insuranceId 保险ID
	 * @param vo 理赔要素
	 * @return
	 */
	public void addInsuranceClaims(InsuranceClaims po);
	
}
