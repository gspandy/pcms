package com.pujjr.carcredit.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.carcredit.dao.QueryMapper;
import com.pujjr.carcredit.service.IQueryService;
import com.pujjr.carcredit.vo.QueryParamApplyVo;
@Service
public class QueryServiceImpl implements IQueryService 
{

	@Autowired
	private QueryMapper queryDao;
	
	@Override
	public List<HashMap<String, Object>> queryApplyList(QueryParamApplyVo param) {
		// TODO Auto-generated method stub
		return queryDao.selectApplyList(param);
	}

}
