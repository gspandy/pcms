package com.pujjr.postloan.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.postloan.domain.ApplySettle;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.vo.ApplySettleVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.RemissionFeeItemVo;
import com.pujjr.postloan.vo.SettleFeeItemVo;
import com.pujjr.postloan.vo.SettleTaskVo;
import com.pujjr.utils.Utils;

/**
 * 结清处理服务
 * **/
@Service
@Transactional
public interface ISettleService 
{
	
	/**查询结清后还款计划（还款计划表未刷新）
	 * tom 2016年11月25日
	 * @param appId 申请单号
	 * @param settleCapital 结清本金
	 * @param period 结清期数
	 * @param applyEndDate 申请有效截止日期
	 * @return
	 * @throws ParseException 
	 */
	public List<RepayPlan> getRefreshRepayPlan(String appId,double settleCapital,int period,String applyEndDate) throws ParseException;
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
	 * @param operId-申请人	
	 * @param appId-提前结清申请单编号
	 * @param settleType-结清类型
	 * @param vo-提前结清申请要素
	 * 返回：无
	 * @throws Exception 
	 * **/
	public void commitApplySettleTask(String operId,String appId,String settleType,ApplySettleVo vo) throws Exception;
	/**
	 * 功能：提交审批
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-审批结果
	 * @throws Exception 
	 * **/
	public void commitApproveSettleTask(String taskId,ApproveResultVo vo) throws Exception;
	/**
	 * 功能：提交减免审批
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-审批结果
	 * @throws Exception 
	 * **/
	public void commitApproveRemissionTask(String taskId,ApproveResultVo vo) throws Exception;
	/**
	 * 功能：提交确认结清申请
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-减免费项
	 * @throws Exception 
	 * **/
	public void commitApplyConfirmSettleTask(String operId,String taskId,RemissionFeeItemVo vo) throws Exception;
	/**
	 * 功能：提交确认入账
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-审批结果
	 * @throws Exception 
	 * **/
	public void commitConfirmSettleTask(String taskId,ApproveResultVo vo) throws Exception;
	/**
	 * 功能：取消结清任务
	 * 参数：
	 * 	taskId-任务ID
	 * 	operId-取消操作者
	 *  cancelComment-取消备注
	 * @throws Exception 
	 * **/
	public void cancelSettleTask(String taskId,String operId,String cancelComment) throws Exception;
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
