package com.pujjr.carcredit.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.carcredit.domain.Apply;
import com.pujjr.carcredit.po.ApplyInfoPo;
import com.pujjr.carcredit.po.ToDoTaskPo;
import com.pujjr.carcredit.po.WorkflowProcessResultPo;
import com.pujjr.carcredit.vo.ApplyVo;

public interface TaskMapper 
{
	public List<ToDoTaskPo> selectToDoTaskListByAccountId(@Param("accountId")String accountId,@Param("queryType")String queryType);
	
	public String selectTaskCntAndOnlineAccountIdByWorkgroupId(@Param("workgroupId")String workgroupId);
	
	public List<HashMap> selectTaskAssignCntInfo(@Param("taskCntRuleIds")List<String> taskCntRuleIds);
	
	public List<WorkflowProcessResultPo> selectWorkflowProcessResult(@Param("procInstId")String procInstId);
	
}