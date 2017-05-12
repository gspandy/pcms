package com.pujjr.assetsmanage.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.assetsmanage.dao.CollectionDisposeMapper;
import com.pujjr.assetsmanage.dao.CollectionLawsuitMapper;
import com.pujjr.assetsmanage.dao.CollectionLogMapper;
import com.pujjr.assetsmanage.dao.CollectionOutMapper;
import com.pujjr.assetsmanage.dao.CollectionRecoverMapper;
import com.pujjr.assetsmanage.dao.CollectionRefundMapper;
import com.pujjr.assetsmanage.dao.CollectionTaskMapper;
import com.pujjr.assetsmanage.dao.CollectionVisitMapper;
import com.pujjr.assetsmanage.domain.CollectionDispose;
import com.pujjr.assetsmanage.domain.CollectionLawsuit;
import com.pujjr.assetsmanage.domain.CollectionLog;
import com.pujjr.assetsmanage.domain.CollectionOut;
import com.pujjr.assetsmanage.domain.CollectionRecover;
import com.pujjr.assetsmanage.domain.CollectionRefund;
import com.pujjr.assetsmanage.domain.CollectionTask;
import com.pujjr.assetsmanage.domain.CollectionVisit;
import com.pujjr.assetsmanage.enumeration.CollectionTaskCommitType;
import com.pujjr.assetsmanage.enumeration.CollectionTaskType;
import com.pujjr.assetsmanage.service.ICollectionService;
import com.pujjr.assetsmanage.vo.CollectionApplyVo;
import com.pujjr.assetsmanage.vo.CollectionLogVo;
import com.pujjr.carcredit.dao.TaskProcessResultMapper;
import com.pujjr.carcredit.domain.TaskProcessResult;
import com.pujjr.carcredit.po.OnlineAcctPo;
import com.pujjr.carcredit.service.ITaskService;
import com.pujjr.carcredit.vo.TaskCommitType;
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.domain.WorkflowNodeAssignee;
import com.pujjr.jbpm.domain.WorkflowRunPath;
import com.pujjr.jbpm.domain.WorkflowVersion;
import com.pujjr.jbpm.service.IConfigWorkflowService;
import com.pujjr.jbpm.service.IRunPathService;
import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.jbpm.vo.ProcessGlobalVariable;
import com.pujjr.jbpm.vo.WorkflowNodeParamVo;
import com.pujjr.postloan.dao.GeneralLedgerMapper;
import com.pujjr.postloan.domain.ApplyAlterRepayDate;
import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.enumeration.LoanApplyStatus;
import com.pujjr.postloan.enumeration.RepayStatus;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.utils.Utils;

@Service
@Transactional(rollbackFor=Exception.class)
public class CollectionServiceImpl implements ICollectionService 
{
	@Autowired
	private CollectionTaskMapper collectionTaskDao;
	@Autowired
	private IRunWorkflowService runWorkflowService;
	@Autowired
	private IRunPathService runPathService;
	@Autowired
	private TaskService actTaskService;
	@Autowired
	private TaskProcessResultMapper taskProcessResultDao;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private CollectionVisitMapper collectionVisitDao;
	@Autowired
	private CollectionOutMapper collectionOutDao;
	@Autowired
	private CollectionRecoverMapper collectionRecoverDao;
	@Autowired
	private CollectionRefundMapper collectionRefundDao;
	@Autowired
	private CollectionDisposeMapper collectionDisposeDao;
	@Autowired
	private CollectionLawsuitMapper collectionLawsuitDao;
	@Autowired
	private CollectionLogMapper collectionLogDao;
	@Autowired
	private GeneralLedgerMapper ledgerDao;
	@Autowired
	private IConfigWorkflowService configWorkflowService;
	@Autowired
	private ITaskService taskService;
	
	@Override
	public void createPhoneCollectionTask(String createId, String appId,String applyComment) 
	{
		// TODO Auto-generated method stub
		//保存任务申请数据
		CollectionTask po = new CollectionTask();
		String businessKey = Utils.get16UUID();
		po.setId(businessKey);
		po.setAppId(appId);
		po.setTaskType(CollectionTaskType.PhoneCollection.getName());
		po.setWorkflowKey(CollectionTaskType.PhoneCollection.getTaskKey());
		po.setCreateId(createId);
		po.setCreateTime(new Date());
		po.setApplyComment(applyComment);
		po.setApplyStatus(LoanApplyStatus.WaitingApprove.getName());
		collectionTaskDao.insert(po);
		//启动流程
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("appId", appId);
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, createId);
		ProcessInstance instance = runWorkflowService.startProcess(CollectionTaskType.PhoneCollection.getTaskKey(), businessKey, vars);
		po.setProcInstId(instance.getProcessInstanceId());
		collectionTaskDao.updateByPrimaryKey(po);
	}

	@Override
	public void applyNewCollectionTask(String taskId,String appId, CollectionTaskType taskType, String createId,
			CollectionApplyVo vo) throws Exception {
		// TODO Auto-generated method stub
		//检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) 
		{
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}
		WorkflowRunPath runpath = runPathService.getFarestRunPathByActId(task.getProcessInstanceId(),task.getTaskDefinitionKey());
		if (runpath == null) 
		{
			throw new Exception("提交任务失败,任务ID" + taskId + "对应路径不存在 ");
		}
		
		//获取业务主键
		String srcBusinessKey = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey();
		
		//保存任务申请任务数据
		CollectionTask po = new CollectionTask();
		String businessKey = Utils.get16UUID();
		po.setId(businessKey);
		po.setAppId(appId);
		po.setTaskType(taskType.getName());
		po.setWorkflowKey(taskType.getTaskKey());
		po.setCreateId(createId);
		po.setCreateTime(new Date());
		po.setApplyComment(vo.getApplyComment());
		po.setApplyStatus(LoanApplyStatus.WaitingApprove.getName());
		po.setParentId(srcBusinessKey);		
		collectionTaskDao.insert(po);
		
		//保存任务申请业务数据
		if(taskType.equals(CollectionTaskType.VisitCollection))
		{
			CollectionVisit visitPo = new CollectionVisit();
			visitPo.setId(Utils.get16UUID());
			visitPo.setApplyId(businessKey);
			visitPo.setVisitReason(vo.getVisitReason());
			visitPo.setVisitComment(vo.getVisitComment());
			collectionVisitDao.insert(visitPo);
		}
		if(taskType.equals(CollectionTaskType.OutCollection))
		{
			CollectionOut outPo = new CollectionOut();
			outPo.setId(Utils.get16UUID());
			outPo.setApplyId(businessKey);
			outPo.setOutUnitId(vo.getOutUnitId());
			outPo.setOutReason(vo.getOutReason());
			outPo.setOutComment(vo.getOutComment());
			collectionOutDao.insert(outPo);
		}
		if(taskType.equals(CollectionTaskType.RecoverCollection))
		{
			CollectionRecover recoverPo = new CollectionRecover();
			recoverPo.setId(Utils.get16UUID());
			recoverPo.setApplyId(businessKey);
			recoverPo.setRecoverReason(vo.getRecoverReason());
			recoverPo.setRecoverUnitId(vo.getRecoverUnitId());
			recoverPo.setRecoverComment(vo.getRecoverComment());
			collectionRecoverDao.insert(recoverPo);
		}
		if(taskType.equals(CollectionTaskType.RefundCollection))
		{
			CollectionRefund refundPo = new CollectionRefund();
			refundPo.setId(Utils.get16UUID());
			refundPo.setApplyId(businessKey);
			refundPo.setBackReason(vo.getBackReason());
			refundPo.setBackComment(vo.getBackComment());
			collectionRefundDao.insert(refundPo);
		}
		if(taskType.equals(CollectionTaskType.DisposeCollection))
		{
			CollectionDispose disposePo = new CollectionDispose();
			disposePo.setId(Utils.get16UUID());
			disposePo.setApplyId(businessKey);
			disposePo.setDisposeUnitId(vo.getDisposeUnitId());
			disposePo.setRefundTime(vo.getRefundTime());
			disposePo.setRefundEvalPrice(vo.getRefundEvalPrice());
			disposePo.setResalePrice(vo.getResalePrice());
			disposePo.setResaleTime(vo.getResaleTime());
			disposePo.setCurEvalPrice(vo.getCurEvalPrice());
			disposePo.setKm(vo.getKm());
			disposePo.setDegree(vo.getDegree());
			disposePo.setAgeLimit(vo.getAgeLimit());
			disposePo.setIsDamage(vo.getIsDamage());
			disposePo.setDisposeReason(vo.getDisposeReason());
			disposePo.setDisposeComment(vo.getDisposeComment());
			collectionDisposeDao.insert(disposePo);
		}
		if(taskType.equals(CollectionTaskType.LawsuitCollection))
		{
			CollectionLawsuit lawsuitPo = new CollectionLawsuit();
			lawsuitPo.setId(Utils.get16UUID());
			lawsuitPo.setApplyId(businessKey);
			lawsuitPo.setLawsuitUnitId(vo.getLawsuitUnitId());
			lawsuitPo.setLawyerName(vo.getLawyerName());
			lawsuitPo.setLawsuitReason(vo.getLawsuitReason());
			lawsuitPo.setLawsuitComment(vo.getLawsuitComment());
			collectionLawsuitDao.insert(lawsuitPo);
		}
		
		//提交任务,保存当前申请新的催收任务ID到流程变量中，供审批时查询
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("newApplyId", businessKey);
		vars.put("taskCommitType", CollectionTaskCommitType.ApplyNewTask.getName());
		runWorkflowService.completeTask(taskId, "", vars, CommandType.COMMIT);
	}

	@Override
	public void reassigneeTaskOperId(String taskId, String newOperId) throws Exception {
		// TODO Auto-generated method stub
		//检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) 
		{
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}
		WorkflowRunPath runpath = runPathService.getFarestRunPathByActId(task.getProcessInstanceId(),task.getTaskDefinitionKey());
		if (runpath == null) 
		{
			throw new Exception("提交任务失败,任务ID" + taskId + "对应路径不存在 ");
		}
		
		//提交任务,保存新的任务执行人ID到流程变量
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("newOperId", newOperId);
		runWorkflowService.completeTask(taskId, "", vars, CommandType.COMMIT);
	}

	@Override
	public List<OnlineAcctPo> getCollectionWorkgroupUserIdList(String taskId) throws Exception {
		// TODO Auto-generated method stub
		//检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) 
		{
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}
		//获取业务主键
		String businessKey = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey();
		CollectionTask po = collectionTaskDao.selectByPrimaryKey(businessKey);
		String workflowKey = po.getWorkflowKey();
		String collectionTaskNodeId = "";
		if(workflowKey.equals(CollectionTaskType.PhoneCollection.getTaskKey()))
		{
			collectionTaskNodeId = "node_dhcs";
		}
		if(workflowKey.equals(CollectionTaskType.VisitCollection.getTaskKey()))
		{
			collectionTaskNodeId = "node_smcs";
		}
		if(workflowKey.equals(CollectionTaskType.OutCollection.getTaskKey()))
		{
			collectionTaskNodeId = "node_wwcs";
		}
		if(workflowKey.equals(CollectionTaskType.RecoverCollection.getTaskKey()))
		{
			collectionTaskNodeId = "node_wwsc";
		}
		if(workflowKey.equals(CollectionTaskType.RefundCollection.getTaskKey()))
		{
			collectionTaskNodeId = "node_clth";
		}
		if(workflowKey.equals(CollectionTaskType.DisposeCollection.getTaskKey()))
		{
			collectionTaskNodeId = "node_zccz";
		}
		if(workflowKey.equals(CollectionTaskType.LawsuitCollection.getTaskKey()))
		{
			collectionTaskNodeId = "node_ss";
		}
		
		//获取流程版本信息
		String workflowVersionId = runtimeService.getVariable(task.getProcessInstanceId(),ProcessGlobalVariable.WORKFLOW_VERSION_ID).toString();
		//获取节点参数配置
		WorkflowNodeAssignee nodeAssignee = configWorkflowService.getNodeAssignee(workflowVersionId, collectionTaskNodeId);
		
		List<OnlineAcctPo> userList = taskService.getOnlineAcctInfo(nodeAssignee.getAssigneeParam(), false);
		return userList;
	}

	@Override
	public void commitApproveApplyNewCollectionTask(String taskId, ApproveResultVo vo) throws Exception {
		// TODO Auto-generated method stub
		//检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) 
		{
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}
		WorkflowRunPath runpath = runPathService.getFarestRunPathByActId(task.getProcessInstanceId(),task.getTaskDefinitionKey());
		if (runpath == null) 
		{
			throw new Exception("提交任务失败,任务ID" + taskId + "对应路径不存在 ");
		}
		
		//获取申请的新催收任务申请ID
		String businessKey = runtimeService.getVariable(task.getExecutionId(), "newApplyId").toString();
		CollectionTask po = collectionTaskDao.selectByPrimaryKey(businessKey);
		
		//保存任务处理结果信息
		String procId = Utils.get16UUID();
		TaskProcessResult taskProcessResult = new TaskProcessResult();
		taskProcessResult.setId(procId);
		taskProcessResult.setRunPathId(runpath.getId());
		taskProcessResult.setProcessResult(TaskCommitType.LOAN_PASS);
		taskProcessResult.setComment(vo.getApproveComment()==null?"通过":vo.getApproveComment());
		taskProcessResultDao.insert(taskProcessResult);
			
		po.setApplyStatus(LoanApplyStatus.ApprovePass.getName());
		po.setApproveId(task.getAssignee());
		po.setApproveTime(new Date());
		po.setApplyComment(vo.getApproveComment());
		//启动新的任务
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("appId", po.getAppId());
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, po.getCreateId());
		ProcessInstance instance = runWorkflowService.startProcess(po.getWorkflowKey(), po.getId(), vars);
		po.setProcInstId(instance.getProcessInstanceId());
	
		collectionTaskDao.updateByPrimaryKey(po);
		
		//完成当前任务
		runWorkflowService.completeTask(taskId, "", null, CommandType.COMMIT);
	}

	@Override
	public void commitSettleLawsuitTask(String taskId) throws Exception {
		// TODO Auto-generated method stub
		//检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) 
		{
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}
		WorkflowRunPath runpath = runPathService.getFarestRunPathByActId(task.getProcessInstanceId(),task.getTaskDefinitionKey());
		if (runpath == null) 
		{
			throw new Exception("提交任务失败,任务ID" + taskId + "对应路径不存在 ");
		}

		//提交任务,保存当前申请新的催收任务ID到流程变量中，供审批时查询
		HashMap<String,Object> vars = new HashMap<String,Object>();
		
		//获取申请单号,查询总账状态
		String appId = runtimeService.getVariable(task.getExecutionId(), "appId").toString();
		GeneralLedger ledgerPo = ledgerDao.selectByAppId(appId);
		if(ledgerPo.getRepayStatus().equals(RepayStatus.Overdue.getName()))
		{
			throw new Exception("当前客户处于逾期状态，不能结案");
		}
		if(ledgerPo.getRepayStatus().equals(RepayStatus.Repaying.getName()))
		{
			vars.put("taskCommitType", CollectionTaskCommitType.NotOverdue.getName());
		}
		if(ledgerPo.getRepayStatus().equals(RepayStatus.Settled.getName()))
		{
			vars.put("taskCommitType", CollectionTaskCommitType.Settleed.getName());
		}
		runWorkflowService.completeTask(taskId, "", vars, CommandType.COMMIT);
	}

	@Override
	public CollectionApplyVo getCollectionAppyInfo(String applyId) {
		// TODO Auto-generated method stub
		CollectionApplyVo vo = new CollectionApplyVo();
		CollectionTask taskVo = collectionTaskDao.selectByPrimaryKey(applyId);
		vo.setTaskType(taskVo.getTaskType());
		if(taskVo.getTaskType().equals(CollectionTaskType.VisitCollection.getName()))
		{
			CollectionVisit po = collectionVisitDao.selectByApplyId(applyId);
			BeanUtils.copyProperties(po, vo);
		}
		if(taskVo.getTaskType().equals(CollectionTaskType.OutCollection.getName()))
		{
			CollectionOut po = collectionOutDao.selectByApplyId(applyId);
			BeanUtils.copyProperties(po, vo);
		}
		if(taskVo.getTaskType().equals(CollectionTaskType.RecoverCollection.getName()))
		{
			CollectionRecover po = collectionRecoverDao.selectByApplyId(applyId);
			BeanUtils.copyProperties(po, vo);
		}
		if(taskVo.getTaskType().equals(CollectionTaskType.RefundCollection.getName()))
		{
			CollectionRefund po = collectionRefundDao.selectByApplyId(applyId);
			BeanUtils.copyProperties(po, vo);
		}
		if(taskVo.getTaskType().equals(CollectionTaskType.DisposeCollection.getName()))
		{
			CollectionDispose po = collectionDisposeDao.selectByApplyId(applyId);
			BeanUtils.copyProperties(po, vo);
		}
		if(taskVo.getTaskType().equals(CollectionTaskType.LawsuitCollection.getName()))
		{
			CollectionLawsuit po = collectionLawsuitDao.selectByApplyId(applyId);
			BeanUtils.copyProperties(po, vo);
		}
		return vo;
	}

	@Override
	public List<CollectionLog> getCollectionLogInfo(String appId, String taskType) {
		// TODO Auto-generated method stub
		return collectionLogDao.selectCollectionLogInfo(appId, taskType);
	}

	@Override
	public List<CollectionLogVo> getImportanCollectionLogInfo(String appId) {
		// TODO Auto-generated method stub
		return collectionLogDao.selectImportanCollectionLogInfo(appId);
	}

	@Override
	public List<CollectionTask> getCollectionApplyTask(String appId) {
		// TODO Auto-generated method stub
		return collectionTaskDao.selectCollectionApplyTask(appId);
	}

	@Override
	public void saveCollectionLog(String taskId, CollectionLogVo vo) throws Exception {
		// TODO Auto-generated method stub
		//检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) 
		{
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}
		
		//获取业务主键
		String businessKey = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey();
		CollectionTask taskPo = collectionTaskDao.selectByPrimaryKey(businessKey);
		
		//保存日志记录
		CollectionLog po = new CollectionLog();
		BeanUtils.copyProperties(vo, po);
		po.setId(Utils.get16UUID());
		po.setAppId(taskPo.getAppId());
		po.setApplyId(businessKey);
		po.setOperTime(new Date());
		po.setOperId(task.getAssignee());
		collectionLogDao.insert(po);
	}

	@Override
	public void applyRecoverCollectionTask(String appId, String createId, CollectionApplyVo vo) throws Exception {
		// TODO Auto-generated method stub
		if(this.checkHasCollectionTask(appId)==false)
		{
			throw new Exception("已存在催收任务，请勿重复发起");
		}
		//保存申请信息
		CollectionTask po = new CollectionTask();
		String businessKey = Utils.get16UUID();
		po.setId(businessKey);
		po.setAppId(appId);
		po.setTaskType(CollectionTaskType.RecoverCollection.getName());
		po.setWorkflowKey(CollectionTaskType.RecoverCollection.getTaskKey());
		po.setCreateId(createId);
		po.setCreateTime(new Date());
		po.setApplyComment(vo.getApplyComment());
		po.setApplyStatus(LoanApplyStatus.WaitingApprove.getName());
		
		//启动新的任务
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("appId", po.getAppId());
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, createId);
		//这里设置任务启动方式,用于与上级任务联动发起的进行区分
		vars.put("taskCreateMode", "manual");
		ProcessInstance instance = runWorkflowService.startProcess(CollectionTaskType.RecoverCollection.getTaskKey(), businessKey, vars);
		
		po.setProcInstId(instance.getProcessInstanceId());
		collectionTaskDao.insert(po);
		CollectionRecover recoverPo = new CollectionRecover();
		recoverPo.setId(Utils.get16UUID());
		recoverPo.setApplyId(businessKey);
		recoverPo.setRecoverReason(vo.getRecoverReason());
		recoverPo.setRecoverComment(vo.getRecoverComment());
		recoverPo.setRecoverUnitId(vo.getRecoverUnitId());
		collectionRecoverDao.insert(recoverPo);
	}

	@Override
	public void commitApproveRecoverCollectionTask(String taskId, ApproveResultVo vo) throws Exception {
		// TODO Auto-generated method stub
		//检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}
		WorkflowRunPath runpath = runPathService.getFarestRunPathByActId(task.getProcessInstanceId(),task.getTaskDefinitionKey());
		if (runpath == null) {
			throw new Exception("提交任务失败,任务ID" + taskId + "对应路径不存在 ");
		}
		
		//获取申请数据
		String businessKey = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey();
		CollectionTask po = collectionTaskDao.selectByPrimaryKey(businessKey);
		
		//保存任务处理结果信息
		String procId = Utils.get16UUID();
		TaskProcessResult taskProcessResult = new TaskProcessResult();
		taskProcessResult.setId(procId);
		taskProcessResult.setRunPathId(runpath.getId());
		taskProcessResult.setProcessResult(vo.getApproveResult());
		taskProcessResult.setComment(vo.getApproveComment());
		taskProcessResultDao.insert(taskProcessResult);
		
		//根据审批结果执行相关操作
		po.setApplyStatus(vo.getApproveResult());
		po.setApproveTime(new Date());
		po.setApproveId(task.getAssignee());
		po.setApplyComment(vo.getApproveComment());
		collectionTaskDao.updateByPrimaryKey(po);
		
		//5、提交任务
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("approveProcessResult", vo.getApproveResult());
		runWorkflowService.completeTask(taskId, "", vars, CommandType.COMMIT);
	}

	@Override
	public void applyReAssigneeTask(String taskId, String reason) throws Exception {
		// TODO Auto-generated method stub
		//检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}
		//提交任务
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("taskCommitType", CollectionTaskCommitType.ReAssignee.getName());
		runWorkflowService.completeTask(taskId, reason, vars, CommandType.COMMIT);
	}

	@Override
	public boolean checkHasCollectionTask(String appId) {
		// TODO Auto-generated method stub
		List<CollectionTask> taskList = collectionTaskDao.selectHasCollectionTaskRunning(appId);
		if(taskList.size()==0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	@Override
	public HashMap<String, Object> getCollectionTaskCnt(String appId) {
		// TODO Auto-generated method stub
		List<HashMap<String,Object>> list =  collectionTaskDao.selectCollectionTaskCnt(appId);
		HashMap<String,Object> map = new HashMap<String,Object>();
		for(HashMap<String,Object> l : list)
		{
			map.put(l.get("taskType").toString(), l.get("cnt").toString());
		}
		return map;
	}

	@Override
	public void commitSettleApprove(String taskId) throws Exception {
		// TODO Auto-generated method stub
		//检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}
		//提交任务
		runWorkflowService.completeTask(taskId, "", null, CommandType.COMMIT);
	}

}
