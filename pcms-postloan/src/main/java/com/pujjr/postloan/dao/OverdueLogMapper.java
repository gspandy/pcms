package com.pujjr.postloan.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.domain.OverdueLog;

public interface OverdueLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(OverdueLog record);

    int insertSelective(OverdueLog record);

    OverdueLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OverdueLog record);

    int updateByPrimaryKey(OverdueLog record);
    /**
     * 查询申请单最近一次逾期记录
     * @param appId
     * @return
     */
    OverdueLog selectLatesetLog(@Param("appId")String appId);
}