package com.pujjr.carcredit.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.pujjr.base.service.ISysAccountService;
import com.pujjr.base.vo.QueryAuthVo;
import com.pujjr.carcredit.dao.FraudInnerResultMapper;
import com.pujjr.carcredit.dao.HisOperMapper;
import com.pujjr.carcredit.dao.QueryMapper;
import com.pujjr.carcredit.domain.FraudInnerResult;
import com.pujjr.carcredit.po.HisOperPo;
import com.pujjr.carcredit.service.IQueryService;
import com.pujjr.carcredit.vo.HisOperVo;
import com.pujjr.carcredit.vo.QueryParamApplyVo;
import com.pujjr.utils.Utils;
@Service
@Transactional(rollbackFor=Exception.class)
public class QueryServiceImpl implements IQueryService 
{	
	private Logger logger = Logger.getLogger(QueryServiceImpl.class);

	@Autowired
	private QueryMapper queryDao;
	@Autowired
	private HisOperMapper hisOperMapper;
	@Autowired
	private FraudInnerResultMapper fraudInnerResultDao;
	@Autowired
	private ISysAccountService sysAccountService;
	
	@Override
	public List<HashMap<String, Object>> queryApplyList(QueryParamApplyVo param) {
		// TODO Auto-generated method stub
		QueryAuthVo authVo = sysAccountService.getAccountQueryAuth(param.getQueryAccountId());
		param.setQueryAuth(authVo);
		PageHelper.startPage(Integer.parseInt(param.getCurPage()), Integer.parseInt(param.getPageSize()),true);
		List<HashMap<String,Object>> list =  queryDao.selectApplyList(param);
		for(int i = 0;i<list.size();i++)
		{
			HashMap<String,Object> item = list.get(i);
			if(item.containsKey("procInstId"))
			{
				String procInstId = item.get("procInstId").toString();
				if(procInstId !=null)
				{
					HashMap<String,Object> taskInfo = queryDao.selectTaskInfoByProcInstId(procInstId);
					if(taskInfo!=null)
					{
						item.put("taskName", taskInfo.containsKey("taskName")?taskInfo.get("taskName").toString():"");
						item.put("assignee", taskInfo.containsKey("assignee")?taskInfo.get("assignee").toString():"");
						list.set(i, item);
					}
				}
			}
			
		}
		return list;
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

	@Override
	public List<FraudInnerResult> queryFraudInnerResult(String appId) {
		// TODO Auto-generated method stub
		return fraudInnerResultDao.selectByAppId(appId);
	}
}
