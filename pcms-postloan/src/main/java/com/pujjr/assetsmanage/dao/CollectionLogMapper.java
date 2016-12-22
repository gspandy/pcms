package com.pujjr.assetsmanage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.assetsmanage.domain.CollectionLog;
import com.pujjr.assetsmanage.vo.CollectionLogVo;

public interface CollectionLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(CollectionLog record);

    int insertSelective(CollectionLog record);

    CollectionLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CollectionLog record);

    int updateByPrimaryKey(CollectionLog record);
    
	List<CollectionLog> selectCollectionLogInfo(@Param("appId")String appId, @Param("taskType")String taskType);
	
	List<CollectionLogVo> selectImportanCollectionLogInfo(@Param("appId")String appId);
}