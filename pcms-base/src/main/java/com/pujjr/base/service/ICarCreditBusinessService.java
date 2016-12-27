package com.pujjr.base.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional(rollbackFor=Exception.class)
public interface ICarCreditBusinessService 
{
	public HashMap<String,Object> getApplyInfo(String appId);
}
