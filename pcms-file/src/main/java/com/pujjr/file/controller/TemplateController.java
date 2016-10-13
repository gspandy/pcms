package com.pujjr.file.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.controller.BaseController;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.file.domain.Directory;
import com.pujjr.file.domain.DirectoryCategory;
import com.pujjr.file.domain.DirectoryTemplate;
import com.pujjr.file.service.ITemplateService;
import com.pujjr.utils.Utils;

@RestController
@RequestMapping(value="/template")
public class TemplateController extends BaseController
{
	@Autowired
	private ITemplateService templateService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<DirectoryTemplate> getTemplateList(@RequestParam("enabled") boolean enabled)
	{
		return templateService.getTemplateList(enabled);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public void addTemplate(@RequestBody DirectoryTemplate record,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		record.setId(Utils.get16UUID());
		record.setCreateId(account.getAccountId());
		record.setCreateTime(new Date());
		templateService.addTemplate(record);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public void modifyTemplate(@RequestBody DirectoryTemplate record,HttpServletRequest request)
	{
		SysAccount account = (SysAccount)request.getAttribute("account");
		record.setUpdateId(account.getAccountId());
		record.setUpdateTime(new Date());
		templateService.modifyTemplate(record);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public void deleteTemplate(@PathVariable String id)
	{
		templateService.deleteTemplateById(id);
	}
	@RequestMapping(value="/{templateId}/saveTemplateDirectory",method=RequestMethod.POST)
	public void saveTemplateDirectory(@PathVariable String templateId,@RequestBody List<Directory> records)
	{
		templateService.saveTemplateDirectory(templateId, records);
	}
	@RequestMapping(value="/{templateId}/saveTemplateCategory",method=RequestMethod.POST)
	public void saveTemplateCategory(@PathVariable String templateId,@RequestBody List<DirectoryCategory> records)
	{
		templateService.saveTemplateCategory(templateId, records);
	}
}
