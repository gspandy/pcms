package com.pujjr.assetsmanage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pujjr.assetsmanage.service.IInsuranceManageService;
import com.pujjr.base.dao.InsuranceHisMapper;
import com.pujjr.base.domain.InsuranceHis;
import com.pujjr.carcredit.constant.InsuranceType;
import com.pujjr.carcredit.domain.SignFinanceDetail;
import com.pujjr.carcredit.service.ISignContractService;

public class InsuranceManageServiceImpl implements IInsuranceManageService {

	@Autowired
	private ISignContractService signContractService;
	@Autowired
	private InsuranceHisMapper insuranceHisDao;
	@Override
	public List<HashMap<String, Object>> getInsuranceHisList(String appId) {
		// TODO Auto-generated method stub
		List<SignFinanceDetail> signListPo = signContractService.getSignFinanceDetailByAppId(appId);
		List<HashMap<String,Object>> vo = new ArrayList<HashMap<String,Object>>();
		for(SignFinanceDetail po : signListPo)
		{
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("carVin", po.getCarVin());
			item.put("signId", po.getId());
			//历史交强险购买记录
			List<InsuranceHis> jqxHisList = insuranceHisDao.selectBySignId(po.getAppId(), InsuranceType.JQX.getName());
			item.put("jqxHisList", jqxHisList);
			//历史商业购买记录
			List<InsuranceHis> syxHisList = insuranceHisDao.selectBySignId(po.getAppId(), InsuranceType.SYX.getName());
			item.put("syxHisList", syxHisList);
			//历史履约险购买记录
			List<InsuranceHis> lyxHisList = insuranceHisDao.selectBySignId(po.getAppId(), InsuranceType.LYX.getName());
			item.put("lyxHisList", lyxHisList);
			vo.add(item);
		}
		return vo;
	}

	@Override
	public void addInsurance(String appId, String signId, InsuranceHis vo) {
		// TODO Auto-generated method stub

	}

}
