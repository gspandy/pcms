package com.pujjr.base.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.domain.Merchant;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.service.IMerchantService;
import com.pujjr.utils.Utils;

@RestController
@RequestMapping(value="/merchant")
public class MerchantController 
{
	@Autowired
	private IMerchantService merchantService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Merchant> getMerchantList(@Param("enabled")boolean enabled)
	{
		return merchantService.getMerchantList(enabled);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public void addMerchant(@RequestBody Merchant record,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		record.setId(Utils.get16UUID());
		record.setCreateId(account.getAccountId());
		record.setCreateTime(new Date());
		merchantService.addMerchant(record);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public void modifyMerchant(@RequestBody Merchant record,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		record.setUpdateId(account.getAccountId());
		record.setUpdateTime(new Date());
		merchantService.modifyMerchant(record);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public void delteMerchant(@PathVariable String id)
	{
		merchantService.deleteMerchantById(id);
	}
}
