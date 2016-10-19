package com.pujjr.base.controller;

import java.util.List;

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
import com.pujjr.base.domain.ProductRule;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.service.ICarService;
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
	public PageVo getCarStylePageList(QueryParamCarStyleVo param)
	{
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<CarStyle> list = carService.getCarStyleList(param);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
}
