package com.pujjr.postloan.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 放款服务
 * **/
@Service
@Transactional(rollbackFor=Exception.class)
public interface ILoanService 
{
	public void commitLoanTask(String taskId,String appId) throws Exception;
}
