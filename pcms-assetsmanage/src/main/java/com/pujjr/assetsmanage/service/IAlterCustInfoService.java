package com.pujjr.assetsmanage.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.assetsmanage.vo.AlterBankInfoVo;
import com.pujjr.assetsmanage.vo.AlterColesseeVo;
import com.pujjr.assetsmanage.vo.AlterLinkmanVo;
import com.pujjr.assetsmanage.vo.AlterTenantVo;

/**
 * 信息变更处理服务
 * @author dengpan
 *
 */
@Service
@Transactional
public interface IAlterCustInfoService 
{
	/**
	 * 变更承租人信息
	 * @param vo 承租人变更信息
	 * @param operId 变更人
	 */
	public void alterTenantInfo(AlterTenantVo vo , String operId);
	/**
	 * 变更共租人信息
	 * @param vo 共租人变更信息
	 * @param operId 变更人
	 */
	public void alterColesseeInfo(AlterColesseeVo vo ,String operId);
	/**
	 * 变更联系人信息
	 * @param vo 联系人变更信息
	 * @param operId 变更人
	 */
	public void alterLinkmanInfo(AlterLinkmanVo vo , String operId);
	/**
	 * 变更还款银行信息
	 * @param vo 还款变更信息
	 * @param operId
	 */
	public void alterBankInfo(AlterBankInfoVo vo , String operId);
	/**
	 * 获取还款历史变更信息
	 * @param appId
	 * @return
	 */
	public List<HashMap<String,Object>> getAlterBankInfoHisList(String appId);
}
