package com.pujjr.carcredit.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.carcredit.domain.Apply;
import com.pujjr.carcredit.po.ApplyInfoPo;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.carcredit.vo.QueryParamApplyVo;

public interface ApplyMapper {
	int deleteByPrimaryKey(String id);

	int insert(Apply record);

	int insertSelective(Apply record);

	Apply selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Apply record);

	int updateByPrimaryKey(Apply record);

	int updateByAppidSelective(Apply record);

	Apply selectByAppid(@Param("appId")String appId);

	List<Apply> selectByMap(HashMap condition);

	ApplyVo selectApplyByMap(String id);
	
	List<ApplyInfoPo> selectApplyInfoList(@Param("accountId")String accountId,@Param("status")String status,@Param("queryParam")QueryParamApplyVo queryParam);
}