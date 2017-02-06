package com.pujjr.postloan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.domain.OverdueToday;

public interface OverdueTodayMapper {
    int deleteByPrimaryKey(String id);

    int insert(OverdueToday record);

    int insertSelective(OverdueToday record);

    OverdueToday selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OverdueToday record);

    int updateByPrimaryKey(OverdueToday record);
    
    List<OverdueToday> selectList();
    
    int deleteAll();
    
    OverdueToday selectByAppId(@Param("appId")String appId);
}