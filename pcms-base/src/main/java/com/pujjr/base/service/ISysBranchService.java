package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.domain.SysBranchDealer;
import com.pujjr.base.vo.SysBranchVo;
@Service
@Transactional(rollbackFor=Exception.class)
public interface ISysBranchService 
{
	public List<SysBranch> getSysBranchList();
	
	public void  addSysBranch(SysBranch record);
	
	public SysBranch getSysBranch(String id,String branchCode);

	public void deleteSysBranchById(String id);
	
	public void modifySysBranch(SysBranchVo record);
	
	public SysBranchDealer getDealerByBranchId(String id);
	
}
