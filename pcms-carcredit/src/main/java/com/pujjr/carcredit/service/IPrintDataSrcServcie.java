package com.pujjr.carcredit.service;

import com.itextpdf.text.pdf.AcroFields;
import com.pujjr.carcredit.vo.LeaseConstractVo;

public interface IPrintDataSrcServcie {
	/**
	 * 获取待打印融资合同数据
	 * @param appId
	 * @return
	 */
	LeaseConstractVo getPrintLeaseConstract(String appId);

	/**
	 * pdf表单填值
	 * @param fields 待填值pdf表单字段
	 * @param leaseConstractVo pdf表单bean
	 * tom 2016年10月27日
	 */
	void setAcroFields(AcroFields fields, Object obj);
}
