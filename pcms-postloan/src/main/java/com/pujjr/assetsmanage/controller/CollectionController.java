package com.pujjr.assetsmanage.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.assetsmanage.domain.CollectionLog;
import com.pujjr.assetsmanage.domain.CollectionTask;
import com.pujjr.assetsmanage.enumeration.CollectionTaskType;
import com.pujjr.assetsmanage.service.ICollectionService;
import com.pujjr.assetsmanage.vo.CollectionApplyVo;
import com.pujjr.assetsmanage.vo.CollectionLogVo;
import com.pujjr.base.controller.BaseController;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.carcredit.po.OnlineAcctPo;
import com.pujjr.jbpm.vo.ProcessGlobalVariable;
import com.pujjr.postloan.vo.ApproveResultVo;

@RestController
@RequestMapping(value="/collection")
public class CollectionController extends BaseController 
{
	@Autowired
	private ICollectionService collectionService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService actTaskService;

	@RequestMapping(value="/createPhoneCollectionTask/{appId}/{applyComment}",method=RequestMethod.POST)
	public void createPhoneCollectionTask(@PathVariable String appId,@PathVariable String applyComment,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		collectionService.createPhoneCollectionTask(account.getAccountId(), appId, applyComment);
	}
	@RequestMapping(value="/applyNewCollectionTask/{taskId}/{appId}/{taskType}",method=RequestMethod.POST)
	public void applyNewCollectionTask(@PathVariable String taskId,@PathVariable String appId,@PathVariable String taskType,@RequestBody CollectionApplyVo vo,HttpServletRequest request) throws Exception
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		if(taskType.equals(CollectionTaskType.OutCollection.getName()))
		{
			collectionService.applyNewCollectionTask(taskId, appId, CollectionTaskType.OutCollection, account.getAccountId(), vo);
		}
		if(taskType.equals(CollectionTaskType.RecoverCollection.getName()))
		{
			collectionService.applyNewCollectionTask(taskId, appId, CollectionTaskType.RecoverCollection, account.getAccountId(), vo);
		}
		if(taskType.equals(CollectionTaskType.RefundCollection.getName()))
		{
			collectionService.applyNewCollectionTask(taskId, appId, CollectionTaskType.RefundCollection, account.getAccountId(), vo);
		}
		if(taskType.equals(CollectionTaskType.DisposeCollection.getName()))
		{
			collectionService.applyNewCollectionTask(taskId, appId, CollectionTaskType.DisposeCollection, account.getAccountId(), vo);
		}
		if(taskType.equals(CollectionTaskType.VisitCollection.getName()))
		{
			collectionService.applyNewCollectionTask(taskId, appId, CollectionTaskType.VisitCollection, account.getAccountId(), vo);
		}
		if(taskType.equals(CollectionTaskType.LawsuitCollection.getName()))
		{
			collectionService.applyNewCollectionTask(taskId, appId, CollectionTaskType.LawsuitCollection, account.getAccountId(), vo);
		}
	}
	
	@RequestMapping(value="/reassigneeTaskOperId/{taskId}/{newOperId}",method=RequestMethod.POST)
	public void reassigneeTaskOperId(@PathVariable String taskId,@PathVariable String newOperId) throws Exception 
	{
		collectionService.reassigneeTaskOperId(taskId, newOperId);
	}
	
	@RequestMapping(value="/getCollectionWorkgroupUserIdList/{taskId}",method=RequestMethod.GET)
	public List<OnlineAcctPo> getCollectionWorkgroupUserIdList(@PathVariable String taskId) throws Exception
	{
		return collectionService.getCollectionWorkgroupUserIdList(taskId);
	}
	
	@RequestMapping(value="/commitApproveApplyNewCollectionTask/{taskId}",method=RequestMethod.POST)
	public void commitApproveApplyNewCollectionTask(@PathVariable String taskId,@RequestBody ApproveResultVo vo) throws Exception
	{
		collectionService.commitApproveApplyNewCollectionTask(taskId, vo);
	}
	
	@RequestMapping(value="/commitSettleLawsuitTask/{taskId}",method=RequestMethod.POST)
	public void commitSettleLawsuitTask(@PathVariable String taskId) throws Exception 
	{
		collectionService.commitSettleLawsuitTask(taskId);
	}
	
	@RequestMapping(value="/getCollectionAppyInfo/{applyId}",method=RequestMethod.GET)
	public CollectionApplyVo getCollectionAppyInfo(@PathVariable String applyId)
	{
		return collectionService.getCollectionAppyInfo(applyId);
	}
	
	@RequestMapping(value="/getNewCollectionAppyInfo/{taskId}",method=RequestMethod.GET)
	public CollectionApplyVo getNewCollectionAppyInfo(@PathVariable String taskId)
	{
		//获取申请的新催收方式申请ID
		Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();
		String newAppId = runtimeService.getVariable(task.getExecutionId(),"newApplyId").toString();
		return collectionService.getCollectionAppyInfo(newAppId);
	}
	
	@RequestMapping(value="/getCollectionLogInfo/{appId}/{taskType}",method=RequestMethod.GET)
	public List<CollectionLog> getCollectionLogInfo(@PathVariable String appId,@PathVariable String taskType)
	{
		return collectionService.getCollectionLogInfo(appId, taskType);
	}
	
	@RequestMapping(value="/getImportanCollectionLogInfo/{appId}",method=RequestMethod.GET)
	List<CollectionLogVo> getImportanCollectionLogInfo(@PathVariable String appId)
	{
		return collectionService.getImportanCollectionLogInfo(appId);
	}
	
	@RequestMapping(value="/saveCollectionLog/{taskId}",method=RequestMethod.POST)
	public void saveCollectionLog(@PathVariable String taskId,@RequestBody CollectionLogVo vo) throws Exception
	{
		collectionService.saveCollectionLog(taskId, vo);
	}
	
	@RequestMapping(value="/applyRecoverCollectionTask/{appId}",method=RequestMethod.POST)
	public void applyRecoverCollectionTask(@PathVariable String appId, @RequestBody CollectionApplyVo vo,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		collectionService.applyRecoverCollectionTask(appId, account.getAccountId(), vo);
	}
	
	@RequestMapping(value="/commitApproveRecoverCollectionTask/{taskId}",method=RequestMethod.POST)
	public void commitApproveRecoverCollectionTask(@PathVariable String taskId,@RequestBody ApproveResultVo vo) throws Exception
	{
		collectionService.commitApproveRecoverCollectionTask(taskId, vo);
	}
	
	@RequestMapping(value="/applyReAssigneeTask/{taskId}",method=RequestMethod.POST)
	public void applyReAssigneeTask(@PathVariable String taskId,@RequestBody String reason) throws Exception
	{
		collectionService.applyReAssigneeTask(taskId, reason);
	}
	
	@RequestMapping(value="/getCollectionTaskCnt/{appId}",method=RequestMethod.GET)
	public HashMap<String,Object> getCollectionTaskCnt(@PathVariable String appId)
	{
		return collectionService.getCollectionTaskCnt(appId);
	}
	
	@RequestMapping(value="/getCollectionApplyTask/{appId}",method=RequestMethod.GET)
	public List<CollectionTask> getCollectionApplyTask(@PathVariable String appId)
	{
		return collectionService.getCollectionApplyTask(appId);
	}
	
	@RequestMapping(value="/commitSettleApprove/{taskId}",method=RequestMethod.POST)
	public void commitSettleApprove(String taskId) throws Exception
	{
		collectionService.commitSettleApprove(taskId);
	}
}