package com.pujjr.postloan.dao;

import java.util.Date;
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
    
    /**
     * 查询当期还款计划
     * @param appId
     * @param curDate
     * @return
     */
    RepayPlan selectCurrentPeriodRepayPlan(@Param("appId")String appId,@Param("curDate")Date curDate);
    
    /**
     * 查询申请单指定期数还款计划(包含首、尾周期)
     * @param appId
     * @param beginPeriod 
     * @param endPeriod
     * @return
     */
    List<RepayPlan> selectSpecialRepayPlanList(@Param("appId")String appId,@Param("beginPeriod")int beginPeriod,@Param("endPeriod")int endPeriod);
    
    /**
     * 查询满足条件需要扣款的还款计划
     * @param curDate
     * @return
     */
    List<RepayPlan> selectNeedChargeRepayPlanList(@Param("curDate")Date curDate);
    
    /**
     *  查询所有未结清的还款计划
     * @param appId
     * @return
     */
    List<RepayPlan> selectNotSettleRepayPlanList(@Param("appId")String appId);

}