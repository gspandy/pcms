package com.pujjr.base.service;

import java.util.List;

import com.pujjr.base.domain.CarBrand;
import com.pujjr.base.domain.CarSerial;
import com.pujjr.base.domain.CarStyle;

public interface ICarService 
{
	public List<CarBrand> getCarBrandList();
	
	public void addCarBrand(CarBrand record);
	
	public void modifyCarBrand(CarBrand record);
	
	public void deleteCarBrand(String id);
	
	public List<CarSerial> getCarSerialByCarBrandId(String id);
	
	public void addCarSerial(String carBrandId,CarSerial record);
	
	public void modifyCarSerial(CarSerial record);
	
	public void deleteCarSerial(String id);
	
	public List<CarStyle> getCarStyleByCarSerialId(String id);
	
	public void addCarStyle(String carSerialId,String CarStyle);
	
	public void modifyCarStyle(CarStyle record);
	
	public void deleteCarStyle(String id);
	
	public CarStyle getCarStyleById(String id);
}
