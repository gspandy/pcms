package com.pujjr.postloan.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.carcredit.domain.ApplyTenant;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.postloan.dao.GeneralLedgerMapper;
import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.domain.RepayPlan;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.postloan.service.IPostLoanSmsService;
import com.pujjr.postloan.vo.RepayingFeeItemVo;
import com.pujjr.sms.domain.SmsTemplate;
import com.pujjr.sms.service.ISmsService;
import com.pujjr.sms.vo.SmsMessageVo;
import com.pujjr.utils.Utils;
@Service
@Transactional(rollbackFor=Exception.class)
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
	public SmsMessageVo genPostLoanSms(String tplKey, String appId) throws Exception {
		// TODO Auto-generated method stub
		SmsMessageVo vo = new SmsMessageVo();
		//还款日前转公司户提醒短信
		if("convertToPublicAcct".equals(tplKey))
		{
			//获取客户姓名
			ApplyTenant tenant = applyService.getApplyTenant(appId);
			String custName = tenant.getName();
			//获取当期还款计划
			RepayPlan repayPlan = accountingService.getCurrentPeriodRepayPlan(appId);
			String tplContent = "";
			SmsTemplate smsTpl = smsService.getTemplateByTplKey(tplKey);
			if(smsTpl == null)
			{
				throw new Exception("未定义模板");
			}
			tplContent = smsTpl.getTplContent();
			tplContent=tplContent.replaceAll("#\\{custName\\}", custName);
			tplContent=tplContent.replaceAll("#\\{repayDate\\}",  Utils.getFormatDate(repayPlan.getClosingDate(),"yyyy年MM月dd日"));
			tplContent=tplContent.replaceAll("#\\{repayAmt\\}", String.valueOf(repayPlan.getRepayTotalAmount()));
			tplContent=tplContent.replaceAll("#\\{curDate\\}", Utils.getFormatDate(new Date(),"yyyy年MM月dd日"));
			vo.setMessage(tplContent);
			vo.setMobile(tenant.getMobile());
		}
		//逾期短信通知
		else
		{
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
				if(smsTpl == null)
				{
					throw new Exception("未定义模板");
				}
				tplContent = smsTpl.getTplContent();
				tplContent=tplContent.replaceAll("#\\{custName\\}", custName);
				tplContent=tplContent.replaceAll("#\\{overdueDay\\}", String.valueOf(overdueDay));
				tplContent=tplContent.replaceAll("#\\{repayAmt\\}", String.valueOf(totalAmt));
			}
			vo.setMessage(tplContent);
			vo.setMobile(tenant.getMobile());
		}
		return vo;
	}

}
