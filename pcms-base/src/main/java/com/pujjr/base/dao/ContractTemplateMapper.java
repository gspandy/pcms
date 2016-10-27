package com.pujjr.base.dao;

import java.util.List;

import com.pujjr.base.domain.ContractTemplate;

public interface ContractTemplateMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContractTemplate record);

    int insertSelective(ContractTemplate record);

    ContractTemplate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContractTemplate record);

    int updateByPrimaryKey(ContractTemplate record);
    
    List<ContractTemplate> selectContractTemplateList();
    
    int deleteTemplateRefContract(String templateId);
}