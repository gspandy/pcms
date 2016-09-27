package com.pujjr.carcredit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.carcredit.domain.RuleDealer;
import com.pujjr.carcredit.domain.RuleFinanceAmount;

public interface RuleFinanceAmountMapper {
    int deleteByPrimaryKey(String id);

    int insert(RuleFinanceAmount record);

    int insertSelective(RuleFinanceAmount record);

    RuleFinanceAmount selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RuleFinanceAmount record);

    int updateByPrimaryKey(RuleFinanceAmount record);
    
    RuleFinanceAmount selectByWorkgroupId(@Param("workgroupId")String workgroupId);
}