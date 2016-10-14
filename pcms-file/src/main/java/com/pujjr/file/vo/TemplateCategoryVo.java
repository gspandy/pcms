package com.pujjr.file.vo;

import java.util.List;

import com.pujjr.file.domain.Directory;
import com.pujjr.file.domain.DirectoryTemplateCategory;

public class TemplateCategoryVo extends DirectoryTemplateCategory{
	
	private List<Directory> templateCategoryDirs ;
	
	private List<Directory> templateCategoryRequireDirs;

	public List<Directory> getTemplateCategoryDirs() {
		return templateCategoryDirs;
	}

	public void setTemplateCategoryDirs(List<Directory> templateCategoryDirs) {
		this.templateCategoryDirs = templateCategoryDirs;
	}

	public List<Directory> getTemplateCategoryRequireDirs() {
		return templateCategoryRequireDirs;
	}

	public void setTemplateCategoryRequireDirs(List<Directory> templateCategoryRequireDirs) {
		this.templateCategoryRequireDirs = templateCategoryRequireDirs;
	}
	

}
