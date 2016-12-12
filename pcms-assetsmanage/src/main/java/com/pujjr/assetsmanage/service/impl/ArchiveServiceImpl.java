package com.pujjr.assetsmanage.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.assetsmanage.dao.ArchiveMapper;
import com.pujjr.assetsmanage.enumeration.CollectionTaskType;
import com.pujjr.assetsmanage.po.ArchiveDetailPo;
import com.pujjr.assetsmanage.po.ArchiveTaskPo;
import com.pujjr.assetsmanage.service.IArchiveService;
import com.pujjr.assetsmanage.vo.ArchiveDelayVo;
import com.pujjr.assetsmanage.vo.ArchiveLogVo;
import com.pujjr.assetsmanage.vo.ArchivePostVo;
import com.pujjr.assetsmanage.vo.ArchiveSupplyVo;
import com.pujjr.base.dao.ArchiveDetailMapper;
import com.pujjr.base.dao.ArchiveSupplyDetailMapper;
import com.pujjr.base.dao.ArchiveSupplyMapper;
import com.pujjr.base.dao.ArchiveTaskMapper;
import com.pujjr.base.domain.ArchiveDetail;
import com.pujjr.base.domain.ArchiveSupply;
import com.pujjr.base.domain.ArchiveSupplyDetail;
import com.pujjr.base.domain.ArchiveTask;
import com.pujjr.base.domain.SysDictData;
import com.pujjr.base.service.ISequenceService;
import com.pujjr.base.service.ISysDictService;
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.jbpm.vo.ProcessGlobalVariable;
import com.pujjr.postloan.enumeration.ArchiveStatus;
import com.pujjr.postloan.enumeration.ArchiveType;
import com.pujjr.utils.Utils;

@Service
@Transactional
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
		
		//更新任务信息
		for(ArchiveDetailPo item : po.getDetailList())
		{
			archiveDetailDao.updateByPrimaryKey(item);
		}
		ArchiveTask archiveTask = archiveTaskDao.selectByPrimaryKey(po.getId());
		archiveTask.setArchiveStatus(ArchiveStatus.Archived.getName());
		archiveTask.setProcessTime(new Date());
		archiveTask.setProcessId(operId);
		archiveTaskDao.updateByPrimaryKey(archiveTask);
		
		//提交任务
		runWorkflowService.completeTask(taskId,"",null,CommandType.COMMIT);
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
		//启动归档任务流程
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("appId", appId);
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, operId);
		ProcessInstance instance = runWorkflowService.startProcess("CSGD", archiveTask.getId(), vars);
		
		archiveTask.setProcInstId(instance.getProcessInstanceId());
		archiveTaskDao.insert(archiveTask);
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
		
		//启动任务
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("appId", appId);
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, operId);
		ProcessInstance instance = runWorkflowService.startProcess("ZDFQDAGD", archiveTask.getId(), vars);
		
		archiveTask.setProcInstId(instance.getProcessInstanceId());
		archiveTaskDao.insert(archiveTask);
	}

	@Override
	public void applyArchiveSupply(ArchiveSupplyVo vo, String taskId,String operId) throws Exception {
		// TODO Auto-generated method stub
		//检查任务合法性
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) 
		{
			throw new Exception("提交任务失败,任务ID" + taskId + "对应任务不存在 ");
		}		
		
		//更新归档信息
		for(ArchiveDetailPo item : vo.getArchiveTask().getDetailList())
		{
			archiveDetailDao.updateByPrimaryKey(item);
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
		
		//提交任务
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("isArchiveSupply", true);
		//runWorkflowService.completeTask(taskId,"",vars,CommandType.COMMIT);
	}

}
