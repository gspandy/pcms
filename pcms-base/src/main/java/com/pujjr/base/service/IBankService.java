package com.pujjr.base.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.domain.BankInfo;
/**
 * 银行参数设置
 * @author 150032
 *
 */
@Service
@Transactional(rollbackFor=Exception.class)
public interface IBankService 
{
	/**
	 * 查询银行信息列表
	 * @return
	 */
	public List<BankInfo> getBankInfoList();
	/**
	 * 增加银行信息
	 * @param record
	 */
	public void addBankInfo(BankInfo record);
	/**
	 * 修改银行信息
	 * @param record
	 */
	public void modifyBankInfo(BankInfo record);
	/**
	 * 删除银行信息
	 * @param id
	 */
	public void deleteBankInfoById(String id);
	/**
	 * 查询所有启用银行列表
	 * @return
	 */
	public List<BankInfo> getAllEnabledBankInfoList();
	/**
	 * 查询所有支持银行的银行列表
	 * @return
	 */
	public List<BankInfo> getAllUnionpayBankInfoList();
	/**
	 * 根据ID查询银行信息
	 * @param id
	 * @return
	 */
	public BankInfo getBankInfoById(String id);
}
