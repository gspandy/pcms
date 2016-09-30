package com.pujjr.base.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.RuleMemberTaskCnt;

public interface RuleMemberTaskCntMapper {
    int deleteByPrimaryKey(String id);

    int insert(RuleMemberTaskCnt record);

    int insertSelective(RuleMemberTaskCnt record);

    RuleMemberTaskCnt selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RuleMemberTaskCnt record);

    int updateByPrimaryKey(RuleMemberTaskCnt record);
    
    int deleteByWorkgroupIdAndAccountId(@Param("workgroupId")String workgroupId,@Param("accountId")String accountId);
}