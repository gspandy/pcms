package com.pujjr.carcredit.exthandle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.domain.SysParam;
import com.pujjr.base.domain.SysWorkgroup;
import com.pujjr.base.service.ISysBranchService;
import com.pujjr.base.service.ISysParamService;
import com.pujjr.base.service.ISysWorkgroupService;
import com.pujjr.carcredit.bo.ProcessTaskUserBo;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.service.ITaskService;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.jbpm.core.handle.ITaskAssigneeHandle;
import com.pujjr.jbpm.domain.ActRuTask;
import com.pujjr.jbpm.service.IRunWorkflowService;
/**
 * 反欺诈处理用户分单，获取反欺诈工作组下当前处理单数最小的虚拟柜员，然后分配给此用户
 * **/
@Service("AssigneeFraud")
public class AssigneeFraud implements ITaskAssigneeHandle {
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IApplyService applyService;
	@Autowired
	private ITaskService taskService;
	@Autowired
	private ISysBranchService sysBranchService;
	@Autowired
	private ISysParamService sysParamService;
	@Autowired
	private ISysWorkgroupService workgroupService;
	@Autowired
	private IRunWorkflowService runWorkflowService;

	@Override
	public String handle(String assigneeParam,TaskEntity taskEntity) {
		// TODO Auto-generated method stub
		SysWorkgroup wg = workgroupService.getWorkgroupById(assigneeParam);
		//设置一个最大值
		int curTaskCnt = 1000000;
		//查找工作组下人员信息
		List<SysAccount> acctList = workgroupService.getWorkgroupSysAccounts(wg.getId());
		String assigneeId = "";
		for(SysAccount item : acctList)
		{
			List<ActRuTask> runTask = runWorkflowService.getRunningTaskList(item.getAccountId());
			//如果没有待处理任务，直接分配给此用户
			if(runTask.size() == 0 )
			{
				assigneeId = item.getAccountId();
				break;
			}
			else
			{
				if(runTask.size()< curTaskCnt)
				{
					assigneeId = item.getAccountId();
					curTaskCnt = runTask.size();
				}
			}
		}
		return assigneeId;
	}

}
