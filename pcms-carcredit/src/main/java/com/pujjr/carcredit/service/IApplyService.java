package com.pujjr.carcredit.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.base.domain.SysAccount;
import com.pujjr.carcredit.domain.Apply;
import com.pujjr.carcredit.po.ApplyInfoPo;
import com.pujjr.carcredit.vo.ApplyUncommitVo;
import com.pujjr.carcredit.vo.ApplyVo;

@Service
@Transactional(rollbackFor=Exception.class) 
public interface IApplyService 
{
	public void tempSaveApply();
	
	public String saveApply(ApplyVo applyVo,String accountId) throws Exception;
	
	public void commitApply();
	
	public void deleteApply();
	
	public void getUnCommitApply(String accountId,List<Apply> applyList);
	
	public ApplyVo getUnCommitApplyDetail(String appId);
	//查询指定用户未提交的申请单信息
	List<ApplyInfoPo>  getApplyInfoList(String accountId,String status);
//	public List<Apply> selectByMap(HashMap condition,List<Apply> applyList);
	
	public void commitApplyTask(ApplyVo applyVo,SysAccount sysAccount);
}
