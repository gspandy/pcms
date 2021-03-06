package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.base.domain.SysDictData;
import com.pujjr.base.domain.SysDictType;
@Service
public interface ISysDictService 
{
	public List<SysDictType> getSysDictTypeList();
	
	public void  addSysDictType(SysDictType record);

	public void deleteSysDictTypeById(String id) throws Exception;
	
	public void modifySysDictType(SysDictType record);
	
	public List<SysDictData> getDictDataListByDictTypeId(String dictTypeId);
	
	public List<SysDictData> getDictDataListByDictTypeCode(String dictTypeCode);
	
	public void addSysDictData(SysDictData record);
	
	public void deleteSysDictDataById(String id);
	
	public void modifySysDictData(SysDictData record);
	
	
}
