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
}
