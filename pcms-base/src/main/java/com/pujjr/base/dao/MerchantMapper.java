package com.pujjr.base.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.base.domain.Merchant;

public interface MerchantMapper {
    int deleteByPrimaryKey(String id);

	int insert(Merchant record);

	int insertSelective(Merchant record);

	Merchant selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Merchant record);

	int updateByPrimaryKey(Merchant record);
	
	List<HashMap<String,Object>> selectAll(@Param("enabled")boolean enabled,@Param("chnlType")String chnlType);
	
	Merchant selectByMerchantNo(@Param("merchantNo")String merchantNo);
}