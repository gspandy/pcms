package com.pujjr.carcredit.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.apache.bcel.generic.BranchHandle;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.pujjr.base.domain.BankInfo;
import com.pujjr.base.domain.CarStyle;
import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.domain.SysBranchDealer;
import com.pujjr.base.domain.SysDictData;
import com.pujjr.base.service.IBankService;
import com.pujjr.base.service.ICarService;
import com.pujjr.base.service.ISysAreaService;
import com.pujjr.base.service.ISysBranchService;
import com.pujjr.base.service.ISysDictService;
import com.pujjr.carcredit.domain.ApplyCloessee;
import com.pujjr.carcredit.domain.ApplyTenant;
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
	@Autowired
	private ISysAreaService sysAreaServiceImpl;
	@Autowired
	private ISysDictService sysDictServiceImpl;
	@Autowired
	private ISysBranchService sysBranchService;
	@Autowired
	private IBankService bankServiceImpl;
	
	/**
	 * 获取融资车辆信息列表
	 * @param applyVo
	 * @return 融资车辆信息列表
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
	/**
	 * 设置担保人信息
	 * @param colessee
	 * @param leaseContractVo
	 */
	public void setLeaseCarVoColessee(ApplyCloessee colessee,LeaseConstractVo leaseContractVo){
		String type = colessee.getType();
		String typeName = "";
		try {
			typeName = sysDictServiceImpl.getDictDataByDictDateCode(type).getDictDataName();//承租人2/担保人类型
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("获取承租人2类型失败");
		}
		if(typeName.equals("担保人")){
			leaseContractVo.setIsTenant("");
		}else{
			leaseContractVo.setIsGuarantor("");
		}
		leaseContractVo.setName2(colessee.getName());
		leaseContractVo.setPhone2(colessee.getMobile());
		//担保人证件类型
		SysDictData sysDicData = sysDictServiceImpl.getDictDataByDictDateCode(colessee.getIdType());
		String idTypeName = sysDicData.getDictDataName();
		switch(idTypeName){
		case "身份证":
			leaseContractVo.setIsPassport2("");
			leaseContractVo.setIsOrgCodeId2("");
			break;
		case "护照":
			leaseContractVo.setIsIdCard2("");
			leaseContractVo.setIsOrgCodeId2("");
			break;
		case "组织机构代码证":
			leaseContractVo.setIsIdCard2("");
			leaseContractVo.setIsPassport2("");
			break;
		}
		//担保人证件号码
		leaseContractVo.setCtfNo1(colessee.getIdNo());
		//担保人地址
		String addrProvinceName = sysAreaServiceImpl.getAreaNameById(colessee.getUnitAddrProvince());
		String addrCityName = sysAreaServiceImpl.getAreaNameById(colessee.getUnitAddrCity());
		String addrCountyName = sysAreaServiceImpl.getAreaNameById(colessee.getUnitAddrCounty());
		String addrExt = colessee.getUnitAddrExt();
		leaseContractVo.setAddress2(addrProvinceName+" "+addrCityName+" "+addrCountyName+" "+addrExt);
	}
	/**
	 * 设置承租人信息
	 * @param tenant
	 * @param leaseContractVo
	 */
	public void setLeaseCarVoTenant(ApplyTenant tenant,LeaseConstractVo leaseContractVo){
		leaseContractVo.setName1(tenant.getName());
		leaseContractVo.setPhone1(tenant.getMobile());
		//承租人1证件类型
		SysDictData sysDicData = sysDictServiceImpl.getDictDataByDictDateCode(tenant.getIdType());
		String idTypeName = sysDicData.getDictDataName();
		switch(idTypeName){
		case "身份证":
			leaseContractVo.setIsPassport1("");
			leaseContractVo.setIsOrgCodeId1("");
			break;
		case "护照":
			leaseContractVo.setIsIdCard1("");
			leaseContractVo.setIsOrgCodeId1("");
			break;
		case "组织机构代码证":
			leaseContractVo.setIsIdCard1("");
			leaseContractVo.setIsPassport1("");
			break;
		}
		//承租人1证件号码
		leaseContractVo.setCtfNo1(tenant.getIdNo());
		//承租人1地址
		String addrProvinceName = sysAreaServiceImpl.getAreaNameById(tenant.getAddrProvince());
		String addrCityName = sysAreaServiceImpl.getAreaNameById(tenant.getAddrCity());
		String addrCountyName = sysAreaServiceImpl.getAreaNameById(tenant.getAddrCounty());
		String addrExt = tenant.getAddrExt();
		leaseContractVo.setAddress1(addrProvinceName+" "+addrCityName+" "+addrCountyName+" "+addrExt);
	}
	
	
	@Override
	public LeaseConstractVo getPrintLeaseConstract(String appId) {
		// TODO Auto-generated method stub
		LeaseConstractVo leaseContractVo = new LeaseConstractVo();
		//获取承租人信息、担保人信息、车辆融资信息
		ApplyVo applyVo = applyServiceImpl.getApplyDetail(appId);
		logger.debug("PrintLeaseConstractVo："+JSONObject.toJSONString(applyVo));
		BeanUtils.copyProperties(applyVo, leaseContractVo);
		//承租人1
		this.setLeaseCarVoTenant(applyVo.getTenant(), leaseContractVo);
		
		//担保人
		this.setLeaseCarVoColessee(applyVo.getCloessee(), leaseContractVo);
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
		for (int i = 0; i < leaseCarList.size(); i++) {
			LeaseCarVo leaseCarVo = leaseCarList.get(i);
			if(i == 0){
				leaseContractVo.setPlateNo1(leaseCarVo.getPlateNo());
				leaseContractVo.setCarBrand1(leaseCarVo.getBrandSerial());
				leaseContractVo.setCarVin1(leaseCarVo.getCarVin());
				leaseContractVo.setCarEngine1(leaseCarVo.getCarEngineNo());
				leaseContractVo.setCarColor1(leaseCarVo.getCarColor());
				leaseContractVo.setCarManu1(leaseCarVo.getCarManu());
			}else if( i == 1){
				leaseContractVo.setPlateNo2(leaseCarVo.getPlateNo());
				leaseContractVo.setCarBrand2(leaseCarVo.getBrandSerial());
				leaseContractVo.setCarVin2(leaseCarVo.getCarVin());
				leaseContractVo.setCarEngine2(leaseCarVo.getCarEngineNo());
				leaseContractVo.setCarColor2(leaseCarVo.getCarColor());
				leaseContractVo.setCarManu2(leaseCarVo.getCarManu());
			}else if(i == 2){
				leaseContractVo.setPlateNo3(leaseCarVo.getPlateNo());
				leaseContractVo.setCarBrand3(leaseCarVo.getBrandSerial());
				leaseContractVo.setCarVin3(leaseCarVo.getCarVin());
				leaseContractVo.setCarEngine3(leaseCarVo.getCarEngineNo());
				leaseContractVo.setCarColor3(leaseCarVo.getCarColor());
				leaseContractVo.setCarManu3(leaseCarVo.getCarManu());
			}
		}
		
		//融资款项发放账户
		String createBranchCode = applyVo.getCreateBranchCode();
		String branchId = sysBranchService.getSysBranch("", createBranchCode).getId();
		SysBranchDealer sysBranchDealer = sysBranchService.getDealerByBranchId(branchId);
		if(sysBranchDealer != null){
			leaseContractVo.setLoanActName(sysBranchDealer.getLoanAcctName());
			BankInfo bankInfo = bankServiceImpl.getBankInfoById(sysBranchDealer.getBankId());//放款银行
			BankInfo subBankInfo = bankServiceImpl.getBankInfoById(sysBranchDealer.getLoanSubbranch());//放款支行
			leaseContractVo.setLoanBankName(bankInfo+"-"+subBankInfo);
			leaseContractVo.setLoanAcctNo(sysBranchDealer.getLoanAcctNo());
		}else{
			logger.error("订单号："+appId+"对应经销商信息查询失败！融资贷款合同【融资款项发放账户】赋值失败");
		}
		//月租金还款账户
		leaseContractVo.setRepayAcctName(signContract.getRepayAcctName());
		BankInfo repayBank = bankServiceImpl.getBankInfoById(signContract.getRepayBankId());
		leaseContractVo.setRepayBankName(repayBank.getBankName());
		leaseContractVo.setRepayAcctNo(signContract.getRepayAcctNo());
		return leaseContractVo;
	}

}
