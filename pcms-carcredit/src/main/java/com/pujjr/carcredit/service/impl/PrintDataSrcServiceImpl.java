package com.pujjr.carcredit.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.pujjr.base.domain.CarStyle;
import com.pujjr.base.service.ICarService;
import com.pujjr.carcredit.domain.SignContract;
import com.pujjr.carcredit.domain.SignFinanceDetail;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.service.IPrintDataSrcServcie;
import com.pujjr.carcredit.service.ISignContractService;
import com.pujjr.carcredit.vo.ApplyFinanceVo;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.carcredit.vo.LeaseCarVo;
import com.pujjr.carcredit.vo.LeaseConstractVo;
import com.pujjr.carcredit.vo.SignFinanceDetailVo;
import com.pujjr.utils.Utils;

@Service
@Transactional
public class PrintDataSrcServiceImpl implements IPrintDataSrcServcie {
	private Logger logger = Logger.getLogger(PrintDataSrcServiceImpl.class);
	@Autowired
	private IApplyService applyServiceImpl;
	@Autowired
	private ISignContractService signContractServiceImpl;
	@Autowired
	private ICarService carServiceImpl;
	/**
	 * 获取融资车辆信息列表
	 * @param appId
	 * @return
	 */
	public List<LeaseCarVo> getLeaseCarList(ApplyVo applyVo){
		String appId = applyVo.getAppId();
		List<LeaseCarVo> leaseCarList = new ArrayList<LeaseCarVo>();
		List<ApplyFinanceVo> financeList = applyVo.getFinances();
		for (ApplyFinanceVo applyFinanceVo:financeList) {
			String applyFinanceId = applyFinanceVo.getId();//融资信息编号
			String carStyleId = applyFinanceVo.getCarStyleId();//车辆款式编号
			LeaseCarVo leaseCarVo = new LeaseCarVo();
			leaseCarVo.setCarVin(applyFinanceVo.getCarVin());
			leaseCarVo.setCarEngineNo(applyFinanceVo.getCarEngineNo());
			leaseCarVo.setCarColor(applyFinanceVo.getCarColor());
			leaseCarVo.setCarManu(applyFinanceVo.getCarManu());
			CarStyle carStyle = carServiceImpl.getCarStyleById(carStyleId);
			SignFinanceDetail signFinanceDetail = signContractServiceImpl.getSignFinanceDetailByApplyFinanceId(applyFinanceId);
			String carStyleName = carStyle.getCarStyleName();//款式名称
			String carBranName = carStyle.getCarBrand().getBrandName();//品牌名称
			String carSerialName = carStyle.getCarSerial().getCarSerialName();//车系名称
			leaseCarVo.setBrandSerial(carBranName+"-"+carBranName+"-"+carSerialName);
			String plateNo = signFinanceDetail.getPlateNo();//车牌号
			leaseCarVo.setPlateNo(plateNo);
			leaseCarList.add(leaseCarVo);
		}
		logger.debug(JSONObject.toJSONString(leaseCarList));
		return leaseCarList;
	}
	@Override
	public LeaseConstractVo getPrintLeaseConstract(String appId) {
		// TODO Auto-generated method stub
		LeaseConstractVo leaseContractVo = new LeaseConstractVo();
		//获取承租人信息、担保人信息、车辆融资信息
		ApplyVo applyVo = applyServiceImpl.getApplyDetail(appId);
		logger.debug("PrintLeaseConstractVo："+JSONObject.toJSONString(applyVo));
		BeanUtils.copyProperties(applyVo, leaseContractVo);
		//计算总融资信息
		List<ApplyFinanceVo> finances = leaseContractVo.getFinances();
		for (ApplyFinanceVo finance:finances) {
			if(finance.getSalePrice() == null)
				continue;
			leaseContractVo.setTotalSalePrice(leaseContractVo.getTotalSalePrice() + finance.getSalePrice());
			leaseContractVo.setTotalPurchaseTax(leaseContractVo.getTotalPurchaseTax() + finance.getPurchaseTax());
			leaseContractVo.setTotalGpsFee(leaseContractVo.getTotalGpsFee() + finance.getGpsFee());
			leaseContractVo.setTotalFinanceFee(leaseContractVo.getTotalFinanceFee() + finance.getFinanceFee());
			leaseContractVo.setTotalServiceFee(leaseContractVo.getTotalServiceFee() + finance.getServiceFee());
			leaseContractVo.setTotalTranserFee(leaseContractVo.getTotalTranserFee() + finance.getTransferFee());
			leaseContractVo.setTotalInsuranceFee(leaseContractVo.getTotalInsuranceFee() + finance.getInsuranceFee());
			leaseContractVo.setTotalAddonFee(leaseContractVo.getTotalAddonFee() + finance.getAddonFee());
			leaseContractVo.setTotalDelayInsuranceFeee(leaseContractVo.getTotalDelayInsuranceFeee() + finance.getDelayInsuranceFee());
			
			//车辆总价
			if (finance.getInitPayAmount() == null) 
				continue;
			leaseContractVo.setInitPayAmt(leaseContractVo.getInitPayAmt() + finance.getInitPayAmount());
			//总保证金
			if (finance.getCollateral() == null) {
				continue;
			}
			leaseContractVo.setCollateral(leaseContractVo.getCollateral() + finance.getCollateral());
			//总首付款
			if (finance.getInitPayAmount() == null) {
				continue;
			}
			leaseContractVo.setInitPayAmt(leaseContractVo.getInitPayAmt() + finance.getInitPayAmount());
		}
		//车辆总价
		double totalCarPrice = leaseContractVo.getTotalSalePrice() + leaseContractVo.getTotalPurchaseTax()
				+ leaseContractVo.getTotalGpsFee() + leaseContractVo.getTotalServiceFee()
				+ leaseContractVo.getTotalTranserFee() + leaseContractVo.getTotalInsuranceFee()
				+ leaseContractVo.getTotalAddonFee() + leaseContractVo.getTotalDelayInsuranceFeee();
		leaseContractVo.setTotalCarPrice(totalCarPrice);
		//附加费，待定?????20161025
		leaseContractVo.setTotalAddonFee(0.00);
		//融资款合计
		leaseContractVo.setTotalFinanceAmt(applyVo.getTotalFinanceAmt());
		//实放金额
		leaseContractVo.setTotalLoan(applyVo.getTotalLoanAmt());
		//月租金
		leaseContractVo.setMonthRent(applyVo.getMonthRent());
		//管理费（留空）
		leaseContractVo.setManageFee(0.00);
		//月还款日，待定?????20161025
		leaseContractVo.setRepayDate("2016-01-01");
		
		SignContract signContract = signContractServiceImpl.getSignContractByAppId(appId);
		//提交签约日历
		Calendar commitSignCl = Calendar.getInstance();
		//到期还款日历
		Calendar duDateCl = Calendar.getInstance();
		//提交签约日期
		Date commitSignDate = signContract.getCommitSignDate();
		try {
			//到期还款日
			Date dueDate = Utils.getDateAfterMonth(commitSignDate, applyVo.getPeriod());
			commitSignCl.setTime(commitSignDate);
			duDateCl.setTime(dueDate);
			//开始日期
			leaseContractVo.setStartYear(commitSignCl.get(Calendar.YEAR));
			leaseContractVo.setStartMonth(commitSignCl.get(Calendar.MONTH)+1);
			leaseContractVo.setStartDay(commitSignCl.get(Calendar.DAY_OF_MONTH));
			//截止日期
			leaseContractVo.setEndYear(commitSignCl.get(Calendar.YEAR));
			leaseContractVo.setEndMonth(commitSignCl.get(Calendar.MONTH)+1);
			leaseContractVo.setEndDay(commitSignCl.get(Calendar.DAY_OF_MONTH));
		} catch (Exception e) {
			logger.error("签约信息表：提交签约日期【缺失】");
		}
		leaseContractVo.setTotalMonth(applyVo.getPeriod());
		//租赁（抵押）车辆列表
		List<LeaseCarVo> leaseCarList = this.getLeaseCarList(applyVo);
		return leaseContractVo;
	}

}
