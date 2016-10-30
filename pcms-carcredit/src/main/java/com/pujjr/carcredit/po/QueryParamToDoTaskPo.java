package com.pujjr.carcredit.po;

import java.util.ArrayList;
import java.util.List;

import com.pujjr.carcredit.vo.QueryParamToDoTaskVo;

public class QueryParamToDoTaskPo extends QueryParamToDoTaskVo
{
	private String assignee;
	
	private List<String> inTaskDefKeyList = new ArrayList<String>();
	
	private List<String> outTaskDefKeyList = new ArrayList<String>();

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public List<String> getInTaskDefKeyList() {
		return inTaskDefKeyList;
	}

	public void setInTaskDefKeyList(List<String> inTaskDefKeyList) {
		this.inTaskDefKeyList = inTaskDefKeyList;
	}

	public List<String> getOutTaskDefKeyList() {
		return outTaskDefKeyList;
	}

	public void setOutTaskDefKeyList(List<String> outTaskDefKeyList) {
		this.outTaskDefKeyList = outTaskDefKeyList;
	}
}
