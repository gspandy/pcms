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
import com.pujjr.carcredit.constant.ApplyStatus;
import com.pujjr.carcredit.domain.Apply;
import com.pujjr.carcredit.po.ApplyInfoPo;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.vo.ApplyUncommitVo;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.carcredit.vo.QueryParamApplyVo;
import com.pujjr.utils.Utils;
/**
 * 
 * @author pujjr 2016-09-18
 *
 */
@Controller
//@RequestMapping(value="/apply")
public class ApplyController extends BaseController {
	@Autowired
	private IApplyService applyService;
//	private ApplyServiceImpl applyService;
	@Value("${pcms.sys_mode}") 
	private String sysMode = "debug";
	
	/**
	 * 查询申请订单表信息 2016-10-19
	 * @param applyVo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/apply/select/{appid}",method=RequestMethod.GET)
	@ResponseBody
	public Apply applySelect(@PathVariable String appid,HttpServletRequest request) throws Exception{
		return applyService.getApply(appid);
	}
	
	/**
	 * 更新申请订单表信息 2016-10-19
	 * @param applyVo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/apply/update",method=RequestMethod.POST)
	@ResponseBody
	public void applyUpdate(@RequestBody ApplyVo applyVo,HttpServletRequest request) throws Exception{
		applyService.updateApply(applyVo);
	}
	
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
		double minLoanAmt = applyVo.getProduct().getMinLoanAmount();
		double maxLoanAmt = applyVo.getProduct().getMaxLoanAmount();
		double totalFinanceAmt = applyVo.getTotalFinanceAmt();
		if(Double.compare(totalFinanceAmt, minLoanAmt)<0 || Double.compare(totalFinanceAmt, maxLoanAmt)>0)
		{
			throw new Exception("融资金额错误："+minLoanAmt+"=<融资金额<="+maxLoanAmt);
		}
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		String appId = "";
		HashMap<String,Object> map = new HashMap<String,Object>();
		appId = applyService.saveApply(applyVo,sysAccount.getAccountId());
		map.put("appId", appId);
		return map;
		
	}
	
	/**
	 * 查询未提交订单列表
	 * @param applyVo
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/apply/unCommit/list",method=RequestMethod.GET)
	public PageVo getUnCommitApplyList(QueryParamApplyVo param,HttpServletRequest request) 
	{
		System.out.println("ApplyController->unCommit");
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		List<Apply> applyList = new ArrayList<Apply>();
		List<ApplyInfoPo> poList = applyService.getApplyInfoList(sysAccount.getAccountId(),ApplyStatus.UNCOMMIT,param);
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
	 * 查询订单详情
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
