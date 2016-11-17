package com.pujjr.postloan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.postloan.domain.ApplyPublicRepay;
import com.pujjr.postloan.vo.ApplyPublicRepayVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.PublicRepayTaskVo;
import com.pujjr.postloan.vo.RepayFeeItemVo;


/**
 * 对公还款处理服务
 * **/
@Service
public interface IPublicRepayService 
{
	/**
	 * 功能：查询指定客户对公还款应还项
	 * 参数：
	 * 	appId-申请单号
	 * 返回：对公还款各项费用金额
	 * **/
	public RepayFeeItemVo getPublicRepayFeeItem(String appId);
	/**
	 * 功能：提交对公还款申请
	 * 参数：
	 * 	appId-对公还款申请单编号
	 * 	vo-对公还款申请要素
	 * 返回：无
	 * @throws Exception 
	 * **/
	public void commitApplyPublicRepayTask(String appId,ApplyPublicRepayVo vo) throws Exception;
	/**
	 * 功能：提交审批
	 * 参数：
	 * 	taskId-任务ID
	 * 	vo-审批结果
	 * **/
	public void commitApprovePublicRepayTask(String taskId,ApproveResultVo vo);
	/**
	 * 功能：取消对公还款任务
	 * 参数：
	 * 	taskId-任务ID
	 * 	operId-取消操作者
	 *  cancelComment-取消备注
	 * **/
	public void cancelPublicRepayTask(String taskId,String operId,String cancelComment);
	/**
	 * 功能：查询对公还款任务列表
	 * 参数：
	 * createId-任务创建人
	 * applyStatus-申请状态	
	 * **/
	public List<PublicRepayTaskVo> getApplyPublicRepayTaskList(String createId,List<String> applyStatus);
	/**
	 * 功能：根据主键查询对公还款申请信息
	 * **/
	public ApplyPublicRepay getApplyPublicRepayTaskById(String id);
	/**
	 * 功能：修改对公还款申请信息
	 * **/
	public void modifyApplyPublicRepayInfo(ApplyPublicRepay record);
	/**
	 * 功能：删除对公还款申请信息
	 * **/
	public void deleteApplyPublicRepayInfoById(String id);
}
