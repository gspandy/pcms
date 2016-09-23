package com.pujjr.carcredit.dao;

import com.pujjr.carcredit.domain.ApplyFamilyDebt;
import com.pujjr.carcredit.vo.ApplyFamilyDebtVo;

public interface ApplyFamilyDebtMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApplyFamilyDebt record);

    int insertSelective(ApplyFamilyDebt record);

    ApplyFamilyDebt selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplyFamilyDebt record);

    int updateByPrimaryKey(ApplyFamilyDebt record);
    
    ApplyFamilyDebtVo selectByAppId(String appId);
}