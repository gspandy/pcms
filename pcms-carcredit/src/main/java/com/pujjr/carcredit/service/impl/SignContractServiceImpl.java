package com.pujjr.carcredit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.carcredit.dao.SignContractMapper;
import com.pujjr.carcredit.dao.SignFinanceDetailMapper;
import com.pujjr.carcredit.domain.SignContract;
import com.pujjr.carcredit.domain.SignFinanceDetail;
import com.pujjr.carcredit.service.ISignContractService;
@Service
@Transactional(rollbackFor=Exception.class)
public class SignContractServiceImpl implements ISignContractService {

	@Autowired
	private SignContractMapper signContractDao;
	@Autowired
	private SignFinanceDetailMapper signFinanceDetailDao;
	@Override
	public SignContract getSignContractByAppId(String appId) {
		// TODO Auto-generated method stub
		return signContractDao.getByAppId(appId);
	}

	@Override
	public SignFinanceDetail getSignFinanceDetailByApplyFinanceId(String applyFinanceId) {
		// TODO Auto-generated method stub
		return signFinanceDetailDao.getByApplyFinanceId(applyFinanceId);
	}

	@Override
	public void addSignContract(SignContract record) {
		// TODO Auto-generated method stub
		signContractDao.insert(record);
	}

	@Override
	public void deleteSignFinanceDetailByAppId(String appId) {
		// TODO Auto-generated method stub
		signFinanceDetailDao.deleteByAppId(appId);
	}

	@Override
	public void addSignFinanceDetail(SignFinanceDetail record) {
		// TODO Auto-generated method stub
		signFinanceDetailDao.insert(record);
	}

	@Override
	public void modifySignContract(SignContract record) {
		// TODO Auto-generated method stub
		signContractDao.updateByPrimaryKey(record);
	}

	@Override
	public void modifySignFinanceDetail(SignFinanceDetail record) {
		// TODO Auto-generated method stub
		signFinanceDetailDao.updateByPrimaryKey(record);
	}

	@Override
	public List<SignFinanceDetail> getSignFinanceDetailByAppId(String appId) {
		// TODO Auto-generated method stub
		return signFinanceDetailDao.selectByAppId(appId);
	}

	@Override
	public SignFinanceDetail getSignFinanceDetailById(String id) {
		// TODO Auto-generated method stub
		return signFinanceDetailDao.selectByPrimaryKey(id);
	}


}
