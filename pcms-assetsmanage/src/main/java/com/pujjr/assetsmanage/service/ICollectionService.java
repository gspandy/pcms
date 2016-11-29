package com.pujjr.assetsmanage.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.assetsmanage.domain.CollectionLog;
import com.pujjr.assetsmanage.domain.CollectionTask;
import com.pujjr.assetsmanage.enumeration.CollectionTaskType;
import com.pujjr.assetsmanage.vo.CollectionApplyVo;
import com.pujjr.assetsmanage.vo.CollectionLogVo;
import com.pujjr.carcredit.po.OnlineAcctPo;
import com.pujjr.postloan.vo.ApproveResultVo;

/**
 * 催收处理服务
 * @author 150032
 *
 */
@Service
public interface ICollectionService
{
	/**
	 * 创建电话催收任务
	 * @param createId 申请人ID
	 * @param vo 申请信息
	 */
	public void createPhoneCollectionTask(String createId,String appId,String applyComment);
	/**
	 * 申请创建新催收任务
	 * @param taskId 当前执行申请任务节点任务ID
	 * @param taskType 任务类型
	 * @param createId 申请人ID
	 * @param vo 申请信息
	 * @throws Exception 
	 */
	public void applyNewCollectionTask(String taskId,String appId,CollectionTaskType taskType,String createId,CollectionApplyVo vo) throws Exception;
	/**
	 * 重新分配任务催收任务执行人
	 * @param taskId 当前任务节点ID
	 * @param newOperId 任务新执行人
	 * @throws Exception 
	 */
	public void reassigneeTaskOperId(String taskId,String newOperId) throws Exception;
	/**
	 * 查询指定流程催收任务执行工作人员列表
	 * @param taskId 当前任务ID
	 * @throws Exception 
	 */
	public List<OnlineAcctPo> getCollectionWorkgroupUserIdList(String taskId) throws Exception;
	/**
	 * 审批创建新催收任务申请
	 * @param taskId 任务ID
	 * @param vo 审批结果
	 * @throws Exception 
	 */
	public void commitApproveApplyNewCollectionTask(String taskId,ApproveResultVo vo) throws Exception;
	/**
	 * 提交结案
	 * @param taskId 流程节点任务ID
	 * @throws Exception 
	 */
	public void commitSettleLawsuitTask(String taskId) throws Exception;
	/**
	 * 查询催收任务申请信息
	 * @param applyId 申请任务ID
	 * @return
	 */
	public CollectionApplyVo getCollectionAppyInfo(String applyId);
	/**
	 * 查询催收记录
	 * @param appId
	 * @param taskType
	 * @return
	 */
	public List<CollectionLog> getCollectionLogInfo(String appId,String taskType);
	/**
	 * 查询重大催收记录
	 * @param appId 申请单号
	 * @return
	 */
	public List<CollectionLogVo> getImportanCollectionLogInfo(String appId);
	/**
	 * 查询催收任务信息
	 * @param appId 申请单号
	 * @return
	 */
	public List<CollectionTask> getCollectionApplyTask(String appId);
	/**
	 * 保存催收日志
	 * @param appId 申请单编号
	 * @param taskType 任务类型
	 * @param taskId 当前任务节点ID
	 * @param vo 日志信息
	 * @throws Exception 
	 */
	public void saveCollectionLog(String taskId,CollectionLogVo vo) throws Exception;
		
}
