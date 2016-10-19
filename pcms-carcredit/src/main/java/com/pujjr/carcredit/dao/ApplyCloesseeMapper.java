package com.pujjr.carcredit.dao;

import com.pujjr.carcredit.domain.ApplyCloessee;
import com.pujjr.carcredit.vo.ApplyCloesseeVo;

public interface ApplyCloesseeMapper {
    int deleteByPrimaryKey(String id);

	int insert(ApplyCloessee record);

	int insertSelective(ApplyCloessee record);

	ApplyCloessee selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ApplyCloessee record);

	int updateByPrimaryKey(ApplyCloessee record);
    
    ApplyCloessee selectByAppId(String appId);
}