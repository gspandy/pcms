package com.pujjr.file.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.file.domain.Directory;
@Service
public interface IDirectoryService 
{
	List<Directory> getDirectoryList(boolean enabled);
	
	void addDirectory(Directory record);
	
	void modifyDirectory(Directory record);
	
	void delelteDirectoryById(String id) throws Exception;
	
	List<Directory> getSubDirectoryList(String parentId);
	
	List<Directory> getDirectoryByTemplateId(String templateId);
}
