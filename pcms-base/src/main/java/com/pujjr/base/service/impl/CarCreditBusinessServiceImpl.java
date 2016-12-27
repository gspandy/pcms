package com.pujjr.base.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.dao.CarCreditBusinessMapper;
import com.pujjr.base.service.ICarCreditBusinessService;
@Service
@Transactional(rollbackFor=Exception.class)
public class CarCreditBusinessServiceImpl implements ICarCreditBusinessService {

	@Autowired
	private CarCreditBusinessMapper carCreditBusinessDao;
	@Override
	public HashMap<String, Object> getApplyInfo(String appId) {
		// TODO Auto-generated method stub
		return carCreditBusinessDao.selectApplyInfo(appId);
	}

}
