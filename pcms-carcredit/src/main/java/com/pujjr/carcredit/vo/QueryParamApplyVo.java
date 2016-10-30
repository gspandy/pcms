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
	private String applyStartDate;
	//申请截止日期
	private String applyEndDate;
	//审批通过开始日期
	private String approveStartDate;
	//审核通过结束日期
	private String approveEndDate;
	//放款完成开始日期
	private String loanStartDate;
	//放款完成结束日期
	private String loanEndDate;
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

	public String getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	public String getApplyStartDate() {
		return applyStartDate;
	}
	public void setApplyStartDate(String applyStartDate) {
		this.applyStartDate = applyStartDate;
	}
	public String getApplyEndDate() {
		return applyEndDate;
	}
	public void setApplyEndDate(String applyEndDate) {
		this.applyEndDate = applyEndDate;
	}
	public String getApproveStartDate() {
		return approveStartDate;
	}
	public void setApproveStartDate(String approveStartDate) {
		this.approveStartDate = approveStartDate;
	}
	public String getApproveEndDate() {
		return approveEndDate;
	}
	public void setApproveEndDate(String approveEndDate) {
		this.approveEndDate = approveEndDate;
	}
	public String getLoanStartDate() {
		return loanStartDate;
	}
	public void setLoanStartDate(String loanStartDate) {
		this.loanStartDate = loanStartDate;
	}
	public String getLoanEndDate() {
		return loanEndDate;
	}
	public void setLoanEndDate(String loanEndDate) {
		this.loanEndDate = loanEndDate;
	}
	
	
}
