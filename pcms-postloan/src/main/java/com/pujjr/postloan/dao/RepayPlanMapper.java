package com.pujjr.postloan.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.po.RepaySchedulePo;

public interface RepayPlanMapper {
    int deleteByPrimaryKey(String id);

    int insert(RepayPlan record);

    int insertSelective(RepayPlan record);

    RepayPlan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RepayPlan record);

    int updateByPrimaryKey(RepayPlan record);
    
    List<RepayPlan> selectRepayPlanList(@Param("appId")String appId,@Param("period")int period);
    
    RepayPlan selectRepayPlan(@Param("appId")String appId,@Param("period")int period);

}