package com.pujjr.assetsmanage.vo;
/**
 * 变更共租人信息
 * @author dengpan
 *
 */
public class AlterColesseeVo 
{
	//变更申请单号
	private String appId;
	//共租人手机号码
	private String mobile;
	//共租人QQ号
	private String qq;
	//共租人微信号
	private String weixin;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	
	
}
