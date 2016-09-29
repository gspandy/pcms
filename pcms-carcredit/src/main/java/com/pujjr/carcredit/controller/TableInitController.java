package com.pujjr.carcredit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pujjr.carcredit.service.ITableInitService;

/**
 * 表格数据初始化
 * @author pujjr
 *
 */
@Controller
public class TableInitController {
	@Autowired
	public ITableInitService tableInitService;
	@ResponseBody
	@RequestMapping(value="init/hisbeanmap",method=RequestMethod.POST)
	public void hisBeanMapInit(){
		tableInitService.hisBeanMapInit();
	}
	
	@ResponseBody
	@RequestMapping(value="init/fieldcomment",method=RequestMethod.POST)
	public void fieldCommentInit(){
		tableInitService.hisFieldCommentInit();
	}
}
