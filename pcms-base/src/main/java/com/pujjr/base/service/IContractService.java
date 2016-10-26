package com.pujjr.base.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pujjr.base.domain.ContractInfo;
import com.pujjr.base.domain.ContractTemplate;
@Service
public interface IContractService 
{
	public List<ContractInfo> getContractInfoList();
	
	public void addContractInfo(ContractInfo record);
	
	public void modifyContractInfo(ContractInfo record);
	
	public void deleteContractInfoById(String id);
	
	public ContractInfo getContractInfoByKey(String contractKey);
	
	public List<ContractTemplate> getContractTemplateList();
	
	public void addContractTemplate(ContractTemplate record);
	
	public void modifyContractTemplate(ContractTemplate record);
	
	public void deleteContractTemplateById(String id);
	
	public List<ContractInfo> getContractInfoListByContractTemplateId(String templateId,boolean enabled);
	
	public void saveContractTemplateRefContractList(String templateId,List<ContractInfo> records);
	
	public void uploadContractFile(MultipartFile file,String contractId) throws IOException;
	
	
}
