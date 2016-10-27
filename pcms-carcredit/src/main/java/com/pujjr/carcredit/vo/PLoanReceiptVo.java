/**
 * tom 2016年10月27日
 */
package com.pujjr.carcredit.vo;

/**
 * @author tom
 *
 */
public class PLoanReceiptVo {
	private String contractNo;
	private String totalLoanAmt;
	private String branchName;
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getTotalLoanAmt() {
		return totalLoanAmt;
	}
	public void setTotalLoanAmt(String totalLoanAmt) {
		this.totalLoanAmt = totalLoanAmt;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
}
