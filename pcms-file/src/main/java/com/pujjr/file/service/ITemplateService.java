package com.pujjr.file.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.file.domain.Directory;
import com.pujjr.file.domain.DirectoryCategory;
import com.pujjr.file.domain.DirectoryTemplate;
import com.pujjr.file.vo.TemplateCategoryVo;
@Service
public interface ITemplateService 
{
	List<DirectoryTemplate> getTemplateList(boolean enabled);
	
	void addTemplate(DirectoryTemplate record);
	
	void modifyTemplate(DirectoryTemplate record);
	
	void deleteTemplateById(String id);
	
	void saveTemplateDirectory(String templateId,List<Directory> records);
	
	void saveTemplateCategory(String templateId,List<DirectoryCategory> records);
	
	TemplateCategoryVo getTemplateCategoryInfo(String templateId,String categoryId);
	
	void saveTemplateCategoryDirectory(String templateId,String categoryId,List<Directory> records);
	
	void saveTemplateCategoryRequireDirectory(String templateId,String categoryId,List<Directory> records);
	
}
