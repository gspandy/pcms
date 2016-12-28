package com.pujjr.base.vo;

import java.util.List;

import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.domain.SysRole;

/**
 * 用户权限信息
 * @author 150032
 *
 */
public class AccountAuthVo
{
	//功能授权角色
	private List<SysRole> roleList;
	//查询授权级别
	private String queryAuthLvl;
	//自定义授权查询机构
	private List<SysBranch> queryAuthBranchList;
	
	public List<SysRole> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<SysRole> roleList) {
		this.roleList = roleList;
	}
	public String getQueryAuthLvl() {
		return queryAuthLvl;
	}
	public void setQueryAuthLvl(String queryAuthLvl) {
		this.queryAuthLvl = queryAuthLvl;
	}
	public List<SysBranch> getQueryAuthBranchList() {
		return queryAuthBranchList;
	}
	public void setQueryAuthBranchList(List<SysBranch> queryAuthBranchList) {
		this.queryAuthBranchList = queryAuthBranchList;
	}
	
}
