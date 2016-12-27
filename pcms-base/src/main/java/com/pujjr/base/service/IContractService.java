package com.pujjr.base.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.pujjr.base.domain.ContractInfo;
import com.pujjr.base.domain.ContractTemplate;
@Service
@Transactional(rollbackFor=Exception.class)
public interface IContractService 
{
	/**
	 * 查询合同列表
	 * @return
	 */
	public List<ContractInfo> getContractInfoList();
	/**
	 * 增加合同信息
	 * @param record
	 */
	public void addContractInfo(ContractInfo record);
	/**
	 * 修改合同信息
	 * @param record
	 */
	public void modifyContractInfo(ContractInfo record);
	/**
	 * 删除合同信息
	 * @param id
	 */
	public void deleteContractInfoById(String id);
	/**
	 * 根据合同标识查询合同信息
	 * @param contractKey
	 * @return
	 */
	public ContractInfo getContractInfoByKey(String contractKey);
	/**
	 * 查询合同模板列表
	 * @return
	 */
	public List<ContractTemplate> getContractTemplateList();
	/**
	 * 增加合同模板
	 * @param record
	 */
	public void addContractTemplate(ContractTemplate record);
	/**
	 * 修改合同模板信息
	 * @param record
	 */
	public void modifyContractTemplate(ContractTemplate record);
	/**
	 * 删除合同模板
	 * @param id
	 */
	public void deleteContractTemplateById(String id);
	/**
	 * 根据模板ID查询合同列表
	 * @param templateId
	 * @param enabled
	 * @return
	 */
	public List<ContractInfo> getContractInfoListByContractTemplateId(String templateId,boolean enabled);
	/**
	 * 保存合同模板信息
	 * @param templateId
	 * @param records
	 */
	public void saveContractTemplateRefContractList(String templateId,List<ContractInfo> records);
	/**
	 * 上传合同模板文件
	 * @param file
	 * @param contractId
	 * @throws IOException
	 */
	public void uploadContractFile(MultipartFile file,String contractId) throws IOException;
	
	
}
