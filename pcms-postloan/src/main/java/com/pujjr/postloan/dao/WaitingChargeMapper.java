package com.pujjr.postloan.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.domain.WaitingCharge;

public interface WaitingChargeMapper {
    int deleteByPrimaryKey(String id);

    int insert(WaitingCharge record);

    int insertSelective(WaitingCharge record);

    WaitingCharge selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WaitingCharge record);

    int updateByPrimaryKey(WaitingCharge record);
    
    /**
     * 查找待扣款明细表指定类型数据
     * @param appId-申请单号
     * @param feeType-费用类型
     * @return
     */
    List<WaitingCharge> selectListOrderByGentimeAsc(@Param("appId")String appId,@Param("feeType")String feeType);
    /**
     * 查找待扣款明细表需要计算罚息的待扣款记录,当还款日小于当前日期并且明细状态不为报盘中
     * @param queryDate-查询日期
     * @return
     */
    List<WaitingCharge> selectNeedCalOverdueAmountList(@Param("queryDate")Date queryDate);
    /**
     * 根据报盘ID查询待扣款明细记录
     * @param offerId报盘ID
     * @return
     */
    WaitingCharge selectByOfferId(@Param("offerId")String offerId);
}