package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.domain.SysJob;
@Service
@Transactional(rollbackFor=Exception.class)
public interface ISysJobService 
{
	public List<SysJob> getSysJobList();
	
	public void addSysJob(SysJob record);
	
	public void deleteSysJobById(String id);
	
	public void modifySysJob(SysJob record);
}
