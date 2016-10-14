package com.pujjr.push.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pujjr.push.dao.IPushDao;
import com.pujjr.push.dao.SysPushMapper;
import com.pujjr.push.domain.SysPush;
import com.pujjr.push.enumeration.ESendFlag;

@Repository
public class PushDaoImpl implements IPushDao{
	@Autowired
	private SysPushMapper sysPushMapper;
	@Override
	public List<SysPush> selectWillPush() {
		// TODO Auto-generated method stub
		HashMap<String,String> condition = new HashMap<String,String>();
		condition.put("sendFlag", ESendFlag.WILL_SEND.getCode());
		List<SysPush> sysPushList = new ArrayList<SysPush>();
		sysPushList = sysPushMapper.selectCommonList(condition);
		return sysPushList;
	}
	@Override
	public int updateByPrimaryKey(SysPush record) {
		// TODO Auto-generated method stub
		sysPushMapper.updateByPrimaryKey(record);
		return 0;
	}
}
