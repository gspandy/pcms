package com.pujjr.base.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.base.domain.FormField;
import com.pujjr.base.domain.FormFieldTemplate;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.service.IFormFieldService;
import com.pujjr.utils.Utils;

@RestController
@RequestMapping(value="/form")
public class FormFieldController extends BaseController
{
	@Autowired
	private IFormFieldService formFieldService;
	
	@RequestMapping(value="/field",method=RequestMethod.GET)
	public List<FormField> getFormFieldList()
	{
		return formFieldService.getFormFieldList();
	}
	@RequestMapping(value="/field",method=RequestMethod.POST)
	public void addFormField(@RequestBody FormField record)
	{
		record.setId(Utils.get16UUID());
		formFieldService.addFormField(record);
	}
	@RequestMapping(value="/field/{id}",method=RequestMethod.PUT)
	public void modifyFormField(@RequestBody FormField record)
	{
		formFieldService.modifyFormField(record);
	}
	@RequestMapping(value="/field/{id}",method=RequestMethod.DELETE)
	public void deleteFormField(@PathVariable String id)
	{
		formFieldService.deleteFormFieldById(id);
	}
	@RequestMapping(value="/field/child/{parentId}",method=RequestMethod.GET)
	public List<FormField> getChildFormFieldList(@PathVariable String parentId)
	{
		return formFieldService.getSubFormFieldByParentId(parentId);
	}
	@RequestMapping(value="/template",method=RequestMethod.GET)
	public List<FormFieldTemplate> getFormFieldTemplateList()
	{
		return formFieldService.getFormFieldTemplateList();
	}
	@RequestMapping(value="/template",method=RequestMethod.POST)
	public void addFormFieldTemplate(@RequestBody FormFieldTemplate record,HttpServletRequest request)
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		record.setId(Utils.get16UUID());
		record.setCreateId(sysAccount.getAccountId());
		record.setCreateTime(new Date());
		formFieldService.addFormFieldTemplate(record);
	}
	@RequestMapping(value="/template/{id}",method=RequestMethod.PUT)
	public void modifyFormFieldTemplate(@RequestBody FormFieldTemplate record,HttpServletRequest request)
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		record.setUpdateId(sysAccount.getAccountId());
		record.setUpdateTime(new Date());
		formFieldService.modifyFormFieldTemplate(record);
	}
	@RequestMapping(value="/template/{id}",method=RequestMethod.DELETE)
	public void deleteFormFieldTemplate(@PathVariable String id)
	{
		formFieldService.deleteFormFieldTemplateById(id);
	}
	@RequestMapping(value="/requirefield/template/{templateId}",method=RequestMethod.GET)
	public List<FormField> getTemplateRequiredFormFieldList(@PathVariable String templateId)
	{
		return formFieldService.getTemplateRequiredFormField(templateId);
	}
	@RequestMapping(value="/saverequiredfield/{templateId}",method=RequestMethod.POST)
	public void saveTemplateRequiredFormField(@PathVariable  String templateId,@RequestBody List<FormField> requiredFields)
	{
		formFieldService.saveTemplateRequiredFormField(templateId, requiredFields);
	}
}
