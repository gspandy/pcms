package com.pujjr.carcredit.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.carcredit.vo.QueryParamApplyVo;

public interface QueryMapper 
{
	List<HashMap<String,Object>> selectApplyList(@Param("param")QueryParamApplyVo param);
	
	List<HashMap<String,Object>> selectApplyConditionLoanCommentList(@Param("appId")String appId);
	
	List<String> selectApplyRunPathNodeList(@Param("appId")String appId);
	/**
	 * 根据流程实例ID查询任务信息
	 * @param procInstId 流程实例ID
	 * @return
	 */
	HashMap<String,Object> selectTaskInfoByProcInstId(@Param("procInstId")String procInstId);
}
