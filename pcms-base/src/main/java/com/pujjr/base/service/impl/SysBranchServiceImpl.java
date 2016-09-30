package com.pujjr.base.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.dao.BranchProductMapper;
import com.pujjr.base.dao.GpsLvlMapper;
import com.pujjr.base.dao.ProductMapper;
import com.pujjr.base.dao.SysBranchDealerMapper;
import com.pujjr.base.dao.SysBranchGpsRuleMapper;
import com.pujjr.base.dao.SysBranchMapper;
import com.pujjr.base.domain.BranchProductKey;
import com.pujjr.base.domain.GpsLvl;
import com.pujjr.base.domain.Product;
import com.pujjr.base.domain.SysBranch;
import com.pujjr.base.domain.SysBranchDealer;
import com.pujjr.base.domain.SysBranchGpsRuleKey;
import com.pujjr.base.service.ISysBranchService;
import com.pujjr.base.vo.GpsRuleVo;
import com.pujjr.base.vo.SysBranchVo;
import com.pujjr.utils.Utils;
@Service
public class SysBranchServiceImpl implements ISysBranchService {
	
	@Autowired
	private SysBranchMapper sysBranchDao;
	@Autowired
	private SysBranchDealerMapper sysBranchDealerDao;
	@Autowired
	private GpsLvlMapper gpsLvlDao;
	@Autowired
	private SysBranchGpsRuleMapper sysBranchGpsRuleDao;
	@Autowired
	private ProductMapper productDao;
	@Autowired
	private BranchProductMapper branchProductDao;
	
	public List<SysBranch> getSysBranchList() {
		// TODO Auto-generated method stub
		return sysBranchDao.selectAll();
	}

	public void addSysBranch(SysBranch record) {
		// TODO Auto-generated method stub
		record.setId(Utils.get16UUID());
		record.setCreateTime(new Date());
		record.setCreateId("admin");
		sysBranchDao.insert(record);
	}

	public SysBranch getSysBranch(String id, String branchCode) {
		// TODO Auto-generated method stub
		return  sysBranchDao.selectSysBranch(id,branchCode);
	}

	public void deleteSysBranchById(String id) {
		// TODO Auto-generated method stub
		sysBranchDao.deleteByPrimaryKey(id);
	}

	public void modifySysBranch(SysBranchVo record) {
		// TODO Auto-generated method stub
		SysBranch sysBranch = record.getSysBranch();
		sysBranchDao.updateByPrimaryKey(sysBranch);
		//为经销商时需处理经销商信息
		if(sysBranch.getBranchType().equals("JGLX02"))
		{
			//保存或更新经销商信息
			SysBranchDealer dealer = sysBranchDealerDao.selectByBranchId(sysBranch.getId());
			if(dealer == null)
			{
				dealer = new SysBranchDealer();
				BeanUtils.copyProperties(record.getSysBranchDealer(), dealer);
				dealer.setId(Utils.get16UUID());
				dealer.setBranchId(sysBranch.getId());
				dealer.setCreateId("admin");
				dealer.setCreateTime(new Date());
				sysBranchDealerDao.insert(dealer);
			}
			else
			{
				BeanUtils.copyProperties(record.getSysBranchDealer(), dealer);
				dealer.setUpdateId("admin");
				dealer.setUpdateTime(new Date());
				sysBranchDealerDao.updateByPrimaryKeySelective(dealer);
				
			}
			//插入GPS档位信息
			gpsLvlDao.deleteGpsLvlByBranchId(sysBranch.getId());
			List<GpsRuleVo> gpsRuleVos = record.getGpsRuleVoList();
			for(GpsRuleVo gpsRuleVo : gpsRuleVos)
			{
				List<GpsLvl> gpsLvlVos =gpsRuleVo.getGpsLvls();
				for(GpsLvl gpsLvlVo : gpsLvlVos)
				{
					SysBranchGpsRuleKey key = new SysBranchGpsRuleKey();
					key.setBranchId(sysBranch.getId());
					key.setGpsRuleId(gpsRuleVo.getId());
					key.setGpsLvlId(gpsLvlVo.getId());
					sysBranchGpsRuleDao.insert(key);
				}
			}
			//保存机构产品信息
			productDao.deleteBranchProduct(sysBranch.getId());
			List<Product> productList = record.getProductList();
			for(Product product : productList)
			{
				BranchProductKey key = new BranchProductKey();
				key.setBranchId(sysBranch.getId());
				key.setProductId(product.getId());
				branchProductDao.insert(key);
			}
		}
		
	}

	public SysBranchDealer getDealerByBranchId(String id) {
		// TODO Auto-generated method stub
		return sysBranchDealerDao.selectByBranchId(id);
	}

}
