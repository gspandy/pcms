package com.pujjr.carcredit.vo;

import java.util.List;

import com.pujjr.base.domain.CarStyle;
import com.pujjr.base.domain.Product;
import com.pujjr.carcredit.domain.SignFinanceDetail;

public class LeaseConstractVo {
	//承租人信息
	private ApplyTenantVo tenant;
	//共租人信息
	private ApplyCloesseeVo cloessee;
	//产品信息
	private Product product;
	//融资信息
	private List<ApplyFinanceVo> finances;
	//抵押车辆信息
	private SignFinanceDetailVo signFinanceDetailVo;
	
	private String name1;
	private String phone1;
	private String name2;
	private String phone2;
	private String isTenant;
	private String isGuarantor;
	
	private String isIdCard1;
	private String isPassport1;
	private String isOrgCodeId1;
	private String isIdCard2;
	private String isPassport2;
	private String isOrgCodeId2;
	
	private String ctfNo1;
	private String address1;
	private String ctfNo2;
	private String address2;
	
	private String isSalePrice;
	private String isPurchaseTax;
	private String isGpsFee;
	private String isFinanceFee;
	private String isServiceFee;
	private String isTransferFee;
	private String isInsruanceFee;
	private String isAddonFee;
	private String isDelayInsuranceFee;
	
	private double totalSalePrice;
	private double totalPurchaseTax;
	private double totalGpsFee;
	private double totalFinanceFee;
	private double totalServiceFee;
	private double totalTranserFee;
	private double totalInsuranceFee;
	private double totalAddonFee;
	private double totalDelayInsuranceFeee;
	
	private double totalCarPrice;
	private double addFee;
	private double collateral;
	private double initPayAmt;
	private double totalFinanceAmt;
	private double totalLoan;
	private double monthRent;
	private double manageFee;
	private String repayDate;
	
	private int startYear;
	private int startMonth;
	private int startDay;
	private int endYear;
	private int endMonth;
	private int endDay;
	private int totalMonth;
	
	private String plateNo1;
	private String plateNo2;
	private String plateNo3;
	private String carBrand1;
	private String carBrand2;
	private String carBrand3;
	private String carVin1;
	private String carVin2;
	private String carVin3;
	private String carEngine1;
	private String carEngine2;
	private String carEngine3;
	private String carColor1;
	private String carColor2;
	private String carColor3;
	private String carManu1;
	private String carManu2;
	private String carManu3;
	
	private String loanActName;
	private String loanBankName;
	private String loanAcctNo;
	private String repayAcctName;
 	private String repayBankName;
 	private String repayAcctNo;
 	
 	
	public String getCarVin1() {
		return carVin1;
	}
	public void setCarVin1(String carVin1) {
		this.carVin1 = carVin1;
	}
	public String getCarVin2() {
		return carVin2;
	}
	public void setCarVin2(String carVin2) {
		this.carVin2 = carVin2;
	}
	public String getCarVin3() {
		return carVin3;
	}
	public void setCarVin3(String carVin3) {
		this.carVin3 = carVin3;
	}
	public ApplyTenantVo getTenant() {
		return tenant;
	}
	public void setTenant(ApplyTenantVo tenant) {
		this.tenant = tenant;
	}
	public ApplyCloesseeVo getCloessee() {
		return cloessee;
	}
	public void setCloessee(ApplyCloesseeVo cloessee) {
		this.cloessee = cloessee;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public List<ApplyFinanceVo> getFinances() {
		return finances;
	}
	public void setFinances(List<ApplyFinanceVo> finances) {
		this.finances = finances;
	}
	public SignFinanceDetailVo getSignFinanceDetailVo() {
		return signFinanceDetailVo;
	}
	public void setSignFinanceDetailVo(SignFinanceDetailVo signFinanceDetailVo) {
		this.signFinanceDetailVo = signFinanceDetailVo;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getIsTenant() {
		return isTenant;
	}
	public void setIsTenant(String isTenant) {
		this.isTenant = isTenant;
	}
	public String getIsGuarantor() {
		return isGuarantor;
	}
	public void setIsGuarantor(String isGuarantor) {
		this.isGuarantor = isGuarantor;
	}
	public String getIsIdCard1() {
		return isIdCard1;
	}
	public void setIsIdCard1(String isIdCard1) {
		this.isIdCard1 = isIdCard1;
	}
	public String getIsPassport1() {
		return isPassport1;
	}
	public void setIsPassport1(String isPassport1) {
		this.isPassport1 = isPassport1;
	}
	public String getIsOrgCodeId1() {
		return isOrgCodeId1;
	}
	public void setIsOrgCodeId1(String isOrgCodeId1) {
		this.isOrgCodeId1 = isOrgCodeId1;
	}
	public String getIsIdCard2() {
		return isIdCard2;
	}
	public void setIsIdCard2(String isIdCard2) {
		this.isIdCard2 = isIdCard2;
	}
	public String getIsPassport2() {
		return isPassport2;
	}
	public void setIsPassport2(String isPassport2) {
		this.isPassport2 = isPassport2;
	}
	public String getIsOrgCodeId2() {
		return isOrgCodeId2;
	}
	public void setIsOrgCodeId2(String isOrgCodeId2) {
		this.isOrgCodeId2 = isOrgCodeId2;
	}
	public String getCtfNo1() {
		return ctfNo1;
	}
	public void setCtfNo1(String ctfNo1) {
		this.ctfNo1 = ctfNo1;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getCtfNo2() {
		return ctfNo2;
	}
	public void setCtfNo2(String ctfNo2) {
		this.ctfNo2 = ctfNo2;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getIsSalePrice() {
		return isSalePrice;
	}
	public void setIsSalePrice(String isSalePrice) {
		this.isSalePrice = isSalePrice;
	}
	public String getIsPurchaseTax() {
		return isPurchaseTax;
	}
	public void setIsPurchaseTax(String isPurchaseTax) {
		this.isPurchaseTax = isPurchaseTax;
	}
	public String getIsGpsFee() {
		return isGpsFee;
	}
	public void setIsGpsFee(String isGpsFee) {
		this.isGpsFee = isGpsFee;
	}
	public String getIsFinanceFee() {
		return isFinanceFee;
	}
	public void setIsFinanceFee(String isFinanceFee) {
		this.isFinanceFee = isFinanceFee;
	}
	public String getIsServiceFee() {
		return isServiceFee;
	}
	public void setIsServiceFee(String isServiceFee) {
		this.isServiceFee = isServiceFee;
	}
	public String getIsTransferFee() {
		return isTransferFee;
	}
	public void setIsTransferFee(String isTransferFee) {
		this.isTransferFee = isTransferFee;
	}
	public String getIsInsruanceFee() {
		return isInsruanceFee;
	}
	public void setIsInsruanceFee(String isInsruanceFee) {
		this.isInsruanceFee = isInsruanceFee;
	}
	public String getIsAddonFee() {
		return isAddonFee;
	}
	public void setIsAddonFee(String isAddonFee) {
		this.isAddonFee = isAddonFee;
	}
	public String getIsDelayInsuranceFee() {
		return isDelayInsuranceFee;
	}
	public void setIsDelayInsuranceFee(String isDelayInsuranceFee) {
		this.isDelayInsuranceFee = isDelayInsuranceFee;
	}
	public double getTotalSalePrice() {
		return totalSalePrice;
	}
	public void setTotalSalePrice(double totalSalePrice) {
		this.totalSalePrice = totalSalePrice;
	}
	public double getTotalPurchaseTax() {
		return totalPurchaseTax;
	}
	public void setTotalPurchaseTax(double totalPurchaseTax) {
		this.totalPurchaseTax = totalPurchaseTax;
	}
	public double getTotalGpsFee() {
		return totalGpsFee;
	}
	public void setTotalGpsFee(double totalGpsFee) {
		this.totalGpsFee = totalGpsFee;
	}
	public double getTotalFinanceFee() {
		return totalFinanceFee;
	}
	public void setTotalFinanceFee(double totalFinanceFee) {
		this.totalFinanceFee = totalFinanceFee;
	}
	public double getTotalServiceFee() {
		return totalServiceFee;
	}
	public void setTotalServiceFee(double totalServiceFee) {
		this.totalServiceFee = totalServiceFee;
	}
	public double getTotalTranserFee() {
		return totalTranserFee;
	}
	public void setTotalTranserFee(double totalTranserFee) {
		this.totalTranserFee = totalTranserFee;
	}
	public double getTotalInsuranceFee() {
		return totalInsuranceFee;
	}
	public void setTotalInsuranceFee(double totalInsuranceFee) {
		this.totalInsuranceFee = totalInsuranceFee;
	}
	public double getTotalAddonFee() {
		return totalAddonFee;
	}
	public void setTotalAddonFee(double totalAddonFee) {
		this.totalAddonFee = totalAddonFee;
	}
	public double getTotalDelayInsuranceFeee() {
		return totalDelayInsuranceFeee;
	}
	public void setTotalDelayInsuranceFeee(double totalDelayInsuranceFeee) {
		this.totalDelayInsuranceFeee = totalDelayInsuranceFeee;
	}
	public double getTotalCarPrice() {
		return totalCarPrice;
	}
	public void setTotalCarPrice(double totalCarPrice) {
		this.totalCarPrice = totalCarPrice;
	}
	public double getAddFee() {
		return addFee;
	}
	public void setAddFee(double addFee) {
		this.addFee = addFee;
	}
	public double getCollateral() {
		return collateral;
	}
	public void setCollateral(double collateral) {
		this.collateral = collateral;
	}
	public double getInitPayAmt() {
		return initPayAmt;
	}
	public void setInitPayAmt(double initPayAmt) {
		this.initPayAmt = initPayAmt;
	}
	public double getTotalFinanceAmt() {
		return totalFinanceAmt;
	}
	public void setTotalFinanceAmt(double totalFinanceAmt) {
		this.totalFinanceAmt = totalFinanceAmt;
	}
	public double getTotalLoan() {
		return totalLoan;
	}
	public void setTotalLoan(double totalLoan) {
		this.totalLoan = totalLoan;
	}
	public double getMonthRent() {
		return monthRent;
	}
	public void setMonthRent(double monthRent) {
		this.monthRent = monthRent;
	}
	public double getManageFee() {
		return manageFee;
	}
	public void setManageFee(double manageFee) {
		this.manageFee = manageFee;
	}
	public String getRepayDate() {
		return repayDate;
	}
	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}
	public int getStartYear() {
		return startYear;
	}
	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}
	public int getStartMonth() {
		return startMonth;
	}
	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}
	public int getStartDay() {
		return startDay;
	}
	public void setStartDay(int startDay) {
		this.startDay = startDay;
	}
	public int getEndYear() {
		return endYear;
	}
	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}
	public int getEndMonth() {
		return endMonth;
	}
	public void setEndMonth(int endMonth) {
		this.endMonth = endMonth;
	}
	public int getEndDay() {
		return endDay;
	}
	public void setEndDay(int endDay) {
		this.endDay = endDay;
	}
	public int getTotalMonth() {
		return totalMonth;
	}
	public void setTotalMonth(int totalMonth) {
		this.totalMonth = totalMonth;
	}
	public String getPlateNo1() {
		return plateNo1;
	}
	public void setPlateNo1(String plateNo1) {
		this.plateNo1 = plateNo1;
	}
	public String getPlateNo2() {
		return plateNo2;
	}
	public void setPlateNo2(String plateNo2) {
		this.plateNo2 = plateNo2;
	}


	public String getPlateNo3() {
		return plateNo3;
	}
	public void setPlateNo3(String plateNo3) {
		this.plateNo3 = plateNo3;
	}
	public String getCarBrand1() {
		return carBrand1;
	}
	public void setCarBrand1(String carBrand1) {
		this.carBrand1 = carBrand1;
	}
	public String getCarBrand2() {
		return carBrand2;
	}
	public void setCarBrand2(String carBrand2) {
		this.carBrand2 = carBrand2;
	}
	public String getCarBrand3() {
		return carBrand3;
	}
	public void setCarBrand3(String carBrand3) {
		this.carBrand3 = carBrand3;
	}
	public String getCarEngine1() {
		return carEngine1;
	}
	public void setCarEngine1(String carEngine1) {
		this.carEngine1 = carEngine1;
	}
	public String getCarEngine2() {
		return carEngine2;
	}
	public void setCarEngine2(String carEngine2) {
		this.carEngine2 = carEngine2;
	}
	public String getCarEngine3() {
		return carEngine3;
	}
	public void setCarEngine3(String carEngine3) {
		this.carEngine3 = carEngine3;
	}
	public String getCarColor1() {
		return carColor1;
	}
	public void setCarColor1(String carColor1) {
		this.carColor1 = carColor1;
	}
	public String getCarColor2() {
		return carColor2;
	}
	public void setCarColor2(String carColor2) {
		this.carColor2 = carColor2;
	}
	public String getCarColor3() {
		return carColor3;
	}
	public void setCarColor3(String carColor3) {
		this.carColor3 = carColor3;
	}
	public String getCarManu1() {
		return carManu1;
	}
	public void setCarManu1(String carManu1) {
		this.carManu1 = carManu1;
	}
	public String getCarManu2() {
		return carManu2;
	}
	public void setCarManu2(String carManu2) {
		this.carManu2 = carManu2;
	}
	public String getCarManu3() {
		return carManu3;
	}
	public void setCarManu3(String carManu3) {
		this.carManu3 = carManu3;
	}


	public String getLoanActName() {
		return loanActName;
	}
	public void setLoanActName(String loanActName) {
		this.loanActName = loanActName;
	}
	public String getLoanBankName() {
		return loanBankName;
	}
	public void setLoanBankName(String loanBankName) {
		this.loanBankName = loanBankName;
	}
	public String getLoanAcctNo() {
		return loanAcctNo;
	}
	public void setLoanAcctNo(String loanAcctNo) {
		this.loanAcctNo = loanAcctNo;
	}
	public String getRepayAcctName() {
		return repayAcctName;
	}
	public void setRepayAcctName(String repayAcctName) {
		this.repayAcctName = repayAcctName;
	}
	public String getRepayBankName() {
		return repayBankName;
	}
	public void setRepayBankName(String repayBankName) {
		this.repayBankName = repayBankName;
	}
	public String getRepayAcctNo() {
		return repayAcctNo;
	}
	public void setRepayAcctNo(String repayAcctNo) {
		this.repayAcctNo = repayAcctNo;
	}
	
	
}
