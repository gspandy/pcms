package com.pujjr.carcredit.dao;

import com.pujjr.carcredit.domain.HisBeanMap;

public interface HisBeanMapMapper {
    int deleteByPrimaryKey(String id);

    int insert(HisBeanMap record);

    int insertSelective(HisBeanMap record);

    HisBeanMap selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HisBeanMap record);

    int updateByPrimaryKey(HisBeanMap record);
    
    HisBeanMap selectByClassName(String className);
    
    int deleteAll();
}