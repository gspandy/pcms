package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.domain.FormField;
import com.pujjr.base.domain.FormFieldTemplate;

@Service
@Transactional(rollbackFor=Exception.class)
public interface IFormFieldService 
{
	public List<FormField> getFormFieldList();
	
	public void addFormField(FormField record);
	
	public void modifyFormField(FormField record);
	
	public void deleteFormFieldById(String id);
	
	public List<FormField> getSubFormFieldByParentId(String parentId);
	
	public List<FormFieldTemplate> getFormFieldTemplateList();
	
	public void addFormFieldTemplate(FormFieldTemplate record);
	
	public void modifyFormFieldTemplate(FormFieldTemplate record);
	
	public void deleteFormFieldTemplateById(String id);
	
	public List<FormField> getTemplateRequiredFormField(String templateId); 
	
	public void saveTemplateRequiredFormField(String templateId,List<FormField> requiredFields);
}
