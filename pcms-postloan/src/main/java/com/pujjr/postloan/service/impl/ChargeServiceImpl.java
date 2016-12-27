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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.aliyun.oss.model.ObjectMetadata;
import com.pujjr.assetsmanage.service.ICollectionService;
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
import com.pujjr.postloan.dao.GeneralLedgerMapper;
import com.pujjr.postloan.dao.LoanQueryMapper;
import com.pujjr.postloan.dao.OfferBatchInfoMapper;
import com.pujjr.postloan.dao.OfferSummaryHisMapper;
import com.pujjr.postloan.dao.OfferSummaryMapper;
import com.pujjr.postloan.dao.WaitingChargeMapper;
import com.pujjr.postloan.domain.ChargeLog;
import com.pujjr.postloan.domain.GeneralLedger;
import com.pujjr.postloan.domain.OfferBatchInfo;
import com.pujjr.postloan.domain.OfferSummary;
import com.pujjr.postloan.domain.OfferSummaryHis;
import com.pujjr.postloan.domain.WaitingCharge;
import com.pujjr.postloan.enumeration.ChargeMode;
import com.pujjr.postloan.enumeration.ChargeStatus;
import com.pujjr.postloan.enumeration.OfferSource;
import com.pujjr.postloan.enumeration.OfferStatus;
import com.pujjr.postloan.enumeration.RepayMode;
import com.pujjr.postloan.enumeration.RepayStatus;
import com.pujjr.postloan.po.OfferInfoPo;
import com.pujjr.postloan.service.IAccountingService;
import com.pujjr.postloan.service.IChargeService;
import com.pujjr.sms.service.ISmsService;
import com.pujjr.utils.Utils;

@Service
@Transactional(rollbackFor=Exception.class)
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
	@Autowired
	private OfferSummaryHisMapper offerSummaryHisDao;
	@Autowired
	private IAccountingService accountingService;
	@Autowired
	private ICollectionService collectionService;
	@Autowired
	private GeneralLedgerMapper ledgerDao;
	@Autowired
	private ISmsService smsService;
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
			if(tmpList.size()==0)
			{
				throw new Exception("没有报盘明细");
			}
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
			 * 产生扣款明细阶段
			 * **/
			int chargeCnt=0;
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
				chargeCnt++;
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
				chargeLog.setTranDate(new Date());
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
			
			/**
			 * 更新报盘信息阶段
			 */
			//更新报盘表状态信息
			OfferSummary summary = new OfferSummary();
			BeanUtils.copyProperties(item, summary);
			summary.setChargeStatus(ChargeStatus.Charging.getName());
			summary.setOfferBatchNo(manualOfferBatchId);
			summary.setSplitCnt(chargeCnt);
			summary.setRetCnt(0);
			offerSummaryDao.updateByPrimaryKey(summary);
			/**
			 * 保存报盘汇总历史信息
			 * **/
			OfferSummaryHis offerSummaryHisPo = new OfferSummaryHis();
			BeanUtils.copyProperties(summary, offerSummaryHisPo);
			offerSummaryHisDao.insert(offerSummaryHisPo);
			
		}
		/**
		 * 生成报盘文件
		 */
		chargeFileStrList.add(0, merchantNo+"|"+batchNo+"|"+totalChargeCnt+"|"+Utils.convertY2F(totalChargeAmount));
		String filePath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/")+File.separator+"tmp"+File.separator+offerFileName;
		File file = new File(filePath);
		FileUtils.writeLines(file, chargeFileStrList);
		/**
		 * 上传报盘文件至OSS存储
		 * **/
		String ossKey = "offerfile/"+chargeDate+"/"+offerFileName;
		InputStream in = new FileInputStream(file);
		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentType("application/octet-stream");
		ossService.putObject(ossKey, in,meta);
		
		/**
		 * 保存报盘批次信息
		 * **/
		OfferBatchInfo offerBatchInfo = new OfferBatchInfo();
		//报盘批次ID以报送银联文件名为主键
		offerBatchInfo.setId(merchantNo+"_"+chargeDate+"_"+batchNo);
		offerBatchInfo.setOfferFileName(offerFileName);
		offerBatchInfo.setOfferFilePath(ossKey);
		offerBatchInfo.setOfferMerchantNo(merchantNo);
		offerBatchInfo.setOfferTotalCnt(totalChargeCnt);
		offerBatchInfo.setOfferTotalAmount(totalChargeAmount);
		offerBatchInfo.setOfferBatchNo(batchNo);
		offerBatchInfo.setOfferTime(new Date());
		offerBatchInfo.setOfferId(operId);
		offerBatchInfo.setRetofferStatus(OfferStatus.Offering.getName());
		offerBatchInfoDao.insert(offerBatchInfo);
		
		return ossKey;
	}

	@Override
	public List<HashMap<String, Object>> getManualOfferHisList(String operId) {
		// TODO Auto-generated method stub
		return chargeDao.selectManualOfferHisList(operId);
	}

	/**
	 * 处理回盘失败
	 * @param appId  申请单号
	 * @param offerId 报盘ID
	 * @throws Exception 
	 */
	private void  processChargeFailed(String appId,String feeId) throws Exception
	{
		//检查是否逾期
		GeneralLedger ledgerPo = ledgerDao.selectByAppId(appId);
		//如果是逾期的客户，理论上已经发起了催收任务，则不需要再判断了
		if(!ledgerPo.getRepayStatus().equals(RepayStatus.Overdue.getName()))
		{
			//判断是否已经发起催收任务，如果没有则发起电话催收任务
			if(collectionService.checkHasCollectionTask(appId)==false)
			{
				collectionService.createPhoneCollectionTask("admin", appId, "系统提示：当日扣款失败，请进行电话催收");
			}
			//如果只报了一次盘，则认为是首次扣款
			List<OfferSummaryHis> listOffer = offerSummaryHisDao.selectByFeeId(feeId);
			if(listOffer.size()==1)
			{
				OfferSummaryHis item = listOffer.get(0);
				ApplyTenant tenant = applyService.getApplyTenant(appId);
				smsService.sendRepayDayFailNotice(appId, "admin", tenant.getMobile(), tenant.getName(), Utils.getFormatDate(new Date(), "yyyy年MM月dd日"), item.getOfferAmount());
			}
		}
		
		
	}
	@Override
	public void retOfferProcess(String offerBatchId,List<String> resultList,String operId) throws Exception {
		// TODO Auto-generated method stub
		int retTotalCnt = 0;
		double retTotalAmount = 0.00 ;
		int retSuccCnt = 0;
		double retSuccAmount = 0.00;
		/**
		 * 先做回盘合法性判断
		 * **/
		OfferBatchInfo offerBatchInfo = offerBatchInfoDao.selectByPrimaryKey(offerBatchId);
		if(offerBatchInfo == null)
		{
			throw new Exception(offerBatchId+"对应的报盘信息不存在");
		}
		else
		{
			if(offerBatchInfo.getRetofferStatus().equals(OfferStatus.RetOffer.getName()))
			{
				throw new Exception(offerBatchId+"已回盘，请勿重复回盘操作");
			}
			if(offerBatchInfo.getRetofferStatus().equals(OfferStatus.WaitOffer.getName()))
			{
				throw new Exception(offerBatchId+"未报盘，请先报盘");
			}
			
		}
		/**
		 * 逐笔报盘记录进行处理
		 * **/
		for(String retOfferStr : resultList)
		{
			
			String[] retOfferItem =  retOfferStr.split("\\|");
			//交易日期
			Date tranDate = Utils.str82date(retOfferItem[1]);
			//订单号
			String orderNo = retOfferItem[2];
			//交易状态
			String tranStatus = retOfferItem[3];
			//交易结果
			String tranResult = retOfferItem[4];
			//卡号
			String cardNo = retOfferItem[6];
			//金额
			double tranAmt = Double.valueOf(retOfferItem[7])/100;
			retTotalCnt++;
			retTotalAmount+=tranAmt;
			
			//通过交易日期+订单号找出原扣款记录
			ChargeLog chargeLog = chargeLogDao.selecOriginChargeLog(tranDate, orderNo);
			if(chargeLog == null)
			{
				throw new Exception(orderNo+"不存在原始扣款记录");
			}
			else
			{
				/**
				 * 记录扣款结果
				 */
				chargeLog.setThirdPartyTime(new Date());
				chargeLog.setThirdPartyResult(tranResult);
				chargeLogDao.updateByPrimaryKey(chargeLog);

				OfferSummary offerSummary = offerSummaryDao.selectByPrimaryKey(chargeLog.getOfferId());
				OfferSummaryHis offerSummaryHis = offerSummaryHisDao.selectByPrimaryKey(chargeLog.getOfferId());
				
				/**
				 * 交易成功处理
				 */
				if(tranStatus.equals("1001"))
				{
					retSuccCnt++;
					retSuccAmount+=tranAmt;
					if(Double.compare(tranAmt, chargeLog.getChargeAmount())!=0)
					{
						throw new Exception(orderNo+"原始扣款金额与回盘金额不一致");
					}
					//冲账处理
					accountingService.repayReverseAccounting(chargeLog.getAppId(), tranAmt, chargeLog.getTranDate(), RepayMode.UnionFile,null);
				}
				else
				{
					//处理扣款失败情况
					this.processChargeFailed(chargeLog.getAppId(), offerSummary.getFeeRefId());
				}
				/**获取扣款对应的报盘信息，完成如下信息更新
				 * 1、T_OFFER_SUMMARY及T_OFFER_SUMMARY_HIS的回盘笔数减一，如果结果为0则此笔报盘记录为回盘完成，删除报盘汇总表，更新历史报盘汇总表
				 * 2、修改对应的待扣款明细记录为待报盘状态
				 */
				if(offerSummary.getRetCnt()+1==offerSummary.getSplitCnt())
				{
					offerSummaryDao.deleteByPrimaryKey(offerSummary.getId());
					offerSummaryHis.setRetCnt(offerSummaryHis.getRetCnt()+1);
					offerSummaryHis.setChargeStatus(ChargeStatus.CompleteCharge.getName());
					offerSummaryHisDao.updateByPrimaryKey(offerSummaryHis);
					/**
					 * 回盘完成修改对应待扣款记录为待报盘状态
					 */
					WaitingCharge waitingChargePo = waitingChargeDao.selectByOfferId(offerSummary.getId());
					if(waitingChargePo != null)
					{
						waitingChargePo.setOfferStatus(OfferStatus.WaitOffer.getName());
						waitingChargePo.setOfferId("");
						waitingChargeDao.updateByPrimaryKey(waitingChargePo);
					}
				}
				else
				{
					/**
					 * 回盘未完成则更新回盘笔数
					 */
					offerSummary.setRetCnt(offerSummaryHis.getRetCnt()+1);
					offerSummaryDao.updateByPrimaryKey(offerSummary);
					offerSummaryHis.setRetCnt(offerSummaryHis.getRetCnt()+1);
					offerSummaryHisDao.updateByPrimaryKey(offerSummaryHis);
				}
			}
		}
		/**
		 * 修改报盘批次信息为已回盘
		 */
		offerBatchInfo.setRetofferTotalCnt(retTotalCnt);
		offerBatchInfo.setRetofferTotalAmount(retTotalAmount);
		offerBatchInfo.setRetofferSuccCnt(retSuccCnt);
		offerBatchInfo.setRetofferSuccAmount(retSuccAmount);
		offerBatchInfo.setOfferFileName(offerBatchId+"_P.txt");
		offerBatchInfo.setRetofferStatus(retTotalCnt==offerBatchInfo.getOfferTotalCnt()?OfferStatus.RetOffer.getName():OfferStatus.Offering.getName());
		offerBatchInfoDao.updateByPrimaryKey(offerBatchInfo);
	}

	@Override
	public List<HashMap<String, Object>> getManualOfferBatchDetail(String offerBatchId) {
		// TODO Auto-generated method stub
		return chargeDao.selectManualOfferBatchDetail(offerBatchId);
	}

}
