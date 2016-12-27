package com.pujjr.file.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.file.domain.Directory;
@Service
@Transactional(rollbackFor=Exception.class)
public interface IDirectoryService 
{
	List<Directory> getDirectoryList(boolean enabled);
	
	void addDirectory(Directory record);
	
	void modifyDirectory(Directory record);
	
	void delelteDirectoryById(String id) throws Exception;
	
	List<Directory> getSubDirectoryList(String parentId);
	
	List<Directory> getDirectoryByTemplateId(String templateId);
	
	List<Directory> getTemplateCategoryDirectory(String templateId,String categoryId,boolean required);
	
	Directory getDirectoryByDirName(String dirName);
}
