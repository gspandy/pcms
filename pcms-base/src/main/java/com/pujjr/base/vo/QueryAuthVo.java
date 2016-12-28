package com.pujjr.base.vo;

import java.util.List;

import com.pujjr.base.domain.SysBranch;

/**
 * 查询授权信息
 * @author 150032
 *
 */
public class QueryAuthVo 
{
	//是否需要检查授权
	private boolean needCheckAuth;
	//授权检查类型 0-根据机构过滤查询  1-根据用户ID过滤查询
	private String checkAuthType;
	//授权机构
	private List<SysBranch> authBranch;
	//授权用户Id
	private String authAcctId;
	
	public boolean isNeedCheckAuth() {
		return needCheckAuth;
	}
	public void setNeedCheckAuth(boolean needCheckAuth) {
		this.needCheckAuth = needCheckAuth;
	}
	public List<SysBranch> getAuthBranch() {
		return authBranch;
	}
	public void setAuthBranch(List<SysBranch> authBranch) {
		this.authBranch = authBranch;
	}
	public String getCheckAuthType() {
		return checkAuthType;
	}
	public void setCheckAuthType(String checkAuthType) {
		this.checkAuthType = checkAuthType;
	}
	public String getAuthAcctId() {
		return authAcctId;
	}
	public void setAuthAcctId(String authAcctId) {
		this.authAcctId = authAcctId;
	}
}
