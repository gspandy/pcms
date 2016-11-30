package com.pujjr.base.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pujjr.base.domain.Merchant;
@Service
public interface IMerchantService 
{
	/**
	 * 查询商户列表
	 * @param enabled
	 * @return
	 */
	public List<HashMap<String,Object>> getMerchantList(boolean enabled,String chnlType);
	/**
	 * 增加商户
	 * @param record
	 */
	public void addMerchant(Merchant record);
	/**
	 * 修改商户
	 * @param record
	 */
	public void modifyMerchant(Merchant record);
	/**
	 * 删除商户
	 * @param id
	 */
	public void deleteMerchantById(String id);
	/**
	 * 根据商户号查询商户信息
	 * @param merchantNo
	 * @return
	 */
	public Merchant getMerchantByNo(String merchantNo);
}
