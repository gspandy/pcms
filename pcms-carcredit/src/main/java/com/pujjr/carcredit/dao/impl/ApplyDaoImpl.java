package com.pujjr.carcredit.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pujjr.base.dao.CarStyleMapper;
import com.pujjr.base.dao.GpsLvlMapper;
import com.pujjr.base.dao.ProductMapper;
import com.pujjr.base.dao.SysAccountMapper;
import com.pujjr.base.service.ISequenceService;
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
import com.pujjr.carcredit.domain.ApplyTenantCar;
import com.pujjr.carcredit.domain.ApplyTenantHouse;
import com.pujjr.carcredit.vo.ApplyCloesseeVo;
import com.pujjr.carcredit.vo.ApplyFamilyDebtVo;
import com.pujjr.carcredit.vo.ApplyFinanceVo;
import com.pujjr.carcredit.vo.ApplyLinkmanVo;
import com.pujjr.carcredit.vo.ApplySpouseVo;
import com.pujjr.carcredit.vo.ApplyTenantVo;

/**
 * 订单dao实现类
 * @author pujjr
 *
 */
@Repository
public class ApplyDaoImpl implements IApplyDao {
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
	@Override
	@ApplyOperHisAnnotation
	public void insertApply(Apply apply, String accountId) {
		// TODO Auto-generated method stub
		applyMapper.insert(apply);
	}
	@Override
	@ApplyOperHisAnnotation
	public void updApply(Apply apply, String accountId) {
		// TODO Auto-generated method stub
//		applyMapper.updateByAppidSelective(apply);
		applyMapper.updateByPrimaryKeySelective(apply);
	}
	@Override
	@ApplyOperHisAnnotation
	public void insertApplyFinance(ApplyFinanceVo afv, String accountId) {
		// TODO Auto-generated method stub
		applyFinanceMapper.insert(afv);
	}
	@Override
	@ApplyOperHisAnnotation
	public void updApplyFinance(ApplyFinanceVo afv, String accountId) {
		// TODO Auto-generated method stub
		applyFinanceMapper.updateByPrimaryKeySelective(afv);
	}
	@Override
	public void insertApplySpouse(ApplySpouseVo asv, String accountId) {
		// TODO Auto-generated method stub
		applySpouseMapper.insert(asv);
	}
	@Override
	@ApplyOperHisAnnotation
	public void updApplySpouse(ApplySpouseVo asv, String accountId) {
		// TODO Auto-generated method stub
		applySpouseMapper.updateByPrimaryKeySelective(asv);
	}
	@Override
	@ApplyOperHisAnnotation
	public void insertApplyCloessee(ApplyCloesseeVo acv, String accountId) {
		// TODO Auto-generated method stub
		applyCloesseeMapper.insert(acv);
	}
	@Override
	@ApplyOperHisAnnotation
	public void updApplyCloessee(ApplyCloesseeVo acv, String accountId) {
		// TODO Auto-generated method stub
		applyCloesseeMapper.updateByPrimaryKeySelective(acv);
	}
	@Override
	@ApplyOperHisAnnotation
	public void insertApplyLinkman(ApplyLinkmanVo alv, String accountId) {
		// TODO Auto-generated method stub
		applyLinkmanMapper.insert(alv);
	}
	@Override
	@ApplyOperHisAnnotation
	public void updApplyLinkman(ApplyLinkmanVo alv, String accountId) {
		// TODO Auto-generated method stub
		applyLinkmanMapper.updateByPrimaryKeySelective(alv);
	}
	@Override
	@ApplyOperHisAnnotation
	public void insertApplyFamilyDebt(ApplyFamilyDebtVo afdv, String account) {
		// TODO Auto-generated method stub
		applyFamilyDebtMapper.insert(afdv);
	}
	@Override
	@ApplyOperHisAnnotation
	public void updApplyFamilyDebt(ApplyFamilyDebtVo afdv, String accountId) {
		// TODO Auto-generated method stub
		applyFamilyDebtMapper.updateByPrimaryKeySelective(afdv);
	}
	@Override
	@ApplyOperHisAnnotation
	public void insertApplyTenant(ApplyTenantVo atv, String accountId) {
		// TODO Auto-generated method stub
		applyTenantMapper.insert(atv);
	}
	@Override
	@ApplyOperHisAnnotation
	public void updApplyTenant(ApplyTenantVo atv, String accountId) {
		// TODO Auto-generated method stub
		applyTenantMapper.updateByPrimaryKey(atv);
	}
	@Override
	@ApplyOperHisAnnotation
	public void insertApplyTenantHouse(ApplyTenantHouse telantHouse, String accountId) {
		// TODO Auto-generated method stub
		applyTenantHouseMapper.insert(telantHouse);
	}
	@Override
	@ApplyOperHisAnnotation
	public void updApplyTenantHouse(ApplyTenantHouse telantHouse, String accountId) {
		// TODO Auto-generated method stub
		applyTenantHouseMapper.updateByPrimaryKeySelective(telantHouse);
	}
	@Override
	@ApplyOperHisAnnotation
	public void insertApplyTenantCar(ApplyTenantCar telantCar, String accountId) {
		// TODO Auto-generated method stub
		applyTenantCarMapper.insert(telantCar);	
	}
	@Override
	@ApplyOperHisAnnotation
	public void updApplyTenantCar(ApplyTenantCar telantCar, String accountId) {
		// TODO Auto-generated method stub
		applyTenantCarMapper.updateByPrimaryKeySelective(telantCar);
	}
}
