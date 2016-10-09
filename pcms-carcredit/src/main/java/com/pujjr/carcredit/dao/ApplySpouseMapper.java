package com.pujjr.carcredit.dao;

import com.pujjr.carcredit.domain.ApplySpouse;
import com.pujjr.carcredit.vo.ApplySpouseVo;

public interface ApplySpouseMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApplySpouse record);

    int insertSelective(ApplySpouse record);

    ApplySpouse selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplySpouse record);

    int updateByPrimaryKey(ApplySpouse record);
    
    ApplySpouse selectByAppId(String appId);
}