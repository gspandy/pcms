package com.pujjr.postloan.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface ILoanQueryService 
{
	/**
	 * 查询贷款客户列表
	 */
	public List<HashMap<String,Object>> getLoanCustList();
}
