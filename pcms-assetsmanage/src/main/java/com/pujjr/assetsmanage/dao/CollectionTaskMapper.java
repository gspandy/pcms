package com.pujjr.assetsmanage.dao;

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
	
	List<CollectionTask>  selectCollectionApplyTask(@Param("appId")String appId);
}