package com.pujjr.carcredit.service;

import com.itextpdf.text.pdf.AcroFields;
import com.pujjr.carcredit.vo.PColesseePromiseVo;
import com.pujjr.carcredit.vo.PDeleiverReceiptVo;
import com.pujjr.carcredit.vo.PLeaseConstractVo;
import com.pujjr.carcredit.vo.PLoanReceiptVo;
import com.pujjr.carcredit.vo.PMortgageContractAVo;
import com.pujjr.carcredit.vo.PMortgageContractBVo;
import com.pujjr.carcredit.vo.PMortgageListVo;
import com.pujjr.carcredit.vo.PRepayRemindVo;

public interface IPrintDataSrcServcie {
	
	
	/**
	 * 获取待打印融资合同数据
	 * @param appId
	 * @return
	 */
	PLeaseConstractVo getPrintLeaseConstract(String appId);
	/**
	 * 获取抵押合同A版数据
	 * @param appId
	 * @return
	 * tom 2016年10月27日
	 */
	PMortgageContractAVo getMortgageContractA(String appId);
	/**
	 * 获取抵押合同B版数据
	 * @param appId
	 * @return
	 * tom 2016年10月27日
	 */
	PMortgageContractBVo getMortgegeContractB(String appId);
	/**
	 * 获取车辆交接单数据
	 * @param appId
	 * @return
	 * tom 2016年10月27日
	 */
	PDeleiverReceiptVo getDeleiverReceipt(String appId);
	/**
	 * 获取车辆抵押清单数据
	 * @param appId
	 * @return
	 * tom 2016年10月27日
	 */
	PMortgageListVo getMortgageList(String appId);
	/**
	 * 获取借款收据数据
	 * @param appId
	 * @return
	 * tom 2016年10月27日
	 */
	PLoanReceiptVo getLoanReceipt(String appId);
	/**
	 * 获取还款提醒数据
	 * @param appId
	 * @return
	 * tom 2016年10月27日
	 */
	PRepayRemindVo getRepayRemind(String appId);
	/**
	 * 获取共租人承诺数据
	 * @param appId
	 * @return
	 * tom 2016年10月27日
	 */
	PColesseePromiseVo getColesseePromise(String appId);
	/**
	 * pdf表单填值
	 * @param fields 待填值pdf表单字段
	 * @param leaseConstractVo pdf表单bean
	 * tom 2016年10月27日
	 */
	void setAcroFields(AcroFields fields, Object obj);
}
