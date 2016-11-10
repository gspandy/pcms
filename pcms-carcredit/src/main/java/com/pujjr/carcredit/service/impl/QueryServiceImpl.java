package com.pujjr.carcredit.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.pujjr.carcredit.dao.HisOperMapper;
import com.pujjr.carcredit.dao.QueryMapper;
import com.pujjr.carcredit.po.HisOperPo;
import com.pujjr.carcredit.service.IQueryService;
import com.pujjr.carcredit.vo.HisOperVo;
import com.pujjr.carcredit.vo.QueryParamApplyVo;
import com.pujjr.utils.Utils;
@Service
public class QueryServiceImpl implements IQueryService 
{	
	private Logger logger = Logger.getLogger(QueryServiceImpl.class);

	@Autowired
	private QueryMapper queryDao;
	@Autowired
	private HisOperMapper hisOperMapper;
	
	@Override
	public List<HashMap<String, Object>> queryApplyList(QueryParamApplyVo param) {
		// TODO Auto-generated method stub
		return queryDao.selectApplyList(param);
	}
	
	public List<HashMap<String,Object>> queryApplyConditionLoanCommentList(String appId)
	{
		return queryDao.selectApplyConditionLoanCommentList(appId);
	}

	@Override
	public HashMap<String,Object> queryApplyRunPathNodeList(String appId) {
		// TODO Auto-generated method stub
		List<String> list = queryDao.selectApplyRunPathNodeList(appId);
		HashMap<String,Object> nodeMap = new HashMap<String,Object>();
		for(String l : list)
		{
			nodeMap.put(l, true);
		}
		return nodeMap;
	}


	@Override
	public List<HisOperVo> querytHisOper(String appId, String accountId) {
		
		List<HisOperPo> hisOperPoList = new ArrayList<HisOperPo>();
		List<HisOperVo> hisOperVoList = new ArrayList<HisOperVo>();
		hisOperPoList = hisOperMapper.selectHisOperList(appId,accountId);
		logger.debug("JSONObject.toJSONString(hisOperPoList):"+JSONObject.toJSONString(hisOperPoList));
		for (HisOperPo hisOperPo : hisOperPoList) {
			HisOperVo hisOperVo = new HisOperVo();
			Utils.copyProperties(hisOperPo, hisOperVo);
			hisOperVoList.add(hisOperVo);
		}
		logger.debug("JSONObject.toJSONString(hisOperVoList):"+JSONObject.toJSONString(hisOperVoList));
		return hisOperVoList;
	}
}
