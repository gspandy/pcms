package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.base.domain.SysArea;
import com.pujjr.base.vo.SysAreaVo;
@Service
public interface ISysAreaService 
{
	public List<SysArea> getSysAreaList();
	
	public void  addSysArea(SysArea record);

	public void deleteSysAreaById(String id);
	
	public void modifySysArea(SysArea record);
	
	public List<SysArea> getSysAreaListByParentId(String parentId);
	
	public List<SysArea> getProvinceList();
	
	public List<SysArea> getCityByProvinceId(String provinceId);
	
	public List<SysArea> getCountyByCityId(String cityId);
}
