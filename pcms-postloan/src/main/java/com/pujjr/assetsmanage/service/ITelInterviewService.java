package com.pujjr.assetsmanage.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.assetsmanage.domain.TelIncomeLog;
import com.pujjr.assetsmanage.domain.TelInterview;

@Service
@Transactional
public interface ITelInterviewService 
{
	/**
	 * 增加电话回访记录
	 * @param appId 申请单号
	 * @param vo 电话回访录入要素
	 * @param operId 回访者
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public void addTelInterviewResult(String appId,TelInterview vo,String operId) throws IllegalAccessException, InvocationTargetException;
	/**
	 * 创建电话回访任务
	 * @param appId 申请单号
	 * @param operId 操作人员
	 */
	public void createTelInterviewTask(String appId,String operId);
	/**
	 * 查询电话回访记录列表信息
	 * @param appId 申请单号
	 * @return
	 */
	public List<TelInterview> getTelInterviewHisList(String appId);
	/**
	 * 提交电话回访任务
	 * @param taskId 任务ID
	 * @throws Exception 
	 */
	public void commmitTelInterviewTask(String taskId) throws Exception;
	/**
	 * 新增电话回访记录
	 * @param po 电话回访记录实体信息
	 */
	public void addTelIncomeLog(TelIncomeLog po);
	/**
	 * 获取电话回访列表
	 * @param appId
	 * @return
	 */
	public List<HashMap<String,Object>> getTelIncomeLogList(String appId);
	/**
	 * 修改电话来电记录
	 * @param po
	 */
	public void  modifyTelIncomeLogInfo(TelIncomeLog po);
}
