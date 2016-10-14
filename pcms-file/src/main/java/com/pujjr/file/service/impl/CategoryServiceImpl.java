package com.pujjr.file.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.file.dao.DirectoryCategoryMapper;
import com.pujjr.file.domain.DirectoryCategory;
import com.pujjr.file.service.ICategoryService;
@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	private DirectoryCategoryMapper categoryDao;
	
	@Override
	public List<DirectoryCategory> getCategoryList() {
		// TODO Auto-generated method stub
		return categoryDao.selectAll();
	}

	@Override
	public void addCategory(DirectoryCategory record) {
		// TODO Auto-generated method stub
		categoryDao.insert(record);
	}

	@Override
	public void modifyCategory(DirectoryCategory record) {
		// TODO Auto-generated method stub
		categoryDao.updateByPrimaryKey(record);
	}

	@Override
	public void deleteCategoryById(String id) {
		// TODO Auto-generated method stub
		categoryDao.deleteByPrimaryKey(id);
	}

	@Override
	public List<DirectoryCategory> getCategoryByTemplateId(String templateId) {
		// TODO Auto-generated method stub
		return categoryDao.selectCatetoryByTemplateId(templateId);
	}

}
