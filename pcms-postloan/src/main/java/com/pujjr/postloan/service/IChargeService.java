package com.pujjr.postloan.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.postloan.enumeration.ChargeMode;
import com.pujjr.postloan.po.OfferInfoPo;
import com.pujjr.utils.Utils;

/**
 * 扣款服务
 * **/
@Service
public interface IChargeService 
{
	/**
	 * 查询代扣款明细表可扣款明细
	 * @return
	 */
	public List<HashMap<String,Object>>  getEnableChargeList();
	/**
	 * 设置指定待扣款明细指定扣款模式,如果待扣款明细为空则设置目前所有可用待扣款明细
	 * @param chargeIds 待扣款明细ID列表
	 * @param mode  扣款模式
	 * @throws Exception 
	 */
	public void setChargeMode(List<String> chargeIds,ChargeMode mode) throws Exception;
	/**
	 * 查询报盘表中待报盘明细记录
	 * @return
	 */
	public List<OfferInfoPo>  getWatingOfferChargeList(ChargeMode mode);
	/**
	 * 确定手工报盘，产生报盘文件
	 * @param merchantNo 报盘商户号
	 * @return OSS存放路径KEY
	 * @throws Exception 
	 */
	public String confirmManualOffer(String merchantNo,String operId) throws Exception;
	
}
