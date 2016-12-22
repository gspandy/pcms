package com.pujjr.postloan.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.carcredit.domain.ApplyTenant;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.postloan.dao.GeneralLedgerMapper;
import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.postloan.service.IPostLoanSmsService;
import com.pujjr.postloan.vo.RepayingFeeItemVo;
import com.pujjr.sms.domain.SmsTemplate;
import com.pujjr.sms.service.ISmsService;
import com.pujjr.sms.vo.SmsMessageVo;
import com.pujjr.utils.Utils;
@Service
@Transactional
public class PostLoanSmsServiceImpl implements IPostLoanSmsService {

	@Autowired
	private ISmsService smsService;
	@Autowired
	private IApplyService applyService;
	@Autowired
	private GeneralLedgerMapper ledgerDao;
	@Autowired
	private IAccountingService accountingService;
	@Override
	public SmsMessageVo genPostLoanSms(String tplKey, String appId) {
		// TODO Auto-generated method stub
		
		//获取客户姓名
		ApplyTenant tenant = applyService.getApplyTenant(appId);
		String custName = tenant.getName();
		//获取逾期天数
		GeneralLedger ledgerPo = ledgerDao.selectByAppId(appId);
		int overdueDay = ledgerPo.getAddupOverdueDay();
		String date = Utils.formateDate2String(new Date(), "yyyy年MM月dd日");
		//获取应还金额
		RepayingFeeItemVo feeItem = accountingService.getRepayingFeeItems(appId, false, null, false, true);
		double totalAmt = feeItem.getRepayCapital()+feeItem.getRepayInterest()+feeItem.getRepayOverdueAmount()+feeItem.getOtherAmount()+feeItem.getOtherOverdueAmount();
		
		String tplContent = "";
		if(tplKey.equals("custom"))
		{
			tplContent = "";
		}
		else
		{
			SmsTemplate smsTpl = smsService.getTemplateByTplKey(tplKey);
			tplContent = smsTpl.getTplContent();
			tplContent=tplContent.replaceAll("#\\{custName\\}", custName);
			tplContent=tplContent.replaceAll("#\\{overdueDay\\}", String.valueOf(overdueDay));
			tplContent=tplContent.replaceAll("#\\{repayAmt\\}", String.valueOf(totalAmt));
		}
		
		SmsMessageVo vo = new SmsMessageVo();
		vo.setMessage(tplContent);
		vo.setMobile(tenant.getMobile());
		return vo;
	}

}
