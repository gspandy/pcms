package com.pujjr.assetsmanage.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.assetsmanage.dao.AlterBankInfoHisMapper;
import com.pujjr.assetsmanage.dao.AlterCustInfoDetailMapper;
import com.pujjr.assetsmanage.dao.AlterCustInfoLogMapper;
import com.pujjr.assetsmanage.domain.AlterBankInfoHis;
import com.pujjr.assetsmanage.domain.AlterCustInfoDetail;
import com.pujjr.assetsmanage.domain.AlterCustInfoLog;
import com.pujjr.assetsmanage.enumeration.AlterCustInfoType;
import com.pujjr.assetsmanage.service.IAlterCustInfoService;
import com.pujjr.assetsmanage.vo.AlterBankInfoVo;
import com.pujjr.assetsmanage.vo.AlterColesseeVo;
import com.pujjr.assetsmanage.vo.AlterLinkmanVo;
import com.pujjr.assetsmanage.vo.AlterTenantVo;
import com.pujjr.base.domain.BankInfo;
import com.pujjr.base.service.IBankService;
import com.pujjr.base.service.ISysAreaService;
import com.pujjr.base.service.ISysDictService;
import com.pujjr.carcredit.domain.SignContract;
import com.pujjr.carcredit.service.IApplyService;
import com.pujjr.carcredit.service.ISignContractService;
import com.pujjr.carcredit.vo.ApplyLinkmanVo;
import com.pujjr.carcredit.vo.ApplyVo;
import com.pujjr.utils.Utils;
@Service
@Transactional
public class AlterCustInfoServiceImpl implements IAlterCustInfoService {

	@Autowired
	private IApplyService applyService;
	@Autowired
	private AlterCustInfoDetailMapper alterDetailDao;
	@Autowired
	private AlterCustInfoLogMapper alterLogDao;
	@Autowired
	private ISysAreaService sysAreaService;
	@Autowired
	private ISysDictService sysDictService;
	@Autowired
	private ISignContractService signContractService;
	@Autowired
	private IBankService bankService;
	@Autowired
	private AlterBankInfoHisMapper alterBankInfoHisDao;
	@Override
	public void alterTenantInfo(AlterTenantVo vo, String operId) {
		// TODO Auto-generated method stub
		ApplyVo applyVo = applyService.getApplyDetail(vo.getAppId());
		
		//保存变更记录
		AlterCustInfoLog log = new AlterCustInfoLog();
		String logId = Utils.get16UUID();
		log.setId(logId);
		log.setAlterTime(new Date());
		log.setAlterType(AlterCustInfoType.Tenant.getName());
		log.setAppId(vo.getAppId());
		log.setOperId(operId);
		log.setAlterComment(vo.getAlterComment());
		alterLogDao.insert(log);
		
		
		//判断承租人电话号码1是否变更
		if(StringUtils.equals(applyVo.getTenant().getMobile(), vo.getMobile1())==false)
		{
			AlterCustInfoDetail dtlPo = new AlterCustInfoDetail();
			dtlPo.setId(Utils.get16UUID());
			dtlPo.setLogId(logId);
			dtlPo.setAlterField("承租人电话号码1");
			dtlPo.setAlterBeforeValue(applyVo.tenant.getMobile());
			dtlPo.setAlterAfterValue(vo.getMobile1());
			alterDetailDao.insert(dtlPo);
		}
		applyVo.tenant.setMobile(vo.getMobile1());
		
		//判断承租人电话号码2是否变更
		if(StringUtils.equals(applyVo.getTenant().getMobile2(), vo.getMobile2())==false)
		{
			AlterCustInfoDetail dtlPo = new AlterCustInfoDetail();
			dtlPo.setId(Utils.get16UUID());
			dtlPo.setLogId(logId);
			dtlPo.setAlterField("承租人电话号码2");
			dtlPo.setAlterBeforeValue(applyVo.getTenant().getMobile2());
			dtlPo.setAlterAfterValue(vo.getMobile2());
			alterDetailDao.insert(dtlPo);
		}
		applyVo.tenant.setMobile2(vo.getMobile2());
		
		//判断承租人QQ是否变更
		if(StringUtils.equals(applyVo.getTenant().getQq(), vo.getQq())==false)
		{
			AlterCustInfoDetail dtlPo = new AlterCustInfoDetail();
			dtlPo.setId(Utils.get16UUID());
			dtlPo.setLogId(logId);
			dtlPo.setAlterField("承租人QQ号");
			dtlPo.setAlterBeforeValue(applyVo.getTenant().getQq());
			dtlPo.setAlterAfterValue(vo.getQq());
			alterDetailDao.insert(dtlPo);
		}
		applyVo.tenant.setQq(vo.getQq());
		
		//判断承租人微信号码是否变更
		if(StringUtils.equals(applyVo.getTenant().getWeixin(), vo.getWeixin())==false)
		{
			AlterCustInfoDetail dtlPo = new AlterCustInfoDetail();
			dtlPo.setId(Utils.get16UUID());
			dtlPo.setLogId(logId);
			dtlPo.setAlterField("承租人微信号");
			dtlPo.setAlterBeforeValue(applyVo.getTenant().getWeixin());
			dtlPo.setAlterAfterValue(vo.getWeixin());
			alterDetailDao.insert(dtlPo);
		}
		applyVo.tenant.setWeixin(vo.getWeixin());
		
		//判断承租人住权所属是否变更
		if(StringUtils.equals(applyVo.getTenant().getHouseOwner(), vo.getHouseOwner())==false)
		{
			String oldVal = sysDictService.getDictDataByDictDateCode(applyVo.getTenant().getHouseOwner()).getDictDataName();
			String newVal = sysDictService.getDictDataByDictDateCode(vo.getHouseOwner()).getDictDataName();
			AlterCustInfoDetail dtlPo = new AlterCustInfoDetail();
			dtlPo.setId(Utils.get16UUID());
			dtlPo.setLogId(logId);
			dtlPo.setAlterField("承租人住权所属");
			dtlPo.setAlterBeforeValue(oldVal);
			dtlPo.setAlterAfterValue(newVal);
			alterDetailDao.insert(dtlPo);
		}
		applyVo.tenant.setHouseOwner(vo.getHouseOwner());
		
		//判断地址是否变更
		if(
				(StringUtils.equals(applyVo.getTenant().getAddrProvince(), vo.getAddrProvince())==false)||
				(StringUtils.equals(applyVo.getTenant().getAddrCity(), vo.getAddrCity())==false)||
				(StringUtils.equals(applyVo.getTenant().getAddrCounty(), vo.getAddrCounty())==false)||
				(StringUtils.equals(applyVo.getTenant().getAddrExt(), vo.getAddrExt())==false))
		{
			String oldAddr = sysAreaService.getFullAddress(applyVo.tenant.getAddrProvince(), applyVo.tenant.getAddrCity(), applyVo.tenant.getAddrCounty())+applyVo.tenant.getAddrExt();
			String newAddr = sysAreaService.getFullAddress(vo.getAddrProvince(), vo.getAddrCity(), vo.getAddrCounty())+vo.getAddrExt();
			AlterCustInfoDetail dtlPo = new AlterCustInfoDetail();
			dtlPo.setId(Utils.get16UUID());
			dtlPo.setLogId(logId);
			dtlPo.setAlterField("承租人地址");
			dtlPo.setAlterBeforeValue(oldAddr);
			dtlPo.setAlterAfterValue(newAddr);
			alterDetailDao.insert(dtlPo);
		}
		applyVo.tenant.setAddrProvince(vo.getAddrProvince());
		applyVo.tenant.setAddrCity(vo.getAddrCity());
		applyVo.tenant.setAddrCounty(vo.getAddrCounty());
		applyVo.tenant.setAddrExt(vo.getAddrExt());
		//保存变更信息
		applyService.saveApplyTenant(applyVo, operId);
		
	}

	@Override
	public void alterColesseeInfo(AlterColesseeVo vo, String operId) {
		// TODO Auto-generated method stub
		ApplyVo applyVo = applyService.getApplyDetail(vo.getAppId());
		
		//保存变更记录
		AlterCustInfoLog log = new AlterCustInfoLog();
		String logId = Utils.get16UUID();
		log.setId(logId);
		log.setAlterTime(new Date());
		log.setAlterType(AlterCustInfoType.Colessee.getName());
		log.setAppId(vo.getAppId());
		log.setOperId(operId);
		log.setAlterComment(vo.getAlterComment());
		alterLogDao.insert(log);
		
		
		//判断共租人电话号码1是否变更
		if(StringUtils.equals(applyVo.getCloessee().getMobile(), vo.getMobile())==false)
		{
			AlterCustInfoDetail dtlPo = new AlterCustInfoDetail();
			dtlPo.setId(Utils.get16UUID());
			dtlPo.setLogId(logId);
			dtlPo.setAlterField("共租人电话号码");
			dtlPo.setAlterBeforeValue(applyVo.getCloessee().getMobile());
			dtlPo.setAlterAfterValue(vo.getMobile());
			alterDetailDao.insert(dtlPo);
		}
		applyVo.cloessee.setMobile(vo.getMobile());
		
		//判断共租人QQ是否变更
		if(StringUtils.equals(applyVo.getCloessee().getQq(), vo.getQq())==false)
		{
			AlterCustInfoDetail dtlPo = new AlterCustInfoDetail();
			dtlPo.setId(Utils.get16UUID());
			dtlPo.setLogId(logId);
			dtlPo.setAlterField("共租人QQ号");
			dtlPo.setAlterBeforeValue(applyVo.getCloessee().getQq());
			dtlPo.setAlterAfterValue(vo.getQq());
			alterDetailDao.insert(dtlPo);
		}
		applyVo.cloessee.setMobile(vo.getMobile());
		
		//判断共租人微信号是否变更
		if(StringUtils.equals(applyVo.getCloessee().getWeixin(), vo.getWeixin())==false)
		{
			AlterCustInfoDetail dtlPo = new AlterCustInfoDetail();
			dtlPo.setId(Utils.get16UUID());
			dtlPo.setLogId(logId);
			dtlPo.setAlterField("共租人微信号");
			dtlPo.setAlterBeforeValue(applyVo.getCloessee().getWeixin());
			dtlPo.setAlterAfterValue(vo.getWeixin());
			alterDetailDao.insert(dtlPo);
		}
		applyVo.cloessee.setWeixin(vo.getWeixin());
		
		applyService.saveApplyCloessee(applyVo, operId);
	}

	@Override
	public void alterLinkmanInfo(AlterLinkmanVo vo, String operId) {
		// TODO Auto-generated method stub
		ApplyVo applyVo = applyService.getApplyDetail(vo.getAppId());
		
		//保存变更记录
		AlterCustInfoLog log = new AlterCustInfoLog();
		String logId = Utils.get16UUID();
		log.setId(logId);
		log.setAlterTime(new Date());
		log.setAlterType(AlterCustInfoType.Linkman.getName());
		log.setAppId(vo.getAppId());
		log.setOperId(operId);
		log.setAlterComment(vo.getAlterComment());
		alterLogDao.insert(log);
		 
		//判断联系人信息是否变更
		for(ApplyLinkmanVo item : vo.getLinkmans())
		{
			for(ApplyLinkmanVo oldItem : applyVo.getLinkmans())
			{
				if(oldItem.getSeq() == item.getSeq())
				{
					//判断姓名是否变更
					if(StringUtils.equals(item.getName(), oldItem.getName())==false)
					{
						AlterCustInfoDetail dtlPo = new AlterCustInfoDetail();
						dtlPo.setId(Utils.get16UUID());
						dtlPo.setLogId(logId);
						dtlPo.setAlterField("联系人"+item.getSeq()+"姓名");
						dtlPo.setAlterBeforeValue(oldItem.getName());
						dtlPo.setAlterAfterValue(item.getName());
						alterDetailDao.insert(dtlPo);
					}
					//判断姓名是否变更
					if(StringUtils.equals(item.getMobile(), oldItem.getMobile())==false)
					{
						AlterCustInfoDetail dtlPo = new AlterCustInfoDetail();
						dtlPo.setId(Utils.get16UUID());
						dtlPo.setLogId(logId);
						dtlPo.setAlterField("联系人"+item.getSeq()+"电话");
						dtlPo.setAlterBeforeValue(oldItem.getMobile());
						dtlPo.setAlterAfterValue(item.getMobile());
						alterDetailDao.insert(dtlPo);
					}
					//判断关系是否变更
					if(StringUtils.equals(item.getTenantRelation(), oldItem.getTenantRelation())==false)
					{
						AlterCustInfoDetail dtlPo = new AlterCustInfoDetail();
						dtlPo.setId(Utils.get16UUID());
						dtlPo.setLogId(logId);
						dtlPo.setAlterField("联系人"+item.getSeq()+"关系");
						String oldVal = sysDictService.getDictDataByDictDateCode(oldItem.getTenantRelation()).getDictDataName();
						String newVal = sysDictService.getDictDataByDictDateCode(item.getTenantRelation()).getDictDataName();
						dtlPo.setAlterBeforeValue(oldVal);
						dtlPo.setAlterAfterValue(newVal);
						alterDetailDao.insert(dtlPo);
					}
				}
			}
		}
		//更新申请信息
		applyVo.setLinkmans(vo.getLinkmans());
		applyService.saveApplyLinkman(applyVo, operId);
	}

	@Override
	public void alterBankInfo(AlterBankInfoVo vo, String operId) {
		// TODO Auto-generated method stub
		SignContract signVo = signContractService.getSignContractByAppId(vo.getAppId());
		
		//保存变更记录
		AlterCustInfoLog log = new AlterCustInfoLog();
		String logId = Utils.get16UUID();
		log.setId(logId);
		log.setAlterTime(new Date());
		log.setAlterType(AlterCustInfoType.BankInfo.getName());
		log.setAppId(vo.getAppId());
		log.setOperId(operId);
		log.setAlterComment(vo.getAlterComment());
		alterLogDao.insert(log);
		
		//判断银行卡号是否变更
		if(StringUtils.equals(signVo.getRepayAcctNo(), vo.getRepayAcctNo())==false)
		{
			AlterCustInfoDetail dtlPo = new AlterCustInfoDetail();
			dtlPo.setId(Utils.get16UUID());
			dtlPo.setLogId(logId);
			dtlPo.setAlterField("还款卡号");
			dtlPo.setAlterBeforeValue(signVo.getRepayAcctNo());
			dtlPo.setAlterAfterValue(vo.getRepayAcctNo());
			alterDetailDao.insert(dtlPo);
			
			AlterCustInfoDetail dtl1Po = new AlterCustInfoDetail();
			dtl1Po.setId(Utils.get16UUID());
			dtl1Po.setLogId(logId);
			dtl1Po.setAlterField("还款银行");
			//获取原还款银行名称
			BankInfo oldBankInfo = bankService.getBankInfoById(signVo.getRepayBankId());
			BankInfo newBankInfo = bankService.getBankInfoById(vo.getRepayBankId());
			dtl1Po.setAlterBeforeValue(oldBankInfo.getBankName());
			dtl1Po.setAlterAfterValue(newBankInfo.getBankName());
			alterDetailDao.insert(dtl1Po);
			
			//如果不一致查询当前银行卡是否已存在，如无保存当前原银行卡历史记录，反之更新历史信息
			
			AlterBankInfoHis his = new AlterBankInfoHis();
			his = alterBankInfoHisDao.selectByAppIdAndAcctNo(vo.getAppId(),vo.getRepayAcctNo());
			if(his != null)
			{
				his.setRepayBankId(vo.getRepayBankId());
				alterBankInfoHisDao.updateByPrimaryKey(his);
			}
			else
			{
				AlterBankInfoHis newHis = new AlterBankInfoHis();
				newHis.setId(Utils.get16UUID());
				newHis.setAppId(vo.getAppId());
				newHis.setRepayAcctNo(signVo.getRepayAcctNo());
				newHis.setRepayBankId(signVo.getRepayBankId());
				alterBankInfoHisDao.insert(newHis);
			}
			
		}
		//变更签约信息
		signVo.setRepayBankId(vo.getRepayBankId());
		signVo.setRepayAcctNo(vo.getRepayAcctNo());
		signContractService.modifySignContract(signVo);
	}

	@Override
	public List<HashMap<String, Object>> getAlterBankInfoHisList(String appId) {
		// TODO Auto-generated method stub
		return alterBankInfoHisDao.selectAlterBankInfoHisList(appId);
	}

}
