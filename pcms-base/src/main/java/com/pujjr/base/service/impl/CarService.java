package com.pujjr.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.dao.CarBrandMapper;
import com.pujjr.base.dao.CarSerialMapper;
import com.pujjr.base.dao.CarStyleMapper;
import com.pujjr.base.domain.CarBrand;
import com.pujjr.base.domain.CarSerial;
import com.pujjr.base.domain.CarStyle;
import com.pujjr.base.service.ICarService;
import com.pujjr.base.vo.QueryParamCarBrandVo;
import com.pujjr.base.vo.QueryParamCarSerialVo;
import com.pujjr.base.vo.QueryParamCarStyleVo;
@Service
public class CarService implements ICarService {
	
	@Autowired
	private CarBrandMapper carBrandDao;
	@Autowired
	private CarSerialMapper carSerialDao;
	@Autowired
	private CarStyleMapper carStyleDao;
	
	@Override
	public List<CarBrand> getCarBrandList(QueryParamCarBrandVo params) {
		// TODO Auto-generated method stub
		return carBrandDao.selectAll(params);
	}

	@Override
	public void addCarBrand(CarBrand record) {
		// TODO Auto-generated method stub
		carBrandDao.insert(record);
	}

	@Override
	public void modifyCarBrand(CarBrand record) {
		// TODO Auto-generated method stub
		carBrandDao.updateByPrimaryKey(record);
	}

	@Override
	public void deleteCarBrand(String id) {
		// TODO Auto-generated method stub
		carBrandDao.deleteByPrimaryKey(id);
	}

	@Override
	public List<CarSerial> getCarSerialList(QueryParamCarSerialVo param) 
	{
		// TODO Auto-generated method stub
		return carSerialDao.selectAll(param);
	}

	@Override
	public void addCarSerial(CarSerial record) {
		// TODO Auto-generated method stub
		carSerialDao.insert(record);
	}

	@Override
	public void modifyCarSerial(CarSerial record) {
		// TODO Auto-generated method stub
		carSerialDao.updateByPrimaryKey(record);
	}

	@Override
	public void deleteCarSerial(String id) {
		// TODO Auto-generated method stub
		carSerialDao.deleteByPrimaryKey(id);
	}

	@Override
	public List<CarStyle> getCarStyleList(QueryParamCarStyleVo param) {
		// TODO Auto-generated method stub
		return carStyleDao.selectAll(param);
	}

	@Override
	public void addCarStyle(CarStyle record) {
		// TODO Auto-generated method stub
		//查找品牌及车系
		CarBrand brand = carBrandDao.selectBrandBySerialId(record.getCarSerialId());
		CarSerial serial = carSerialDao.selectByPrimaryKey(record.getCarSerialId());
		//创建索引字段品牌名称-车系-车型-排量
		record.setIndexStr(brand.getBrandName()+"-"+serial.getCarSerialName()+"-"+record.getCarStyleName()+"-"+record.getDisplacement());
		carStyleDao.insert(record);
	}

	@Override
	public void modifyCarStyle(CarStyle record) {
		// TODO Auto-generated method stub
		CarBrand brand = carBrandDao.selectBrandBySerialId(record.getCarSerialId());
		CarSerial serial = carSerialDao.selectByPrimaryKey(record.getCarSerialId());
		//创建索引字段品牌名称-车系-车型-排量
		record.setIndexStr(brand.getBrandName()+"-"+serial.getCarSerialName()+"-"+record.getCarStyleName()+"-"+record.getDisplacement());
		carStyleDao.updateByPrimaryKey(record);
	}

	@Override
	public void deleteCarStyle(String id) {
		// TODO Auto-generated method stub
		carStyleDao.deleteByPrimaryKey(id);
	}

	@Override
	public CarStyle getCarStyleById(String id) {
		// TODO Auto-generated method stub
		return carStyleDao.selectByPrimaryKey(id);
	}

}
