package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.RuleDealer;
import com.pujjr.base.domain.RuleFinanceAmount;

public interface RuleFinanceAmountMapper {
    int deleteByPrimaryKey(String id);

    int insert(RuleFinanceAmount record);

    int insertSelective(RuleFinanceAmount record);

    RuleFinanceAmount selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RuleFinanceAmount record);

    int updateByPrimaryKey(RuleFinanceAmount record);
    
    RuleFinanceAmount selectByWorkgroupId(@Param("workgroupId")String workgroupId);
    
    int deleteByWorkgroupId(@Param("workgroupId")String workgroupId);
}