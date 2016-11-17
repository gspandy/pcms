package com.pujjr.postloan.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.postloan.po.OfferInfoPo;

/**
 * 贷款查询Dao处理
 * @author 150032
 *
 */
public interface LoanQueryMapper 
{
	/**
	 * 查询贷款客户列表
	 * @return
	 */
	public List<HashMap<String,Object>> selectLoanCustList();
}
