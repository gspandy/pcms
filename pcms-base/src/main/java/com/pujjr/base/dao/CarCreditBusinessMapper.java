package com.pujjr.base.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Param;

public interface CarCreditBusinessMapper 
{
	HashMap<String,Object> selectApplyInfo(@Param("appId")String appId);
}
