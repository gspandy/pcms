package com.pujjr.file.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.file.domain.DirectoryCategory;
@Service
@Transactional(rollbackFor=Exception.class)
public interface ICategoryService 
{
	List<DirectoryCategory> getCategoryList();
	
	void addCategory(DirectoryCategory record);
	
	void modifyCategory(DirectoryCategory record);
	
	void deleteCategoryById(String id);

	List<DirectoryCategory> getCategoryByTemplateId(String templateId);
}
