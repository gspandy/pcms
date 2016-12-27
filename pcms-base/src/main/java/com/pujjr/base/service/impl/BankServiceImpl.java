package com.pujjr.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.dao.BankInfoMapper;
import com.pujjr.base.domain.BankInfo;
import com.pujjr.base.service.IBankService;
@Service
@Transactional(rollbackFor=Exception.class)
public class BankServiceImpl implements IBankService {

	@Autowired
	private BankInfoMapper bankInfoDao;
	
	@Override
	public List<BankInfo> getBankInfoList() {
		// TODO Auto-generated method stub
		return bankInfoDao.selectAll(false);
	}

	@Override
	public void addBankInfo(BankInfo record) {
		// TODO Auto-generated method stub
		bankInfoDao.insert(record);
	}

	@Override
	public void modifyBankInfo(BankInfo record) {
		// TODO Auto-generated method stub
		bankInfoDao.updateByPrimaryKey(record);
	}

	@Override
	public void deleteBankInfoById(String id) {
		// TODO Auto-generated method stub
		bankInfoDao.deleteByPrimaryKey(id);
	}

	@Override
	public List<BankInfo> getAllEnabledBankInfoList() {
		// TODO Auto-generated method stub
		return bankInfoDao.selectAll(true);
	}

	@Override
	public List<BankInfo> getAllUnionpayBankInfoList() {
		// TODO Auto-generated method stub
		return bankInfoDao.selectAllUnionpayBankInfoList();
	}

	@Override
	public BankInfo getBankInfoById(String id) {
		// TODO Auto-generated method stub
		return bankInfoDao.selectByPrimaryKey(id);
	}

}
