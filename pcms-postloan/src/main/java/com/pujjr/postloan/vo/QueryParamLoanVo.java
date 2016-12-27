package com.pujjr.postloan.vo;

import java.util.Date;

import com.pujjr.base.vo.QueryParamPageVo;

/**
 * 申请单查询参数
 * **/
public class QueryParamLoanVo extends QueryParamPageVo 
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
	//放款完成开始日期
	private Date loanStartDate;
	//放款完成结束日期
	private Date loanEndDate;
	//还款状态
	private String repayStatus;
	 //逾期天数
	private int overdueDay;
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
	public String getRepayStatus() {
		return repayStatus;
	}
	public void setRepayStatus(String repayStatus) {
		this.repayStatus = repayStatus;
	}
	public int getOverdueDay() {
		return overdueDay;
	}
	public void setOverdueDay(int overdueDay) {
		this.overdueDay = overdueDay;
	}
	
	
	
	
}
