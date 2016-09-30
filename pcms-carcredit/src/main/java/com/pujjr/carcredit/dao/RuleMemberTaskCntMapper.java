package com.pujjr.carcredit.dao;

import com.pujjr.carcredit.domain.RuleMemberTaskCnt;

public interface RuleMemberTaskCntMapper {
    int deleteByPrimaryKey(String id);

    int insert(RuleMemberTaskCnt record);

    int insertSelective(RuleMemberTaskCnt record);

    RuleMemberTaskCnt selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RuleMemberTaskCnt record);

    int updateByPrimaryKey(RuleMemberTaskCnt record);
}