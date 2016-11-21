package com.pujjr.postloan.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.postloan.domain.ApplyAlterRepayDate;
import com.pujjr.postloan.vo.AlterRepayDateFeeItemVo;
import com.pujjr.postloan.vo.AlterRepayDateTaskVo;
import com.pujjr.postloan.vo.ApplyAlterRepayDateVo;
import com.pujjr.postloan.vo.ApproveResultVo;


/**
 * 变更还款日处理服务
 * **/
@Service
public interface IAlterRepayDateService 
{
	/**
	 * 功能：查询指定客户变更还款日应还项
	 * 参数：
	 * 	appId-申请单号
	 * 返回：变更还款日各项费用金额
	 * **/
	public AlterRepayDateFeeItemVo getAlterRepayDateFeeItem(String appId,Date oldClosingDate,Date newClosingDate);
	/**
	 * 功能：提交变更申请
	 * 参数：
	 * 	appId-变更申请单编号
	 * 	vo-变更申请要素
	 * 返回：无
	 * **/
	public void commitApplyAlterRepayDateTask(String appId,ApplyAlterRepayDateVo vo,String operId);
	/**
	 * 功能：提交审批
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-审批结果
	 * @throws Exception 
	 * **/
	public void commitApproveAlterRepayDateTask(String taskId,ApproveResultVo vo) throws Exception;
	/**
	 * 功能：提交确认入账
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-审批结果
	 * @throws Exception 
	 * **/
	public void commitConfirmAlterRepayDateTask(String taskId) throws Exception;
	/**
	 * 功能：取消结清任务
	 * 参数：
	 * 	taskId-任务ID
	 * 	operId-取消操作者
	 *  cancelComment-取消备注
	 * **/
	public void cancelAlterRepayDateTask(String taskId,String operId,String cancelComment);
	/**
	 * 功能：查询变更还款日任务列表
	 * 参数：
	 * createId-任务创建人
	 * applyStatus-申请状态	
	 * **/
	public List<AlterRepayDateTaskVo> getApplyAlterRepayDateTaskList(String createId,List<String> applyStatus);
	/**
	 * 功能：根据主键查询变更还款日申请信息
	 * **/
	public ApplyAlterRepayDate getApplyAlterRepayDateTaskById(String id);
	/**
	 * 功能：修改变更还款日申请信息
	 * **/
	public void modifyApplyAlterRepayDateInfo(ApplyAlterRepayDate record);
	/**
	 * 功能：删除变更还款日申请信息
	 * **/
	public void deleteApplyAlterRepayDateInfoById(String id);
}
