package com.pujjr.base.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pujjr.base.domain.ContractInfo;
import com.pujjr.base.domain.ContractTemplate;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.service.IContractService;
import com.pujjr.utils.Utils;

@RestController
@RequestMapping(value="/contract")
public class ContractController extends BaseController 
{
	@Autowired
	private IContractService contractService;
	
	@RequestMapping(value="/contractinfo",method=RequestMethod.GET)
	public List<ContractInfo> getContractInfoList()
	{
		return contractService.getContractInfoList();
	}
	@RequestMapping(value="/contractinfo",method=RequestMethod.POST)
	public void addContractInfo(@RequestBody ContractInfo record,HttpServletRequest request)
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		record.setId(Utils.get16UUID());
		record.setOssKey("未上传模板");
		record.setCreateId(sysAccount.getAccountId());
		record.setCreateTime(new Date());
		contractService.addContractInfo(record);
	}
	@RequestMapping(value="/contractinfo/{id}",method=RequestMethod.PUT)
	public void modifyContractInfo(@RequestBody ContractInfo record,HttpServletRequest request)
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		record.setUpdateId(sysAccount.getAccountId());
		record.setUpdateTime(new Date());
		contractService.modifyContractInfo(record);
	}
	@RequestMapping(value="/contractinfo/{contractId}",method=RequestMethod.DELETE)
	public void deleteContractInfoById(@PathVariable String contractId)
	{
		contractService.deleteContractInfoById(contractId);
	}
	@RequestMapping(value="/contracttemplate",method=RequestMethod.GET)
	public List<ContractTemplate> getContractTemplateList()
	{
		return contractService.getContractTemplateList();
	}
	@RequestMapping(value="/contracttemplate",method=RequestMethod.POST)
	public void addContractTemplate(@RequestBody  ContractTemplate record,HttpServletRequest request)
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		record.setId(Utils.get16UUID());
		record.setCreateId(sysAccount.getAccountId());
		record.setCreateTime(new Date());
		contractService.addContractTemplate(record);
	}
	@RequestMapping(value="/contracttemplate/{templateId}",method=RequestMethod.PUT)
	public void modifyContractTemplate(@RequestBody  ContractTemplate record,HttpServletRequest request)
	{
		SysAccount sysAccount = (SysAccount)request.getAttribute("account");
		record.setUpdateId(sysAccount.getAccountId());
		record.setUpdateTime(new Date());
		contractService.modifyContractTemplate(record);
	}
	@RequestMapping(value="/contracttemplate/{templateId}",method=RequestMethod.DELETE)
	public void deleteContractTemplateById(@PathVariable String templateId)
	{
		contractService.deleteContractTemplateById(templateId);
	}
	
	@RequestMapping(value="/getContractInfoListByContractTemplateId/{templateId}",method=RequestMethod.GET)
	public List<ContractInfo> getContractInfoListByContractTemplateId(@PathVariable String templateId,@Param("enabled")boolean enabled)
	{
		return contractService.getContractInfoListByContractTemplateId(templateId, enabled);
	}
	
	@RequestMapping(value="/saveContractTemplateRefContractList/{templateId}",method=RequestMethod.POST)
	public void saveContractTemplateRefContractList(@PathVariable String templateId,@RequestBody List<ContractInfo> records)
	{
		contractService.saveContractTemplateRefContractList(templateId, records);
	}
	@RequestMapping(value="/uploadContractFile/{contractId}")
	public void updateContractFile(@Param("file")MultipartFile file,@PathVariable String contractId) throws IOException
	{
		contractService.uploadContractFile(file, contractId);
	}
}
