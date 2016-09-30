package com.pujjr.carcredit.service;

import java.util.List;

import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import com.pujjr.base.domain.SysAccount;
import com.pujjr.carcredit.bo.ProcessTaskUserBo;
import com.pujjr.carcredit.po.OnlineAcctPo;
import com.pujjr.carcredit.po.ToDoTaskPo;
import com.pujjr.carcredit.vo.ApplyApproveVo;
import com.pujjr.carcredit.vo.ApplyCheckVo;
import com.pujjr.carcredit.vo.ApplyVo;
@Service
public interface ITaskService 
{
	public List<ToDoTaskPo> getToDoTaskListByAccountId(String accountId,String queryType);
	
	public void commitApplyTask(ApplyVo applyVo,String operId) throws Exception;
	
	public void commitCheckTask(ApplyVo applyVo,ApplyCheckVo checkVo,String taskId,String operId) throws Exception;
	
	public void commitApproveTask(ApplyVo applyVo,ApplyApproveVo approveVo,String taskId,String operId) throws Exception;
	
	public String getMinTaskCountAccountIdByWorkgroupId(String workgroupId); 
	
	public ProcessTaskUserBo getProcessTaskAccount(String productCode,double financeAmount,String dealerId,String workgroupId,List<String> candidateAccounts);
	
	public List<OnlineAcctPo>  getOnlineAcctInfo(String workgroupId);
	
}
