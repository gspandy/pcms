package com.pujjr.assetsmanage.dao;

import com.pujjr.assetsmanage.domain.InsuranceClaims;

public interface InsuranceClaimsMapper {
    int deleteByPrimaryKey(String id);

    int insert(InsuranceClaims record);

    int insertSelective(InsuranceClaims record);

    InsuranceClaims selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InsuranceClaims record);

    int updateByPrimaryKey(InsuranceClaims record);
}