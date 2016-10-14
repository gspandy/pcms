package com.pujjr.file.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.file.po.CategoryDirectoryPo;
import com.pujjr.file.service.IFileService;

@RestController
@RequestMapping(value="/file")
public class FileController 
{
	@Autowired
	private IFileService fileService;
	
	@RequestMapping(value="/getApplyFormCategoryDirectoryList/{appId}/{categoryKey}",method=RequestMethod.GET)
	public List<CategoryDirectoryPo>  getApplyFormCategoryDirectoryList(@PathVariable String appId,@PathVariable String categoryKey)
	{
		return fileService.getApplyFormCategoryDirectoryList(categoryKey, appId); 
	}
}
