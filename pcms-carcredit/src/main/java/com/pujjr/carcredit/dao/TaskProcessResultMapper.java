package com.pujjr.carcredit.dao;

import com.pujjr.carcredit.domain.TaskProcessResult;

public interface TaskProcessResultMapper {
    int deleteByPrimaryKey(String id);

    int insert(TaskProcessResult record);

    int insertSelective(TaskProcessResult record);

    TaskProcessResult selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TaskProcessResult record);

    int updateByPrimaryKey(TaskProcessResult record);
}