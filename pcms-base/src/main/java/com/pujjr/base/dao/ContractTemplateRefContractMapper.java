package com.pujjr.base.dao;

import com.pujjr.base.domain.ContractTemplateRefContractKey;

public interface ContractTemplateRefContractMapper {
    int deleteByPrimaryKey(ContractTemplateRefContractKey key);

    int insert(ContractTemplateRefContractKey record);

    int insertSelective(ContractTemplateRefContractKey record);
}