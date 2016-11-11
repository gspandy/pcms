package com.pujjr.postloan.service;

import java.util.List;

import com.pujjr.postloan.domain.ApplyRemission;
import com.pujjr.postloan.vo.ApplyRemissionVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.RemissionTaskVo;
import com.pujjr.postloan.vo.RepayFeeItemVo;


/**
 * 减免处理服务
 * **/
public interface IRemissionService 
{/**
	 * 功能：查询指定客户应还项
	 * 参数：
	 * 	appId-申请单号
	 * 返回：当前应还各项费用金额
	 * **/
	public RepayFeeItemVo getRemissionRepayFeeItem(String appId);
	/**
	 * 功能：提交减免申请
	 * 参数：
	 * 	appId-减免申请单编号
	 * 	vo-减免申请要素
	 * 返回：无
	 * **/
	public void commitApplyRemissionTask(String appId,ApplyRemissionVo vo);
	/**
	 * 功能：提交审批
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-审批结果
	 * **/
	public void commitApproveRemissionTask(String taskId,ApproveResultVo vo);
	/**
	 * 功能：取消减免任务
	 * 参数：
	 * 	taskId-任务ID
	 * 	operId-取消操作者
	 *  cancelComment-取消备注
	 * **/
	public void cancelRemissionTask(String taskId,String operId,String cancelComment);
	/**
	 * 功能：查询减免任务列表
	 * 参数：
	 * createId-任务创建人
	 * applyStatus-申请状态	
	 * **/
	public List<RemissionTaskVo> getApplyRemissionTaskList(String createId,List<String> applyStatus);
	/**
	 * 功能：根据主键查询减免申请信息
	 * **/
	public ApplyRemission getApplyRemissionTaskById(String id);
	/**
	 * 功能：修改减免申请信息
	 * **/
	public void modifyApplyRemissionInfo(ApplyRemission record);
	/**
	 * 功能：删除减免申请信息
	 * **/
	public void deleteApplyRemissionInfoById(String id);
}
