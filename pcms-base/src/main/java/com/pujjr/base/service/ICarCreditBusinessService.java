package com.pujjr.base.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;
@Service
public interface ICarCreditBusinessService 
{
	public HashMap<String,Object> getApplyInfo(String appId);
}
