package com.pujjr.postloan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.postloan.domain.ApplyRefund;
import com.pujjr.postloan.vo.ApplyRefundVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.RefundTaskVo;
import com.pujjr.postloan.vo.RepayFeeItemVo;


/**
 * 退款处理服务
 * **/
@Service
public interface IRefundService 
{
	/**
	 * 功能：查询挂账费项信息
	 * @param appId 申请单编号
	 * @return
	 */
	public RepayFeeItemVo getRefundFeeItem(String appId);
	/**
	 * 功能：提交退款申请
	 * 参数：
	 * 	appId-退款申请单编号
	 * 	vo-退款申请要素
	 *  operId-任务申请人
	 * 返回：无
	 * @throws Exception 
	 * **/
	public void commitApplyRefundTask(String appId,ApplyRefundVo vo,String operId) throws Exception;
	/**
	 * 功能：提交审批
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-审批结果
	 * @throws Exception 
	 * **/
	public void commitApproveRefundTask(String taskId,ApproveResultVo vo) throws Exception;
	/**功能：
	 * 提交财务确认退款
	 * @param taskId
	 * @throws Exception 
	 */
	public void commitConfirmRefundTask(String taskId) throws Exception;
	/**
	 * 功能：取消退款任务
	 * 参数：
	 * 	taskId-任务ID
	 * 	operId-取消操作者
	 *  cancelComment-取消备注
	 * **/
	public void cancelRefundTask(String taskId,String operId,String cancelComment);
	/**
	 * 功能：查询退款任务列表
	 * 参数：
	 * createId-任务创建人
	 * applyStatus-申请状态	
	 * **/
	public List<RefundTaskVo> getApplyRefundTaskList(String createId,List<String> applyStatus);
	/**
	 * 功能：根据主键查询退款申请信息
	 * **/
	public ApplyRefund getApplyRefundTaskById(String id);
	/**
	 * 功能：修改退款申请信息
	 * **/
	public void modifyApplyRefundInfo(ApplyRefund record);
	/**
	 * 功能：删除退款申请信息
	 * **/
	public void deleteApplyRefundInfoById(String id);
}
