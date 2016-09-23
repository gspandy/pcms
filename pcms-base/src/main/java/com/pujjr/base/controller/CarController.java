package com.pujjr.base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pujjr.base.domain.CarBrand;
import com.pujjr.base.domain.CarSerial;
import com.pujjr.base.domain.CarStyle;
import com.pujjr.base.domain.ProductRule;
import com.pujjr.base.service.ICarService;
import com.pujjr.base.vo.PageVo;

@RestController
@RequestMapping(value="/car")
public class CarController extends BaseController {
	
	@Autowired
	private ICarService carService;
	
	@RequestMapping(value="/brand",method=RequestMethod.GET)
	public List<CarBrand> getAllCarBrand()
	{
		return carService.getCarBrandList();
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
