package com.pujjr.file.service;

import java.util.List;

import com.pujjr.file.domain.DirectoryCategory;

public interface ICategoryService 
{
	List<DirectoryCategory> getCategoryList();
	
	void addCategory(DirectoryCategory record);
	
	void modifyCategory(DirectoryCategory record);
	
	void deleteCategoryById(String id);
	
}
