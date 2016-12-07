package com.pujjr.assetsmanage.service;

import java.lang.reflect.InvocationTargetException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
