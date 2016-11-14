package com.pujjr.postloan.service;

import org.springframework.stereotype.Service;

/**
 * 放款服务
 * **/
@Service
public interface ILoanService 
{
	public void commitLoanTask(String taskId,String appId) throws Exception;
}
