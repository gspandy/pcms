package com.pujjr.carcredit.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.formula.functions.Finance;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
import com.pujjr.base.service.ISysBranchService;
import com.pujjr.carcredit.annotion.ApplyOperHisAnnotation;
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
import com.pujjr.carcredit.domain.ApplyFinance;
import com.pujjr.carcredit.domain.ApplyTenantCar;
import com.pujjr.carcredit.domain.ApplyTenantHouse;
import com.pujjr.carcredit.po.ApplyInfoPo;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.vo.ApplyCloesseeVo;
import com.pujjr.carcredit.vo.ApplyFamilyDebtVo;
import com.pujjr.carcredit.vo.ApplyFinanceVo;
import com.pujjr.carcredit.vo.ApplyLinkmanVo;
import com.pujjr.carcredit.vo.ApplySpouseVo;
import com.pujjr.carcredit.vo.ApplyStatus;
import com.pujjr.carcredit.vo.ApplyTenantVo;
import com.pujjr.carcredit.vo.ApplyUncommitVo;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.jbpm.service.IRunWorkflowService;
import com.pujjr.jbpm.vo.ProcessGlobalVariable;
import com.pujjr.utils.Utils;

@Service
@Transactional
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
	
	@Autowired
	@Qualifier("applyDaoImpl")
	private IApplyDao applyDao;
	@Autowired
	private ISysBranchService sysBranchService;
	@Autowired
	private IRunWorkflowService runWorkflowService ;
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

	public ApplyVo getUnCommitApplyDetail(String appId) {
		// TODO Auto-generated method stub
		ApplyVo applyVo = applyMapper.selectApplyByMap(appId);
		
		System.out.println(applyVo);
		//产品信息
		Product product = productMapper.selectProductByProductCode(applyVo.getProductCode());
		applyVo.setProduct(product);
		//融资信息
		List<ApplyFinanceVo> finances = applyFinanceMapper.selectByAppId(appId);
		for (ApplyFinanceVo finance:finances) {
			String carStyleId = finance.getCarStyleId();
			String gpsLvlId = finance.getGpsLvlId();
			//融资车辆信息
			CarStyle carStyle = carStyleMapper.selectByPrimaryKey(carStyleId);
			//GPS价格档位
			GpsLvl gpsLvl = gpsLvlMapper.selectByPrimaryKey(gpsLvlId);
			finance.setSelect(true);
			finance.setCarStyle(carStyle);
			finance.setGpsLvl(gpsLvl);
		}
		applyVo.setFinances(finances);
		//承租人信息
		ApplyTenantVo tenant = applyTenantMapper.selectByAppId(appId);
		List<ApplyTenantCar> tenantCars = applyTenantCarMapper.selectByAppId(appId);
		List<ApplyTenantHouse> tenantHouses = applyTenantHouseMapper.selectByAppId(appId);
		tenant.setTenantCars(tenantCars);
		tenant.setTenantHouses(tenantHouses);
		applyVo.setTenant(tenant);
		//配偶信息
		ApplySpouseVo spouse = applySpouseMapper.selectByAppId(appId);
		applyVo.setSpouse(spouse);
		//共租人信息
		ApplyCloesseeVo cloessee = applyCloesseeMapper.selectByAppId(appId);
		applyVo.setCloessee(cloessee);
		//联系人信息
		List<ApplyLinkmanVo> linkmans = applyLinkmanMapper.selectByAppId(appId);
		applyVo.setLinkmans(linkmans);
		//家庭负债
		ApplyFamilyDebtVo familyDebt = applyFamilyDebtMapper.selectByAppId(appId);
		applyVo.setFamilyDebt(familyDebt);
		System.out.println(finances);
		System.out.println(applyVo);
		return applyVo;
		
	}

	public List<ApplyInfoPo> getApplyInfoList(String accountId,String status) {
		// TODO Auto-generated method stub
		return applyMapper.selectApplyInfoList(accountId, status);
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
		for (int i = 0; i < finances.size(); i++) {
			ApplyFinanceVo afv = finances.get(i);
			if(afv.isSelect()){
				String id = afv.getId();
				if((id == null || "".equals(id))){//add
					try {
						afv.setCarStyleId(afv.getCarStyle().getCarSerialId());
					} catch (Exception e) {
						afv.setCarStyleId("");
					}
					try {
						afv.setGpsLvlId(afv.getGpsLvl().getId());
					} catch (Exception e) {
						afv.setGpsLvlId("");
					}
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
	public String saveApply(ApplyVo applyVo,String accountId){
		System.out.println("ApplyServiceImpl->savApply："+sysMode);
		IApplyService currentProxy = (IApplyService) AopContext.currentProxy();
		String id = applyVo.getId();
		String appId = applyVo.getAppId();
		Apply applyExist = null;
		if("debug".equals(sysMode)){
			//测试
			// TODO Auto-generated method stub
			String productCode = applyVo.getProduct().getProductCode();
			applyExist = applyMapper.selectByPrimaryKey(id);
			System.out.println("applyExist:"+applyExist);
			//基本信息
//			apply = new Apply();
			applyVo.setId(Utils.get16UUID());//主键随机数
			if(appId == null)
				appId = "000116092831231001";
			applyVo.setAppId(appId);
			applyVo.setProductCode(productCode);
			applyVo.setStatus(ApplyStatus.UNCOMMIT);	
			applyVo.setCreateBranchCode("1");
			applyVo.setCreateAccountId(accountId);
		}else{
			//生产
			String productCode = applyVo.getProduct().getProductCode();
			SysAccount sysAccount = sysAccountMapper.selectByAccountId(accountId);
			System.out.println("*****sysAccount:"+sysAccount);
			SysBranch branch = sysBranchService.getSysBranch(sysAccount.getBranchId(), "");
			System.out.println("**********branch:"+branch);
			String branchId = branch.getBranchCode();
			String dataNow = Utils.getFormatDate(Calendar.getInstance().getTime(), "YYMMdd");
			String sequence=String.format("%04d", sequenceService.getNextVal("appid"));
			if("".equals(appId) || appId == null)
				appId = branchId+""+dataNow+""+productCode+""+sequence;//订单编号
			applyExist = applyMapper.selectByPrimaryKey(id);
//			System.out.println("applyList.size():"+applyList.size());
			//基本信息
//			apply = new Apply();
			applyVo.setId(Utils.get16UUID());//主键随机数
			applyVo.setAppId(appId);		
			applyVo.setProductCode(productCode);
			applyVo.setPeriod(applyVo.getPeriod());
			applyVo.setComment(applyVo.getComment());
			applyVo.setStatus(ApplyStatus.UNCOMMIT);	
			applyVo.setCreateBranchCode(branchId);
			applyVo.setCreateAccountId(sysAccount.getAccountId());
		}
		
		if(applyExist == null){
			applyVo.setCreateTime(Calendar.getInstance().getTime());//录入时间
			applyDao.insertApply(applyVo, accountId);
		}else{
			applyVo.setId(id);
			applyDao.updApply(applyVo, accountId);
		}
		//保存关联信息-融资信息
		currentProxy.saveApplyFinance(applyVo,accountId);
		currentProxy.saveApplyTenant(applyVo, accountId);
		currentProxy.saveApplySpouse(applyVo, accountId);
		currentProxy.saveApplyCloessee(applyVo, accountId);
		currentProxy.saveApplyLinkman(applyVo, accountId);
		currentProxy.saveApplyFamilyDebt(applyVo, accountId);
		return appId;
	}
}
