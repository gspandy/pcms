package com.pujjr.file.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.file.dao.DirectoryTemplateCategoryMapper;
import com.pujjr.file.dao.DirectoryTemplateCategoryRefDirectoryMapper;
import com.pujjr.file.dao.DirectoryTemplateMapper;
import com.pujjr.file.dao.DirectoryTemplateRefDirectoryMapper;
import com.pujjr.file.domain.Directory;
import com.pujjr.file.domain.DirectoryCategory;
import com.pujjr.file.domain.DirectoryTemplate;
import com.pujjr.file.domain.DirectoryTemplateCategory;
import com.pujjr.file.domain.DirectoryTemplateRefDirectory;
import com.pujjr.file.service.ITemplateService;
import com.pujjr.utils.Utils;
@Service
public class TemplateServiceImpl implements ITemplateService {

	@Autowired
	private DirectoryTemplateMapper templateDao;
	@Autowired
	private DirectoryTemplateRefDirectoryMapper templateRefDirDao;
	@Autowired
	private DirectoryTemplateCategoryMapper templateCategoryDao;
	@Autowired
	private DirectoryTemplateCategoryRefDirectoryMapper templateCategoryRefDirDao;
	
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

	@Override
	public void saveTemplateDirectory(String templateId, List<Directory> records) {
		// TODO Auto-generated method stub
		templateDao.deleteTemplateRefDir(templateId);
		for(Directory item : records)
		{
			DirectoryTemplateRefDirectory po = new DirectoryTemplateRefDirectory();
			po.setId(Utils.get16UUID());
			po.setTplId(templateId);
			po.setDirId(item.getId());
			templateRefDirDao.insert(po);
		}
	}

	@Override
	public void saveTemplateCategory(String templateId, List<DirectoryCategory> records) {
		// TODO Auto-generated method stub
		List<String> categoryIds = new ArrayList<String>(); 
		for(DirectoryCategory record : records)
		{
			categoryIds.add(record.getId());
		}
		//找出新增和现在的差异进行删除
		List<DirectoryTemplateCategory> uncontainList = templateCategoryDao.selectUnContainListByTemplateId(templateId, categoryIds);
		for(DirectoryTemplateCategory item : uncontainList)
		{
			//先删除对应分类关联目录
			templateCategoryRefDirDao.deleteByTplCategoryId(item.getId());
			//再删除模板与分类关联关系
			templateCategoryDao.deleteByPrimaryKey(item.getId());
		}
		//再进行插入并判断是否存在，存在则跳过
		for(String categoryId:categoryIds)
		{
			if(templateCategoryDao.selectByTplIdAndCategoryId(templateId, categoryId)!=null){
				continue;
			}else{
				DirectoryTemplateCategory po = new DirectoryTemplateCategory();
				po.setId(Utils.get16UUID());
				po.setTplId(templateId);
				po.setCategoryId(categoryId);
				templateCategoryDao.insert(po);
			}
		}
	}


}
