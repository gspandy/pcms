package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.ContractInfo;

public interface ContractInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContractInfo record);

    int insertSelective(ContractInfo record);

    ContractInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContractInfo record);

    int updateByPrimaryKey(ContractInfo record);
    
    List<ContractInfo> selectContractInfoList();
    
    ContractInfo selectContractInfoByKey(@Param("contractKey")String contractKey);
    
    List<ContractInfo> selectContractInfoListByContractTemplateId(@Param("templateId")String templateId, @Param("enabled")boolean enabled) ;
}