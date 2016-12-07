package com.pujjr.assetsmanage.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.assetsmanage.dao.TelInterviewMapper;
import com.pujjr.assetsmanage.domain.TelInterview;
import com.pujjr.assetsmanage.service.ITelInterviewService;
import com.pujjr.utils.Utils;
@Service
@Transactional
public class TelInterviewServiceImpl implements ITelInterviewService {

	@Autowired
	private TelInterviewMapper telInterviewDao;
	
	@Override
	public void addTelInterviewResult(String appId,TelInterview vo, String operId) throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		TelInterview po = new TelInterview();
		BeanUtils.copyProperties(vo, po);;
		po.setId(Utils.get16UUID());
		po.setAppId(appId);
		po.setCreateId(operId);
		po.setCreateTime(new Date());
		telInterviewDao.insert(po);
	}

}
