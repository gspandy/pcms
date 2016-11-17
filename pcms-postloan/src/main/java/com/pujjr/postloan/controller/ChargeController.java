package com.pujjr.postloan.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pujjr.base.controller.BaseController;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.vo.PageVo;
import com.pujjr.base.vo.QueryParamPageVo;
import com.pujjr.postloan.enumeration.ChargeMode;
import com.pujjr.postloan.po.OfferInfoPo;
import com.pujjr.postloan.schedule.ScheduleService;
import com.pujjr.postloan.service.IChargeService;

@RestController
@RequestMapping(value="/charge")
public class ChargeController extends BaseController
{
	@Autowired
	private IChargeService chargeService;
	@Autowired
	private ScheduleService schedule;
	
	@RequestMapping(value="/getEnableChargeList",method=RequestMethod.GET)
	public PageVo getEnableChargeList(QueryParamPageVo param) throws ParseException
	{
		//schedule.dayJob();
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<HashMap<String,Object>> list = chargeService.getEnableChargeList();
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	@RequestMapping(value="/setChargeMode/{chargeMode}",method=RequestMethod.POST)
	public void setChargeMode(@RequestBody List<String> chargeIds,@PathVariable String chargeMode) throws Exception
	{
		if(chargeMode.equals(ChargeMode.UnionFile.getName()))
		{
			chargeService.setChargeMode(chargeIds, ChargeMode.UnionFile);
		}
		if(chargeMode.equals(ChargeMode.UnionRealTime.getName()))
		{
			chargeService.setChargeMode(chargeIds, ChargeMode.UnionRealTime);
		}
	}
	@RequestMapping(value="/confirmManualOffer/{merchantNo}",method=RequestMethod.GET)
	public HashMap<String,Object> confirmManualOffer(@PathVariable String merchantNo,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		String ossKey = chargeService.confirmManualOffer(merchantNo, account.getAccountId());
		HashMap<String,Object> var = new HashMap<String,Object>();
		var.put("ossKey", ossKey);
		return var;
	}
	
	@RequestMapping(value="/getWatingOfferChargeList/{chargeMode}",method=RequestMethod.GET)
	public PageVo getWatingOfferChargeList(QueryParamPageVo param,@PathVariable String chargeMode)
	{
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<OfferInfoPo> list = new ArrayList<OfferInfoPo>();
		if(chargeMode.equals(ChargeMode.UnionFile.getName()))
		{
			list = chargeService.getWatingOfferChargeList(ChargeMode.UnionFile);
		}
		if(chargeMode.equals(ChargeMode.UnionRealTime.getName()))
		{
			list = chargeService.getWatingOfferChargeList(ChargeMode.UnionRealTime);
		}
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	
	@RequestMapping(value="/getManualOfferHisList",method=RequestMethod.GET)
	public PageVo getManualOfferHisList(QueryParamPageVo param,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<HashMap<String,Object>> list = chargeService.getManualOfferHisList(account.getAccountId());
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	@RequestMapping(value="/doFileRetOffer",method=RequestMethod.POST)
	public void doFileRetOffer(MultipartFile file,HttpServletRequest request) throws Exception
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		String fileName = file.getOriginalFilename();
		String filePath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/")+"tmp"+File.separator+fileName;
		System.out.println(filePath);
		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath));
		List<String> listStr =  FileUtils.readLines(new File(filePath));
		String offerBatchId = fileName.substring(0, fileName.length()-6);
		//最后一笔为汇总信息，删除不用
		listStr.remove(listStr.size()-1);
		chargeService.retOfferProcess(offerBatchId,listStr,account.getAccountId());
	}
	
	@RequestMapping(value="/getManualOfferBatchDetail/{offerBatchId}",method=RequestMethod.GET)
	public PageVo getManualOfferBatchDetail(@PathVariable String offerBatchId,QueryParamPageVo param,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<HashMap<String,Object>> list = chargeService.getManualOfferBatchDetail(offerBatchId);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	
	
}
