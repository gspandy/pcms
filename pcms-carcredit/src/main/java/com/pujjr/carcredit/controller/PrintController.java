package com.pujjr.carcredit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pujjr.base.controller.BaseController;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.vo.PageVo;
import com.pujjr.carcredit.domain.Apply;
import com.pujjr.carcredit.po.ApplyInfoPo;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.service.IPrintService;
import com.pujjr.carcredit.vo.ApplyStatus;
import com.pujjr.carcredit.vo.ApplyUncommitVo;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.utils.Utils;
/**
 * 
 * @author pujjr 2016-10-24
 *
 */
@Controller
@RequestMapping(value="/print")
public class PrintController extends BaseController {
	@Autowired
	private IPrintService printServiceImpl;
	@Value("${pcms.sys_mode}") 
	private String sysMode = "debug";
	
	/**
	 * 打印租赁合同
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="/leasecontract",method=RequestMethod.GET)
	@ResponseBody
	public void applySelect(HttpServletRequest request) throws Exception{
//		System.out.println("request.getSession().getServletContext():"+);
		String url = request.getSession().getServletContext().getRealPath("");
		printServiceImpl.prtLeaseContract("2008", "WZXK161020N10136",url);
	}
}
