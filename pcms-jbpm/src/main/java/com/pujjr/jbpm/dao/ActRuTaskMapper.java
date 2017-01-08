package com.pujjr.jbpm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.jbpm.domain.ActRuTask;

public interface ActRuTaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(ActRuTask record);

    int insertSelective(ActRuTask record);

    ActRuTask selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ActRuTask record);

    int updateByPrimaryKey(ActRuTask record);
    //查询当前正在执行任务列表
    List<ActRuTask> selectList(@Param("assignee")String assignee);
}