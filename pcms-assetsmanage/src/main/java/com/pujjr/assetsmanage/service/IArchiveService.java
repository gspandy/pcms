package com.pujjr.assetsmanage.service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.assetsmanage.po.ArchiveDetailPo;
import com.pujjr.assetsmanage.po.ArchiveTaskPo;
import com.pujjr.assetsmanage.vo.ArchiveDelayVo;
import com.pujjr.assetsmanage.vo.ArchiveLogVo;
import com.pujjr.assetsmanage.vo.ArchivePostVo;
import com.pujjr.assetsmanage.vo.ArchiveSupplyVo;
import com.pujjr.assetsmanage.vo.ReApplyArchiveSupplyVo;
import com.pujjr.base.domain.ArchiveStore;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.assetsmanage.vo.ApplyArchiveBorrowVo;
import com.pujjr.assetsmanage.vo.ApplyArchiveSupplyVo;

@Service
@Transactional
public interface IArchiveService 
{
	/**
	 * 查询已放款档案整理代办
	 * @param assignee 任务执行人ID
	 * @return
	 */
	List<HashMap<String,Object>> getArchiveToDoTaskList(String assignee); 
	
	/**
	 * 查询归档任务明细信息
	 * @param archiveTaskId
	 * @return
	 */
	List<ArchiveDetailPo>  getArchiveTaskDetail(String archiveTaskId,String archiveTaskType);
	/**
	 * 保存归档任务明细信息
	 * @param acchiveTaskId
	 * @param records
	 * @param operId
	 */
	public void saveArchiveTaskDetail(String archiveTaskId,List<ArchiveDetailPo> records,String operId);
	/**
	 * 档案邮寄
	 * @param vo 档案邮寄提交vo
	 * @param operId 操作人
	 */
	public void archivePost(ArchivePostVo vo,String operId);
	/**
	 * 查询档案信息
	 * @return
	 */
	public List<HashMap<String,Object>> getArchiveList();
	/**
	 * 档案延期
	 * @param vo
	 * @throws Exception 
	 */
	public void archiveDelay(ArchiveDelayVo vo) throws Exception;
	/**
	 * 提交档案归档申请
	 * @param archiveTaskId
	 * @param operId
	 * @throws Exception 
	 */
	public void applyArchiveLog(List<ArchiveLogVo> records,String operId) throws Exception;
	/**
	 * 获取归档任务申请信息
	 * @param archiveTaskId
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public ArchiveTaskPo getArchiveApplyInfo(String archiveTaskId) throws IllegalAccessException, InvocationTargetException;
	/**
	 * 提交归档任务
	 * @param po
	 * @param taskId
	 * @throws Exception 
	 */
	public void commitArchiveLog(ArchiveTaskPo po,String taskId,String operId) throws Exception;
	/**
	 * 创建催收归档任务
	 * @param appId
	 * @param records
	 * @param operId
	 */
	public void createCollectionArchiveTask(String appId,String archiveType,List<ArchiveDetailPo> records,String operId);
	/**
	 * 创建自动归档任务
	 * @param appId
	 * @param archiveType
	 * @param operId
	 */
	public void createAutoArchiveTask(String appId,String archiveType,String operId);
	/**
	 * 申请补充归档资料
	 * @param vo 补充信息
	 * @param operId 执行人
	 * @throws Exception 
	 */
	public void applyArchiveSupply(ApplyArchiveSupplyVo vo,String taskId,String operId) throws Exception;
	/**
	 * 获取补充归档资料信息
	 * @param taskId
	 * @return
	 * @throws Exception 
	 */
	public ArchiveSupplyVo getArchiveSupplyInfo(String taskId) throws Exception;
	/**
	 * 提交补充资料任务
	 * @param vo
	 * @param taskId
	 * @param operId
	 * @throws Exception 
	 */
	public void commitArchiveSupplyTask(ArchiveSupplyVo vo,String taskId,String operId) throws Exception;
	/**
	 * 用于提交补充资料的归档任务
	 * @param vo
	 * @param taskId
	 * @param operId
	 * @throws Exception 
	 */
	public void commitArchiveLogSupplyTask(ArchiveSupplyVo vo ,String taskId,String operId) throws Exception;
	/**
	 * 再次申请补充归档资料
	 * @param vo
	 * @param taskId
	 * @param operId
	 * @throws Exception 
	 */
	public void reApplyArchiveSupply(ReApplyArchiveSupplyVo vo ,String taskId,String operId) throws Exception;
	
	/**
	 * 查询申请单档案库存信息
	 * @param appId
	 * @return
	 */
	public List<ArchiveStore> getArchiveStoreList(String appId);
	/**
	 * 申请档案借阅
	 * @param vo
	 * @param appId
	 * @param operId
	 */
	public void applyArchiveBorrow(ApplyArchiveBorrowVo vo ,String appId, String operId);
	/**
	 * 根据借阅ID查询借阅信息
	 * @param borrowId
	 * @return
	 */
	public ApplyArchiveBorrowVo getArchiveBorrowInfo(String borrowId);
	/**
	 * 提交借阅审批
	 * @param taskId
	 * @param vo
	 * @throws Exception 
	 */
	public void commitApproveArchiveBorrowTask(String taskId,ApproveResultVo vo) throws Exception;
	/**
	 * 档案归还
	 * @param vo
	 * @param taskId
	 * @throws Exception 
	 */
	public void commitArchiveBorrowReturnTask(ApplyArchiveBorrowVo vo , String taskId) throws Exception;
	
	/**
	 * 档案借阅退回
	 * @param taskId
	 * @param message
	 * @throws Exception 
	 */
	public void backArchiveBorrowTask(String taskId,String message) throws Exception;
	/**
	 * 重新提交借阅申请
	 * @param taskId
	 * @param vo
	 * @throws Exception 
	 */
	public void reApplyArchiveBorrow(String taskId,ApplyArchiveBorrowVo vo) throws Exception;
}
