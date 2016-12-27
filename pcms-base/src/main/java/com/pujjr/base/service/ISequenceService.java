package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.domain.Sequence;
@Service
@Transactional(rollbackFor=Exception.class)
public interface ISequenceService 
{
	public int getNextVal(String name);
	
	public List<Sequence> getAll();
	
	public void modify(Sequence record);
}
