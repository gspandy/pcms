package com.pujjr.base.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pujjr.base.domain.CarBrand;
import com.pujjr.base.domain.CarSerial;
import com.pujjr.base.domain.CarStyle;
import com.pujjr.base.domain.CarTemplate;
import com.pujjr.base.domain.CarTemplateChoice;
import com.pujjr.base.domain.ProductRule;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.domain.SysBranchDealer;
import com.pujjr.base.service.ICarCreditBusinessService;
import com.pujjr.base.service.ICarService;
import com.pujjr.base.service.ISysBranchService;
import com.pujjr.base.vo.CarTreeVo;
import com.pujjr.base.vo.PageVo;
import com.pujjr.base.vo.QueryParamCarBrandVo;
import com.pujjr.base.vo.QueryParamCarSerialVo;
import com.pujjr.base.vo.QueryParamCarStyleVo;
import com.pujjr.utils.Utils;

@RestController
@RequestMapping(value="/car")
public class CarController extends BaseController {
	
	@Autowired
	private ICarService carService;
	@Autowired
	private ICarCreditBusinessService carCreditBusinessService;
	@Autowired
	private ISysBranchService sysBranchService;
	
	@RequestMapping(value="/brand",method=RequestMethod.GET)
	public List<CarBrand> getAllCarBrand()
	{
		QueryParamCarBrandVo param = new QueryParamCarBrandVo();
		return carService.getCarBrandList(param);
	}
	
	@RequestMapping(value="/brand/pagelist",method=RequestMethod.GET)
	public PageVo getCarBrandPageList(QueryParamCarBrandVo param)
	{
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<CarBrand> list = carService.getCarBrandList(param);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	
	@RequestMapping(value="/brand",method=RequestMethod.POST)
	public void addCarBrand(@RequestBody CarBrand record)
	{
		record.setId(Utils.get16UUID());
		carService.addCarBrand(record);
	}
	@RequestMapping(value="/brand/{id}",method=RequestMethod.PUT)
	public void modifyCarBrand(@RequestBody CarBrand record)
	{
		carService.modifyCarBrand(record);
	}
	@RequestMapping(value="/brand/{id}",method=RequestMethod.DELETE)
	public void deleteCarBrandById(@PathVariable String id)
	{
		carService.deleteCarBrand(id);
	}
	
	@RequestMapping(value="/serial",method=RequestMethod.POST)
	public void addCarSerial(@RequestBody CarSerial record)
	{
		record.setId(Utils.get16UUID());
		carService.addCarSerial(record);
	}
	
	@RequestMapping(value="/serial/{id}",method=RequestMethod.PUT)
	public void modifyCarSerial(@RequestBody CarSerial record)
	{
		carService.modifyCarSerial(record);
	}
	
	@RequestMapping(value="/serial/{id}",method=RequestMethod.DELETE)
	public void deleteCarSerial(@PathVariable String id)
	{
		carService.deleteCarSerial(id);
	}
	
	@RequestMapping(value="/style",method=RequestMethod.POST)
	public void addCarStyle(@RequestBody CarStyle record)
	{
		record.setId(Utils.get16UUID());
		carService.addCarStyle(record);
	}
	
	@RequestMapping(value="/style/{id}",method=RequestMethod.PUT)
	public void modifyCarStyle(@RequestBody CarStyle record)
	{
		carService.modifyCarStyle(record);
	}
	
	@RequestMapping(value="/style/{id}",method=RequestMethod.DELETE)
	public void deleteCarStyle(@PathVariable String id)
	{
		carService.deleteCarStyle(id);
	}
	
	@RequestMapping(value="/brand/{carBrandId}/serial",method=RequestMethod.GET)
	public List<CarSerial> getAllCarSerialByCarBrandId(@PathVariable String carBrandId)
	{
		QueryParamCarSerialVo param = new QueryParamCarSerialVo();
		param.setCarBrandId(carBrandId);
		return carService.getCarSerialList(param);
	}
	@RequestMapping(value="/serial/pagelist",method=RequestMethod.GET)
	public PageVo getCarSerialPageList(QueryParamCarSerialVo param)
	{
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<CarSerial> list = carService.getCarSerialList(param);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	@RequestMapping(value="/serial/{carSerialId}/style",method=RequestMethod.GET)
	public List<CarStyle> getAllCarStyleByCarSerialId(@PathVariable String carSerialId,String curPage,String pageSize)
	{
		QueryParamCarStyleVo param = new QueryParamCarStyleVo();
		param.setCarSerialId(carSerialId);
		return carService.getCarStyleList(param);
	}
	@RequestMapping(value="/style/pagelist",method=RequestMethod.GET)
	public PageVo getCarStylePageList(QueryParamCarStyleVo param,HttpServletRequest request)
	{
		HashMap<String,Object> applyInfo = carCreditBusinessService.getApplyInfo(param.getAppId());
		String branchId="";
		if(applyInfo==null)
		{
			SysAccount sysAccount = (SysAccount)request.getAttribute("account");
			branchId = sysAccount.getBranchId();
		}
		else
		{
			String branchCode = applyInfo.get("CREATE_BRANCH_CODE").toString();
			SysBranch sysBranch = sysBranchService.getSysBranch(null, branchCode);
			branchId = sysBranch.getId();
		}
		
		SysBranchDealer dealer = sysBranchService.getDealerByBranchId(branchId);
		String carTemplateId = dealer.getReserver2();
		param.setCarTemplateId(carTemplateId);
		
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<CarStyle> list = carService.getCarStyleList(param);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	@RequestMapping(value="/template",method=RequestMethod.GET)
	public List<CarTemplate> getCarTemplateList()
	{
		return carService.getCarTemplateList();
	}
	@RequestMapping(value="/template",method=RequestMethod.POST)
	public void addCarTemplate(@RequestBody CarTemplate record,HttpServletRequest request)
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		record.setId(Utils.get16UUID());
		record.setCreateId(sysAccount.getAccountId());
		record.setCreateTime(new Date());
		carService.addCarTemplate(record);
	}
	@RequestMapping(value="/template/{templateId}",method=RequestMethod.PUT)
	public void modifyCarTemplate(@RequestBody CarTemplate record,HttpServletRequest request)
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		record.setUpdateId(sysAccount.getAccountId());
		record.setUpdateTime(new Date());
		carService.modifyCarTemplate(record);
	}
	@RequestMapping(value="/template/{templateId}",method=RequestMethod.DELETE)
	public void deleteCarTemplateById(@PathVariable String templateId)
	{
		carService.deleteCarTemplateById(templateId);
	}
	/**
	 * 创建一个车辆数据的树形数据
	 * **/
	@RequestMapping(value="/getCarTreeList",method=RequestMethod.GET)
	public List<CarTreeVo> getCarTreeList()
	{
		List<CarTreeVo> list = new ArrayList<CarTreeVo>();
		//创建顶级节点
		CarTreeVo lvlTop = new CarTreeVo();
		
		lvlTop.setId("top0001");
		lvlTop.setLabel("车辆信息 ");
		lvlTop.setType("0");
		lvlTop.setParentId("0000");
		list.add(lvlTop);
		//获取车辆品牌加入顶级节点
		List<CarBrand> carBrandList = carService.getCarBrandList(null);
		for(CarBrand brandItem : carBrandList)
		{
			CarTreeVo brandNode = new CarTreeVo();
			brandNode.setId(brandItem.getId());
			brandNode.setLabel(brandItem.getBrandName());
			brandNode.setType("brand");
			brandNode.setParentId("top0001");
			list.add(brandNode);
			QueryParamCarSerialVo serialParamVo =new QueryParamCarSerialVo();
			serialParamVo.setCarBrandId(brandItem.getId());
			List<CarSerial> carSerialList = carService.getCarSerialList(serialParamVo);
			for(CarSerial serialItem : carSerialList)
			{
				CarTreeVo serialNode = new CarTreeVo();
				serialNode.setId(serialItem.getId());
				serialNode.setLabel(serialItem.getCarSerialName());
				serialNode.setType("serial");
				serialNode.setParentId(brandItem.getId());
				list.add(serialNode);
			}
		}
		return list;
		
	}
	@RequestMapping(value="/getCarTemplateChoiceList/{templateId}",method=RequestMethod.GET)
	public List<CarTemplateChoice> getCarTemplateChoiceList(@PathVariable String templateId)
	{
		return carService.getCarTemplateChoiceList(templateId);
	}
	@RequestMapping(value="/saveCarTemplateChoice/{templateId}",method=RequestMethod.POST)
	public void saveCarTemplateChoice(@PathVariable String templateId,@RequestBody List<CarTreeVo> vos)
	{
		List<CarTemplateChoice> list = new ArrayList<CarTemplateChoice>();
		for(CarTreeVo vo : vos)
		{
			if(vo.getType().equals("brand")||vo.getType().equals("serial"))
			{
				CarTemplateChoice po = new CarTemplateChoice();
				po.setId(Utils.get16UUID());
				po.setTplId(templateId);
				po.setCarDataType(vo.getType());
				po.setCarDataId(vo.getId());
				list.add(po);
			}
			
		}
		carService.saveCarTemplateChoice(templateId, list);
	}
	
	@RequestMapping(value="/getCurrentApplyEnabledCarBrand/{appId}",method=RequestMethod.GET)
	public List<CarBrand> getCurrentApplyEnabledCarBrand(@PathVariable String appId,HttpServletRequest request)
	{
		//查询这个申请单是否存在，如果不存在则认为是新单，此时通过账户信息获取账户机构信息得到车型选择模板ID
		HashMap<String,Object> applyInfo = carCreditBusinessService.getApplyInfo(appId);
		String branchId="";
		if(applyInfo==null)
		{
			SysAccount sysAccount = (SysAccount)request.getAttribute("account");
			branchId = sysAccount.getBranchId();
		}
		else
		{
			String branchCode = applyInfo.get("CREATE_BRANCH_CODE").toString();
			SysBranch sysBranch = sysBranchService.getSysBranch(null, branchCode);
			branchId = sysBranch.getId();
		}
		
		SysBranchDealer dealer = sysBranchService.getDealerByBranchId(branchId);
		String carTemplateId = dealer.getReserver2();
		List<CarBrand> carBrandList = carService.getCarBrandListByTemplateId(carTemplateId);
		return carBrandList;
	}
	
	@RequestMapping(value="/getCurrentApplyEnabledCarSerial/{appId}/{carBrandId}",method=RequestMethod.GET)
	public List<CarSerial> getCurrentApplyEnabledCarSerial(@PathVariable String appId,@PathVariable String carBrandId,HttpServletRequest request)
	{
		//查询这个申请单是否存在，如果不存在则认为是新单，此时通过账户信息获取账户机构信息得到车型选择模板ID
		HashMap<String,Object> applyInfo = carCreditBusinessService.getApplyInfo(appId);
		String branchId="";
		if(applyInfo==null)
		{
			SysAccount sysAccount = (SysAccount)request.getAttribute("account");
			branchId = sysAccount.getBranchId();
		}
		else
		{
			String branchCode = applyInfo.get("CREATE_BRANCH_CODE").toString();
			SysBranch sysBranch = sysBranchService.getSysBranch(null, branchCode);
			branchId = sysBranch.getId();
		}
		
		SysBranchDealer dealer = sysBranchService.getDealerByBranchId(branchId);
		String carTemplateId = dealer.getReserver2();
		List<CarSerial> carSerialList = carService.getCarSerialListByTemplateIdAndCarBrandId(carTemplateId, carBrandId);
		return carSerialList;
	}
	
	
}
