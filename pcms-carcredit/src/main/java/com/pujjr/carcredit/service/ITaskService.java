package com.pujjr.carcredit.service;

import java.util.List;

import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import com.pujjr.base.domain.SysAccount;
import com.pujjr.carcredit.bo.ProcessTaskUserBo;
import com.pujjr.carcredit.domain.AutoAssigneeConfig;
import com.pujjr.carcredit.domain.CallBackResult;
import com.pujjr.carcredit.domain.CancelApplyInfo;
import com.pujjr.carcredit.domain.ChangeApplyInfo;
import com.pujjr.carcredit.domain.CheckResult;
import com.pujjr.carcredit.domain.LoanCheck;
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
import com.pujjr.carcredit.vo.CancelApplyInfoVo;
import com.pujjr.carcredit.vo.ChangeApplyInfoVo;
import com.pujjr.carcredit.vo.ReconsiderApplyVo;
import com.pujjr.carcredit.vo.ReconsiderApproveVo;
import com.pujjr.carcredit.vo.SignContractVo;
import com.pujjr.carcredit.vo.TaskLoanApproveVo;
@Service
public interface ITaskService 
{
	public List<ToDoTaskPo> getToDoTaskListByAccountId(String accountId,String queryType);
	
	public void commitApplyTask(ApplyVo applyVo,String operId) throws Exception;
	
	public void commitReApplyTask(ApplyVo applyVo,String taskId,String operId) throws Exception;
	
	public void commitSupplyCheckTask(ApplyVo applyVo,String taskId,String operId) throws Exception;
	
	public void commitPreCheckTask(String taskId,String operId) throws Exception;
	
	public void commitCheckTask(ApplyVo applyVo,ApplyCheckVo checkVo,String taskId,String operId) throws Exception;
	
	public void commitApproveTask(ApplyVo applyVo,ApplyApproveVo approveVo,String taskId,String operId) throws Exception;
	
	public String getMinTaskCountAccountIdByWorkgroupId(String workgroupId); 
	
	public ProcessTaskUserBo getProcessTaskAccount(String productCode,double financeAmount,String dealerId,String workgroupId,List<String> candidateAccounts);
	
	public List<OnlineAcctPo>  getOnlineAcctInfo(String workgroupId,boolean checkOnline);
	
	public void saveSignContractInfo(SignContractVo signContractVo,String operId);
	
	public void commitSignContract(String appId,String taskId,String operId) throws Exception;
	
	public void saveLoanCheckInfo(SignContractVo signContractVo,String operId);
	
	public void commitLoanCheck(SignContractVo signContractVo,String commitType,String taskId,String operId) throws Exception;
	
	public void commitSupplyLoanCheckTask(String taskId,String operId);
	
	public void commitPrevLoanApprove(String taskId,String operId);
	
	public void commitLoanApprove(TaskLoanApproveVo loanApproveVo,String taskId,String operId) throws Exception;
	
	public TaskProcessResult getTaskProcessResultByPathId(String pathId);
	
	public void commitReconsiderApply(ReconsiderApplyVo reconsiderApplyVo,String appId,String taskId,String operId);
	
	public Reconsider getEnabledReconsiderByAppId(String appId);
	
	public void commitReconsiderApprove(ReconsiderApproveVo reconsiderApproveVo,String taskId,String operId) throws Exception;
	
	public void backTask(String taskId,String message);
	
	public List<WorkflowProcessResultPo> getWorkflowProcessResult(String procInstId);
	
	public void checkTaskHasUploadFile(String templateId,String categoryKey,String businessId) throws Exception;
	
	public LoanCheck getLoanCheckInfoByAppId(String appId);
	
	public void commitCallBackTask(CallBackResult result , String appId,String taskId,String operId) throws Exception;
	//提交变更申请
	public void commitChangeApplyInfoTask(ChangeApplyInfo info,String appId,String taskId) throws Exception;
	//获取最近一次申请变更信息
	public ChangeApplyInfo getLatestChangeApplyInfo(String taskId) throws Exception;
	//提交变更审批任务
	public void commitApproveChangeApplyInfoTask(ChangeApplyInfoVo vo,String appId,String taskId) throws Exception;
	//提交取消申请
	public void commitCancelApplyTask(CancelApplyInfo info,String appId,String taskId) throws Exception;
	//获取最近一次取消申请信息
	public CancelApplyInfo getLatestCancelApplyInfo(String taskId) throws Exception;
	//提交审批取消申请
	public void commitApprvoeCancelApply(CancelApplyInfoVo vo,String appId,String taskId) throws Exception;
	//查询自动分单信息
	public AutoAssigneeConfig getAutoAssigneeConfigInfo();
	//设置自动分单信息
	public void setAutoAssigneeConfigInfo(AutoAssigneeConfig params);
	
}
