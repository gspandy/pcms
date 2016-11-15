package com.pujjr.postloan.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import com.pujjr.base.domain.BankInfo;
import com.pujjr.base.domain.Merchant;
import com.pujjr.base.service.IBankService;
import com.pujjr.base.service.IMerchantService;
import com.pujjr.base.service.IOSSService;
import com.pujjr.base.service.ISequenceService;
import com.pujjr.carcredit.domain.ApplyTenant;
import com.pujjr.carcredit.domain.SignContract;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.service.ISignContractService;
import com.pujjr.postloan.dao.ChargeLogMapper;
import com.pujjr.postloan.dao.ChargeMapper;
import com.pujjr.postloan.dao.OfferBatchInfoMapper;
import com.pujjr.postloan.dao.OfferSummaryMapper;
import com.pujjr.postloan.dao.WaitingChargeMapper;
import com.pujjr.postloan.domain.ChargeLog;
import com.pujjr.postloan.domain.OfferBatchInfo;
import com.pujjr.postloan.domain.OfferSummary;
import com.pujjr.postloan.domain.WaitingCharge;
import com.pujjr.postloan.enumeration.ChargeMode;
import com.pujjr.postloan.enumeration.ChargeStatus;
import com.pujjr.postloan.enumeration.OfferSource;
import com.pujjr.postloan.enumeration.OfferStatus;
import com.pujjr.postloan.po.OfferInfoPo;
import com.pujjr.postloan.service.IChargeService;
import com.pujjr.utils.Utils;

@Service
public class ChargeServiceImpl implements IChargeService {

	@Autowired
	private ChargeMapper chargeDao;
	@Autowired
	private WaitingChargeMapper waitingChargeDao;
	@Autowired
	private OfferSummaryMapper offerSummaryDao;
	@Autowired
	private IMerchantService merchantService;
	@Autowired
	private ISignContractService signContractService;
	@Autowired
	private ISequenceService sequenceService;
	@Autowired
	private IBankService bankService;
	@Autowired
	private IApplyService applyService;
	@Autowired
	private ChargeLogMapper chargeLogDao;
	@Autowired
	private IOSSService ossService;
	@Autowired
	private OfferBatchInfoMapper offerBatchInfoDao;
	@Override
	public List<HashMap<String, Object>> getEnableChargeList() {
		// TODO Auto-generated method stub
		return chargeDao.selectEnableChargeList();
	}

	@Override
	public void setChargeMode(List<String> chargeIds, ChargeMode mode) throws Exception {
		// TODO Auto-generated method stub
		if(chargeIds.size()==0)
		{
			List<HashMap<String,Object>> tmpList = this.getEnableChargeList();
			chargeIds = new ArrayList<String>();
			for(HashMap<String,Object> tmp : tmpList)
			{
				chargeIds.add(tmp.get("id").toString());
			}
		}
		for(String chargeId : chargeIds)
		{
			WaitingCharge chargePo = waitingChargeDao.selectByPrimaryKey(chargeId);
			if(chargePo != null)
			{
				//插入报盘表
				OfferSummary offerPo = new OfferSummary();
				String offerId = Utils.get16UUID();
				offerPo.setId(offerId);
				offerPo.setAppId(chargePo.getAppId());
				offerPo.setFeeType(chargePo.getFeeType());
				offerPo.setFeeRefId(chargePo.getFeeRefId());
				offerPo.setOfferSource(OfferSource.SYSTEM.getName());
				offerPo.setChargeMode(mode.getName());
				offerPo.setOfferAmount(chargePo.getRepayCapital()+chargePo.getRepayInterest()+chargePo.getRepayOverdueAmount());
				offerPo.setOfferTime(new Date());
				//设置扣款状态为待扣款
				offerPo.setChargeStatus(ChargeStatus.Waiting.getName());
				offerSummaryDao.insert(offerPo);
				
				//修改待扣款明细报盘状态为报盘中
				chargePo.setOfferId(offerId);
				chargePo.setOfferStatus(OfferStatus.Offering.getName());
				waitingChargeDao.updateByPrimaryKey(chargePo);
			}
			
		}
	}

	@Override
	public List<OfferInfoPo> getWatingOfferChargeList(ChargeMode mode) {
		// TODO Auto-generated method stub
		return chargeDao.selectWatingOfferChargeList(mode.getName());
	}

	@Override
	public String confirmManualOffer(String merchantNo,String operId) throws Exception {
		// TODO Auto-generated method stub
		Merchant merchant = merchantService.getMerchantByNo(merchantNo); 
		if(merchant == null)
		{
			throw new Exception("商户号不存在");
		}
		//生成批次号
		int seqBatchNo = sequenceService.getNextVal("unionBatchNo");
		String batchNo = String.format("%06d", seqBatchNo);
		String chargeDate = Utils.getFormatDate(new Date(), "yyyyMMdd");
		//生成报盘文件名
		String offerFileName = merchantNo+"_"+chargeDate+"_"+batchNo+"_Q.txt";
		String manualOfferBatchId = merchantNo+"_"+chargeDate+"_"+batchNo;
		//总笔数、总金额
		double totalChargeAmount = 0.00;
		int totalChargeCnt= 0;
		
		List<String> chargeFileStrList = new ArrayList<String>();
		List<OfferInfoPo> manualOfferList = chargeDao.selectWatingOfferChargeList(ChargeMode.UnionFile.getName());
		for(OfferInfoPo item : manualOfferList)
		{
			/**
			 * 更新报盘信息阶段
			 */
			//更新报盘表状态信息
			OfferSummary summary = new OfferSummary();
			BeanUtils.copyProperties(item, summary);
			summary.setChargeStatus(ChargeStatus.Charging.getName());
			summary.setOfferBatchNo(manualOfferBatchId);
			offerSummaryDao.updateByPrimaryKey(summary);
			/**
			 * 产生扣款明细阶段
			 * **/
			//获取签约信息
			SignContract signContract = signContractService.getSignContractByAppId(item.getAppId());
			String repayAcctName = signContract.getRepayAcctName();
			String repayAcctNo = signContract.getRepayAcctNo();
			BankInfo bankInfo = bankService.getBankInfoById(signContract.getRepayBankId());
			String repayBankNo = bankInfo.getUnionpayCode();
			//单笔最大扣款金额
			double maxChargeAmount = bankInfo.getMaxChargeAmount();
			//如果没有设置或者为0则设置一个很大的值
			if(maxChargeAmount==0.00)
			{
				maxChargeAmount=100000000.00;
			}
			double offerAmount = item.getOfferAmount();
			//合计总金额
			totalChargeAmount+=offerAmount;
			//按照银行上限拆分报盘金额
			do
			{
				//临时扣款金额
				double chargeAmount=0.00; 
				if(offerAmount>maxChargeAmount)
				{
					chargeAmount = maxChargeAmount;
					offerAmount-=chargeAmount;
				}
				else
				{
					chargeAmount = offerAmount;
					offerAmount=0.00;
				}
				//生成订单号
				int seqOrderNo = sequenceService.getNextVal("orderNo");
				String orderNo = String.format("%016d", seqOrderNo);
				
				//生成扣款信息
				ChargeLog chargeLog = new ChargeLog();
				chargeLog.setId(Utils.get16UUID());
				chargeLog.setAppId(item.getAppId());
				chargeLog.setOfferId(item.getId());
				chargeLog.setChargeMode(item.getChargeMode());
				chargeLog.setMerchantNo(merchantNo);
				chargeLog.setOrderNo(orderNo);
				chargeLog.setOpenBankNo(repayBankNo);
				chargeLog.setCustName(repayAcctName);
				chargeLog.setCardNo(repayAcctNo);
				//默认身份证
				chargeLog.setIdType("01");
				ApplyTenant tenant = applyService.getApplyTenant(item.getAppId());
				chargeLog.setIdNo(tenant.getIdNo());
				chargeLog.setChargeAmount(chargeAmount);
				chargeLog.setChargeTime(new Date());
				chargeLog.setChargeResult("");
				chargeLogDao.insert(chargeLog);
				//合计总笔数
				totalChargeCnt++;
				
				String chargeStr = chargeDate +"|"+orderNo+"|"+repayBankNo+"|"+"0"+"|"+repayAcctNo+"|"+
						repayAcctName+"|"+"01|"+tenant.getIdNo()+"|"+Utils.convertY2F(chargeAmount)+
						"|代扣|"+String.valueOf(totalChargeCnt)+"笔";
				chargeFileStrList.add(chargeStr);
			}while(Double.compare(offerAmount, 0.00)>0);
			
		}
		/**
		 * 生成报盘文件
		 */
		chargeFileStrList.add(0, merchantNo+"|"+batchNo+"|"+totalChargeCnt+"|"+Utils.convertY2F(totalChargeAmount));
		String realPath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
		File file = new File(realPath+File.separator+"tmp"+File.separator+offerFileName);
		FileUtils.writeLines(file, chargeFileStrList);
		/**
		 * 上传报盘文件至OSS存储
		 * **/
		String ossKey = "offerfile/"+chargeDate+"/"+offerFileName;
		InputStream in = new FileInputStream(file);
		ossService.putObject(ossKey, in);
		
		/**
		 * 保存报盘批次信息
		 * **/
		OfferBatchInfo offerBatchInfo = new OfferBatchInfo();
		offerBatchInfo.setId(Utils.get16UUID());
		offerBatchInfo.setOfferFileName(offerFileName);
		offerBatchInfo.setOfferFilePath(ossKey);
		offerBatchInfo.setOfferMerchantNo(merchantNo);
		offerBatchInfo.setOfferTotalCnt(totalChargeCnt);
		offerBatchInfo.setOfferTotalAmount(totalChargeAmount);
		offerBatchInfo.setOfferBatchNo(batchNo);
		offerBatchInfo.setOfferTime(new Date());
		offerBatchInfo.setOfferId(operId);
		offerBatchInfoDao.insert(offerBatchInfo);
		
		return ossKey;
	}

}
