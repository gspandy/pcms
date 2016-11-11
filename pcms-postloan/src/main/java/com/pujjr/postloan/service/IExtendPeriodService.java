package com.pujjr.postloan.service;

import java.util.List;

import com.pujjr.postloan.domain.ApplyExtendPeriod;
import com.pujjr.postloan.vo.ApplyExtendPeriodVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.ExtendPeriodFeeItemVo;
import com.pujjr.postloan.vo.ExtendPeriodTaskVo;
import com.pujjr.postloan.vo.RemissionFeeItemVo;

/**
 * 展期处理服务
 * **/
public interface IExtendPeriodService 
{
	/**
	 * 功能：查询指定客户展期应还项
	 * 参数：
	 * 	appId-申请单号
	 * 返回：展期各项费用金额
	 * **/
	public ExtendPeriodFeeItemVo getExtendPeriodFeeItem(String appId);
	/**
	 * 功能：提交展期申请
	 * 参数：
	 * 	appId-展期申请单编号
	 * 	vo-展期申请要素
	 * 返回：无
	 * **/
	public void commitApplyExtendPeriodTask(String appId,ApplyExtendPeriodVo vo);
	/**
	 * 功能：提交审批
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-审批结果
	 * **/
	public void commitApproveExtendPeriodTask(String taskId,ApproveResultVo vo);
	/**
	 * 功能：提交减免审批
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-审批结果
	 * **/
	public void commitApproveRemissionTask(String taskId,ApproveResultVo vo);
	/**
	 * 功能：提交确认展期申请
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-减免费项
	 * **/
	public void commitApplyConfirmExtendPeriodTask(String taskId,RemissionFeeItemVo vo);
	/**
	 * 功能：提交确认入账
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-审批结果
	 * **/
	public void commitConfirmExtendPeriodTask(String taskId,ApproveResultVo vo);
	/**
	 * 功能：取消结清任务
	 * 参数：
	 * 	taskId-任务ID
	 * 	operId-取消操作者
	 *  cancelComment-取消备注
	 * **/
	public void cancelExtendPeriodTask(String taskId,String operId,String cancelComment);
	/**
	 * 功能：查询展期任务列表
	 * 参数：
	 * createId-任务创建人
	 * applyStatus-申请状态	
	 * **/
	public List<ExtendPeriodTaskVo> getApplyExtendPeriodTaskList(String createId,List<String> applyStatus);
	/**
	 * 功能：根据主键查询展期申请信息
	 * **/
	public ApplyExtendPeriod getApplyExtendPeriodTaskById(String id);
	/**
	 * 功能：修改展期申请信息
	 * **/
	public void modifyApplyExtendPeriodInfo(ApplyExtendPeriod record);
	/**
	 * 功能：删除展期申请信息
	 * **/
	public void deleteApplyExtendPeriodInfoById(String id);
}
