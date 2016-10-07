package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.InsuranceCompany;

public interface InsuranceCompanyMapper {

	int deleteByPrimaryKey(String id);

    int insert(InsuranceCompany record);

    int insertSelective(InsuranceCompany record);

    InsuranceCompany selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InsuranceCompany record);

    int updateByPrimaryKey(InsuranceCompany record);
    
    List<InsuranceCompany> selectAll(@Param("enabled")boolean enabled);
}