package com.pujjr.file.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.file.dao.DirectoryTemplateMapper;
import com.pujjr.file.domain.DirectoryTemplate;
import com.pujjr.file.service.ITemplateService;
@Service
public class TemplateServiceImpl implements ITemplateService {

	@Autowired
	private DirectoryTemplateMapper templateDao;
	
	@Override
	public List<DirectoryTemplate> getTemplateList(boolean enabled) {
		// TODO Auto-generated method stub
		return templateDao.selectAll(enabled);
	}

	@Override
	public void addTemplate(DirectoryTemplate record) {
		// TODO Auto-generated method stub
		templateDao.insert(record);
	}

	@Override
	public void modifyTemplate(DirectoryTemplate record) {
		// TODO Auto-generated method stub
		templateDao.updateByPrimaryKey(record);
	}

	@Override
	public void deleteTemplateById(String id) {
		// TODO Auto-generated method stub
		templateDao.deleteByPrimaryKey(id);
	}

}
