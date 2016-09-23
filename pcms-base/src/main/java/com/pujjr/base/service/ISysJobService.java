package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.base.domain.SysJob;
@Service
public interface ISysJobService 
{
	public List<SysJob> getSysJobList();
	
	public void addSysJob(SysJob record);
	
	public void deleteSysJobById(String id);
	
	public void modifySysJob(SysJob record);
}
