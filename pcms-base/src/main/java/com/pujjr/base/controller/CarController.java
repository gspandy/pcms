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

@RestController
@RequestMapping(value="/car")
public class CarController extends BaseController {
	
	@Autowired
	private ICarService carService;
	
	@RequestMapping(value="/brand",method=RequestMethod.GET)
	public List<CarBrand> getAllCarBrand()
	{
		return carService.getCarBrandList(null);
	}
	
	@RequestMapping(value="/brand/pagelist",method=RequestMethod.GET)
	public PageVo getCarBrandPageList(QueryParamCarBrandVo params)
	{
		PageHelper.startPage(Integer.parseInt("1"), Integer.parseInt("10"),true);
		List<CarBrand> list = carService.getCarBrandList(params);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
	
	@RequestMapping(value="/brand",method=RequestMethod.POST)
	public void addCarBrand(@RequestBody CarBrand record)
	{
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
		carService.addCarStyle(record);
	}
	
	@RequestMapping(value="/style",method=RequestMethod.PUT)
	public void modifyCarStyle(@RequestBody CarStyle record)
	{
		carService.modifyCarStyle(record);
	}
	
	@RequestMapping(value="/style",method=RequestMethod.DELETE)
	public void deleteCarStyle(@PathVariable String id)
	{
		carService.deleteCarStyle(id);
	}
	
	@RequestMapping(value="/brand/{carBrandId}/serial",method=RequestMethod.GET)
	public List<CarSerial> getAllCarSerialByCarBrandId(@PathVariable String carBrandId)
	{
		return carService.getCarSerialByCarBrandId(carBrandId);
	}
	
	@RequestMapping(value="/serial/{carSerialId}/style",method=RequestMethod.GET)
	public PageVo getAllCarStyleByCarSerialId(@PathVariable String carSerialId,String curPage,String pageSize)
	{
		PageHelper.startPage(Integer.parseInt(curPage), Integer.parseInt(pageSize),true);
		List<CarStyle> list = carService.getCarStyleByCarSerialId(carSerialId);
		PageVo page=new PageVo();
		page.setTotalItem(((Page)list).getTotal());
		page.setData(list);
		return page;
	}
}
