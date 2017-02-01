package com.pujjr.postloan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.domain.StayAccount;

public interface StayAccountMapper {
    int deleteByPrimaryKey(String id);

    int insert(StayAccount record);

    int insertSelective(StayAccount record);

    StayAccount selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StayAccount record);

    int updateByPrimaryKey(StayAccount record);
    
    /**
     * 查询申请单号对应挂账信息
     * @param appId申请单号
     * @return 挂账信息
     */
    StayAccount selectByAppId(@Param("appId")String appId);
    /**
     * 查找存在挂账并需要冲账的挂账记录，
     * 满足挂账冲账的条件是
     * 1、挂账金额大于0
     * 2、存在应还项
     * 3、总账处理状态为对公还款或为空的情况下才能做挂账冲账处理
     *    如果正在做减免、结清、展期、变更还款日则不能做冲账处理
     * @return
     */
    List<StayAccount> selectNeedReserveList();
}