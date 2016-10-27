package com.pujjr.base.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pujjr.base.dao.ContractInfoMapper;
import com.pujjr.base.dao.ContractTemplateMapper;
import com.pujjr.base.dao.ContractTemplateRefContractMapper;
import com.pujjr.base.domain.ContractInfo;
import com.pujjr.base.domain.ContractTemplate;
import com.pujjr.base.domain.ContractTemplateRefContractKey;
import com.pujjr.base.service.IContractService;
import com.pujjr.base.service.IOSSService;
@Service
public class ContractServiceImpl implements IContractService {

	@Autowired
	private ContractInfoMapper contractInfoDao;
	@Autowired
	private ContractTemplateMapper contractTemplateDao;
	@Autowired
	private ContractTemplateRefContractMapper refContractDao;
	@Autowired
	private IOSSService ossService;
	
	@Override
	public List<ContractInfo> getContractInfoList() {
		// TODO Auto-generated method stub
		return contractInfoDao.selectContractInfoList();
	}

	@Override
	public void addContractInfo(ContractInfo record) {
		// TODO Auto-generated method stub
		contractInfoDao.insert(record);
	}

	@Override
	public void modifyContractInfo(ContractInfo record) {
		// TODO Auto-generated method stub
		contractInfoDao.updateByPrimaryKey(record);
	}

	@Override
	public void deleteContractInfoById(String id) {
		// TODO Auto-generated method stub
		ContractInfo po = contractInfoDao.selectByPrimaryKey(id);
		contractInfoDao.deleteByPrimaryKey(id);
		ossService.deleteObject(po.getOssKey());
	}

	@Override
	public ContractInfo getContractInfoByKey(String contractKey) {
		// TODO Auto-generated method stub
		return contractInfoDao.selectContractInfoByKey(contractKey);
	}

	@Override
	public List<ContractTemplate> getContractTemplateList() {
		// TODO Auto-generated method stub
		return contractTemplateDao.selectContractTemplateList();
	}

	@Override
	public void addContractTemplate(ContractTemplate record) {
		// TODO Auto-generated method stub
		contractTemplateDao.insert(record);
	}

	@Override
	public void modifyContractTemplate(ContractTemplate record) {
		// TODO Auto-generated method stub
		contractTemplateDao.updateByPrimaryKey(record);
	}

	@Override
	public void deleteContractTemplateById(String id) {
		// TODO Auto-generated method stub
		contractTemplateDao.deleteByPrimaryKey(id);
	}

	@Override
	public List<ContractInfo> getContractInfoListByContractTemplateId(String templateId, boolean enabled) {
		// TODO Auto-generated method stub
		return contractInfoDao.selectContractInfoListByContractTemplateId(templateId, enabled);
	}

	@Override
	public void saveContractTemplateRefContractList(String templateId, List<ContractInfo> records) {
		// TODO Auto-generated method stub
		contractTemplateDao.deleteTemplateRefContract(templateId);
		for(ContractInfo record : records)
		{
			ContractTemplateRefContractKey key = new ContractTemplateRefContractKey();
			key.setTplId(templateId);
			key.setContractId(record.getId());
			refContractDao.insert(key);
		}
	}

	@Override
	public void uploadContractFile(MultipartFile file, String contractId) throws IOException {
		// TODO Auto-generated method stub
		ContractInfo po = contractInfoDao.selectByPrimaryKey(contractId);
		String ossKey = "contract-template/"+po.getContractKey()+"-"+file.getOriginalFilename();
		ossService.putObject(ossKey, file.getInputStream());
		po.setOssKey(ossKey);
		contractInfoDao.updateByPrimaryKey(po);
	}

}
