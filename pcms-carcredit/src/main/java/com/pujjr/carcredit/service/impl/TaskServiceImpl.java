package com.pujjr.carcredit.service.impl;

import java.util.HashMap;
import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.carcredit.dao.CheckResultMapper;
import com.pujjr.carcredit.dao.TaskMapper;
import com.pujjr.carcredit.dao.TaskProcessResultMapper;
import com.pujjr.carcredit.domain.CheckResult;
import com.pujjr.carcredit.domain.TaskProcessResult;
import com.pujjr.carcredit.po.ToDoTaskPo;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.service.ITaskService;
import com.pujjr.carcredit.vo.ApplyApproveVo;
import com.pujjr.carcredit.vo.ApplyCheckVo;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.carcredit.vo.TaskCommitType;
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.domain.WorkflowRunPath;
import com.pujjr.jbpm.service.IRunPathService;
import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.jbpm.vo.ProcessGlobalVariable;
import com.pujjr.utils.Utils;
@Service
@Transactional(rollbackFor=Exception.class) 
public class TaskServiceImpl implements ITaskService 
{
	@Autowired
	private TaskMapper taskDao;
	@Autowired
	private IRunWorkflowService  runWorkflowService;
	@Autowired
	private IRunPathService runPathService;
	@Autowired
	private IApplyService applyService;
	@Autowired
	private TaskService actTaskService;
	@Autowired
	private CheckResultMapper checkResultDao;
	@Autowired
	private TaskProcessResultMapper taskProcessResultDao;
	
	public List<ToDoTaskPo> getToDoTaskListByAccountId(String accountId) {
		// TODO Auto-generated method stub
		return taskDao.selectToDoTaskListByAccountId(accountId);
	}

	public void commitApplyTask(ApplyVo applyVo, String operId) throws Exception {
		// TODO Auto-generated method stub
		String businessKey = applyService.saveApply(applyVo, operId);
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("productCode", applyVo.getProduct().getProductCode());
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, operId);
		vars.put("assigneeType", "1");
		runWorkflowService.startProcess("PCCA", businessKey, vars);
	}
	
	public void commitCheckTask(ApplyVo applyVo,ApplyCheckVo checkVo, String taskId,String operId) throws Exception {
		// TODO Auto-generated method stub
		//1、保存申请单变更信息
		applyService.saveApply(applyVo, operId);
		Task task =  actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if(task == null)
		{
			throw new Exception("提交任务失败,任务ID"+taskId+"对应任务不存在 ");
		}
		WorkflowRunPath runpath = runPathService.getFarestRunPathByActId(task.getProcessInstanceId(), task.getTaskDefinitionKey());
		if(runpath == null)
		{
			throw new Exception("提交任务失败,任务ID"+taskId+"对应路径不存在 ");
		}
		
		//2、保存审核信息
		CheckResult checkResult = new CheckResult();
		String taskBusinessId = Utils.get16UUID();
		checkResult.setId(taskBusinessId);
		checkResult.setAppId(applyVo.getAppId());
		checkResult.setNetCheckResult(checkVo.getNetCheckResult());
		checkResult.setNetCheckNotPassReason(checkVo.getNetCheckNotPassReason());
		checkResult.setNetCheckComment(checkVo.getNetCheckComment());
		checkResult.setTelCheckResult(checkVo.getTelCheckResult());
		checkResult.setTelCheckNotPassReason(checkVo.getTelCheckNotPassReason());
		checkResult.setTelCheckComment(checkVo.getTelCheckComment());
		checkResultDao.insert(checkResult);
		
		//3、保存任务处理结果信息
		TaskProcessResult taskProcessResult = new TaskProcessResult();
		taskProcessResult.setId(Utils.get16UUID());
		taskProcessResult.setRunPathId(runpath.getId());
		taskProcessResult.setProcessResult(checkVo.getResult());
		//建议通过
		if(checkVo.getResult().equals(TaskCommitType.SUGGEST_PASS))
		{
			taskProcessResult.setProcessResultDesc("建议通过");
		}
		else if(checkVo.getResult().equals(TaskCommitType.SUGGEST_CONDITION_LOAN))
		{
			taskProcessResult.setProcessResultDesc(checkVo.getLoanExtConditon());
		}
		else if(checkVo.getResult().equals(TaskCommitType.SUGGEST_CANCEL))
		{
			taskProcessResult.setProcessResultDesc(checkVo.getCancelReason());
		}
		else
		{
			taskProcessResult.setProcessResultDesc(checkVo.getRejectReason());
		}
		taskProcessResult.setComment(checkVo.getComment());
		taskProcessResult.setTaskBusinessId(taskBusinessId);
		taskProcessResultDao.insert(taskProcessResult);
		
		//4、放入流程变量
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("checkProcessResult", checkVo.getResult());
		
		runWorkflowService.completeTask(taskId, "提交任务",vars, CommandType.COMMIT);
	}

	public void commitApproveTask(ApplyVo applyVo, ApplyApproveVo approveVo, String taskId,String operId) {
		// TODO Auto-generated method stub
		
	}



}
