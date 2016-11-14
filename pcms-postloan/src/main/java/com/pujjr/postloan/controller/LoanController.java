package com.pujjr.postloan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.postloan.service.ILoanService;

@RestController
public class LoanController 
{
	@Autowired
	private ILoanService loanService;
	
	@RequestMapping(value="/task/commitLoanTask/{taskId}/{appId}")
	public void commitLoanTask(@PathVariable String taskId,@PathVariable String appId) throws Exception
	{
		loanService.commitLoanTask(taskId, appId);
	}
}
