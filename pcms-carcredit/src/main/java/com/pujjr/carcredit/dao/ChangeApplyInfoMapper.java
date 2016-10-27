package com.pujjr.carcredit.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.carcredit.domain.ChangeApplyInfo;

public interface ChangeApplyInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(ChangeApplyInfo record);

    int insertSelective(ChangeApplyInfo record);

    ChangeApplyInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ChangeApplyInfo record);

    int updateByPrimaryKey(ChangeApplyInfo record);
    
    ChangeApplyInfo selectByRunPathId(@Param("runPathId")String runPathId);
}