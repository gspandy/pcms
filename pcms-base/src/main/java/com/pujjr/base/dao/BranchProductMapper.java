package com.pujjr.base.dao;

import com.pujjr.base.domain.BranchProductKey;

public interface BranchProductMapper {
    int deleteByPrimaryKey(BranchProductKey key);

    int insert(BranchProductKey record);

    int insertSelective(BranchProductKey record);
}