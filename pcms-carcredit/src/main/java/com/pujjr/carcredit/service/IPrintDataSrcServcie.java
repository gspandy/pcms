package com.pujjr.carcredit.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.pdf.AcroFields;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.carcredit.vo.LeaseCarVo;
import com.pujjr.carcredit.vo.PColesseePromiseVo;
import com.pujjr.carcredit.vo.PDeleiverReceiptVo;
import com.pujjr.carcredit.vo.PLeaseConstractVo;
import com.pujjr.carcredit.vo.PLoanReceiptVo;
import com.pujjr.carcredit.vo.PMortgageContractAVo;
import com.pujjr.carcredit.vo.PMortgageContractBVo;
import com.pujjr.carcredit.vo.PMortgageListVo;
import com.pujjr.carcredit.vo.PRepayRemindVo;
@Service
@Transactional(rollbackFor=Exception.class)
public interface IPrintDataSrcServcie {
	/**
	 * 获取融资车辆信息列表
	 * @param applyVo
	 * @return 融资车辆信息列表
	 */
	List<LeaseCarVo> getLeaseCarList(ApplyVo applyVo);
	/**
	 * 获取待打印融资合同数据
	 * @param appId
	 * @return
	 */
	PLeaseConstractVo getPrintLeaseConstract(String appId);
	/**
	 * 获取抵押合同数据
	 * @param appId
	 * @return
	 * tom 2016年10月27日
	 */
	PMortgageContractAVo getMortgageContract(String appId,String contractKey);
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
	 * @param obj pdf表单数据源
	 * tom 2016年10月27日
	 */
	void setAcroFields(AcroFields fields, Object obj);
	/**
	 * 抵押清单表单填值
	 * @param fields 待填值pdf表单字段
	 * @param obj pdf表单数据源
	 * tom 2016年10月27日
	 */
	void setMorgageListFields(AcroFields fields,Object obj);
	/**
	 * 还款计划表单填值
	 * @param fields 待填值pdf表单字段
	 * @param obj pdf表单数据源
	 * tom 2016年10月28日
	 */
	void setRepayRemindFields(AcroFields fields,Object obj);
	

	
}
