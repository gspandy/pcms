package com.pujjr.base.vo;

public class QueryParamPageVo 
{
	//当前页数
	private String curPage;
	//每页数量
	private String pageSize;
	//查询授权信息
	private QueryAuthVo queryAuth;
	//查询操作人用户编码
	private String queryAccountId;

	public String getCurPage() {
		return curPage;
	}

	public void setCurPage(String curPage) {
		this.curPage = curPage;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public QueryAuthVo getQueryAuth() {
		return queryAuth;
	}

	public void setQueryAuth(QueryAuthVo queryAuth) {
		this.queryAuth = queryAuth;
	}

	public String getQueryAccountId() {
		return queryAccountId;
	}

	public void setQueryAccountId(String queryAccountId) {
		this.queryAccountId = queryAccountId;
	}
	
	
}
