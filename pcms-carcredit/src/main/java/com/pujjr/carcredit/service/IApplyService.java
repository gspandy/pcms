package com.pujjr.carcredit.service;

import java.util.HashMap;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.domain.SysAccount;
import com.pujjr.carcredit.domain.Apply;
import com.pujjr.carcredit.domain.ApplyCloessee;
import com.pujjr.carcredit.domain.ApplyFinance;
import com.pujjr.carcredit.domain.ApplyTenant;
import com.pujjr.carcredit.domain.ApplyTenantCar;
import com.pujjr.carcredit.domain.ApplyTenantHouse;
import com.pujjr.carcredit.po.ApplyInfoPo;
import com.pujjr.carcredit.vo.ApplyCloesseeVo;
import com.pujjr.carcredit.vo.ApplyFamilyDebtVo;
import com.pujjr.carcredit.vo.ApplyFinanceVo;
import com.pujjr.carcredit.vo.ApplyLinkmanVo;
import com.pujjr.carcredit.vo.ApplySpouseVo;
import com.pujjr.carcredit.vo.ApplyTenantVo;
import com.pujjr.carcredit.vo.ApplyUncommitVo;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.carcredit.vo.QueryParamApplyVo;

@Service
@Transactional(rollbackFor=Exception.class) 
public interface IApplyService {
	public void tempSaveApply();

	public String saveApply(ApplyVo applyVo, String accountId);

	public void saveApplyFinance(ApplyVo applyVo, String accountId);

	public void saveApplySpouse(ApplyVo applyVo, String accountId);

	public void saveApplyCloessee(ApplyVo applyVo, String accountId);

	public void saveApplyLinkman(ApplyVo applyVo, String accountId);

	public void saveApplyFamilyDebt(ApplyVo applyVo, String accountId);

	public void saveApplyTenant(ApplyVo applyVo, String accountId);

	public void saveApplyTenantHouse(ApplyTenantVo atv, String accountId);

	public void saveApplyTenantCar(ApplyTenantVo atv, String accountId);

	public void commitApply();

	public void deleteApply();

	public void getUnCommitApply(String accountId, List<Apply> applyList);

	public ApplyVo getApplyDetail(String appId);

	// 查询指定用户未提交的申请单信息
	List<ApplyInfoPo> getApplyInfoList(String accountId, String status,QueryParamApplyVo queryParam);

	// public List<Apply> selectByMap(HashMap condition,List<Apply> applyList);
	public void commitApplyTask(ApplyVo applyVo, SysAccount sysAccount);

	public List<ApplyFinance> getApplyFinanceList(String appId);

	/**
	 * 获取t_apply表信息
	 * 
	 * @param appId
	 * @return
	 */
	public Apply getApply(String appId);

	/**
	 * 更新t_apply表信息
	 * 
	 * @param applyVo
	 * @return
	 */
	public int updateApply(Apply apply);
	/**
	 * 根据申请单号查询承租人信息
	 * @param appId
	 * @return
	 */
	public ApplyTenant getApplyTenant(String appId);
	/**
	 * 根据申请单号查询承租人房产信息
	 * @param appId
	 * @return
	 */
	public List<ApplyTenantHouse> getApplyTenantHouseList(String appId);
	/**
	 * 根据申请单好查询承租人车产信息
	 * @param appId
	 * @return
	 */
	public List<ApplyTenantCar> getApplyTenantCarList(String appId);
	/**
	 * 通过申请数据计算客户等级
	 * @param applyVo 申请数据对象
	 * @return
	 */
	public String calCustomLevel(ApplyVo applyVo);
}
