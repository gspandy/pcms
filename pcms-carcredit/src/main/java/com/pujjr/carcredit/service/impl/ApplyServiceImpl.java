package com.pujjr.carcredit.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.Finance;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONObject;
import com.pujjr.base.dao.CarStyleMapper;
import com.pujjr.base.dao.GpsLvlMapper;
import com.pujjr.base.dao.ProductMapper;
import com.pujjr.base.dao.SequenceMapper;
import com.pujjr.base.dao.SysAccountMapper;
import com.pujjr.base.domain.CarStyle;
import com.pujjr.base.domain.GpsLvl;
import com.pujjr.base.domain.Product;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.service.ISequenceService;
import com.pujjr.base.service.ISysAreaService;
import com.pujjr.base.service.ISysBranchService;
import com.pujjr.carcredit.annotion.ApplyOperHisAnnotation;
import com.pujjr.carcredit.constant.ApplyStatus;
import com.pujjr.carcredit.dao.ApplyCloesseeMapper;
import com.pujjr.carcredit.dao.ApplyFamilyDebtMapper;
import com.pujjr.carcredit.dao.ApplyFinanceMapper;
import com.pujjr.carcredit.dao.ApplyLinkmanMapper;
import com.pujjr.carcredit.dao.ApplyMapper;
import com.pujjr.carcredit.dao.ApplySpouseMapper;
import com.pujjr.carcredit.dao.ApplyTenantCarMapper;
import com.pujjr.carcredit.dao.ApplyTenantHouseMapper;
import com.pujjr.carcredit.dao.ApplyTenantMapper;
import com.pujjr.carcredit.dao.IApplyDao;
import com.pujjr.carcredit.domain.Apply;
import com.pujjr.carcredit.domain.ApplyCloessee;
import com.pujjr.carcredit.domain.ApplyFamilyDebt;
import com.pujjr.carcredit.domain.ApplyFinance;
import com.pujjr.carcredit.domain.ApplyLinkman;
import com.pujjr.carcredit.domain.ApplySpouse;
import com.pujjr.carcredit.domain.ApplyTenant;
import com.pujjr.carcredit.domain.ApplyTenantCar;
import com.pujjr.carcredit.domain.ApplyTenantHouse;
import com.pujjr.carcredit.po.ApplyInfoPo;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.vo.ApplyCloesseeVo;
import com.pujjr.carcredit.vo.ApplyFamilyDebtVo;
import com.pujjr.carcredit.vo.ApplyFinanceVo;
import com.pujjr.carcredit.vo.ApplyLinkmanVo;
import com.pujjr.carcredit.vo.ApplySpouseVo;
import com.pujjr.carcredit.vo.ApplyTenantVo;
import com.pujjr.carcredit.vo.ApplyUncommitVo;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.carcredit.vo.QueryParamApplyVo;
import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.jbpm.vo.ProcessGlobalVariable;
import com.pujjr.utils.Utils;

@Service
@Transactional(rollbackFor=Exception.class)
public class ApplyServiceImpl implements IApplyService { 
	@Value("${pcms.sys_mode}")  
	private String sysMode;//develop\debug
	@Autowired
	private ApplyMapper applyMapper;
	@Autowired
	private ApplyFinanceMapper applyFinanceMapper;
	@Autowired
	private ApplyTenantMapper applyTenantMapper;
	@Autowired
	private ApplyTenantHouseMapper applyTenantHouseMapper;
	@Autowired
	private ApplyTenantCarMapper applyTenantCarMapper;
	@Autowired
	private ApplySpouseMapper applySpouseMapper;
	@Autowired
	private ApplyCloesseeMapper applyCloesseeMapper;
	@Autowired
	private ApplyLinkmanMapper applyLinkmanMapper;
	@Autowired
	private ApplyFamilyDebtMapper applyFamilyDebtMapper;
	@Autowired
	private SysAccountMapper sysAccountMapper;
	@Autowired
	private ISequenceService sequenceService;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private CarStyleMapper carStyleMapper;
	@Autowired
	private GpsLvlMapper gpsLvlMapper;
	
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("applyDaoImpl")
	private IApplyDao applyDao;
	@Autowired
	private ISysBranchService sysBranchService;
	@Autowired
	private IRunWorkflowService runWorkflowService ;
	@Autowired
	private ISysAreaService sysAreaService;
	public void tempSaveApply() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 订单查询
	 * @param id
	 */
	public Apply queryApplyById(String id){
		Apply apply = applyMapper.selectByPrimaryKey(id);
		return apply;
	}
	
	public void commitApply() {
		// TODO Auto-generated method stub
	}
	public void deleteApply() {
		// TODO Auto-generated method stub
	}
	public void getUnCommitApply(String accountId,List<Apply> applyList) {
		// TODO Auto-generated method stub
		HashMap<String,String> condition = new HashMap<String, String>();
		condition.put("accountId", "1");
		condition.put("status", "1");//未提交
		applyList = applyMapper.selectByMap(condition);
		System.out.println(applyList);
	}

	public ApplyVo getApplyDetail(String appId) {
		// TODO Auto-generated method stub
		ApplyVo applyVo = new ApplyVo();
		Apply apply = applyMapper.selectByAppid(appId);
		Utils.copyProperties(apply, applyVo);
		System.out.println(applyVo);
		//产品信息
		Product product = productMapper.selectProductByProductCode(applyVo.getProductCode());
		applyVo.setProduct(product);
		//融资信息
		List<ApplyFinance> financesPo = applyFinanceMapper.selectByAppId(appId);
		List<ApplyFinanceVo> financesVo = new ArrayList<ApplyFinanceVo>();
		for (ApplyFinance financePo:financesPo) {
			ApplyFinanceVo financeVo = new ApplyFinanceVo();
			Utils.copyProperties(financePo, financeVo);
			String carStyleId = financePo.getCarStyleId();
			String gpsLvlId = financePo.getGpsLvlId();
			//融资车辆信息
			CarStyle carStyle = carStyleMapper.selectByPrimaryKey(carStyleId);
			//GPS价格档位
			GpsLvl gpsLvl = gpsLvlMapper.selectByPrimaryKey(gpsLvlId);
			financeVo.setSelect(true);
			financeVo.setCarStyle(carStyle);
			financeVo.setGpsLvl(gpsLvl);
			financesVo.add(financeVo);
		}
		applyVo.setFinances(financesVo);
		//承租人信息
		ApplyTenant tenantPo = applyTenantMapper.selectByAppId(appId);
		ApplyTenantVo tenantVo = new ApplyTenantVo();
		Utils.copyProperties(tenantPo, tenantVo);
		List<ApplyTenantCar> tenantCars = applyTenantCarMapper.selectByAppId(appId);
		List<ApplyTenantHouse> tenantHouses = applyTenantHouseMapper.selectByAppId(appId);
		tenantVo.setTenantCars(tenantCars);
		tenantVo.setTenantHouses(tenantHouses);
		applyVo.setTenant(tenantVo);
		//配偶信息
		ApplySpouse spousePo = applySpouseMapper.selectByAppId(appId);
		ApplySpouseVo spouseVo = new ApplySpouseVo();
		Utils.copyProperties(spousePo, spouseVo);
		applyVo.setSpouse(spouseVo);
		//共租人信息
		ApplyCloessee cloesseePo = applyCloesseeMapper.selectByAppId(appId);
		ApplyCloesseeVo cloesseeVo = new ApplyCloesseeVo();
		Utils.copyProperties(cloesseePo, cloesseeVo);
		applyVo.setCloessee(cloesseeVo);
		//联系人信息
		List<ApplyLinkman> linkmansPo = applyLinkmanMapper.selectByAppId(appId);
		List<ApplyLinkmanVo> linkmansVo = new ArrayList();
		for(ApplyLinkman linkmanPo:linkmansPo){
			ApplyLinkmanVo linkmanVo = new ApplyLinkmanVo();
			Utils.copyProperties(linkmanPo, linkmanVo);
			linkmansVo.add(linkmanVo);
		}
		applyVo.setLinkmans(linkmansVo);
		//家庭负债
		ApplyFamilyDebt familyDebtPo = applyFamilyDebtMapper.selectByAppId(appId);
		ApplyFamilyDebtVo familyDebtVo = new ApplyFamilyDebtVo();
		Utils.copyProperties(familyDebtPo, familyDebtVo);
		applyVo.setFamilyDebt(familyDebtVo);
//		System.out.println(finances);
//		System.out.println(applyVo);
		return applyVo;
		
	}

	public List<ApplyInfoPo> getApplyInfoList(String accountId,String status,QueryParamApplyVo queryParam) {
		// TODO Auto-generated method stub
		return applyMapper.selectApplyInfoList(accountId, status,queryParam);
	}

	public void commitApplyTask(ApplyVo applyVo,SysAccount sysAccount) {
		// TODO Auto-generated method stub
		IApplyService currentProxy = (IApplyService) AopContext.currentProxy();
		String businessKey = currentProxy.saveApply(applyVo, sysAccount.getAccountId());
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("productCode", applyVo.getProduct().getProductCode());
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, sysAccount.getAccountId());
		runWorkflowService.startProcess("gcsq", businessKey, vars);
	}

	/**
	 * 保存融资信息
	 * @param applyVo
	 * @throws Exception
	 */
	public void saveApplyFinance(ApplyVo applyVo,String accountId){
		IApplyService currentProxy = (IApplyService)AopContext.currentProxy();
		String appId = applyVo.getAppId();
		List<ApplyFinanceVo> finances = applyVo.getFinances();
		if(finances == null)
			return;
		for (int i = 0; i < finances.size(); i++) {
			ApplyFinanceVo afv = finances.get(i);
			if(afv.isSelect()){
				String id = afv.getId();
				try {
					afv.setCarStyleId(afv.getCarStyle().getId());
				} catch (Exception e) {
					afv.setCarStyleId("");
				}
				try {
					afv.setGpsLvlId(afv.getGpsLvl().getId());
				} catch (Exception e) {
					afv.setGpsLvlId("");
				}
				if((id == null || "".equals(id))){//add
					afv.setId(Utils.get16UUID());
//					afv.setSeq(i);
					afv.setAppId(appId);
					applyDao.insertApplyFinance(afv, accountId);;
				}else{//upd
					applyDao.updApplyFinance(afv, accountId);;
				}
			}
		}
	}
	
	@Override
	public void saveApplySpouse(ApplyVo applyVo, String accountId) {
		// TODO Auto-generated method stub
		IApplyService currentProxy = (IApplyService) AopContext.currentProxy();
		String appId = applyVo.getAppId();
		ApplySpouseVo spouse = applyVo.getSpouse();
		if(spouse == null)
			return;
		spouse.setAppId(appId);
		String tempId = "";
		tempId = spouse.getId();
		if(tempId == null || "".equals(tempId)){//insert
//			currentProxy.saveApplySpouse(applyVo);;
			spouse.setAppId(appId);
			spouse.setId(Utils.get16UUID());
			applyDao.insertApplySpouse(spouse, accountId);
		}else{//upd
			applyDao.updApplySpouse(spouse, accountId);
		}
	}

	@Override
	public void saveApplyCloessee(ApplyVo applyVo, String accountId) {
		// TODO Auto-generated method stub
		IApplyService currentProxy = (IApplyService) AopContext.currentProxy();
		String appId = applyVo.getAppId();
		ApplyCloesseeVo ac = applyVo.getCloessee();
		if(ac == null)
			return;
		String tempId = "";
		tempId = ac.getId();
		if(tempId == null || "".equals(tempId)){//insert
			ac.setAppId(appId);
			ac.setId(Utils.get16UUID());
			applyDao.insertApplyCloessee(ac, accountId);
		}else{//upd
			applyDao.updApplyCloessee(ac, accountId);
		}
	}

	@Override
	public void saveApplyLinkman(ApplyVo applyVo, String accountId) {
		// TODO Auto-generated method stub
		IApplyService currentProxy = (IApplyService) AopContext.currentProxy();
		String appId = applyVo.getAppId();
		List<ApplyLinkmanVo> linkmans = applyVo.getLinkmans();
		if(linkmans == null)
			return;
		for (int i = 0; i < linkmans.size(); i++) {
			String tempId = "";
			ApplyLinkmanVo linkman = linkmans.get(i);
			tempId = linkman.getId();
			if(linkman.getName() != null || !"".equals(linkman.getName())){//存在有效数据
				if(tempId == null || "".equals(tempId)){//insert
					linkman.setId(Utils.get16UUID());
					linkman.setAppId(appId);
					applyDao.insertApplyLinkman(linkman, accountId);
				}else{//upd
					applyDao.updApplyLinkman(linkman, accountId);
				}
			}
		}
	}

	@Override
	public void saveApplyFamilyDebt(ApplyVo applyVo, String accountId) {
		IApplyService currentProxy = (IApplyService) AopContext.currentProxy();
		// TODO Auto-generated method stub
		String appId = applyVo.getAppId();
		String tempId = "";
		ApplyFamilyDebtVo familyDebt = applyVo.getFamilyDebt();
		if(familyDebt == null)
			return;
		tempId = familyDebt.getId();
		if(tempId == null || "".equals(tempId)){//insert
			familyDebt.setAppId(appId);
			familyDebt.setId(Utils.get16UUID());
			applyDao.insertApplyFamilyDebt(familyDebt, accountId);
		}else{//upd
			applyDao.updApplyFamilyDebt(familyDebt, accountId);
		}
	}

	@Override
	public void saveApplyTenant(ApplyVo applyVo, String accountId) {
		// TODO Auto-generated method stub
		IApplyService currentProxy = (IApplyService) AopContext.currentProxy();
		String appId = applyVo.getAppId();
		ApplyTenantVo tenant = applyVo.getTenant();
		if(tenant == null)
			return;
		String tempId = "";
		tempId = tenant.getId();
		System.out.println("tempId:"+tempId);
		if(tempId == null || "".equals(tempId)){//insert
			tenant.setId(Utils.get16UUID());
			tenant.setAppId(appId);
			applyDao.insertApplyTenant(tenant, accountId);
		}else{//upd
			tenant.setAppId(appId);
			applyDao.updApplyTenant(tenant, accountId);
		}
		currentProxy.saveApplyTenantCar(tenant,accountId);
		currentProxy.saveApplyTenantHouse(tenant,accountId);
		
	}
	
	@Override
	public void saveApplyTenantHouse(ApplyTenantVo tenant, String accountId) {
		IApplyService currentProxy = (IApplyService) AopContext.currentProxy();
		// TODO Auto-generated method stub
		String tempId = "";
		String appId = tenant.getAppId();
		List<ApplyTenantHouse> houseList = tenant.getTenantHouses();
		for (int i = 0; i < houseList.size(); i++) {
			ApplyTenantHouse house = houseList.get(i);
			if(house.getStatus() != null && !("".equals(house.getStatus()))){//存在有效数据
				tempId = house.getId();
				if(tempId == null || "".equals(tempId)){//add house
					house.setId(Utils.get16UUID());
					house.setAppId(appId);
//					house.setSeq(i);
					applyDao.insertApplyTenantHouse(house, accountId);
				}else{//upd house
					applyDao.updApplyTenantHouse(house, accountId);
				}
			}
		}
		
	}

	@Override
	public void saveApplyTenantCar(ApplyTenantVo tenant, String accountId) {
		IApplyService currentProxy = (IApplyService) AopContext.currentProxy();
		// TODO Auto-generated method stub
		String tempId = "";
		String appId = tenant.getAppId();
		List<ApplyTenantCar> carList = tenant.getTenantCars();
		for (int i = 0; i < carList.size(); i++) {
			ApplyTenantCar car = carList.get(i);
			System.out.println(car);
			if(car.getCarStatus() != null && !("".equals(car.getCarStatus()))){
				tempId = car.getId();
				if(tempId == null || "".equals(tempId)){//add car
					car.setId(Utils.get16UUID());
					car.setAppId(appId);
//					car.setSeq(i);
					applyDao.insertApplyTenantCar(car, accountId);
				}else{//upd car
//					currentProxy.updApplyTenantCar(car, accountId);
					applyDao.updApplyTenantCar(car, accountId);
				}
			}
		}
	}
	
	/**
	 * 订单保存
	 * @author pujjr 2016-09-18
	 * @throws Exception 
	 */
	public String saveApply(ApplyVo applyVo,String accountId)
	{
		System.out.println("ApplyServiceImpl->savApply："+sysMode);
		IApplyService currentProxy = (IApplyService) AopContext.currentProxy();
		String id = applyVo.getId();
		String appId = applyVo.getAppId();
		Apply applyExist = null;
		Apply applyToDao = new Apply();
		//计算客户等级
		String custLvl = this.calCustomLevel(applyVo);
		applyVo.setCustType(custLvl);
		String productCode = applyVo.getProduct().getProductCode();
		SysAccount sysAccount = sysAccountMapper.selectByAccountId(accountId);
		SysBranch branch = sysBranchService.getSysBranch(sysAccount.getBranchId(), "");
		String branchId = branch.getBranchCode();
		String dataNow = Utils.getFormatDate(Calendar.getInstance().getTime(), "YYMMdd");
		String sequence=String.format("%04d", sequenceService.getNextVal("appid"));
		if("".equals(appId) || appId == null)
		{
			appId = branchId+""+dataNow+""+sequence+""+productCode;//订单编号
		}
		applyExist = applyMapper.selectByPrimaryKey(id);
		if(applyExist == null)
		{
			applyToDao.setCreateBranchCode(branchId);
			applyToDao.setCreateAccountId(sysAccount.getAccountId());
			//只有不存在时申请单才是未提交状态
			applyToDao.setStatus(ApplyStatus.UNCOMMIT);	
		}
		//基本信息
		//主键随机数
		id = Utils.get16UUID();
		applyToDao.setId(id);
		applyToDao.setAppId(appId);		
		applyToDao.setProductCode(productCode);
		applyToDao.setPeriod(applyVo.getPeriod());
		applyToDao.setComment(applyVo.getComment());
		applyToDao.setCustType(applyVo.getCustType());
		applyToDao.setTotalFinanceAmt(applyVo.getTotalFinanceAmt());
		applyToDao.setTotalLoanAmt(applyVo.getTotalLoanAmt());
		applyToDao.setMonthRent(applyVo.getMonthRent());
		applyToDao.setApproveDate(applyVo.getApproveDate());
		applyToDao.setProcInstId(applyVo.getProcInstId());
		if(applyExist == null)
		{
			//录入时间
			applyToDao.setCreateTime(Calendar.getInstance().getTime());
			applyDao.insertApply(applyToDao, accountId);
		}
		else
		{
			id = applyVo.getId();
			appId = applyVo.getAppId();
			applyToDao.setId(id);
			applyToDao.setAppId(appId);
			applyDao.updApply(applyToDao, accountId);
		}
		
		//保存关联信息-融资信息
		applyVo.setAppId(appId);
		currentProxy.saveApplyFinance(applyVo,accountId);
		currentProxy.saveApplyTenant(applyVo, accountId);
		currentProxy.saveApplySpouse(applyVo, accountId);
		currentProxy.saveApplyCloessee(applyVo, accountId);
		currentProxy.saveApplyLinkman(applyVo, accountId);
		currentProxy.saveApplyFamilyDebt(applyVo, accountId);
		return appId;
	}

	@Override
	public List<ApplyFinance> getApplyFinanceList(String appId) {
		// TODO Auto-generated method stub
		return applyFinanceMapper.selectListByAppId(appId);
	}

	@Override
	public Apply getApply(String appId) {
		// TODO Auto-generated method stub
		Apply apply = applyMapper.selectByAppid(appId);
		return apply;
	}

	@Override
	public int updateApply(Apply apply) {
		// TODO Auto-generated method stub
		return applyMapper.updateByPrimaryKey(apply);
	}

	@Override
	public ApplyTenant getApplyTenant(String appId) {
		// TODO Auto-generated method stub
		return applyTenantMapper.selectByAppId(appId);
	}

	@Override
	public List<ApplyTenantHouse> getApplyTenantHouseList(String appId) {
		// TODO Auto-generated method stub
		return applyTenantHouseMapper.selectByAppId(appId);
	}

	@Override
	public List<ApplyTenantCar> getApplyTenantCarList(String appId) {
		// TODO Auto-generated method stub
		return applyTenantCarMapper.selectByAppId(appId);
	}
	@Override
	public String calCustomLevel(ApplyVo applyVo) {
		// TODO Auto-generated method stub
		String custLvl ="B";
		/**
		 * A级客户判断
		 */
		//产品代码
		String productCode = applyVo.getProduct().getProductCode();
		if("N20".equals(productCode)||"N30".equals(productCode)||"N40".equals(productCode))
		{
			return "A";
		};
		//承租人单位电话
		String tenantUnitTel = applyVo.getTenant().getUnitTel();
		//承租人先住址省份
		String tenantAddrProvince = applyVo.getTenant().getAddrProvince();
		String tenantArea = sysAreaService.getAreaNameById(tenantAddrProvince);
		//承租人单位类型
		String unitType = applyVo.getTenant().getUnitType();
		//承租人所属行业
		String unitIndustry = applyVo.getTenant().getUnitIndustry();
		//承租人户籍
		String houseHold = applyVo.getTenant().getHouseHold();
		//承租人职级
		String rank = applyVo.getTenant().getRank();
		
		boolean flagA = true;
		for(ApplyFinanceVo item : applyVo.getFinances())
		{
			if(!item.select)
			{
				continue;
			}
			//首付比例
			double initPayPercent = item.getInitPayPercent()/100;
			//融资金额
			double finAmt = item.getFinanceAmount();
			//裸车价
			double salePrice = item.getSalePrice();
			//购置税
			double purchaseTax = item.getPurchaseTax();
			//服务费
			double serviceFee = item.getServiceFee();
			//保险费
			double insuranceFee = item.getInsuranceFee();
			//延保费
			double delayInsuranceFee = item.getDelayInsuranceFee();
			//过户费
			double transferFee = item.getTransferFee();
			//加装费
			double addonFee = item.getAddonFee();
			//费用合计
			double feeTotal = purchaseTax+serviceFee+insuranceFee+delayInsuranceFee+transferFee+addonFee;
			//判断融资金额<=155000，大于则不是A
			boolean checkFinance = Double.compare(finAmt, 155000)<=0;
			if(!checkFinance)
			{
				flagA = false;
				break;
			}
			//判断“购置税+服务费+保险费+延保费+过户费+加装费”≤裸车价*20%，大于则不是A
			boolean checkFee = Double.compare(feeTotal, salePrice*0.2)<=0;
			if(!checkFee)
			{
				flagA = false;
				break;
			}
			/**
			 * (首付比例>=20% )
			 * and (承租人工作信息---单位电话：位数大于等于8位且第一位不为1) 
			 * and (承租人单位类型为国家机关/事业单位/国企 or 学校/教育机构)
			 */
			if(Double.compare(initPayPercent, 0.2)<0)
			{
				flagA = false;
				break;
			}
			if(!(tenantUnitTel!=null && tenantUnitTel.length()>=8 && tenantUnitTel.charAt(0)!='1'))
			{
				flagA = false;
				break;
			}
			if(!"dwlx05".equals(unitType) && !"dwlx07".equals(unitType))
			{
				flagA = false;
				break;
			}
		}
		if(flagA)
		{
			return "A";
		}
		flagA = true;
		for(ApplyFinanceVo item : applyVo.getFinances())
		{
			if(!item.select)
			{
				continue;
			}
			//首付比例
			double initPayPercent = item.getInitPayPercent()/100;
			//融资金额
			double finAmt = item.getFinanceAmount();
			//裸车价
			double salePrice = item.getSalePrice();
			//购置税
			double purchaseTax = item.getPurchaseTax();
			//服务费
			double serviceFee = item.getServiceFee();
			//保险费
			double insuranceFee = item.getInsuranceFee();
			//延保费
			double delayInsuranceFee = item.getDelayInsuranceFee();
			//过户费
			double transferFee = item.getTransferFee();
			//加装费
			double addonFee = item.getAddonFee();
			//费用合计
			double feeTotal = purchaseTax+serviceFee+insuranceFee+delayInsuranceFee+transferFee+addonFee;
			//判断融资金额<=155000，大于则不是A
			boolean checkFinance = Double.compare(finAmt, 155000)<=0;
			if(!checkFinance)
			{
				flagA = false;
				break;
			}
			//判断“购置税+服务费+保险费+延保费+过户费+加装费”≤裸车价*20%，大于则不是A
			boolean checkFee = Double.compare(feeTotal, salePrice*0.2)<=0;
			if(!checkFee)
			{
				flagA = false;
				break;
			}
			/**
			 * 首付比例>=40%
			 * and “GPS费用、购置税、服务费、保险费、延保费、过户费、加装费”中只有GPS费用；
			 */
			if(Double.compare(initPayPercent, 0.4)<0)
			{
				flagA = false;
				break;
			}
			if(
					!(Utils.doubleIsZero(item.getPurchaseTax())&&
					Utils.doubleIsZero(item.getServiceFee()) &&
					Utils.doubleIsZero(item.getInsuranceFee())&&
					Utils.doubleIsZero(item.getDelayInsuranceFee())&&
					Utils.doubleIsZero(item.getTransferFee())&&
					Utils.doubleIsZero(item.getAddonFee())&&
					!Utils.doubleIsZero(item.getGpsFee()))
			)
			{
				flagA = false;
				break;
			}
		}
		if(flagA)
		{
			return "A";
		}
		flagA = true;
		for(ApplyFinanceVo item : applyVo.getFinances())
		{
			if(!item.select)
			{
				continue;
			}
			//首付比例
			double initPayPercent = item.getInitPayPercent()/100;
			//融资金额
			double finAmt = item.getFinanceAmount();
			//裸车价
			double salePrice = item.getSalePrice();
			//购置税
			double purchaseTax = item.getPurchaseTax();
			//服务费
			double serviceFee = item.getServiceFee();
			//保险费
			double insuranceFee = item.getInsuranceFee();
			//延保费
			double delayInsuranceFee = item.getDelayInsuranceFee();
			//过户费
			double transferFee = item.getTransferFee();
			//加装费
			double addonFee = item.getAddonFee();
			//费用合计
			double feeTotal = purchaseTax+serviceFee+insuranceFee+delayInsuranceFee+transferFee+addonFee;
			//判断融资金额<=155000，大于则不是A
			boolean checkFinance = Double.compare(finAmt, 155000)<=0;
			if(!checkFinance)
			{
				flagA = false;
				break;
			}
			//判断“购置税+服务费+保险费+延保费+过户费+加装费”≤裸车价*20%，大于则不是A
			boolean checkFee = Double.compare(feeTotal, salePrice*0.2)<=0;
			if(!checkFee)
			{
				flagA = false;
				break;
			}
			/**
			 * 承租人基本信息---现详细住址=北京市（全部）
			 * and 承租人基本信息---户籍所属=本地
			 */
			if(!"北京市".equals(tenantArea)||!"hjss01".equals(houseHold))
			{
				flagA = false;
				break;
			}
		}
		if(flagA)
		{
			return "A";
		}
		/**
		 * C级客户判断
		 * 承租人工作信息---所属行业 = 房地产开发经营 or 钢材生产及贸易 or 建筑/工程/劳务/施工 or 煤炭开采/洗选/商贸 or 水泥、瓦砖、玻璃、陶瓷建筑材料生产制造/石矿开采 or 融资租赁、财务公司、汽车金融等金融公司 or 小贷、担保、典当、P2P、理财投资等融资机构
		 * and 承租人工作信息---职级=法人/股东 or 经营者 or 自雇人士
		 * and “购置税、服务费、保险费、延保费、过户费、加装费”只有任意 1项；或零项
		 */
		if(unitIndustry!=null && !StringUtils.isBlank(unitIndustry))
		{
			if(
				"hy01".equals(unitIndustry)	||
				"hy02".equals(unitIndustry)	||
				"hy03".equals(unitIndustry)	||
				"hy04".equals(unitIndustry)	||
				"hy17".equals(unitIndustry)	||
				"hy45".equals(unitIndustry)	||
				"hy46".equals(unitIndustry)	)
			{
				if("zwjb07".equals(rank)||"zwjb04".equals(rank)||"zwjb02".equals(rank))
				{
					boolean flag = false;
					for(ApplyFinanceVo item : applyVo.getFinances())
					{
						int i=0;
						//裸车价
						double salePrice = item.getSalePrice();
						if(Double.compare(salePrice, 0.00)>0)
						{
							i++;
						}
						//购置税
						double purchaseTax = item.getPurchaseTax();
						if(Double.compare(purchaseTax, 0.00)>0)
						{
							i++;
						}
						//服务费
						double serviceFee = item.getServiceFee();
						if(Double.compare(serviceFee, 0.00)>0)
						{
							i++;
						}
						//保险费
						double insuranceFee = item.getInsuranceFee();
						if(Double.compare(insuranceFee, 0.00)>0)
						{
							i++;
						}
						//延保费
						double delayInsuranceFee = item.getDelayInsuranceFee();
						if(Double.compare(delayInsuranceFee, 0.00)>0)
						{
							i++;
						}
						//过户费
						double transferFee = item.getTransferFee();
						if(Double.compare(transferFee, 0.00)>0)
						{
							i++;
						}
						//加装费
						double addonFee = item.getAddonFee();
						if(Double.compare(addonFee, 0.00)>0)
						{
							i++;
						}
						if(i>1)
						{
							flag=true;
							break;
						}
					}
					if(flag)
					{
						custLvl =  "B";
					}
					else
					{
						custLvl =  "C";
					}
				}
			}
			
		}
		return custLvl;
	}
}
