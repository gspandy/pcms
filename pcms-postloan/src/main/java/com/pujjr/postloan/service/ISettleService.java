package com.pujjr.postloan.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.postloan.domain.ApplySettle;
import com.pujjr.postloan.vo.ApplySettleVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.RemissionFeeItemVo;
import com.pujjr.postloan.vo.SettleFeeItemVo;
import com.pujjr.postloan.vo.SettleTaskVo;

/**
 * 结清处理服务
 * **/
@Service
public interface ISettleService 
{
	/**功能：查询指定客户提前结清应还项
	 * tom 2016年11月14日
	 * @param appId 申请单号
	 * @param settleEeffectDate 有效截止日期
	 * @return 提前结清各项费用金额
	 */
	public SettleFeeItemVo getAllSettleFeeItem(String appId,Date settleEffectDate);

	/**
	 *功能：查询指定客户部分提前结清应还项
	 * @param appId 申请单号
	 * @param beginPeriod 开始期数
	 * @param endPeriod 截止期数
	 * @param settleEeffectDate 有效截止日期
	 * @param settleCapital 结清本金
	 * @return
	 */
	public SettleFeeItemVo getPartSettleFeeItem(String appId,int beginPeriod,int endPeriod,Date settleEeffectDate);
	/**
	 * 功能：提交提前结清申请
	 * 参数：
	 * 	appId-提前结清申请单编号
	 * 	vo-提前结清申请要素
	 * 返回：无
	 * **/
	public void commitApplySettleTask(String operId,String appId,ApplySettleVo vo);
	/**
	 * 功能：提交审批
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-审批结果
	 * **/
	public void commitApproveSettleTask(String taskId,ApproveResultVo vo);
	/**
	 * 功能：提交减免审批
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-审批结果
	 * **/
	public void commitApproveRemissionTask(String taskId,ApproveResultVo vo);
	/**
	 * 功能：提交确认结清申请
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-减免费项
	 * **/
	public void commitApplyConfirmSettleTask(String taskId,RemissionFeeItemVo vo);
	/**
	 * 功能：提交确认入账
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-审批结果
	 * **/
	public void commitConfirmSettleTask(String taskId,ApproveResultVo vo);
	/**
	 * 功能：取消结清任务
	 * 参数：
	 * 	taskId-任务ID
	 * 	operId-取消操作者
	 *  cancelComment-取消备注
	 * **/
	public void cancelSettleTask(String taskId,String operId,String cancelComment);
	/**
	 * 功能：查询提前结清任务列表
	 * 参数：
	 * createId-任务创建人
	 * settleType-结清类型
	 * applyStatus-申请状态	
	 * **/
	public List<SettleTaskVo> getApplySettleTaskList(String createId,String settleType,List<String> applyStatus);
	/**
	 * 功能：根据主键查询提前结清申请信息
	 * **/
	public ApplySettle getApplySettleTaskById(String id);
	/**
	 * 功能：修改提前结清申请信息
	 * **/
	public void modifyApplySettleInfo(ApplySettle record);
	/**
	 * 功能：删除提前结清申请信息
	 * **/
	public void deleteApplySettleInfoById(String id);
	
}
