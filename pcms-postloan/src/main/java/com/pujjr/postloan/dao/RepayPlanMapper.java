package com.pujjr.postloan.dao;

import com.pujjr.postloan.domain.RepayPlan;

public interface RepayPlanMapper {
    int deleteByPrimaryKey(String id);

    int insert(RepayPlan record);

    int insertSelective(RepayPlan record);

    RepayPlan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RepayPlan record);

    int updateByPrimaryKey(RepayPlan record);
}