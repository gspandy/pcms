package com.pujjr.file.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.file.domain.Directory;
import com.pujjr.file.domain.DirectoryCategory;
import com.pujjr.file.domain.DirectoryTemplate;
@Service
public interface ITemplateService 
{
	List<DirectoryTemplate> getTemplateList(boolean enabled);
	
	void addTemplate(DirectoryTemplate record);
	
	void modifyTemplate(DirectoryTemplate record);
	
	void deleteTemplateById(String id);
	
	void saveTemplateDirectory(String templateId,List<Directory> records);
	
	void saveTemplateCategory(String templateId,List<DirectoryCategory> records);
}
