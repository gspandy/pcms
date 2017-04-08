package com.pujjr.assetsmanage.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.assetsmanage.dao.ArchiveMapper;
import com.pujjr.assetsmanage.domain.CollectionTask;
import com.pujjr.assetsmanage.enumeration.CollectionTaskType;
import com.pujjr.assetsmanage.po.ArchiveDetailPo;
import com.pujjr.assetsmanage.po.ArchiveTaskPo;
import com.pujjr.assetsmanage.service.IArchiveService;
import com.pujjr.assetsmanage.vo.ArchiveDelayVo;
import com.pujjr.assetsmanage.vo.ArchiveLogVo;
import com.pujjr.assetsmanage.vo.ArchivePostVo;
import com.pujjr.assetsmanage.vo.ArchiveSupplyVo;
import com.pujjr.assetsmanage.vo.ReApplyArchiveSupplyVo;
import com.pujjr.assetsmanage.vo.ApplyArchiveBorrowVo;
import com.pujjr.assetsmanage.vo.ApplyArchiveSupplyVo;
import com.pujjr.base.dao.ArchiveBorrowDetailMapper;
import com.pujjr.base.dao.ArchiveBorrowMapper;
import com.pujjr.base.dao.ArchiveDetailMapper;
import com.pujjr.base.dao.ArchiveStoreMapper;
import com.pujjr.base.dao.ArchiveSupplyDetailMapper;
import com.pujjr.base.dao.ArchiveSupplyMapper;
import com.pujjr.base.dao.ArchiveTaskMapper;
import com.pujjr.base.domain.ArchiveBorrow;
import com.pujjr.base.domain.ArchiveBorrowDetail;
import com.pujjr.base.domain.ArchiveDetail;
import com.pujjr.base.domain.ArchiveStore;
import com.pujjr.base.domain.ArchiveSupply;
import com.pujjr.base.domain.ArchiveSupplyDetail;
import com.pujjr.base.domain.ArchiveTask;
import com.pujjr.base.domain.SysDictData;
import com.pujjr.base.service.ISequenceService;
import com.pujjr.base.service.ISysDictService;
import com.pujjr.carcredit.dao.TaskProcessResultMapper;
import com.pujjr.carcredit.domain.TaskProcessResult;
import com.pujjr.carcredit.vo.TaskCommitType;
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.domain.WorkflowRunPath;
import com.pujjr.jbpm.service.IRunPathService;
import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.jbpm.vo.ProcessGlobalVariable;
import com.pujjr.postloan.enumeration.ArchiveStatus;
import com.pujjr.postloan.enumeration.ArchiveType;
import com.pujjr.postloan.enumeration.LoanApplyStatus;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.utils.Utils;

@Service
@Transactional(rollbackFor=Exception.class)
public class ArchiveServiceImpl implements IArchiveService {

	@Autowired
	private ArchiveMapper archiveDao;
	@Autowired
	private ArchiveDetailMapper archiveDetailDao;
	@Autowired
	private ArchiveTaskMapper archiveTaskDao;
	@Autowired
	private TaskService actTaskService;
	@Autowired
	private IRunWorkflowService runWorkflowService;
	@Autowired
	private ISequenceService seqService;
	@Autowired
	private ISysDictService dictService;
	@Autowired
	private ArchiveSupplyMapper archiveSupplyDao;
	@Autowired
	private ArchiveSupplyDetailMapper archiveSupplyDetailDao;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ArchiveStoreMapper archiveStoreDao;
	@Autowired
	private ArchiveBorrowMapper archiveBorrowDao;
	@Autowired
	private ArchiveBorrowDetailMapper archiveBorrowDetailDao;
	@Autowired
	private IRunPathService runPathService;
	@Autowired
	private TaskProcessResultMapper taskProcessResultDao;
	
	@Override
	public List<HashMap<String, Object>> getArchiveToDoTaskList(String assignee) {
		// TODO Auto-generated method stub
		return archiveDao.selectArchiveToDoTaskList(assignee);
	}

	@Override
	public List<ArchiveDetailPo> getArchiveTaskDetail(String archiveTaskId,String archiveTaskType) {
		// TODO Auto-generated method stub
		return archiveDao.selectArchiveTaskDetail(archiveTaskId,archiveTaskType);
	}

	@Override
	public void saveArchiveTaskDetail(String archiveTaskId, List<ArchiveDetailPo> records,String operId) {
		// TODO Auto-generated method stub
		ArchiveTask task = archiveTaskDao.selectByPrimaryKey(archiveTaskId);
		archiveDetailDao.deleteByArchiveTaskId(archiveTaskId);
		for(ArchiveDetailPo item : records)
		{
			if(item.getPostFileCnt()==null || item.getPostFileCnt().equals("0"))
			{
				continue;
			}
			ArchiveDetail dtlPo = new ArchiveDetail();
			dtlPo.setId(Utils.get16UUID());
			dtlPo.setArchiveTaskId(archiveTaskId);
			dtlPo.setFileName(item.getFileName());
			dtlPo.setPostFileCnt(item.getPostFileCnt());
			dtlPo.setComment(item.getComment());
			archiveDetailDao.insert(dtlPo);
		}
		task.setArchiveStatus(ArchiveStatus.WaitingPost.getName());
		task.setCheckId(operId);
		task.setCheckTime(new Date());
		archiveTaskDao.updateByPrimaryKey(task);
	}

	@Override
	public void archivePost(ArchivePostVo vo, String operId) {
		// TODO Auto-generated method stub
		for(String archiveTaskId : vo.getSelArchives())
		{
			ArchiveTask task = archiveTaskDao.selectByPrimaryKey(archiveTaskId);
			task.setExpressCompany(vo.getExpressCompany());
			task.setExpressNo(vo.getExpressNo());
			task.setComment(vo.getComment());
			task.setPostTime(new Date());
			task.setPostId(operId);
			task.setArchiveStatus(ArchiveStatus.WaitingCommit.getName());
			archiveTaskDao.updateByPrimaryKey(task);
		}
	}

	@Override
	public List<HashMap<String, Object>> getArchiveList() {
		// TODO Auto-generated method stub
		return archiveDao.selectArchiveList();
	}

	@Override
	public void archiveDelay(ArchiveDelayVo vo) throws Exception {
		// TODO Auto-generated method stub
		ArchiveTask task = archiveTaskDao.selectByPrimaryKey(vo.getArchiveTaskId());
		if(Utils.getSpaceDay(task.getArchiveEndDate(), vo.getDelayEndDate())<=0)
		{
			throw new Exception("延期截止日期应大于逾期截止日期");
		}
		task.setIsDelay(true);
		task.setDelayEndDate(vo.getDelayEndDate());
		archiveTaskDao.updateByPrimaryKey(task);
	}

	@Override
	public void applyArchiveLog(List<ArchiveLogVo> records, String operId) throws Exception {
		// TODO Auto-generated method stub
		for(ArchiveLogVo item : records)
		{
			//检查任务合法性
			Task task = actTaskService.createTaskQuery().taskId(item.getTaskId()).singleResult();
			if (task == null) 
			{
				throw new Exception("提交任务失败,任务ID" + item.getTaskId() + "对应任务不存在 ");
			}
			
			ArchiveTask archiveTask = archiveTaskDao.selectByPrimaryKey(item.getArchiveTaskId());
			
			//启动归档任务流程
			HashMap<String,Object> vars = new HashMap<String,Object>();
			vars.put("appId", archiveTask.getAppId());
			vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, operId);
			ProcessInstance instance = runWorkflowService.startProcess("FKWCDAGD", archiveTask.getId(), vars);
			
			//更新归档申请信息
			archiveTask.setArchiveStatus(ArchiveStatus.WaitingProcess.getName());
			archiveTask.setCommitId(operId);
			archiveTask.setCommitTime(new Date());
			archiveTask.setProcessId(instance.getProcessInstanceId());
			archiveTaskDao.updateByPrimaryKey(archiveTask);
			
			//结束当前档案整理任务
			runWorkflowService.completeTask(item.getTaskId(), "", null, CommandType.COMMIT);
		}
	}

	@Override
	public ArchiveTaskPo getArchiveApplyInfo(String archiveTaskId) throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		ArchiveTaskPo taskPo = new ArchiveTaskPo();
		ArchiveTask archiveTask = archiveTaskDao.selectByPrimaryKey(archiveTaskId);
		taskPo.setId(archiveTask.getId());
		taskPo.setAppId(archiveTask.getAppId());
		taskPo.setArchiveNo(archiveTask.getArchiveNo());
		taskPo.setArchiveType(archiveTask.getArchiveType());
		taskPo.setArchiveStatus(archiveTask.getArchiveStatus());
		taskPo.setExpressCompany(archiveTask.getExpressCompany());
		taskPo.setExpressNo(archiveTask.getArchiveNo());
		List<ArchiveDetailPo> detailListPo = archiveDao.selectArchiveTaskDetailNoNull(archiveTaskId);
		taskPo.setDetailList(detailListPo);
		return taskPo;
	}

	@Override
	public void commitArchiveLog(ArchiveTaskPo po, String taskId,String operId) throws Exception {
		// TODO Auto-generated method stub
		//检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) 
		{
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}
		
		//更新任务信息及库存信息
		for(ArchiveDetailPo item : po.getDetailList())
		{
			archiveDetailDao.updateByPrimaryKey(item);
			if(item.getPostFileCnt()>0)
			{
				ArchiveStore store = archiveStoreDao.selectOne(po.getAppId(), po.getArchiveType(), item.getFileName());
				if(store==null)
				{
					store = new ArchiveStore();
					store.setId(Utils.get16UUID());
					store.setAppId(po.getAppId());
					store.setArchiveType(po.getArchiveType());
					store.setFileName(item.getFileName());
					store.setTotalFileCnt(item.getPostFileCnt());
					store.setRemainFileCnt(item.getPostFileCnt());
					archiveStoreDao.insert(store);
				}
				else
				{
					store.setTotalFileCnt(store.getTotalFileCnt()+item.getPostFileCnt());
					store.setRemainFileCnt(store.getRemainFileCnt()+item.getPostFileCnt());
					archiveStoreDao.updateByPrimaryKey(store);
				}
			}
			archiveDetailDao.updateByPrimaryKey(item);
		}
		ArchiveTask archiveTask = archiveTaskDao.selectByPrimaryKey(po.getId());
		archiveTask.setArchiveStatus(ArchiveStatus.Archived.getName());
		archiveTask.setProcessTime(new Date());
		archiveTask.setProcessId(operId);
		archiveTaskDao.updateByPrimaryKey(archiveTask);
		
		//提交任务
		HashMap<String, Object> vars = new HashMap<String, Object>();
		vars.put("isArchiveSupply", false);
		runWorkflowService.completeTask(taskId,"",vars,CommandType.COMMIT);
	}

	@Override
	public void createCollectionArchiveTask(String appId,String archiveType, List<ArchiveDetailPo> records, String operId) {
		// TODO Auto-generated method stub
		
		ArchiveTask archiveTask =  new ArchiveTask();
		String businessKey = Utils.get16UUID();
		archiveTask.setId(businessKey);
		archiveTask.setArchiveType(archiveType);
		archiveTask.setAppId(appId);
		archiveTask.setArchiveNo(String.valueOf(seqService.getNextVal("archiveNo")));
		archiveTask.setArchiveStatus(ArchiveStatus.WaitingProcess.getName());
		archiveTask.setCommitId(operId);
		archiveTask.setCommitTime(new Date());
		for(ArchiveDetailPo item : records)
		{
			if(item.getPostFileCnt()==null || item.getPostFileCnt().equals("0"))
			{
				continue;
			}
			ArchiveDetail dtlPo = new ArchiveDetail();
			dtlPo.setId(Utils.get16UUID());
			dtlPo.setArchiveTaskId(businessKey);
			dtlPo.setFileName(item.getFileName());
			dtlPo.setPostFileCnt(item.getPostFileCnt());
			dtlPo.setComment(item.getComment());
			archiveDetailDao.insert(dtlPo);
		}
		archiveTaskDao.insert(archiveTask);
		//启动归档任务流程
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("appId", appId);
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, operId);
		ProcessInstance instance = runWorkflowService.startProcess("CSGD", archiveTask.getId(), vars);
		//更新任务关联流程信息
		archiveTask.setProcInstId(instance.getProcessInstanceId());
		archiveTaskDao.updateByPrimaryKey(archiveTask);
	}

	@Override
	public void createAutoArchiveTask(String appId, String archiveType, String operId) {
		// TODO Auto-generated method stub
		ArchiveTask archiveTask =  new ArchiveTask();
		String businessKey = Utils.get16UUID();
		archiveTask.setId(businessKey);
		archiveTask.setArchiveType(archiveType);
		archiveTask.setAppId(appId);
		archiveTask.setArchiveNo(String.valueOf(seqService.getNextVal("archiveNo")));
		archiveTask.setArchiveStatus(ArchiveStatus.WaitingProcess.getName());
		archiveTask.setCommitId(operId);
		archiveTask.setCommitTime(new Date());
		for(ArchiveType type : ArchiveType.values())
		{
			if(type.getName().equals(archiveType))
			{
				//生成明细项
				List<SysDictData> items = dictService.getDictDataListByDictTypeCode(type.getType());
				for(SysDictData item : items)
				{
					ArchiveDetail dtl = new ArchiveDetail();
					dtl.setId(Utils.get16UUID());
					dtl.setArchiveTaskId(businessKey);
					dtl.setFileName(item.getDictDataCode());
					dtl.setPostFileCnt(0);
					dtl.setRecvFileCnt(0);
					archiveDetailDao.insert(dtl);
				}
			}
		}
		archiveTaskDao.insert(archiveTask);
		//启动任务
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("appId", appId);
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, operId);
		ProcessInstance instance = runWorkflowService.startProcess("ZDFQDAGD", archiveTask.getId(), vars);
		
		//更新任务关联流程信息
		archiveTask.setProcInstId(instance.getProcessInstanceId());
		archiveTaskDao.updateByPrimaryKey(archiveTask);
	}

	@Override
	public void applyArchiveSupply(ApplyArchiveSupplyVo vo, String taskId,String operId) throws Exception {
		// TODO Auto-generated method stub
		//检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) 
		{
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}		
		
		//更新归档信息，将实际收到的份数放入档案库存表，如果没有库存则创建库存信息
		for(ArchiveDetailPo item : vo.getArchiveTask().getDetailList())
		{
			archiveDetailDao.updateByPrimaryKey(item);
			if(item.getPostFileCnt()>0)
			{
				ArchiveStore store = archiveStoreDao.selectOne(vo.getArchiveTask().getAppId(), vo.getArchiveTask().getArchiveType(), item.getFileName());
				if(store==null)
				{
					store = new ArchiveStore();
					store.setId(Utils.get16UUID());
					store.setAppId(vo.getArchiveTask().getAppId());
					store.setArchiveType(vo.getArchiveTask().getArchiveType());
					store.setFileName(item.getFileName());
					store.setTotalFileCnt(item.getPostFileCnt());
					store.setRemainFileCnt(item.getPostFileCnt());
					archiveStoreDao.insert(store);
				}
				else
				{
					store.setTotalFileCnt(store.getTotalFileCnt()+item.getPostFileCnt());
					store.setRemainFileCnt(store.getRemainFileCnt()+item.getPostFileCnt());
					archiveStoreDao.updateByPrimaryKey(store);
				}
			}
			
			
		}
		
		//创建补充资料信息
		ArchiveSupply supplyPo = new ArchiveSupply();
		String supplyId = Utils.get16UUID();
		supplyPo.setId(supplyId);
		supplyPo.setArchiveTaskId(vo.getArchiveTask().getId());
		supplyPo.setCreateId(operId);
		supplyPo.setCreateTime(new Date());
		supplyPo.setIsProcessed(false);
		supplyPo.setComment(vo.getComment());
		archiveSupplyDao.insert(supplyPo);
		
		//插入补充明细信息
		for(ArchiveSupplyDetail item : vo.getSupplyDetailList())
		{
			if(item.getSupplyFileCnt()==null || item.getSupplyFileCnt().equals("0"))
			{
				continue;
			}
			ArchiveSupplyDetail supplyDetailPo = new ArchiveSupplyDetail();
			supplyDetailPo.setId(Utils.get16UUID());
			supplyDetailPo.setSupplyId(supplyId);
			supplyDetailPo.setFileName(item.getFileName());
			supplyDetailPo.setSupplyFileCnt(item.getSupplyFileCnt());
			supplyDetailPo.setPostFileCnt(0);
			supplyDetailPo.setRecvFileCnt(0);
			archiveSupplyDetailDao.insert(supplyDetailPo);
		}
		
		//更新归档任务状态为补充归档资料
		ArchiveTask archiveTask = archiveTaskDao.selectByPrimaryKey(vo.getArchiveTask().getId());
		archiveTask.setArchiveStatus(ArchiveStatus.ArchiveSupply.getName());
		archiveTaskDao.updateByPrimaryKey(archiveTask);
		
		//提交任务
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("isArchiveSupply", true);
		vars.put("supplyId", supplyId);
		runWorkflowService.completeTask(taskId,"",vars,CommandType.COMMIT);
	}

	@Override
	public ArchiveSupplyVo getArchiveSupplyInfo(String taskId) throws Exception {
		// TODO Auto-generated method stub
		//检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) 
		{
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}	
		
		//获取补充归档任务ID
		String supplyId = runtimeService.getVariable(task.getExecutionId(), "supplyId").toString();
		ArchiveSupply supplyInfo = archiveSupplyDao.selectByPrimaryKey(supplyId);
		List<ArchiveSupplyDetail> list = archiveSupplyDetailDao.selectAll(supplyId);
		ArchiveSupplyVo vo = new ArchiveSupplyVo();
		vo.setSupplyInfo(supplyInfo);
		vo.setSupplyDetailList(list);
		return vo;
	}

	@Override
	public void commitArchiveSupplyTask(ArchiveSupplyVo vo, String taskId, String operId) throws Exception {
		// TODO Auto-generated method stub
		//检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) 
		{
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}	
		
		//更新补充资料信息
		ArchiveSupply supplyInfo = vo.getSupplyInfo();
		supplyInfo.setProcessId(operId);
		supplyInfo.setProcessTime(new Date());
		archiveSupplyDao.updateByPrimaryKey(supplyInfo);
		for(ArchiveSupplyDetail item : vo.getSupplyDetailList())
		{
			archiveSupplyDetailDao.updateByPrimaryKey(item);
		}
		
		runWorkflowService.completeTask(taskId,"",null,CommandType.COMMIT);
	}

	@Override
	public void commitArchiveLogSupplyTask(ArchiveSupplyVo vo, String taskId, String operId) throws Exception {
		// TODO Auto-generated method stub
		//检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) 
		{
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}
		
		//更新归档任务为已归档
		String businessKey = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey();
		ArchiveTask archiveTaskPo = archiveTaskDao.selectByPrimaryKey(businessKey);
		archiveTaskPo.setProcessId(operId);
		archiveTaskPo.setProcessTime(new Date());
		archiveTaskPo.setArchiveStatus(ArchiveStatus.Archived.getName());
		archiveTaskDao.updateByPrimaryKey(archiveTaskPo);
		
		//保存补充明细信息，更新档案库存表
		ArchiveSupply supplyPo = vo.getSupplyInfo();
		supplyPo.setIsProcessed(true);
		supplyPo.setProcessId(operId);
		supplyPo.setProcessTime(new Date());
		archiveSupplyDao.updateByPrimaryKey(supplyPo);
		for(ArchiveSupplyDetail item : vo.getSupplyDetailList())
		{
			archiveSupplyDetailDao.updateByPrimaryKey(item);
			if(item.getRecvFileCnt()>0)
			{
				ArchiveStore store = archiveStoreDao.selectOne(businessKey, archiveTaskPo.getArchiveType(), item.getFileName());
				if(store==null)
				{
					store = new ArchiveStore();
					store.setId(Utils.get16UUID());
					store.setAppId(archiveTaskPo.getAppId());
					store.setArchiveType(archiveTaskPo.getArchiveType());
					store.setFileName(item.getFileName());
					store.setTotalFileCnt(item.getPostFileCnt());
					store.setRemainFileCnt(item.getPostFileCnt());
					archiveStoreDao.insert(store);
				}
				else
				{
					store.setTotalFileCnt(store.getTotalFileCnt()+item.getPostFileCnt());
					store.setRemainFileCnt(store.getRemainFileCnt()+item.getPostFileCnt());
					archiveStoreDao.updateByPrimaryKey(store);
				}
			}
		}
		
		// 提交任务
		HashMap<String, Object> vars = new HashMap<String, Object>();
		vars.put("isArchiveSupply", false);
		runWorkflowService.completeTask(taskId,"",vars,CommandType.COMMIT);
	}

	@Override
	public void reApplyArchiveSupply(ReApplyArchiveSupplyVo vo, String taskId, String operId) throws Exception {
		// TODO Auto-generated method stub
		//检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) 
		{
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}
		
		//更新原始补充资料信息
		String businessKey = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey();
		ArchiveTask archiveTaskPo = archiveTaskDao.selectByPrimaryKey(businessKey);
		
		//更新上一个补充任务信息为已处理
		ArchiveSupply supplyPo = vo.getOldSupplyVo().getSupplyInfo();
		supplyPo.setIsProcessed(true);
		supplyPo.setProcessId(operId);
		supplyPo.setProcessTime(new Date());
		archiveSupplyDao.updateByPrimaryKey(supplyPo);
		
		for(ArchiveSupplyDetail item : vo.getOldSupplyVo().getSupplyDetailList())
		{
			archiveSupplyDetailDao.updateByPrimaryKey(item);
			//更新档案仓库数量
			if(item.getRecvFileCnt()>0)
			{
				ArchiveStore store = archiveStoreDao.selectOne(businessKey, archiveTaskPo.getArchiveType(), item.getFileName());
				if(store==null)
				{
					store = new ArchiveStore();
					store.setId(Utils.get16UUID());
					store.setAppId(archiveTaskPo.getAppId());
					store.setArchiveType(archiveTaskPo.getArchiveType());
					store.setFileName(item.getFileName());
					store.setTotalFileCnt(item.getPostFileCnt());
					store.setRemainFileCnt(item.getPostFileCnt());
					archiveStoreDao.insert(store);
				}
				else
				{
					store.setTotalFileCnt(store.getTotalFileCnt()+item.getPostFileCnt());
					store.setRemainFileCnt(store.getRemainFileCnt()+item.getPostFileCnt());
					archiveStoreDao.updateByPrimaryKey(store);
				}
			}
		}
		//创建新的补充资料信息
		ArchiveSupply newSupplyPo = new ArchiveSupply();
		String supplyId = Utils.get16UUID();
		newSupplyPo.setId(supplyId);
		newSupplyPo.setArchiveTaskId(businessKey);
		newSupplyPo.setCreateId(operId);
		newSupplyPo.setCreateTime(new Date());
		newSupplyPo.setIsProcessed(false);
		newSupplyPo.setComment(vo.getComment());
		archiveSupplyDao.insert(newSupplyPo);

		// 插入补充明细信息
		for (ArchiveSupplyDetail item : vo.getSupplyDetailList()) 
		{
			if (item.getSupplyFileCnt() == null || item.getSupplyFileCnt().equals("0")) 
			{
				continue;
			}
			ArchiveSupplyDetail supplyDetailPo = new ArchiveSupplyDetail();
			supplyDetailPo.setId(Utils.get16UUID());
			supplyDetailPo.setSupplyId(supplyId);
			supplyDetailPo.setFileName(item.getFileName());
			supplyDetailPo.setSupplyFileCnt(item.getSupplyFileCnt());
			supplyDetailPo.setPostFileCnt(0);
			supplyDetailPo.setRecvFileCnt(0);
			archiveSupplyDetailDao.insert(supplyDetailPo);
		}

		// 提交任务
		HashMap<String, Object> vars = new HashMap<String, Object>();
		vars.put("isArchiveSupply", true);
		vars.put("supplyId", supplyId);
		runWorkflowService.completeTask(taskId, "", vars, CommandType.COMMIT);
	}

	@Override
	public List<ArchiveStore> getArchiveStoreList(String appId) {
		// TODO Auto-generated method stub
		return archiveStoreDao.selectAll(appId);
	}

	@Override
	public void applyArchiveBorrow(ApplyArchiveBorrowVo vo,String appId, String operId) {
		// TODO Auto-generated method stub
		ArchiveBorrow borrowInfo = vo.getBorrowInfo();
		String businessKey = Utils.get16UUID();
		borrowInfo.setId(businessKey);
		borrowInfo.setAppId(appId);
		borrowInfo.setCreateId(operId);
		borrowInfo.setCreateTime(new Date());
		
		//保存明细信息
		for(ArchiveBorrowDetail item : vo.getDetailList())
		{
			if(item.getBorrowCnt()==null || item.getBorrowCnt()==0)
			{
				continue;
			}
			item.setId(Utils.get16UUID());
			item.setBorrowTaskId(businessKey);
			archiveBorrowDetailDao.insert(item);
			
			//再申请时减去库存，如果审批被拒绝则返回库存，避免后续申请没有多余库存
			ArchiveStore store = archiveStoreDao.selectOne(appId, item.getArchiveType(),item.getFileName());
			store.setRemainFileCnt(store.getRemainFileCnt()-item.getBorrowCnt());
			archiveStoreDao.updateByPrimaryKey(store);
		}
		
		//启动任务
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("appId", appId);
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, operId);
		ProcessInstance instance = runWorkflowService.startProcess("DAJY", businessKey, vars);
		
		borrowInfo.setProcInstId(instance.getProcessInstanceId());
		archiveBorrowDao.insert(borrowInfo);
	}

	@Override
	public ApplyArchiveBorrowVo getArchiveBorrowInfo(String borrowId) {
		// TODO Auto-generated method stub
		ArchiveBorrow borrowInfo = archiveBorrowDao.selectByPrimaryKey(borrowId);
		List<ArchiveBorrowDetail> detailList = archiveBorrowDetailDao.selectAllByBorrowId(borrowId);
		ApplyArchiveBorrowVo vo = new ApplyArchiveBorrowVo();
		vo.setBorrowInfo(borrowInfo);
		vo.setDetailList(detailList);
		return vo;
	}

	@Override
	public void commitApproveArchiveBorrowTask(String taskId, ApproveResultVo vo) throws Exception {
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
		//获取借阅任务ID
		String businessKey = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey();
		ArchiveBorrow borrowInfo = archiveBorrowDao.selectByPrimaryKey(businessKey);
		List<ArchiveBorrowDetail> detailList = archiveBorrowDetailDao.selectAllByBorrowId(businessKey);
		
		//保存任务处理结果信息
		String procId = Utils.get16UUID();
		TaskProcessResult taskProcessResult = new TaskProcessResult();
		taskProcessResult.setId(procId);
		taskProcessResult.setRunPathId(runpath.getId());
		taskProcessResult.setProcessResult(vo.getApproveResult());
		taskProcessResult.setComment(vo.getApproveComment()==null?"通过":vo.getApproveComment());
		taskProcessResultDao.insert(taskProcessResult);
		//审批拒绝则把库存信息回滚
		if(vo.getApproveResult().equals(TaskCommitType.LOAN_REJECT))
		{
			for(ArchiveBorrowDetail item : detailList)
			{
				ArchiveStore store = archiveStoreDao.selectOne(borrowInfo.getAppId(), item.getArchiveType(), item.getFileName());
				store.setRemainFileCnt(item.getBorrowCnt()+store.getRemainFileCnt());
				archiveStoreDao.updateByPrimaryKey(store);
			}
		}
		
		borrowInfo.setApproveResult(vo.getApproveResult());
		borrowInfo.setApproveComment(vo.getApproveComment());
		archiveBorrowDao.updateByPrimaryKey(borrowInfo);
		//完成当前任务
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("approveResult", vo.getApproveResult());
		runWorkflowService.completeTask(taskId, "", vars, CommandType.COMMIT);
	}

	@Override
	public void commitArchiveBorrowReturnTask(ApplyArchiveBorrowVo vo, String taskId) throws Exception {
		// TODO Auto-generated method stub
		//检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) 
		{
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}
		
		//更新借阅任务信息
		archiveBorrowDao.updateByPrimaryKey(vo.getBorrowInfo());
		//借阅信息归还库存
		for(ArchiveBorrowDetail item : vo.getDetailList())
		{
			ArchiveStore store = archiveStoreDao.selectOne(vo.getBorrowInfo().getAppId(), item.getArchiveType(), item.getFileName());
			store.setRemainFileCnt(item.getBorrowCnt()+store.getRemainFileCnt());
			archiveStoreDao.updateByPrimaryKey(store);
		}
		
		//完成任务
		runWorkflowService.completeTask(taskId, "", null, CommandType.COMMIT);		
	}

	@Override
	public void backArchiveBorrowTask(String taskId, String message) throws Exception {
		// TODO Auto-generated method stub
		//检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) 
		{
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}
		
		//获取借阅任务ID
		String businessKey = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey();
		ArchiveBorrow borrowInfo = archiveBorrowDao.selectByPrimaryKey(businessKey);
		List<ArchiveBorrowDetail> detailList = archiveBorrowDetailDao.selectAllByBorrowId(businessKey);
		
		//借阅信息归还库存
		for(ArchiveBorrowDetail item : detailList)
		{
			ArchiveStore store = archiveStoreDao.selectOne(borrowInfo.getAppId(), item.getArchiveType(), item.getFileName());
			store.setRemainFileCnt(item.getBorrowCnt()+store.getRemainFileCnt());
			archiveStoreDao.updateByPrimaryKey(store);
		}
		runWorkflowService.completeTask(taskId, message, null, CommandType.BACK);
	}

	@Override
	public void reApplyArchiveBorrow(String taskId, ApplyArchiveBorrowVo vo) throws Exception {
		// TODO Auto-generated method stub
		//检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) 
		{
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}
		
		//获取借阅任务ID
		String businessKey = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey();

		
		//更新借阅申请信息
		archiveBorrowDao.updateByPrimaryKey(vo.getBorrowInfo());
		
		//删除原借阅明细信息
		archiveBorrowDetailDao.deleteByBorrowId(vo.getBorrowInfo().getId());
		
		//保存明细信息
		for(ArchiveBorrowDetail item : vo.getDetailList())
		{
			if(item.getBorrowCnt()==null || item.getBorrowCnt()==0)
			{
				continue;
			}
			item.setId(Utils.get16UUID());
			item.setBorrowTaskId(businessKey);
			archiveBorrowDetailDao.insert(item);
			
			//再申请时减去库存，如果审批被拒绝则返回库存，避免后续申请没有多余库存
			ArchiveStore store = archiveStoreDao.selectOne(vo.getBorrowInfo().getAppId(), item.getArchiveType(),item.getFileName());
			store.setRemainFileCnt(store.getRemainFileCnt()-item.getBorrowCnt());
			archiveStoreDao.updateByPrimaryKey(store);
		}
		
		//完成任务
		runWorkflowService.completeTask(taskId, "", null, CommandType.COMMIT);
	}

}
