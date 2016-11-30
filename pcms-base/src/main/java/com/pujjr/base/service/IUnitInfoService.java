package com.pujjr.base.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.base.domain.UnitInfo;

@Service
public interface IUnitInfoService 
{
	public List<HashMap<String,Object>> getUnitInfoList(boolean enabled,String unitType);
	
	public void addUnitInfo(UnitInfo record);
	
	public void modifyUnitInfo(UnitInfo record);
	
	public void deleteUnitInfoById(String id);
	
	public UnitInfo getUnitInfoById(String id);
}
