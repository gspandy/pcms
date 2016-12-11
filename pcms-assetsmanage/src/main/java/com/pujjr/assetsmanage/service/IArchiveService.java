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
	
}
