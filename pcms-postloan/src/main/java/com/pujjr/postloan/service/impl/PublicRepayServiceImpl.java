package com.pujjr.postloan.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.jbpm.vo.ProcessGlobalVariable;
import com.pujjr.postloan.dao.ApplyPublicRepayMapper;
import com.pujjr.postloan.dao.GeneralLedgerMapper;
import com.pujjr.postloan.domain.ApplyPublicRepay;
import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.enumeration.LedgerProcessStatus;
import com.pujjr.postloan.enumeration.LoanApplyStatus;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.postloan.service.IPublicRepayService;
import com.pujjr.postloan.vo.ApplyPublicRepayVo;
import com.pujjr.postloan.vo.ApproveResultVo;
import com.pujjr.postloan.vo.PublicRepayTaskVo;
import com.pujjr.postloan.vo.RepayFeeItemVo;
import com.pujjr.utils.Utils;
@Service
@Transactional
public class PublicRepayServiceImpl implements IPublicRepayService {

	@Autowired
	private IAccountingService accountingService;
	@Autowired
	private IRunWorkflowService runWorkflowService;
	@Autowired
	private ApplyPublicRepayMapper publicRepayDao;
	@Autowired
	private GeneralLedgerMapper ledgerDao;
	
	@Override
	public RepayFeeItemVo getPublicRepayFeeItem(String appId) {
		// TODO Auto-generated method stub
		return accountingService.getRepayingFeeItems(appId, false, null, true);
	}

	@Override
	public void commitApplyPublicRepayTask(String appId, ApplyPublicRepayVo vo) throws Exception {
		// TODO Auto-generated method stub
		if(accountingService.checkCandoPublicRepay(appId))
		{

			/**
			 * 修改总账处理状态为申请对公还款,避免其他交易操作
			 * **/
			GeneralLedger ledgerPo = ledgerDao.selectByAppId(appId);
			ledgerPo.setProcessStatus(LedgerProcessStatus.ApplyPublicRepay.getName());
			ledgerDao.updateByPrimaryKey(ledgerPo);
			/**
			 * 创建申请信息
			 */
			ApplyPublicRepay po = new ApplyPublicRepay();
			String businessKey = Utils.get16UUID();
			po.setId(businessKey);
			po.setAppId(appId);
			po.setRepayCapital(vo.getFeeItem().getRemainCapital());
			po.setRepayInterest(vo.getFeeItem().getRepayInterest());
			po.setRepayOverdueAmount(vo.getFeeItem().getRepayOverdueAmount());
			po.setOtherFee(vo.getFeeItem().getOtherAmount());
			po.setOtherOverdueAmount(vo.getFeeItem().getOtherOverdueAmount());
			po.setChargeAmount(vo.getChargeAmount());
			po.setChargeDate(vo.getChargeDate());
			po.setApplyComment(vo.getApplyComment());
			po.setApplyStatus(LoanApplyStatus.WaitingApprove.getName());
			po.setCreateId(vo.getCreateId());
			po.setCreateDate(new Date());
			/**
			 * 启动流程
			 */
			HashMap<String,Object> vars = new HashMap<String,Object>();
			vars.put("appId", appId);
			vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, vo.getCreateId());
			ProcessInstance instance = runWorkflowService.startProcess("DGHK", businessKey, vars);
			po.setProcInstId(instance.getId());
			publicRepayDao.insert(po);
		}
		else
		{
			throw new Exception("当前不能操作对公还款");
		}
	}

	@Override
	public void commitApprovePublicRepayTask(String taskId, ApproveResultVo vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelPublicRepayTask(String taskId, String operId, String cancelComment) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<PublicRepayTaskVo> getApplyPublicRepayTaskList(String createId, List<String> applyStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplyPublicRepay getApplyPublicRepayTaskById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modifyApplyPublicRepayInfo(ApplyPublicRepay record) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteApplyPublicRepayInfoById(String id) {
		// TODO Auto-generated method stub

	}

}
