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
	/**查询经销商列表**/
	List<SysBranch> getDealerList();
	/**
	 * 根据机构编码查询下级机构列表
	 * @param branchId 机构ID
	 * @param branchCode 机构编码
	 * @param includeSelf 是否包含自身
	 * @return
	 */
	public List<SysBranch> getChildBranchList(String branchId,String branchCode, boolean includeSelf);
	
}
