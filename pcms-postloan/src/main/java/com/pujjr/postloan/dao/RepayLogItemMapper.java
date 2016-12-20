package com.pujjr.postloan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.domain.RepayLogItem;

public interface RepayLogItemMapper {
    int deleteByPrimaryKey(String id);

    int insert(RepayLogItem record);

    int insertSelective(RepayLogItem record);

    RepayLogItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RepayLogItem record);

    int updateByPrimaryKey(RepayLogItem record);
    /**
     * 查询冲账项目信息
     * @param feeType
     * @param feeId
     * @param repayItem
     * @return
     */
    List<RepayLogItem> selectList(@Param("feeType")String feeType,@Param("feeId")String feeId,@Param("repayItem")String repayItem);
}