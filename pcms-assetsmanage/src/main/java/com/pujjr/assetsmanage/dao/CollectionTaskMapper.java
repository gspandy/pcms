package com.pujjr.assetsmanage.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.assetsmanage.domain.CollectionTask;

public interface CollectionTaskMapper {
    int deleteByPrimaryKey(String id);

	int insert(CollectionTask record);

	int insertSelective(CollectionTask record);

	CollectionTask selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(CollectionTask record);

	int updateByPrimaryKey(CollectionTask record);
	/**
	 * 根据申请单号查询申请的催收任务列表
	 * @param appId
	 * @return
	 */
	List<CollectionTask>  selectCollectionApplyTask(@Param("appId")String appId);
	/**
	 * 检查是否已经有催收任务运行中
	 * @param appId
	 * @return
	 */
	List<CollectionTask>  selectHasCollectionTaskRunning(@Param("appId")String appId);
	/**
	 * 查询催收任务记录类型及对应数量
	 * @param appId
	 * @return
	 */
	List<HashMap<String,Object>> selectCollectionTaskCnt(@Param("appId")String appId);
}