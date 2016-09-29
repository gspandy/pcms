package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.Product;
import com.pujjr.base.domain.RuleProduct;

public interface RuleProductMapper {
    int deleteByPrimaryKey(String id);

    int insert(RuleProduct record);

    int insertSelective(RuleProduct record);

    RuleProduct selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RuleProduct record);

    int updateByPrimaryKey(RuleProduct record);
    
    List<Product> selectListByWorkgroupId(@Param("workgroupId")String workgroupId);
    
    int deleteByWorkgroupId(@Param("workgroupId")String workgroupId);
}