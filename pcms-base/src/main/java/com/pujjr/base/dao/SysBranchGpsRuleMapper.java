package com.pujjr.base.dao;

import com.pujjr.base.domain.SysBranchGpsRuleKey;

public interface SysBranchGpsRuleMapper {
    int deleteByPrimaryKey(SysBranchGpsRuleKey key);

    int insert(SysBranchGpsRuleKey record);

    int insertSelective(SysBranchGpsRuleKey record);
}