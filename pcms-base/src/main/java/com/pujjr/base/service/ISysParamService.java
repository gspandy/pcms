package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.domain.SysParam;
@Service
@Transactional(rollbackFor=Exception.class)
public interface ISysParamService {
	
	public List<SysParam> getSysParamList(String paramName);
	
	public void addSysParam(SysParam record);
	
	public void modifySysParam(SysParam record);
	
	public void deleteSysParam(String id);
	
	public SysParam getSysParamByParamName(String paramName);

}
