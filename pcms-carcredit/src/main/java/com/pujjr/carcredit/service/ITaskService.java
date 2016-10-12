package com.pujjr.carcredit.service;

import java.util.List;

import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import com.pujjr.base.domain.SysAccount;
import com.pujjr.carcredit.bo.ProcessTaskUserBo;
import com.pujjr.carcredit.domain.Reconsider;
import com.pujjr.carcredit.domain.SignContract;
import com.pujjr.carcredit.domain.SignFinanceDetail;
import com.pujjr.carcredit.domain.TaskProcessResult;
import com.pujjr.carcredit.po.OnlineAcctPo;
import com.pujjr.carcredit.po.ToDoTaskPo;
import com.pujjr.carcredit.po.WorkflowProcessResultPo;
import com.pujjr.carcredit.vo.ApplyApproveVo;
import com.pujjr.carcredit.vo.ApplyCheckVo;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.carcredit.vo.ReconsiderApplyVo;
import com.pujjr.carcredit.vo.ReconsiderApproveVo;
import com.pujjr.carcredit.vo.SignContractVo;
import com.pujjr.carcredit.vo.TaskLoanApproveVo;
@Service
public interface ITaskService 
{
	public List<ToDoTaskPo> getToDoTaskListByAccountId(String accountId,String queryType);
	
	public void commitApplyTask(ApplyVo applyVo,String operId) throws Exception;
	
	public void commitCheckTask(ApplyVo applyVo,ApplyCheckVo checkVo,String taskId,String operId) throws Exception;
	
	public void commitApproveTask(ApplyVo applyVo,ApplyApproveVo approveVo,String taskId,String operId) throws Exception;
	
	public String getMinTaskCountAccountIdByWorkgroupId(String workgroupId); 
	
	public ProcessTaskUserBo getProcessTaskAccount(String productCode,double financeAmount,String dealerId,String workgroupId,List<String> candidateAccounts);
	
	public List<OnlineAcctPo>  getOnlineAcctInfo(String workgroupId,boolean checkOnline);
	
	public void commitSignContract(SignContractVo signContractVo,String taskId,String operId);
	
	public void commitLoanCheck(SignContractVo signContractVo,String taskId,String operId);
	
	public void commitPrevLoanApprove(String taskId,String operId);
	
	public void commitLoanApprove(TaskLoanApproveVo loanApproveVo,String taskId,String operId) throws Exception;
	
	public TaskProcessResult getTaskProcessResultByPathId(String pathId);
	
	public void commitReconsiderApply(ReconsiderApplyVo reconsiderApplyVo,String appId,String taskId,String operId);
	
	public Reconsider getEnabledReconsiderByAppId(String appId);
	
	public void commitReconsiderApprove(ReconsiderApproveVo reconsiderApproveVo,String taskId,String operId) throws Exception;
	
	public void backTask(String taskId,String message);
	
	public List<WorkflowProcessResultPo> getWorkflowProcessResult(String procInstId);
}
