package com.pujjr.assetsmanage.exthandle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.assetsmanage.dao.CollectionTaskMapper;
import com.pujjr.assetsmanage.domain.CollectionTask;
import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.domain.SysParam;
import com.pujjr.base.domain.SysWorkgroup;
import com.pujjr.base.service.ISysBranchService;
import com.pujjr.base.service.ISysParamService;
import com.pujjr.base.service.ISysWorkgroupService;
import com.pujjr.carcredit.bo.ProcessTaskUserBo;
import com.pujjr.carcredit.dao.TaskMapper;
import com.pujjr.carcredit.domain.Apply;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.service.ITaskService;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.jbpm.core.handle.ITaskAssigneeHandle;
import com.pujjr.postloan.dao.GeneralLedgerMapper;
import com.pujjr.postloan.domain.GeneralLedger;
/**
 * 审批新增催收任务分单任务按照产品编码、经销商、逾期天数、任务类型进行任务分单
 * **/
@Service("ApproveNewCollection")
public class ApproveNewCollection implements ITaskAssigneeHandle {

	@Autowired
	private ISysWorkgroupService workgroupService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IApplyService applyService;
	@Autowired
	private ISysBranchService sysBranchService;
	@Autowired
	private ISysParamService sysParamService;
	@Autowired
	private TaskMapper taskDao;
	@Autowired
	private CollectionTaskMapper collTaskDao;
	@Autowired
	private GeneralLedgerMapper ledgerDao;
	@Override
	public String handle(String assigneeParam, TaskEntity taskEntity) {
		// TODO Auto-generated method stub
		String businessKey = runtimeService.getVariable(taskEntity.getProcessInstanceId(), "newApplyId").toString();
		CollectionTask collTaskPo  = collTaskDao.selectByPrimaryKey(businessKey);
		//获取经销商ID
		Apply apply = applyService.getApply(collTaskPo.getAppId());
		//获取逾期天数
		GeneralLedger ledgerPo = ledgerDao.selectByAppId(collTaskPo.getAppId());
		SysBranch sysBranch = sysBranchService.getSysBranch(null, apply.getCreateBranchCode());
		ProcessTaskUserBo assignee = this.getProcessTaskAccount(apply.getProductCode(),sysBranch.getId(), ledgerPo.getAddupOverdueDay() ,collTaskPo.getTaskType(),assigneeParam, null);
		//如果未找到任务执行者，则分配给系统默认的执行
		if(assignee != null)
		{
			runtimeService.setVariable(taskEntity.getExecutionId(), "batchAssigneeWorkgroupId", assignee.getWorkgroupId());
			runtimeService.setVariable(taskEntity.getExecutionId(), "batchAssigneeAccountId", assignee.getAccountId());
		}
		else
		{
			SysParam sysParam = sysParamService.getSysParamByParamName("noAssigneeTaskProcessAccountId");
			return sysParam.getParamValue();
		}
		return assignee.getAccountId();
	}
	
	private ProcessTaskUserBo getProcessTaskAccount(String productCode,String dealerId,int overdueDay,String taskType,String workgroupId,List<String> candidateAccounts) {
		// TODO Auto-generated method stub
		ProcessTaskUserBo bo = new ProcessTaskUserBo();
		//1、获取工作组及其子工作组
		List<SysWorkgroup> listGroup = workgroupService.getChildWorkgroup(workgroupId,true);
		//2、获取满足任务的用户，如果有待选用户则从待选中过滤用户
		List<HashMap> listMatch = workgroupService.getCollectionDeployTaskMatchRuleAccountList(productCode, dealerId, overdueDay, taskType, listGroup, candidateAccounts);
		if(listMatch.size()==0)
		{
			return null;
		}
		else
		{
			//3、获取满足执行条件用户当前任务数及可支配最大任务数
			List<String> listMatchRuleTaskCntId = new ArrayList<String>();
			String assignee=null;
			String assigneeWorkgroupId = null;
			int hasRemainCnt = 0;
			for(HashMap item :listMatch)
			{
				listMatchRuleTaskCntId.add(item.get("taskCntRuleId").toString());
			}
			List<HashMap> matchAccountTaskCntInfo = taskDao.selectTaskAssignCntInfo(listMatchRuleTaskCntId);
			for(HashMap l : matchAccountTaskCntInfo)
			{
				int curTaskCnt = Integer.valueOf(l.get("curTaskCnt").toString());
				int maxTaskCnt = Integer.valueOf(l.get("maxTaskCnt").toString());
				if((maxTaskCnt-curTaskCnt)>hasRemainCnt)
				{
					hasRemainCnt = maxTaskCnt-curTaskCnt;
					assignee = l.get("assignee").toString();
					assigneeWorkgroupId = l.get("workgroupId").toString();
				}
				
			}
			if(assignee == null)
			{
				return null;
			}
			else
			{
				bo.setAccountId(assignee);
				bo.setWorkgroupId(assigneeWorkgroupId);
			}
			return bo;
		}
	}

}
