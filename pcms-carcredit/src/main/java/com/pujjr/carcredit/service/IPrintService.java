package com.pujjr.carcredit.service;

public interface IPrintService {
	/**
	 * 打印租赁合同 pring leaser contract 2016-10-24
	 * @param operId
	 * @param businessId
	 */
	void prtLeaseContract(String operId,String businessId,String url);
	/**
	 * 打印抵押合同B版  print mortgatge contract B 2016-10-24
	 * @param operId
	 * @param businessId
	 */
	void prtMortgageContract(String operId,String businessId);
	/**
	 * 打印车辆交接单print car delievery receipt
	 * @param operId
	 * @param bussinessId
	 */
	void prtDeleiverReceipt(String operId,String bussinessId);
	/**
	 * 打印车辆抵押列表
	 * @param operId
	 * @param businessId
	 */
	void prtMortgageList(String operId,String businessId);
	/**
	 * 打印贷款收据
	 * @param operId
	 * @param businessId
	 */
	void prtLoanReceipt(String operId,String businessId);
	/**
	 * 打印还款提醒
	 * @param operId
	 * @param businessId
	 */
	void prtRepayRemind(String operId,String businessId);
	/**
	 * 打印共租人承诺
	 * @param operId
	 * @param businessId
	 */
	void prtColesseePromise(String operId,String businessId);
}
