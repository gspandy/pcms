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

import com.pujjr.assetsmanage.dao.TelIncomeLogMapper;
import com.pujjr.assetsmanage.dao.TelInterviewMapper;
import com.pujjr.assetsmanage.domain.TelIncomeLog;
import com.pujjr.assetsmanage.domain.TelInterview;
import com.pujjr.assetsmanage.service.ITelInterviewService;
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.domain.WorkflowRunPath;
import com.pujjr.jbpm.service.IRunPathService;
import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.jbpm.vo.ProcessGlobalVariable;
import com.pujjr.utils.Utils;
@Service
@Transactional
public class TelInterviewServiceImpl implements ITelInterviewService {

	@Autowired
	private TelInterviewMapper telInterviewDao;
	@Autowired
	private IRunWorkflowService runWorkflowService;
	@Autowired
	private IRunPathService runPathService;
	@Autowired
	private TaskService actTaskService;
	@Autowired
	private TelIncomeLogMapper telIncomeLogDao;
	
	@Override
	public void addTelInterviewResult(String appId,TelInterview vo, String operId) throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		vo.setId(Utils.get16UUID());
		vo.setAppId(appId);
		vo.setCreateId(operId);
		vo.setCreateTime(new Date());
		telInterviewDao.insert(vo);
	}

	@Override
	public void createTelInterviewTask(String appId,String operId) {
		// TODO Auto-generated method stub
		//启动流程
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("appId", appId);
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, operId);
		ProcessInstance instance = runWorkflowService.startProcess("DHHF", "", vars);
	}

	@Override
	public List<TelInterview> getTelInterviewHisList(String appId) {
		// TODO Auto-generated method stub
		return telInterviewDao.selectTelInterviewHisList(appId);
	}

	@Override
	public void commmitTelInterviewTask(String taskId) throws Exception {
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
		
		//完成当前任务
		runWorkflowService.completeTask(taskId, "", null, CommandType.COMMIT);
	}

	@Override
	public void addTelIncomeLog(TelIncomeLog po) {
		// TODO Auto-generated method stub
		telIncomeLogDao.insert(po);
	}

	@Override
	public List<HashMap<String, Object>> getTelIncomeLogList(String appId) {
		// TODO Auto-generated method stub
		return telIncomeLogDao.selectTelIncomeLogList(appId);
	}

	@Override
	public void modifyTelIncomeLogInfo(TelIncomeLog po) {
		// TODO Auto-generated method stub
		telIncomeLogDao.updateByPrimaryKey(po);
	}

}
