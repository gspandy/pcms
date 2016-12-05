package com.pujjr.assetsmanage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.assetsmanage.enumeration.CollectionTaskType;
import com.pujjr.assetsmanage.service.IInsuranceManageService;
import com.pujjr.base.dao.InsuranceHisMapper;
import com.pujjr.base.domain.InsuranceHis;
import com.pujjr.base.po.InsuranceHisPo;
import com.pujjr.carcredit.constant.InsuranceType;
import com.pujjr.carcredit.domain.SignFinanceDetail;
import com.pujjr.carcredit.service.ISignContractService;
import com.pujjr.jbpm.core.command.CommandType;
import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.jbpm.vo.ProcessGlobalVariable;
import com.pujjr.utils.Utils;
@Service
@Transactional
public class InsuranceManageServiceImpl implements IInsuranceManageService {

	@Autowired
	private ISignContractService signContractService;
	@Autowired
	private InsuranceHisMapper insuranceHisDao;
	@Autowired
	private IRunWorkflowService runWorkflowService;
	@Override
	public List<HashMap<String, Object>> getInsuranceHisList(String appId) {
		// TODO Auto-generated method stub
		List<SignFinanceDetail> signListPo = signContractService.getSignFinanceDetailByAppId(appId);
		List<HashMap<String,Object>> vo = new ArrayList<HashMap<String,Object>>();
		for(SignFinanceDetail po : signListPo)
		{
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("carVin", po.getCarVin());
			item.put("signId", po.getId());
			//历史交强险购买记录
			List<InsuranceHisPo> jqxHisList = insuranceHisDao.selectBySignId(po.getId(), InsuranceType.JQX.getName());
			item.put("jqxHisList", jqxHisList);
			//历史商业购买记录
			List<InsuranceHisPo> syxHisList = insuranceHisDao.selectBySignId(po.getId(), InsuranceType.SYX.getName());
			item.put("syxHisList", syxHisList);
			//历史履约险购买记录
			List<InsuranceHisPo> lyxHisList = insuranceHisDao.selectBySignId(po.getId(), InsuranceType.LYX.getName());
			item.put("lyxHisList", lyxHisList);
			vo.add(item);
		}
		return vo;
	}

	@Override
	public void addInsurance(String appId, String signId,String insType, InsuranceHis vo) {
		// TODO Auto-generated method stub
		vo.setId(Utils.get16UUID());
		vo.setSignId(signId);
		vo.setAppId(appId);
		vo.setInsType(insType);
		insuranceHisDao.insert(vo);
	}

	@Override
	public void createInsuranceContinueTask(String appId,String operId) {
		// TODO Auto-generated method stub
		//启动流程
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("appId", appId);
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, operId);
		ProcessInstance instance = runWorkflowService.startProcess("BXXB", "", vars);
	}

	@Override
	public void commitInsuranceContinue(String taskId) {
		// TODO Auto-generated method stub
		runWorkflowService.completeTask(taskId, "",null, CommandType.COMMIT);
	}

}
