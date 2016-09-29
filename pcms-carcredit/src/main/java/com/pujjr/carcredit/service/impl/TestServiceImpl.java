package com.pujjr.carcredit.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.pujjr.carcredit.service.ITestService;

@Service
@Transactional
public class TestServiceImpl implements ITestService{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void rollback_test(){
		System.out.println("CoreServiceImpl2->rollback_test");
		String sql = "insert into test1(userId,couse,point,id) values('1','1','1','1')";
		jdbcTemplate.update(sql);
//		throw new RuntimeException("ttttttttttttttt");
//		throw new NullPointerException("ttttttttttttttttttt");
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}
}
