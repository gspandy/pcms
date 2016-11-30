package com.pujjr.base.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.dao.MerchantMapper;
import com.pujjr.base.domain.Merchant;
import com.pujjr.base.service.IMerchantService;
@Service
public class MerchantServiceImpl implements IMerchantService {

	@Autowired
	private MerchantMapper merchantDao;
	
	@Override
	public List<HashMap<String,Object>>  getMerchantList(boolean enabled,String chnlType) {
		// TODO Auto-generated method stub
		return merchantDao.selectAll(enabled,chnlType);
	}

	@Override
	public void addMerchant(Merchant record) {
		// TODO Auto-generated method stub
		merchantDao.insert(record);
	}

	@Override
	public void modifyMerchant(Merchant record) {
		// TODO Auto-generated method stub
		merchantDao.updateByPrimaryKey(record);
	}

	@Override
	public void deleteMerchantById(String id) {
		// TODO Auto-generated method stub
		merchantDao.deleteByPrimaryKey(id);
	}

	@Override
	public Merchant getMerchantByNo(String merchantNo) {
		// TODO Auto-generated method stub
		return merchantDao.selectByMerchantNo(merchantNo);
	}

}
