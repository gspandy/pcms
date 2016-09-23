package com.pujjr.base.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.utils.Utils;

@RestController
@RequestMapping("/common")
public class CommonController extends BaseController 
{
	@RequestMapping(value="/getSystemDate", method=RequestMethod.GET)
	public String getSystemDate()
	{
		return Utils.getCurrentTime("yyyyMMdd");
	}
}
