package com.pujjr.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.dao.SequenceMapper;
import com.pujjr.base.domain.Sequence;
import com.pujjr.base.service.ISequenceService;


@Service
public class SequenceServiceImpl implements ISequenceService
{
	@Autowired
	private SequenceMapper seqDao;
	
	public synchronized int getNextVal(String name)
	{
		Sequence seq=seqDao.selectByName(name);
		int curVal=seq.getCurval();
		int increment=seq.getIncrement();
		seq.setCurval(curVal+increment);
		seqDao.updateByPrimaryKey(seq);
		return curVal;
	}
}
