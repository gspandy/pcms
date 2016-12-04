package com.pujjr.assetsmanage.vo;

import java.util.List;

import com.pujjr.carcredit.vo.ApplyLinkmanVo;

/**
 * 联系人变更信息
 * @author dengpan
 *
 */
public class AlterLinkmanVo 
{
	//变更申请单号
	private String appId;
	//联系人信息
	private List<ApplyLinkmanVo> linkmans;
	//变更备注
	private String alterComment;
		
	public String getAlterComment() {
		return alterComment;
	}
	public void setAlterComment(String alterComment) {
		this.alterComment = alterComment;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public List<ApplyLinkmanVo> getLinkmans() {
		return linkmans;
	}
	public void setLinkmans(List<ApplyLinkmanVo> linkmans) {
		this.linkmans = linkmans;
	}
}
