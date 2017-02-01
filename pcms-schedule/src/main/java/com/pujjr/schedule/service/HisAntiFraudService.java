package com.pujjr.schedule.service;

import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.pujjr.carcredit.dao.FraudHisResultMapper;
import com.pujjr.carcredit.domain.FraudHisResult;
import com.pujjr.carcredit.po.QueryParamToDoTaskPo;
import com.pujjr.carcredit.po.ToDoTaskPo;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.service.ITaskService;
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.domain.WorkflowRunPath;
import com.pujjr.jbpm.service.IRunPathService;
import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.utils.PuspSocketClient;
import com.pujjr.utils.Utils;


/**
 * 历史数据反欺诈服务，每个线程处理对应分配虚拟柜员的申请单，处理完成后提交任务进入下一个环节
 * @author 150032
 *
 */
public class HisAntiFraudService 
{
	@Autowired
	private ITaskService taskService;
	@Autowired
	private IRunWorkflowService  runWorkflowService;
	@Autowired
	private IRunPathService runPathService;
	@Autowired
	private IApplyService applyService;
	@Autowired
	private TaskService actTaskService;
	@Autowired
	private FraudHisResultMapper fraudHisResultDao;
	private ExecutorService executor;
	 //最大线程池数量
	private int maxThreadPoolSize;
	//最大处理数量
	private int maxThreadProcessSize;
	//扫描间隔时间(毫秒)
	private long scanIntervalTime;
	public ExecutorService getExecutor() {
		return executor;
	}
	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}
	public int getMaxThreadPoolSize() {
		return maxThreadPoolSize;
	}
	public void setMaxThreadPoolSize(int maxThreadPoolSize) {
		this.maxThreadPoolSize = maxThreadPoolSize;
	}
	public int getMaxThreadProcessSize() {
		return maxThreadProcessSize;
	}
	public void setMaxThreadProcessSize(int maxThreadProcessSize) {
		this.maxThreadProcessSize = maxThreadProcessSize;
	}
	public long getScanIntervalTime() {
		return scanIntervalTime;
	}
	public void setScanIntervalTime(long scanIntervalTime) {
		this.scanIntervalTime = scanIntervalTime;
	}
	@PostConstruct
	private void startService()
	{
		executor = Executors.newFixedThreadPool(maxThreadPoolSize);
		for(int i=0;i<maxThreadProcessSize;i++)
		{
			HisAntiFraudThread processor=new HisAntiFraudThread("VMF000"+(i+1));
			executor.execute(processor);
		}		
	}
	@PreDestroy
	private void stopService()
	{
		executor.shutdownNow();
	}
	private class HisAntiFraudThread implements Runnable
	{
		private String vmId;
		public HisAntiFraudThread(String vmId)
		{
			this.vmId=vmId;
		}
		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			while(!Thread.currentThread().isInterrupted())
			{
				try
				{
					System.out.println(this.vmId);
					QueryParamToDoTaskPo queryParam = new QueryParamToDoTaskPo();
					queryParam.setAssignee(vmId);
					List<ToDoTaskPo> todoList = taskService.getToDoTaskList(queryParam);
					for(ToDoTaskPo item : todoList)
					{
						Task task =  actTaskService.createTaskQuery().taskId(item.getTaskId()).singleResult();
						if(task == null)
						{
							throw new Exception("提交任务失败,任务ID"+item.getTaskId()+"对应任务不存在 ");
						}
						WorkflowRunPath runpath = runPathService.getFarestRunPathByActId(task.getProcessInstanceId(), task.getTaskDefinitionKey());
						if(runpath == null)
						{
							throw new Exception("提交任务失败,任务ID"+item.getTaskId()+"对应路径不存在 ");
						}
						//获取上级人工任务节点名称
						WorkflowRunPath parentUserTaskRunPath = runPathService.getRunPathById(runpath.getParentUsertaskPathId());
						PuspSocketClient sc = new PuspSocketClient();
						Socket sk = sc.doConnect("172.18.10.41", 5000);
						String rsp = "[]";
						//查询申请提交后反欺诈信息
						if("lrsqd".equals(parentUserTaskRunPath.getActId()))
						{
							rsp = sc.doSend(sk,"{\"tranCode\":\"10001\",\"appId\":\""+item.getBusinessKey()+"\"}");
							rsp = StringUtils.trim(rsp);
							System.out.println("rsp="+rsp);
						}
						//查询审核提交后反欺诈
						if("zlsh".equals(parentUserTaskRunPath.getActId()))
						{
							rsp = sc.doSend(sk,"{\"tranCode\":\"10003\",\"appId\":\""+item.getBusinessKey()+"\"}");
							rsp = StringUtils.trim(rsp);
							System.out.println("rsp="+rsp);
						}
						//查询签约提交后反欺诈
						if("jxsqy".equals(parentUserTaskRunPath.getActId()))
						{
							rsp = sc.doSend(sk,"{\"tranCode\":\"10004\",\"appId\":\""+item.getBusinessKey()+"\"}");
							rsp = StringUtils.trim(rsp);
							System.out.println("rsp="+rsp);
							
						}
						//查询放款复核提交后反欺诈
						if("fkfh".equals(parentUserTaskRunPath.getActId()))
						{
							rsp = sc.doSend(sk,"{\"tranCode\":\"10005\",\"appId\":\""+item.getBusinessKey()+"\"}");
							rsp = StringUtils.trim(rsp);
							System.out.println("rsp="+rsp);
							
						}
						List<FraudHisResult> hisAntiList = JSON.parseArray(rsp, FraudHisResult.class) ;
						fraudHisResultDao.deleteByAppId(item.getBusinessKey(),parentUserTaskRunPath.getActId());
						int seq = 1;
						for(FraudHisResult result : hisAntiList)
						{
							result.setId(Utils.get16UUID());
							result.setSeq(seq);
							result.setTaskNodeName(parentUserTaskRunPath.getActId());
							fraudHisResultDao.insert(result);
							seq++;
						}
						//提交任务至下一节点
						runWorkflowService.completeTask(item.getTaskId(), "",null, CommandType.COMMIT);
					}
					Thread.sleep(scanIntervalTime*1000);
				}
				catch(InterruptedException e)
				{
					Thread.currentThread().interrupt();
				}
				catch(Exception e)
				{
					e.printStackTrace();					
				}
				
			}
		}
	}
}


