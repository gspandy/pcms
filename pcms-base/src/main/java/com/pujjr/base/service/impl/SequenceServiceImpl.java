package com.pujjr.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.dao.SequenceMapper;
import com.pujjr.base.domain.Sequence;
import com.pujjr.base.service.ISequenceService;


@Service
@Transactional(rollbackFor=Exception.class)
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

	@Override
	public List<Sequence> getAll() {
		// TODO Auto-generated method stub
		return seqDao.selectAll();
	}

	@Override
	public void modify(Sequence record) {
		// TODO Auto-generated method stub
		seqDao.updateByPrimaryKey(record);
	}
}
