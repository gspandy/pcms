package com.pujjr.file.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.file.domain.DirectoryCategory;
@Service
public interface ICategoryService 
{
	List<DirectoryCategory> getCategoryList();
	
	void addCategory(DirectoryCategory record);
	
	void modifyCategory(DirectoryCategory record);
	
	void deleteCategoryById(String id);
	
}
