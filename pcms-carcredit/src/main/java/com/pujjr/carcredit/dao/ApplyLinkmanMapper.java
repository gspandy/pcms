package com.pujjr.carcredit.dao;

import java.util.List;

import com.pujjr.carcredit.domain.ApplyLinkman;
import com.pujjr.carcredit.vo.ApplyLinkmanVo;

public interface ApplyLinkmanMapper {
    int deleteByPrimaryKey(String id);

	int insert(ApplyLinkman record);

	int insertSelective(ApplyLinkman record);

	ApplyLinkman selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(ApplyLinkman record);

	int updateByPrimaryKey(ApplyLinkman record);
	
	List<ApplyLinkman> selectByAppId(String appId);
}