package com.pujjr.carcredit.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.formula.functions.Finance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.pujjr.base.dao.CarStyleMapper;
import com.pujjr.base.dao.GpsLvlMapper;
import com.pujjr.base.dao.ProductMapper;
import com.pujjr.base.dao.SequenceMapper;
import com.pujjr.base.dao.SysAccountMapper;
import com.pujjr.base.domain.CarStyle;
import com.pujjr.base.domain.GpsLvl;
import com.pujjr.base.domain.Product;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.service.ISequenceService;
import com.pujjr.base.service.ISysBranchService;
import com.pujjr.carcredit.dao.ApplyCloesseeMapper;
import com.pujjr.carcredit.dao.ApplyFamilyDebtMapper;
import com.pujjr.carcredit.dao.ApplyFinanceMapper;
import com.pujjr.carcredit.dao.ApplyLinkmanMapper;
import com.pujjr.carcredit.dao.ApplyMapper;
import com.pujjr.carcredit.dao.ApplySpouseMapper;
import com.pujjr.carcredit.dao.ApplyTenantCarMapper;
import com.pujjr.carcredit.dao.ApplyTenantHouseMapper;
import com.pujjr.carcredit.dao.ApplyTenantMapper;
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
public class ApplyServiceImpl implements IApplyService {
//public class ApplyServiceImpl{
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
	private ISysBranchService sysBranchService;
	@Autowired
	private IRunWorkflowService runWorkflowService ;
	public void tempSaveApply() {
		// TODO Auto-generated method stub

	}

	/**
	 * 修改家庭负债信息
	 * @param applyVo
	 * @throws Exception
	 */
	public void updApplyFamilyDebt(ApplyVo applyVo){
		String appId = applyVo.getAppId();
		String tempId = "";
		ApplyFamilyDebtVo familyDebt = applyVo.getFamilyDebt();
		tempId = familyDebt.getId();
		if(tempId == null || "".equals(tempId)){//insert
			familyDebt.setAppId(appId);
			familyDebt.setId(Utils.get16UUID());
			applyFamilyDebtMapper.insert(familyDebt);
		}else{//upd
			applyFamilyDebtMapper.updateByPrimaryKeySelective(familyDebt);
		}
	}
	
	/**
	 * 修改联系人信息
	 * @param applyVo
	 * @throws Exception
	 */
	public void updApplyLinkman(ApplyVo applyVo){
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
					applyLinkmanMapper.insert(linkman);
				}else{//upd
					applyLinkmanMapper.updateByPrimaryKeySelective(linkman);
				}
			}
		}
	}
	
	/**
	 * 修改共租人/担保人信息
	 * @param applyVo
	 * @throws Exception
	 */
	public void updApplyCloessee(ApplyVo applyVo){
		String appId = applyVo.getAppId();
		ApplyCloessee ac = applyVo.getCloessee();
		String tempId = "";
		tempId = ac.getId();
		if(tempId == null || "".equals(tempId)){//insert
			this.saveApplyCloessee(applyVo);
		}else{//upd
			applyCloesseeMapper.updateByPrimaryKeySelective(ac);
		}
		
	}
	
	/**
	 * 修改配偶信息
	 * @param applyVo
	 * @throws Exception
	 */
	public void updApplySpouse(ApplyVo applyVo){
		String appId = applyVo.getAppId();
		ApplySpouseVo spouse = applyVo.getSpouse();
		spouse.setAppId(appId);
		String tempId = "";
		tempId = spouse.getId();
		if(tempId == null || "".equals(tempId)){//insert
			this.saveApplySpouse(applyVo);;
		}else{//upd
			applySpouseMapper.updateByPrimaryKeySelective(spouse);
		}
	}
	
	/**
	 * 修改承租人信息
	 * @param applyVo
	 * @throws Exception
	 */
	public void updApplyTenant(ApplyVo applyVo){
		String appId = applyVo.getAppId();
		ApplyTenantVo tenant = applyVo.getTenant();
		String tempId = "";
		tempId = tenant.getId();
		if(tempId == null || "".equals(tempId)){//insert
			this.saveApplyTenant(applyVo);
		}else{//upd
			tenant.setAppId(appId);
			applyTenantMapper.updateByPrimaryKey(tenant);
			List<ApplyTenantHouse> houseList = tenant.getTenantHouses();
			List<ApplyTenantCar> carList = tenant.getTenantCars();
			for (int i = 0; i < houseList.size(); i++) {
				ApplyTenantHouse house = houseList.get(i);
				if(house.getStatus() != null && !("".equals(house.getStatus()))){//存在有效数据
					tempId = house.getId();
					if(tempId == null || "".equals(tempId)){//add
						house.setId(Utils.get16UUID());
						house.setAppId(appId);
//						house.setSeq(i);
						applyTenantHouseMapper.insert(house);
					}else{//upd
						applyTenantHouseMapper.updateByPrimaryKeySelective(house);
					}
				}
			}
			for (int i = 0; i < carList.size(); i++) {
				ApplyTenantCar car = carList.get(i);
				if(car.getCarStatus() != null && !("".equals(car.getCarStatus()))){
					tempId = car.getId();
					if(tempId == null || "".equals(tempId)){//add
						car.setId(Utils.get16UUID());
						car.setAppId(appId);
//						car.setSeq(i);
						applyTenantCarMapper.insert(car);	
					}else{//upd
						applyTenantCarMapper.updateByPrimaryKeySelective(car);
					}
				}
			}
		}
	}
	
	/**
	 * 修改融资信息
	 * @param applyVo
	 * @throws Exception
	 */
	public void updAppllyFinance(ApplyVo applyVo){
		String appId = applyVo.getAppId();
		List<ApplyFinanceVo> finances = applyVo.getFinances();
		
		for (int i = 0; i < finances.size(); i++) {
			ApplyFinanceVo afv = finances.get(i);
			if(afv.isSelect()){
				String id = afv.getId();
				if(id == null || "".equals(id)){//add
					afv.setCarStyleId(afv.getCarStyle().getCarSerialId());
					afv.setGpsLvlId(afv.getGpsLvl().getId());
					afv.setId(Utils.get16UUID());
//					afv.setSeq(i);
					afv.setAppId(appId);
					applyFinanceMapper.insert(afv);
				}else{//upd
					applyFinanceMapper.updateByPrimaryKeySelective(afv);
				}
			}
		}
	}
	
	/**
	 * 保存家庭负债信息
	 * @param applyVo
	 * @throws Exception
	 */
	public void saveApplyFamilyDebt(ApplyVo applyVo){
		String appId = applyVo.getAppId();
		ApplyFamilyDebtVo familyDebt = applyVo.getFamilyDebt();
		familyDebt.setAppId(appId);
		familyDebt.setId(Utils.get16UUID());
		applyFamilyDebtMapper.insert(familyDebt);
	}
	
	/**
	 * 保存联系人信息
	 * @param applyVo
	 * @throws Exception
	 */
	public void saveApplyLinkman(ApplyVo applyVo){
		String appId = applyVo.getAppId();
		List<ApplyLinkmanVo> linkmans = applyVo.getLinkmans();
		for (int i = 0; i < linkmans.size(); i++) {
			ApplyLinkmanVo linkman = linkmans.get(i);
			if(linkman.getName() != null || !"".equals(linkman.getName())){
				linkman.setAppId(appId);
				linkman.setId(Utils.get16UUID());
				applyLinkmanMapper.insert(linkman);
			}
		}
	}
	
	/**
	 * 保存共租人/担保人信息
	 * @param applyVo
	 * @throws Exception
	 */
	public void saveApplyCloessee(ApplyVo applyVo){
		String appId = applyVo.getAppId();
		ApplyCloessee ac = applyVo.getCloessee();
		ac.setAppId(appId);
		ac.setId(Utils.get16UUID());
		applyCloesseeMapper.insert(ac);
	}
	
	/**
	 * 保存配偶信息
	 * @param applyVo
	 * @throws Exception
	 */
	public void saveApplySpouse(ApplyVo applyVo){
		String appId = applyVo.getAppId();
		ApplySpouseVo spouse = applyVo.getSpouse();
		spouse.setAppId(appId);
		spouse.setId(Utils.get16UUID());
		applySpouseMapper.insert(spouse);
	}
	
	/**
	 * 保存承租人信息
	 * @param applyVo
	 * @throws Exception
	 */
	public void saveApplyTenant(ApplyVo applyVo){
		String appId = applyVo.getAppId();
		ApplyTenantVo tenant = applyVo.getTenant();
		tenant.setId(Utils.get16UUID());
		tenant.setAppId(appId);
		applyTenantMapper.insert(tenant);
		List<ApplyTenantHouse> houseList = tenant.getTenantHouses();
		List<ApplyTenantCar> carList = tenant.getTenantCars();
		for (int i = 0; i < houseList.size(); i++) {
			ApplyTenantHouse house = houseList.get(i);
			if(house.getStatus() != null && !("".equals(house.getStatus()))){
				house.setId(Utils.get16UUID());
				house.setAppId(appId);
//				house.setSeq(i);
				applyTenantHouseMapper.insert(house);
			}
		}
		for (int i = 0; i < carList.size(); i++) {
			ApplyTenantCar car = carList.get(i);
			if(car.getCarStatus() != null && !("".equals(car.getCarStatus()))){
				car.setId(Utils.get16UUID());
				car.setAppId(appId);
//				car.setSeq(i);
				applyTenantCarMapper.insert(car);
			}
		}
	}
	
	/**
	 * 保存融资信息
	 * @param applyVo
	 * @throws Exception
	 */
	public void saveAppllyFinance(ApplyVo applyVo){
		String appId = applyVo.getAppId();
		List<ApplyFinanceVo> finances = applyVo.getFinances();
		for (int i = 0; i < finances.size(); i++) {
			ApplyFinanceVo afv = finances.get(i);
			if(afv.isSelect()){
				afv.setCarStyleId(afv.getCarStyle().getCarSerialId());
				afv.setGpsLvlId(afv.getGpsLvl().getId());
				afv.setId(Utils.get16UUID());
//				afv.setSeq(i);
				afv.setAppId(appId);
				applyFinanceMapper.insertSelective(afv);
			}
		}
	}
	
	/**
	 * 订单查询
	 * @param id
	 */
	public Apply queryApplyById(String id){
		Apply apply = applyMapper.selectByPrimaryKey(id);
		return apply;
	}
	
	/**
	 * 订单保存
	 * @author pujjr 2016-09-18
	 * @throws Exception 
	 */
	public String saveApply(ApplyVo applyVo,String accountId){
		boolean isInsert = false;
		String appId = applyVo.getAppId();
		Calendar cl = Calendar.getInstance();
//		String applyId = applyVo.getAppId();
		String productCode = applyVo.getProduct().getProductCode();
		SysAccount sysAccount = sysAccountMapper.selectByAccountId(accountId);
		String branchId = sysBranchService.getSysBranch(sysAccount.getBranchId(), "").getBranchCode();
		String dataNow = Utils.getFormatDate(cl.getTime(), "YYYYMMdd");
		String sequence=String.format("%04d", sequenceService.getNextVal("appid"));
		if("".equals(appId) || appId == null)
			appId = branchId+""+dataNow+""+productCode+""+sequence;//订单编号
		List<Apply> applyList = applyMapper.selectByAppid(appId);
		System.out.println("applyList.size():"+applyList.size());
		//基本信息
		Apply apply = new Apply();
		apply.setId(Utils.get16UUID());					//主键随机数
		apply.setAppId(appId);
		apply.setProductCode(productCode);
		apply.setPeriod(applyVo.getPeriod());
		apply.setComment(applyVo.getComment());
		apply.setStatus(ApplyStatus.UNCOMMIT);	
		apply.setCreateBranchCode(branchId);//申请状态,待定？
		apply.setCreateAccountId(sysAccount.getAccountId());
		apply.setCreateTime(cl.getTime());				//录入时间
		
		//测试
		/*accountId = "150032";
		// TODO Auto-generated method stub
		Calendar cl = Calendar.getInstance();
		String retMsg = "返回信息";
//		String applyId = applyVo.getAppId();
		String productCode = applyVo.getProduct().getProductCode();
		SysAccount sysAccount = sysAccountMapper.selectByAccountId(accountId);
//		String appId = "333";	//订单编号
		String appId = applyVo.getAppId();	//订单编号
		List<Apply> applyList = applyMapper.selectByAppid(appId);
		System.out.println("applyList.size():"+applyList.size());
		//基本信息
		Apply apply = new Apply();
		apply.setId(Utils.get16UUID());					//主键随机数
		apply.setAppId(appId);
		apply.setProductCode(productCode);
		apply.setPeriod(applyVo.getPeriod());
		apply.setComment(applyVo.getComment());
		apply.setStatus("1");	
		apply.setCreateBranchCode("1");//申请状态,待定？
		apply.setCreateAccountId("1");
		apply.setCreateTime(cl.getTime());				//录入时间
		*/
		
		applyVo.setAppId(appId);
		if(applyList.size() == 0){//add
			if(applyMapper.insert(apply) > 0){
					this.saveAppllyFinance(applyVo);
					this.saveApplyTenant(applyVo);
					this.saveApplySpouse(applyVo);
					this.saveApplyCloessee(applyVo);
					this.saveApplyLinkman(applyVo);
					this.saveApplyFamilyDebt(applyVo);
			}
		}else{//update
			if(applyMapper.updateByAppidSelective(apply) > 0){
				this.updAppllyFinance(applyVo);
				this.updApplyTenant(applyVo);
				this.updApplySpouse(applyVo);
				this.updApplyCloessee(applyVo);
				this.updApplyLinkman(applyVo);
				this.updApplyFamilyDebt(applyVo);
			}
		}
		return appId;
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
		String businessKey = this.saveApply(applyVo, sysAccount.getAccountId());
		HashMap<String,Object> vars = new HashMap<String,Object>();
		vars.put("productCode", applyVo.getProduct().getProductCode());
		vars.put(ProcessGlobalVariable.WORKFLOW_OWNER, sysAccount.getAccountId());
		runWorkflowService.startProcess("gcsq", businessKey, vars);
	}

//	public List<Apply> selectByMap(HashMap condition,List<Apply> applyList) {
//		// TODO Auto-generated method stub
//		 List<Apply> applyList2 = new ArrayList<Apply>();
//		 return applyList2;
//	}
}
