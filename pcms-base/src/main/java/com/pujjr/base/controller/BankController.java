package com.pujjr.base.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.domain.BankInfo;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.service.IBankService;
import com.pujjr.utils.Utils;

@RestController
@RequestMapping(value="/bank")
public class BankController 
{
	@Autowired
	private IBankService bankService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<BankInfo> getBankInfoList(@RequestParam("enabled")boolean enabled)
	{
		if(enabled)
		{
			return bankService.getAllEnabledBankInfoList();
		}
		else
		{
			return bankService.getBankInfoList();
		}
	}
	@RequestMapping(method=RequestMethod.POST)
	public void addBankInfo(@RequestBody BankInfo bankInfo,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		bankInfo.setId(Utils.get16UUID());
		bankInfo.setCreateId(account.getAccountId());
		bankInfo.setCreateTime(new Date());
		bankService.addBankInfo(bankInfo);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public void modifyBankInfo(@RequestBody BankInfo bankInfo,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		bankInfo.setUpdateId(account.getAccountId());
		bankInfo.setUpdateTime(new Date());
		bankService.modifyBankInfo(bankInfo);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public void deleteBankInfo(@PathVariable String id)
	{
		bankService.deleteBankInfoById(id);
	}
	@RequestMapping(value="/unionpay",method=RequestMethod.GET)
	public List<BankInfo> getUnionPayBankInfoList()
	{
		return bankService.getAllUnionpayBankInfoList();
	}
}
