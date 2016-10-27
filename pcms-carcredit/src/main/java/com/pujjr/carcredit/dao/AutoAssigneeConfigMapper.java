package com.pujjr.carcredit.dao;

import com.pujjr.carcredit.domain.AutoAssigneeConfig;

public interface AutoAssigneeConfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(AutoAssigneeConfig record);

    int insertSelective(AutoAssigneeConfig record);

    AutoAssigneeConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AutoAssigneeConfig record);

    int updateByPrimaryKey(AutoAssigneeConfig record);
}