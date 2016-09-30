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
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.vo.PageVo;
import com.pujjr.carcredit.domain.Apply;
import com.pujjr.carcredit.po.ApplyInfoPo;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.service.impl.ApplyServiceImpl;
import com.pujjr.carcredit.vo.ApplyStatus;
import com.pujjr.carcredit.vo.ApplyUncommitVo;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.utils.Utils;

/**
 * 
 * @author pujjr 2016-09-18
 *
 */
@Controller
//@RequestMapping(value="/apply")
public class ApplyController {
	@Autowired
	private IApplyService applyService;
//	private ApplyServiceImpl applyService;
	@Value("${pcms.sys_mode}") 
	private String sysMode = "debug";
	
	@ResponseBody
	@RequestMapping(value="/apply/{id}/{name}",method=RequestMethod.GET)
	public JSONArray clientInfoQuery(@PathVariable String id,@PathVariable String name){
		System.out.println("clientInfoQuery");
		JSONArray ja = new JSONArray();
		return ja;
	}
	
	/**
	 * 保存申请订单
	 * @param applyVo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/apply",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String,Object> applySave(@RequestBody ApplyVo applyVo,HttpServletRequest request) throws Exception{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		String appId = "";
		System.out.println("ApplyController->sysMode："+sysMode);
		HashMap<String,Object> map = new HashMap<String,Object>();
		if("debug".equals(sysMode))
			appId = applyService.saveApply(applyVo,"333");
		else
			appId = applyService.saveApply(applyVo,sysAccount.getAccountId());
		map.put("appId", appId);
		return map;
		
	}
	
	/**
	 * 查询未提交订单
	 * @param applyVo
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/apply/unCommit/list",method=RequestMethod.GET)
	public PageVo getUnCommitApplyList(String curPage,String pageSize,HttpServletRequest request) 
	{
		System.out.println("ApplyController->unCommit");
		PageHelper.startPage(Integer.parseInt(curPage), Integer.parseInt(pageSize),true);
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		List<Apply> applyList = new ArrayList<Apply>();
		List<ApplyInfoPo> poList = applyService.getApplyInfoList(sysAccount.getAccountId(),ApplyStatus.UNCOMMIT);
		List<ApplyUncommitVo> voList = new ArrayList<ApplyUncommitVo>();
		for(ApplyInfoPo po : poList)
		{
			ApplyUncommitVo vo = new ApplyUncommitVo();
			BeanUtils.copyProperties(po,vo);
			voList.add(vo);
		}
		PageVo page=new PageVo();
		page.setTotalItem(((Page)poList).getTotal());
		page.setData(voList);
		return page;
	}
	
	/**
	 * 查询未提交订单列表
	 * @param applyVo
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/apply/{appId}",method=RequestMethod.GET)
	public Apply getApplyDetail(HttpServletRequest request,@PathVariable String appId) throws Exception{
		System.out.println("ApplyController->getApplyDetail");
		ApplyVo applyVo = applyService.getApplyDetail(appId);
		System.out.println(applyVo);
		return applyVo;
	}
	@ResponseBody
	@RequestMapping(value="/apply/commitApplyTask",method=RequestMethod.POST)
	public void commitApplyTask(@RequestBody ApplyVo applyVo,HttpServletRequest request)
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		applyService.commitApplyTask(applyVo, sysAccount);
	}
}
