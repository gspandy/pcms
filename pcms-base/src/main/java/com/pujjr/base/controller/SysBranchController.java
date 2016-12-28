package com.pujjr.base.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pujjr.base.domain.GpsLvl;
import com.pujjr.base.domain.GpsRule;
import com.pujjr.base.domain.Product;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.domain.SysBranchDealer;
import com.pujjr.base.service.IGpsService;
import com.pujjr.base.service.IProductService;
import com.pujjr.base.service.ISysAccountService;
import com.pujjr.base.service.ISysBranchService;
import com.pujjr.base.vo.GpsRuleVo;
import com.pujjr.base.vo.PageVo;
import com.pujjr.base.vo.SysBranchVo;

@RestController
@RequestMapping(value="/sysbranch")
public class SysBranchController  extends BaseController
{
	@Autowired
	private ISysBranchService sysBranchService;
	@Autowired
	private ISysAccountService sysAccountServcie;
	@Autowired
	private IGpsService gpsService;
	@Autowired
	private IProductService productService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<SysBranch> getSysBranchList()
	{
		return sysBranchService.getSysBranchList();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public void addSysBranch(@RequestBody SysBranch sysBranch)
	{
		sysBranchService.addSysBranch(sysBranch);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public void deleteSysBranch(@PathVariable String id)
	{
		sysBranchService.deleteSysBranchById(id);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public void modifySysBranch(@RequestBody SysBranchVo sysBranchVo)
	{
		sysBranchService.modifySysBranch(sysBranchVo);
	}
	
	@RequestMapping(value="/{branchId}",method=RequestMethod.GET)
	public SysBranchVo getSysBranchByBranchId(@PathVariable String branchId)
	{
		SysBranchVo vo = new SysBranchVo();
		SysBranch branchPo = sysBranchService.getSysBranch(branchId, null);
		vo.setSysBranch(branchPo);
		//如果是经销商需返回经销商信息
		if(branchPo.getBranchType().equals("JGLX02"))
		{
			//经销商信息
			SysBranchDealer dealerPo = sysBranchService.getDealerByBranchId(branchId);
			vo.setSysBranchDealer(dealerPo);
			//GPS信息
			List<GpsRuleVo> gpsRuleVos = new ArrayList<GpsRuleVo>();
			List<GpsRule> gpsRulePos = gpsService.getAllGpsRuleList();
			for(GpsRule gpsRulePo : gpsRulePos)
			{
				GpsRuleVo gpsRuleVo = new GpsRuleVo();
				BeanUtils.copyProperties(gpsRulePo, gpsRuleVo);
				List<GpsLvl> gpsLvlPos = gpsService.getGpsLvlListByBranchIdAndRuleId(branchId, gpsRulePo.getId());
				gpsRuleVo.setGpsLvls(gpsLvlPos);
				gpsRuleVos.add(gpsRuleVo);
			}
			//可用产品信息
			List<Product> productList = productService.getBranchEnableProductList(branchId);
			vo.setProductList(productList);
			
			vo.setGpsRuleVoList(gpsRuleVos);
		}
		return vo;
	}
	@RequestMapping(value="/{branchId}/accounts",method=RequestMethod.GET)
	public PageVo getSysAccountListByBranchId(@PathVariable String branchId,String curPage,String pageSize)
	{
		PageHelper.startPage(Integer.parseInt(curPage), Integer.parseInt(pageSize),true);
		List<SysAccount> list = sysAccountServcie.getSysAccountListByBranchId(branchId);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	
	@RequestMapping(value="/getDealerList",method=RequestMethod.GET)
	public List<SysBranch> getDealerList()
	{
		List<SysBranch> list = sysBranchService.getDealerList();
		SysBranch root = sysBranchService.getSysBranch("", "0001");
		list.add(root);
		return list;
	}
}
