package com.pujjr.assetsmanage.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.assetsmanage.service.IInsuranceManageService;
import com.pujjr.base.controller.BaseController;

@RestController
@RequestMapping(value="/insmanage")
public class InsuranceManageController extends BaseController 
{
	@Autowired
	private IInsuranceManageService insMngService;
	
	@RequestMapping(value="/getHisList/{appId}",method=RequestMethod.GET)
	public List<HashMap<String, Object>> getInsuranceHisList(@PathVariable String appId)
	{
		return insMngService.getInsuranceHisList(appId);
	}
}
