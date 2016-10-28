package com.pujjr.carcredit.vo;

import java.util.Date;

import com.pujjr.base.vo.QueryParamPageVo;

/**
 * 申请单查询参数
 * **/
public class QueryParamApplyVo extends QueryParamPageVo 
{
	//申请单编号
	private String appId;
	//客户姓名
	private String name;
	//产品编码
	private String productCode;
	//证件号码
	private String idNo;
	//承租人电话号码
	private String mobile;
	//合同编号
	private String contractNo;
	//经销商编码
	private String sysBranchCode;
	//申请开始日期
	private Date applyStartDate;
	//申请截止日期
	private Date applyEndDate;
	//审批通过开始日期
	private Date approveStartDate;
	//审核通过结束日期
	private Date approveEndDate;
	//放款完成开始日期
	private Date loanStartDate;
	//放款完成结束日期
	private Date loanEndDate;
	//案件状态
	private String applyStatus;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getSysBranchCode() {
		return sysBranchCode;
	}
	public void setSysBranchCode(String sysBranchCode) {
		this.sysBranchCode = sysBranchCode;
	}
	public Date getApplyStartDate() {
		return applyStartDate;
	}
	public void setApplyStartDate(Date applyStartDate) {
		this.applyStartDate = applyStartDate;
	}
	public Date getApplyEndDate() {
		return applyEndDate;
	}
	public void setApplyEndDate(Date applyEndDate) {
		this.applyEndDate = applyEndDate;
	}
	public Date getApproveStartDate() {
		return approveStartDate;
	}
	public void setApproveStartDate(Date approveStartDate) {
		this.approveStartDate = approveStartDate;
	}
	public Date getApproveEndDate() {
		return approveEndDate;
	}
	public void setApproveEndDate(Date approveEndDate) {
		this.approveEndDate = approveEndDate;
	}
	public Date getLoanStartDate() {
		return loanStartDate;
	}
	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}
	public Date getLoanEndDate() {
		return loanEndDate;
	}
	public void setLoanEndDate(Date loanEndDate) {
		this.loanEndDate = loanEndDate;
	}
	public String getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	
	
}
