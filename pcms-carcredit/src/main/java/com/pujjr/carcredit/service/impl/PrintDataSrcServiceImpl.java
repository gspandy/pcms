package com.pujjr.carcredit.service.impl;

import java.io.IOException;
import java.lang.reflect.Field;
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
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.pujjr.base.domain.BankInfo;
import com.pujjr.base.domain.CarStyle;
import com.pujjr.base.domain.ContractInfo;
import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.domain.SysBranchDealer;
import com.pujjr.base.domain.SysDictData;
import com.pujjr.base.service.IBankService;
import com.pujjr.base.service.ICarService;
import com.pujjr.base.service.IContractService;
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
import com.pujjr.carcredit.vo.ApplyTenantVo;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.carcredit.vo.LeaseCarVo;
import com.pujjr.carcredit.vo.PColesseePromiseVo;
import com.pujjr.carcredit.vo.PDeleiverReceiptVo;
import com.pujjr.carcredit.vo.PLeaseConstractVo;
import com.pujjr.carcredit.vo.PLoanReceiptVo;
import com.pujjr.carcredit.vo.PMortgageContractAVo;
import com.pujjr.carcredit.vo.PMortgageContractBVo;
import com.pujjr.carcredit.vo.PMortgageListVo;
import com.pujjr.carcredit.vo.PRepayRemindVo;
import com.pujjr.carcredit.vo.RepayScheduleVo;
import com.pujjr.carcredit.vo.SignContractVo;
import com.pujjr.carcredit.vo.SignFinanceDetailVo;
import com.pujjr.utils.Utils;

/**
 * @author tom
 *
 */
/**
 * @author tom
 *
 */
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
	
	public void setAcroFields(AcroFields fields,Object obj){
		Class cls = obj.getClass();
		Field[] fieldArray = cls.getDeclaredFields();
		for (Field field:fieldArray) {
			field.setAccessible(true);
			try {
				if(field.get(obj) != null){
					System.out.println("*************"+field.get(obj));
					if(!"select".equals(field.get(obj)))//"select":复选框已选中;"true"：叉形;"其他字符串"：取消选中复选框
						fields.setField(field.getName(), field.get(obj).toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void setMorgageListFields(AcroFields fields, Object obj) {
		//普通直接变量填值
		this.setAcroFields(fields, obj);
		//特殊数组变量独立填值
		PMortgageListVo pmlv = (PMortgageListVo) obj;
		List<LeaseCarVo> leaseCarVoList = pmlv.getLeaseCarVoList();
		for (int i = 0; i < leaseCarVoList.size(); i++) {
			LeaseCarVo leaseCarVo = leaseCarVoList.get(i);
			try {
				fields.setField("fill_"+(6*i+2),leaseCarVo.getPlateNo());
				fields.setField("fill_"+(6*i+3),leaseCarVo.getBrandSerial());
				fields.setField("fill_"+(6*i+4),leaseCarVo.getCarVin());
				fields.setField("fill_"+(6*i+5),leaseCarVo.getCarEngineNo());
				fields.setField("fill_"+(6*i+6),leaseCarVo.getCarColor());
				fields.setField("fill_"+(6*i+7),leaseCarVo.getCarManu());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void setRepayRemindFields(AcroFields fields, Object obj) {
		this.setAcroFields(fields, obj);
		PRepayRemindVo prrv = (PRepayRemindVo) obj;
		List<RepayScheduleVo> repayScheduleVos = prrv.getRepayScheduleVoList();
		for(int i = 0;i < repayScheduleVos.size();i++){
			RepayScheduleVo rsv = repayScheduleVos.get(i);
			try {
				fields.setField("fill_"+(i*2+1), rsv.getRepayDate());
				fields.setField("fill_"+(i*2+2), rsv.getRepayMoney()+"");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public List<LeaseCarVo> getLeaseCarList(ApplyVo applyVo){
		String appId = applyVo.getAppId();
		List<LeaseCarVo> leaseCarList = new ArrayList<LeaseCarVo>();
		List<ApplyFinanceVo> financeList = applyVo.getFinances();
		for (ApplyFinanceVo applyFinanceVo:financeList) {
			String applyFinanceId = applyFinanceVo.getId();//融资信息编号
			String carStyleId = applyFinanceVo.getCarStyleId();//车辆款式编号
			LeaseCarVo leaseCarVo = new LeaseCarVo();
			leaseCarVo.setStyleId(carStyleId);
			leaseCarVo.setCarVin(applyFinanceVo.getCarVin());
			leaseCarVo.setCarEngineNo(applyFinanceVo.getCarEngineNo());
			leaseCarVo.setCarColor(applyFinanceVo.getCarColor());
			leaseCarVo.setCarManu(applyFinanceVo.getCarManu());
			CarStyle carStyle = carServiceImpl.getCarStyleById(carStyleId);
			SignFinanceDetail signFinanceDetail = signContractServiceImpl.getSignFinanceDetailByApplyFinanceId(applyFinanceId);
			String carStyleName = carStyle.getCarStyleName();//款式名称
			String carBranName = carStyle.getCarBrand().getBrandName();//品牌名称
			String carSerialName = carStyle.getCarSerial().getCarSerialName();//车系名称
			leaseCarVo.setBrandSerial(carBranName+" "+carBranName+" "+carSerialName);
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
	public void setLeaseCarVoColessee(ApplyCloessee colessee,PLeaseConstractVo leaseContractVo){
		String type = colessee.getType();
		String typeName = "";
		try {
			typeName = sysDictServiceImpl.getDictDataByDictDateCode(type).getDictDataName();//承租人2/担保人类型
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("获取承租人2/担保人类型失败");
			leaseContractVo.setIsIdCard2("unselect");
			leaseContractVo.setIsPassport2("unselect");
			leaseContractVo.setIsOrgCodeId2("unselect");
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
		logger.info("设置担保人信息************************idTypeName:"+idTypeName);
		switch(idTypeName){
		case "身份证":
			leaseContractVo.setIsIdCard2("select");
			leaseContractVo.setIsPassport2("unselect");
			leaseContractVo.setIsOrgCodeId2("unselect");
			break;
		case "护照":
			leaseContractVo.setIsIdCard2("unselect");
			leaseContractVo.setIsPassport2("select");
			leaseContractVo.setIsOrgCodeId2("unselect");
			break;
		case "组织机构代码证":
			leaseContractVo.setIsIdCard2("unselect");
			leaseContractVo.setIsPassport2("unselect");
			leaseContractVo.setIsOrgCodeId2("select");
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
	public void setLeaseCarVoTenant(ApplyTenant tenant,PLeaseConstractVo leaseContractVo){
		leaseContractVo.setName1(tenant.getName());
		leaseContractVo.setPhone1(tenant.getMobile());
		//承租人1证件类型
		SysDictData sysDicData = sysDictServiceImpl.getDictDataByDictDateCode(tenant.getIdType());
		String idTypeName = "";
		try {
			idTypeName = sysDicData.getDictDataName();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("获取承租人1类型失败");
			leaseContractVo.setIsIdCard1("unselect");
			leaseContractVo.setIsPassport1("unselect");
			leaseContractVo.setIsOrgCodeId1("unselect");
		}
		logger.info("设置承租人信息************************idTypeName:"+idTypeName);
		switch(idTypeName){
		case "身份证":
			leaseContractVo.setIsIdCard1("select");
			leaseContractVo.setIsPassport1("unselect");
			leaseContractVo.setIsOrgCodeId1("unselect");
			break;
		case "护照":
			leaseContractVo.setIsIdCard1("unselect");
			leaseContractVo.setIsPassport1("select");
			leaseContractVo.setIsOrgCodeId1("unselect");
			break;
		case "组织机构代码证":
			leaseContractVo.setIsIdCard1("unselect");
			leaseContractVo.setIsPassport1("unselect");
			leaseContractVo.setIsOrgCodeId1("select");
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
	public PLeaseConstractVo getPrintLeaseConstract(String appId) {
		// TODO Auto-generated method stub
		PLeaseConstractVo leaseContractVo = new PLeaseConstractVo();
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
			leaseContractVo.setTotalDelayInsuranceFee(leaseContractVo.getTotalDelayInsuranceFee() + finance.getDelayInsuranceFee());
			//总保证金
			if (finance.getCollateral() != null) 
				leaseContractVo.setCollateral(leaseContractVo.getCollateral() + finance.getCollateral());
			//总首付款
			if (finance.getInitPayAmount() != null)
				leaseContractVo.setInitPayAmt(leaseContractVo.getInitPayAmt() + finance.getInitPayAmount());
		}
		if(leaseContractVo.getTotalSalePrice() <= 0)
			leaseContractVo.setIsSalePrice("unselect");
		if(leaseContractVo.getTotalPurchaseTax() <= 0)
			leaseContractVo.setIsPurchaseTax("unselect");
		if(leaseContractVo.getTotalGpsFee() <= 0)
			leaseContractVo.setIsGpsFee("unselect");
		if(leaseContractVo.getTotalFinanceFee() <= 0)
			leaseContractVo.setIsFinanceFee("unselect");
		if(leaseContractVo.getTotalServiceFee() <= 0)
			leaseContractVo.setIsServiceFee("unselect");
		if(leaseContractVo.getTotalTranserFee() <= 0)
			leaseContractVo.setIsTransferFee("unselect");
		if(leaseContractVo.getTotalInsuranceFee() <= 0)
			leaseContractVo.setIsInsruanceFee("unselect");
		if(leaseContractVo.getTotalAddonFee() <= 0)
			leaseContractVo.setIsAddonFee("unselect");
		if(leaseContractVo.getTotalDelayInsuranceFee() <= 0)
			leaseContractVo.setIsDelayInsuranceFee("unselect");
		//车辆总价
		double totalCarPrice = leaseContractVo.getTotalSalePrice() + leaseContractVo.getTotalPurchaseTax()
				+ leaseContractVo.getTotalGpsFee() + leaseContractVo.getTotalServiceFee()
				+ leaseContractVo.getTotalTranserFee() + leaseContractVo.getTotalInsuranceFee()
				+ leaseContractVo.getTotalAddonFee() + leaseContractVo.getTotalDelayInsuranceFee();
		leaseContractVo.setTotalCarPrice(totalCarPrice);
		//附加费，待定?????20161025
		leaseContractVo.setTotalAddonFee(0.00000000);
		//融资款合计
		leaseContractVo.setTotalFinanceAmt(applyVo.getTotalFinanceAmt());
		//实放金额
		leaseContractVo.setTotalLoanAmt(applyVo.getTotalLoanAmt());
		//月租金
		leaseContractVo.setMonthRent(applyVo.getMonthRent());
		//管理费（留空）
		leaseContractVo.setManageFee(0.00);
		//月还款日，待定?????20161025
		leaseContractVo.setRepayDate("待定");
		
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
			leaseContractVo.setStartYear(commitSignCl.get(commitSignCl.YEAR));
			leaseContractVo.setStartMonth(commitSignCl.get(commitSignCl.MONTH)+1);
			leaseContractVo.setStartDay(commitSignCl.get(commitSignCl.DAY_OF_MONTH));
			//截止日期
			leaseContractVo.setEndYear(duDateCl.get(duDateCl.YEAR));
			leaseContractVo.setEndMonth(duDateCl.get(duDateCl.MONTH)+1);
			leaseContractVo.setEndDay(duDateCl.get(duDateCl.DAY_OF_MONTH));
		} catch (Exception e) {
			logger.error("签约信息表：提交签约日期【缺失】,租赁（抵押）期限赋值失败");
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
			leaseContractVo.setLoanAcctName(sysBranchDealer.getLoanAcctName());
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

	@Override
	public PMortgageContractAVo getMortgageContract(String appId,String contractKey) {
		// TODO Auto-generated method stub
		PMortgageContractAVo pmca = new PMortgageContractAVo();
		ApplyVo applyVo = applyServiceImpl.getApplyDetail(appId);
		ApplyTenantVo applyTenantVo = applyVo.getTenant();
		SignContract signContract = signContractServiceImpl.getSignContractByAppId(appId);
		pmca.setContactNo(signContract.getContractNo());
		pmca.setTenant(applyTenantVo.getName());
		pmca.setIdNo(applyTenantVo.getIdNo());
		pmca.setFinanceAmt(applyVo.getTotalFinanceAmt());
		pmca.setFinanceAmtChn(Utils.number2Chn(applyVo.getTotalFinanceAmt()));
		//抵押人信息
		pmca.setPledgerName(applyTenantVo.getName());
		SysDictData sysDictData = sysDictServiceImpl.getDictDataByDictDateCode(applyTenantVo.getIdType());
		if(sysDictData == null)
			logger.error("类型编码："+applyTenantVo.getIdType()+"【获取证件类型名称失败】");
		else
			pmca.setPledgerCtftype(sysDictData.getDictDataName());
		pmca.setPledgerCtfNo(applyTenantVo.getIdNo());
		pmca.setPledgerPhone(applyTenantVo.getMobile());
		//抵押人（承租人）地址
		String addrProvinceName = sysAreaServiceImpl.getAreaNameById(applyTenantVo.getAddrProvince());
		String addrCityName = sysAreaServiceImpl.getAreaNameById(applyTenantVo.getAddrCity());
		String addrCountyName = sysAreaServiceImpl.getAreaNameById(applyTenantVo.getAddrCounty());
		String addrExt = applyTenantVo.getAddrExt();
		pmca.setPledgerAddress(addrProvinceName+" "+addrCityName+" "+addrCountyName+" "+addrExt);
		
		//租赁（抵押）车辆列表
		List<LeaseCarVo> leaseCarList = this.getLeaseCarList(applyVo);
		for (int i = 0; i < leaseCarList.size(); i++) {
			LeaseCarVo leaseCarVo = leaseCarList.get(i);
			if(i == 0){//抵押合同仅填一辆抵押车辆信息，默认取第一辆
				pmca.setCarBrand(leaseCarVo.getBrandSerial());
				pmca.setCarEnginNo(leaseCarVo.getCarEngineNo());
				pmca.setCarModelNo(leaseCarVo.getStyleId());
				pmca.setCarColor(leaseCarVo.getCarColor());
				pmca.setCarFrameNo(leaseCarVo.getCarVin());
				pmca.setCarPlateNo(leaseCarVo.getPlateNo());
			}
		}
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
			pmca.setStartYear(commitSignCl.get(commitSignCl.YEAR));
			pmca.setStartMonth(commitSignCl.get(commitSignCl.MONTH)+1);
			pmca.setStartDay(commitSignCl.get(commitSignCl.DAY_OF_MONTH));
			//截止日期
			pmca.setEndYear(duDateCl.get(duDateCl.YEAR));
			pmca.setEndMonth(duDateCl.get(duDateCl.MONTH)+1);
			pmca.setEndDay(duDateCl.get(duDateCl.DAY_OF_MONTH));
		} catch (Exception e) {
			logger.error("签约信息表：提交签约日期【缺失】,租赁（抵押）期限赋值失败");
		}
		return pmca;
	}
	@Override
	public PDeleiverReceiptVo getDeleiverReceipt(String appId) {
		PDeleiverReceiptVo pdrv = new PDeleiverReceiptVo();
		ApplyVo applyVo = applyServiceImpl.getApplyDetail(appId);
		//租赁（抵押）车辆列表
		List<LeaseCarVo> leaseCarList = this.getLeaseCarList(applyVo);
		for (int i = 0; i < leaseCarList.size(); i++) {
			LeaseCarVo leaseCarVo = leaseCarList.get(i);
			if(i == 0){//抵押合同仅填一辆抵押车辆信息，默认取第一辆
				pdrv.setCarEngineNo(leaseCarVo.getCarEngineNo());
				pdrv.setCarStyle(leaseCarVo.getStyleId());
				pdrv.setCarColor(leaseCarVo.getCarColor());
				pdrv.setCarVin(leaseCarVo.getCarVin());
				pdrv.setPlateNo(leaseCarVo.getPlateNo());
			}
		}
		//经销商
		SysBranch sysBranch = sysBranchService.getSysBranch("", applyVo.getCreateBranchCode());
		try{
			pdrv.setDealerName(sysBranch.getBranchName());
		}catch(Exception e){
			logger.error("获取车辆交接单数据【获取经销商信息失败】，订单号appId："+applyVo.getAppId());
		}
		ApplyTenantVo applyTenantVo = applyVo.getTenant();
		pdrv.setTenantIdNo(applyTenantVo.getIdNo());
		pdrv.setTenantName(applyTenantVo.getName());
		pdrv.setTenantPhone(applyTenantVo.getMobile());
		return pdrv;
	}

	@Override
	public PMortgageListVo getMortgageList(String appId) {
		PMortgageListVo pmlv = new PMortgageListVo();
		ApplyVo applyVo = applyServiceImpl.getApplyDetail(appId);
		try {
			pmlv.setContractNo(signContractServiceImpl.getSignContractByAppId(appId).getContractNo());
		} catch (Exception e) {
			logger.error("获取合同编号【失败】");
		}
		try {
			List<LeaseCarVo> leaseCarVoList = this.getLeaseCarList(applyVo);
			pmlv.setLeaseCarVoList(leaseCarVoList);
			pmlv.setCarAmt(leaseCarVoList.size());
		} catch (Exception e) {
			logger.error("获取抵押车辆信息【失败】");
		}
		Date date = Calendar.getInstance().getTime();
		pmlv.setYear(Utils.getYear(date));
		pmlv.setMonth(Utils.getMonth(date));
		pmlv.setDay(Utils.getDayOfMonth(date));
		return pmlv;
	}

	@Override
	public PLoanReceiptVo getLoanReceipt(String appId) {
		PLoanReceiptVo plrv = new PLoanReceiptVo();
		ApplyVo applyVo = applyServiceImpl.getApplyDetail(appId);
		SignContract signContract = signContractServiceImpl.getSignContractByAppId(appId);
		try {
			plrv.setContractNo(signContract.getContractNo());
		} catch (Exception e) {
			logger.error("获取合同编号失败");
		}
		plrv.setTotalLoanAmt(applyVo.getTotalFinanceAmt());
		SysBranch sysBranch = sysBranchService.getSysBranch("", applyVo.getCreateBranchCode());
		plrv.setBranchName(sysBranch.getBranchName());
		return plrv;
	}
	@Override
	public PRepayRemindVo getRepayRemind(String appId) {
		PRepayRemindVo prrv = new PRepayRemindVo();
		ApplyVo applyVo = applyServiceImpl.getApplyDetail(appId);
		prrv.setAccountName(applyVo.getTenant().getName());
		SignContract signContract = signContractServiceImpl.getSignContractByAppId(appId);
		try {
			prrv.setContractNo(signContract.getContractNo()); 
		} catch (Exception e) {
			logger.error("获取合同编号失败");
		}
		prrv.setTotalFinanceAmt(applyVo.getTotalFinanceAmt());
		prrv.setPeriod(applyVo.getPeriod());
		List<RepayScheduleVo> repayScheduleVoList = new ArrayList<RepayScheduleVo>();
		//首次还款日，待定？？？？2016-10-28
		prrv.setFirstRepayDate("首次还款日");
		prrv.setFirstRepayFee(2086.52);
		//还款计划表查询还款计划，待定？？？？2016-10-28
		//测试：模拟还款计划表查询测试数据
		RepayScheduleVo rsv1 = new RepayScheduleVo();
		rsv1.setRepayDate("2016-01-01");
		rsv1.setRepayMoney(2618.52);
		RepayScheduleVo rsv2 = new RepayScheduleVo();
		rsv2.setRepayDate("2016-02-01");
		rsv2.setRepayMoney(2618.52);
		repayScheduleVoList.add(rsv1);
		repayScheduleVoList.add(rsv2);
		prrv.setRepayScheduleVoList(repayScheduleVoList);
		return prrv;
	}

	@Override
	public PColesseePromiseVo getColesseePromise(String appId) {
		PColesseePromiseVo pcpv = new PColesseePromiseVo();
		ApplyVo applyVo = applyServiceImpl.getApplyDetail(appId);
		SignContract signContract = signContractServiceImpl.getSignContractByAppId(appId);
		try {
			pcpv.setContractNo(signContract.getContractNo());
		} catch (Exception e) {
			logger.error("获取合同编号失败");
		}
		ApplyTenantVo tenant = applyVo.getTenant();
		pcpv.setTenantIdNo(tenant.getIdNo());
		pcpv.setTenantName(tenant.getName());
		try {
			ApplyCloessee colessee = applyVo.getCloessee();
			pcpv.setColesseeIdNo(colessee.getIdNo());
			pcpv.setColesseeName(colessee.getName());
			pcpv.setRelation(sysDictServiceImpl.getDictDataByDictDateCode(colessee.getRelation()).getDictDataName());
		} catch (Exception e) {
			logger.error("获取共租人信息失败");
		}
		return pcpv;
	}

}
