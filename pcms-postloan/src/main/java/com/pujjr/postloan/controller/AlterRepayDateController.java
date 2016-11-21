package com.pujjr.postloan.controller;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.controller.BaseController;
import com.pujjr.postloan.service.IAlterRepayDateService;
import com.pujjr.postloan.vo.AlterRepayDateFeeItemVo;
import com.pujjr.utils.Utils;

@RestController
@RequestMapping(value="/alterrepaydate")
public class AlterRepayDateController extends BaseController 
{
	@Autowired
	private IAlterRepayDateService alterRepayDateService;
	
	@RequestMapping(value="/getAlterRepayDateFeeItem/{appId}",method=RequestMethod.GET)
	public AlterRepayDateFeeItemVo getAlterRepayDateFeeItem(@PathVariable String appId,String oldClosingDate,String newClosingDate) throws ParseException
	{
		return alterRepayDateService.getAlterRepayDateFeeItem(appId, Utils.htmlTime2Date(oldClosingDate, "yyyyMMdd"), Utils.htmlTime2Date(newClosingDate, "yyyyMMdd"));
	}
}
