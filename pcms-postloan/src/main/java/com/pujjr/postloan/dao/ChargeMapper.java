package com.pujjr.postloan.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.po.OfferInfoPo;

/**
 * 扣款Dao处理
 * @author 150032
 *
 */
public interface ChargeMapper 
{
	/**
	 * 查询代扣款明细表可扣款明细
	 * @return
	 */
	public List<HashMap<String,Object>> selectEnableChargeList();
	/**
	 * 查询报盘表中待报盘明细记录
	 * @param chargeMode-扣款模式
	 * @return
	 */
	public List<OfferInfoPo> selectWatingOfferChargeList(@Param("chargeMode")String chargeMode);
}
