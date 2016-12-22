package com.pujjr.sms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pujjr.base.controller.BaseController;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.vo.PageVo;
import com.pujjr.base.vo.QueryParamPageVo;
import com.pujjr.sms.domain.SmsHis;
import com.pujjr.sms.domain.SmsTemplate;
import com.pujjr.sms.service.ISmsService;
import com.pujjr.utils.Utils;

@RestController
@RequestMapping(value="/sms")
public class SmsController extends BaseController 
{
	@Autowired
	private ISmsService smsService;
	
	@RequestMapping(value="/template",method=RequestMethod.GET)
	public List<SmsTemplate> getSmsTemplateList()
	{
		return smsService.getSmsTemplateList();
	}
	
	@RequestMapping(value="/template",method=RequestMethod.POST)
	public void addSmsTemplate(@RequestBody SmsTemplate record,HttpServletRequest request)
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		record.setId(Utils.get16UUID());
		record.setCreateId(sysAccount.getAccountId());
		record.setCreateTime(new Date());
		smsService.addSmsTemplate(record);
	}
	
	@RequestMapping(value="/template/{id}",method=RequestMethod.PUT)
	public void modifySmsTemplate(@RequestBody SmsTemplate record,HttpServletRequest request)
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		record.setUpdateId(sysAccount.getAccountId());
		record.setUpdateTime(new Date());
		smsService.modifySmsTemplate(record);
	}
	
	@RequestMapping(value="/history",method=RequestMethod.GET)
	public PageVo getSmsHistoryList(QueryParamPageVo param)
	{
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<SmsHis> list = smsService.getSmsHisList();
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	
}
