package com.pujjr.carcredit.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.carcredit.vo.QueryParamApplyVo;

public interface QueryMapper 
{
	List<HashMap<String,Object>> selectApplyList(@Param("param")QueryParamApplyVo param);
	
	List<HashMap<String,Object>> selectApplyConditionLoanCommentList(@Param("appId")String appId);
}
