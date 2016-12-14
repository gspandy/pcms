package com.pujjr.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.ArchiveSupplyDetail;

public interface ArchiveSupplyDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(ArchiveSupplyDetail record);

    int insertSelective(ArchiveSupplyDetail record);

    ArchiveSupplyDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ArchiveSupplyDetail record);

    int updateByPrimaryKey(ArchiveSupplyDetail record);
    
    List<ArchiveSupplyDetail> selectAll(@Param("supplyId")String supplyId);
}