package com.pujjr.file.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.controller.BaseController;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.file.domain.DirectoryCategory;
import com.pujjr.file.service.ICategoryService;
import com.pujjr.utils.Utils;

@RestController
@RequestMapping(value="/category")
public class CategoryController extends BaseController
{
	@Autowired
	private ICategoryService categoryService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<DirectoryCategory> getCategoryList()
	{
		return categoryService.getCategoryList();
	}
	@RequestMapping(method=RequestMethod.POST)
	public void addCategory(@RequestBody DirectoryCategory record,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		record.setId(Utils.get16UUID());
		record.setCreateId(account.getAccountId());
		record.setCreateTime(new Date());
		categoryService.addCategory(record);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public void modifyCategory(@RequestBody DirectoryCategory record,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		record.setUpdateId(account.getAccountId());
		record.setUpdateTime(new Date());
		categoryService.modifyCategory(record);
	}
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public void deleteCategory(@PathVariable String id)
	{
		categoryService.deleteCategoryById(id);
	}
}
