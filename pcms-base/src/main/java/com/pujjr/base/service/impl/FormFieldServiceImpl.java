package com.pujjr.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.dao.FormFieldMapper;
import com.pujjr.base.dao.FormFieldTemplateMapper;
import com.pujjr.base.dao.FormFieldTemplateRequiredMapper;
import com.pujjr.base.domain.FormField;
import com.pujjr.base.domain.FormFieldTemplate;
import com.pujjr.base.domain.FormFieldTemplateRequiredKey;
import com.pujjr.base.service.IFormFieldService;
@Service
public class FormFieldServiceImpl implements IFormFieldService {

	@Autowired
	private FormFieldMapper formFieldDao;
	@Autowired
	private FormFieldTemplateMapper formFieldTemplateDao;
	@Autowired
	private FormFieldTemplateRequiredMapper formFieldTemplateRequiredDao;
	
	@Override
	public List<FormField> getFormFieldList() {
		// TODO Auto-generated method stub
		return formFieldDao.selectAll();
	}

	@Override
	public void addFormField(FormField record) {
		// TODO Auto-generated method stub
		formFieldDao.insert(record);
	}

	@Override
	public void modifyFormField(FormField record) {
		// TODO Auto-generated method stub
		formFieldDao.updateByPrimaryKey(record);
	}

	@Override
	public void deleteFormFieldById(String id) {
		// TODO Auto-generated method stub
		formFieldDao.deleteByPrimaryKey(id);
	}

	@Override
	public List<FormField> getSubFormFieldByParentId(String parentId) {
		// TODO Auto-generated method stub
		return formFieldDao.selectChildList(parentId);
	}

	@Override
	public List<FormFieldTemplate> getFormFieldTemplateList() {
		// TODO Auto-generated method stub
		return formFieldTemplateDao.selectAll();
	}

	@Override
	public void addFormFieldTemplate(FormFieldTemplate record) {
		// TODO Auto-generated method stub
		formFieldTemplateDao.insert(record);
	}

	@Override
	public void modifyFormFieldTemplate(FormFieldTemplate record) {
		// TODO Auto-generated method stub
		formFieldTemplateDao.updateByPrimaryKey(record);
	}

	@Override
	public void deleteFormFieldTemplateById(String id) {
		// TODO Auto-generated method stub
		formFieldTemplateDao.deleteByPrimaryKey(id);
	}

	@Override
	public List<FormField> getTemplateRequiredFormField(String templateId) {
		// TODO Auto-generated method stub
		return formFieldDao.selectTemplateRequireFieldList(templateId);
	}

	@Override
	public void saveTemplateRequiredFormField(String templateId, List<FormField> requiredFields) {
		// TODO Auto-generated method stub
		formFieldTemplateRequiredDao.deleteByTemplateId(templateId);
		for(FormField item : requiredFields)
		{
			FormFieldTemplateRequiredKey po = new FormFieldTemplateRequiredKey();
			po.setTplId(templateId);
			po.setFieldId(item.getId());
			formFieldTemplateRequiredDao.insert(po);
		}
	}

}
