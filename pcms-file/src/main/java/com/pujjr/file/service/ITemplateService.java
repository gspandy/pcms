package com.pujjr.file.service;

import java.util.List;

import com.pujjr.file.domain.DirectoryTemplate;

public interface ITemplateService 
{
	List<DirectoryTemplate> getTemplateList(boolean enabled);
	
	void addTemplate(DirectoryTemplate record);
	
	void modifyTemplate(DirectoryTemplate record);
	
	void deleteTemplateById(String id);
}
