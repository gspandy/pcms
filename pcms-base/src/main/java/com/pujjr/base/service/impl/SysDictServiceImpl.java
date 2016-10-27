package com.pujjr.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.dao.SysDictDataMapper;
import com.pujjr.base.dao.SysDictTypeMapper;
import com.pujjr.base.domain.SysDictData;
import com.pujjr.base.domain.SysDictType;
import com.pujjr.base.service.ISysDictService;
import com.pujjr.utils.Utils;
@Service
public class SysDictServiceImpl implements ISysDictService {

	@Autowired
	private SysDictTypeMapper sysDictTypeDao;
	@Autowired
	private SysDictDataMapper sysDictDataDao;
	
	public List<SysDictType> getSysDictTypeList() {
		// TODO Auto-generated method stub
		return sysDictTypeDao.selectAll();
	}

	public void addSysDictType(SysDictType record) {
		// TODO Auto-generated method stub
		record.setId(Utils.get16UUID());
		sysDictTypeDao.insert(record);
	}

	public void deleteSysDictTypeById(String id) throws Exception {
		// TODO Auto-generated method stub
		if(sysDictTypeDao.selectAllByParentId(id).size()>0)
		{
			throw new Exception("拥有子节点数据，请先删除再执行删除操作！");
		}
		List<SysDictData> dictData = sysDictDataDao.selectAllWithFilter(id,null);
		if(dictData.size()>0)
		{
			throw new Exception("拥有字典数据，请先删除字典数据再执行删除操作！");
		}
		sysDictTypeDao.deleteByPrimaryKey(id);
	}

	public void modifySysDictType(SysDictType record) {
		// TODO Auto-generated method stub
		sysDictTypeDao.updateByPrimaryKey(record);
	}

	public List<SysDictData> getDictDataListByDictTypeId(String dictTypeId) {
		// TODO Auto-generated method stub
		return sysDictDataDao.selectAllWithFilter(dictTypeId,null);
	}

	public void addSysDictData(SysDictData record) {
		// TODO Auto-generated method stub
		record.setId(Utils.get16UUID());
		record.setEnabled(true);
		sysDictDataDao.insert(record);
	}

	public void deleteSysDictDataById(String id) {
		// TODO Auto-generated method stub
		sysDictDataDao.deleteByPrimaryKey(id);
	}

	public void modifySysDictData(SysDictData record) {
		// TODO Auto-generated method stub
		sysDictDataDao.updateByPrimaryKey(record);
	}

	public List<SysDictData> getDictDataListByDictTypeCode(String dictTypeCode) {
		// TODO Auto-generated method stub
		return sysDictDataDao.selectAllWithFilter(null, dictTypeCode);
	}

	@Override
	public SysDictData getDictDataByDictDateCode(String dictDataCode) {
		// TODO Auto-generated method stub
		return sysDictDataDao.selectByDictDataCode(dictDataCode);
	}

}
