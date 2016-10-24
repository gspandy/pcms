package com.pujjr.carcredit.vo;

import java.util.List;

import com.pujjr.carcredit.domain.SignContract;

public class SignContractVo extends SignContract
{
	private String loanBankName;
	private String loanSubBranch;
	private String loanAcctNo;
	private String loanAcctName;
	//补充放款资料备注
	private String supplyLoanInfoComment;
	
	public String getSupplyLoanInfoComment() {
		return supplyLoanInfoComment;
	}

	public void setSupplyLoanInfoComment(String supplyLoanInfoComment) {
		this.supplyLoanInfoComment = supplyLoanInfoComment;
	}

	private List<SignFinanceDetailVo> signFinanceList;

	public List<SignFinanceDetailVo> getSignFinanceList() {
		return signFinanceList;
	}

	public void setSignFinanceList(List<SignFinanceDetailVo> signFinanceList) {
		this.signFinanceList = signFinanceList;
	}

	public String getLoanBankName() {
		return loanBankName;
	}

	public void setLoanBankName(String loanBankName) {
		this.loanBankName = loanBankName;
	}

	public String getLoanSubBranch() {
		return loanSubBranch;
	}

	public void setLoanSubBranch(String loanSubBranch) {
		this.loanSubBranch = loanSubBranch;
	}

	public String getLoanAcctNo() {
		return loanAcctNo;
	}

	public void setLoanAcctNo(String loanAcctNo) {
		this.loanAcctNo = loanAcctNo;
	}

	public String getLoanAcctName() {
		return loanAcctName;
	}

	public void setLoanAcctName(String loanAcctName) {
		this.loanAcctName = loanAcctName;
	}
	
	
}
