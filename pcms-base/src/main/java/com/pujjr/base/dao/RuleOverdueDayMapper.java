package com.pujjr.base.dao;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.RuleOverdueDay;

public interface RuleOverdueDayMapper {
    int deleteByPrimaryKey(String id);

	int insert(RuleOverdueDay record);

	int insertSelective(RuleOverdueDay record);

	RuleOverdueDay selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(RuleOverdueDay record);

	int updateByPrimaryKey(RuleOverdueDay record);
    
    RuleOverdueDay selectByWorkgroupId(@Param("workgroupId")String workgroupId);
    
    void deleteByWorkgrouId(@Param("workgroupId")String workgroupId);
}