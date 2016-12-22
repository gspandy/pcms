package com.pujjr.assetsmanage.dao;

import com.pujjr.assetsmanage.domain.AlterCustInfoDetail;

public interface AlterCustInfoDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(AlterCustInfoDetail record);

    int insertSelective(AlterCustInfoDetail record);

    AlterCustInfoDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AlterCustInfoDetail record);

    int updateByPrimaryKey(AlterCustInfoDetail record);
}