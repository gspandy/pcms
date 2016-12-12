package com.pujjr.base.service;

import java.util.List;

import com.pujjr.base.domain.Sequence;

public interface ISequenceService 
{
	public int getNextVal(String name);
	
	public List<Sequence> getAll();
	
	public void modify(Sequence record);
}
