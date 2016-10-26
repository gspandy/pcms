package com.pujjr.carcredit.service;

import com.pujjr.carcredit.vo.LeaseConstractVo;

public interface IPrintDataSrcServcie {
	/**
	 * 获取待打印融资合同数据
	 * @param appId
	 * @return
	 */
	LeaseConstractVo getPrintLeaseConstract(String appId);
}
