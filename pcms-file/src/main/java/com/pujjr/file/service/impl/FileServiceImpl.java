package com.pujjr.file.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.file.dao.FileMapper;
import com.pujjr.file.po.CategoryDirectoryPo;
import com.pujjr.file.service.IFileService;
@Service
public class FileServiceImpl implements IFileService 
{
	@Autowired
	private FileMapper fileDao;
	
	@Override
	public List<CategoryDirectoryPo> getApplyFormCategoryDirectoryList(String categoryKey, String appId) {
		// TODO Auto-generated method stub
		return fileDao.selectApplyFormCategoryDirectoryList(categoryKey, appId);
	}

}
