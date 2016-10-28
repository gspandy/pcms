package com.pujjr.carcredit.service;

public interface IPrintService {
	
	/**
	 * 打印租赁合同 pring leaser contract
	 * @param operId 操作者
	 * @param businessId 申请单号
	 * @param contextPath 绝对路径
	 * @param contractKey oss存储上合同打印模板osskey
	 * tom 2016年10月27日
	 */
	String prtLeaseContract(String businessId,String contextPath,String contractKey);
	/**
	 * 打印抵押合同A版  print mortgatge contract B 2016-10-24
	 * @param operId
	 * @param businessId
	 */
	String prtMortgageContract(String businessId,String contextPath,String contractKey);
	/**
	 * 打印车辆交接单print car delievery receipt
	 * @param operId
	 * @param bussinessId
	 */
	String prtDeleiverReceipt(String businessId,String contextPath,String contractKey);
	/**
	 * 打印车辆抵押列表
	 * @param operId
	 * @param businessId
	 */
	String prtMortgageList(String businessId,String contextPath,String contractKey);
	/**
	 * 打印贷款收据
	 * @param operId
	 * @param businessId
	 */
	String prtLoanReceipt(String businessId,String contextPath,String contractKey);
	/**
	 * 打印还款提醒
	 * @param operId
	 * @param businessId
	 */
	String prtRepayRemind(String businessId,String contextPath,String contractKey);
	/**
	 * 打印共租人承诺
	 * @param operId
	 * @param businessId
	 */
	String prtColesseePromise(String businessId,String contextPath,String contractKey);
	/**
	 * 通过申请单生产指定的合同，并返回存放OSSKEY
	 * @param appId 申请单号
	 * @param contractKey 合同标识
	 * **/
	String generateContract(String appId,String contextPath,String contractKey);
	
	/**
	 * 打印核准函
	 * tom 2016年10月28日
	 */
	String prtCheckLetter(String appId,String contextPath,String contractKey);
	
}
