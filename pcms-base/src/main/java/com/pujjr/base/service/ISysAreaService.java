package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.domain.SysArea;
import com.pujjr.base.vo.SysAreaVo;
@Service
@Transactional(rollbackFor=Exception.class)
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
	
	public String getAreaNameById(String id);
	/**
	 * 获取地址全部信息
	 * @param provinceId 省ID
	 * @param cityId 市ID
	 * @param countryId 区ID
	 * @return
	 */
	public String getFullAddress(String provinceId,String cityId,String countryId);
}
