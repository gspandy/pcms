package com.pujjr.carcredit.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.carcredit.vo.QueryParamApplyVo;

@Service
public interface IQueryService 
{
	/**
	 * 综合查询申请信息
	 * **/
	List<HashMap<String,Object>> queryApplyList(QueryParamApplyVo param);
	/**
	 * 查询申请单放款条件
	 * **/
	public List<HashMap<String,Object>> queryApplyConditionLoanCommentList(String appId);
	/**
	 * 查询申请单已执行任务节点信息
	 * **/
	public HashMap<String,Object> queryApplyRunPathNodeList(String appId);
}
