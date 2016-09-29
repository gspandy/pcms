package com.pujjr.carcredit.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pujjr.carcredit.dao.HisBeanMapMapper;
import com.pujjr.carcredit.domain.HisBeanMap;
import com.pujjr.carcredit.service.ITableInitService;
import com.pujjr.utils.Utils;

@Transactional
@Service
public class TableInitServiceImpl implements ITableInitService {
	@Autowired
	private HisBeanMapMapper hisBeanMapMapper;
	@Override
	public void hisBeanMapInit() {
		// TODO Auto-generated method stub
//		ArrayList<HashMap<String,String>> al = new ArrayList<HashMap<String,String>>();
		String[][] table = new String[][] {
				{ "com.pujjr.carcredit.domain.HisBeanMap", "对象映射表实体", "T_HIS_BEANMAP", "对象映射表" }, 
				{ "com.pujjr.carcredit.domain.Apply", "申请信息实体", "T_APPLY", "申请信息" },
				{ "com.pujjr.carcredit.domain.ApplyCloessee", "共租人信息实体", "T_APPLY_COLESSEE", "共租人信息" }, 
				{ "com.pujjr.carcredit.domain.ApplyFamilyDebt", "家庭负债信息实体", "T_APPLY_FAMILY_DEBT", "家庭负债信息" }, 
				{ "com.pujjr.carcredit.domain.ApplyFinance", "融资信息实体", "T_APPLY_FINANCE", "融资信息" }, 
				{ "com.pujjr.carcredit.domain.ApplyLinkman", "联系人信息实体", "T_APPLY_LINKMAN", "联系人信息" }, 
				{ "com.pujjr.carcredit.domain.ApplySpouse", "配偶信息实体", "T_APPLY_SPOUSE", "配偶信息" }, 
				{ "com.pujjr.carcredit.domain.ApplyTenant", "承租人信息实体", "T_APPLY_TENANT", "承租人信息" }, 
				{ "com.pujjr.carcredit.domain.ApplyTenantCar", "承租人车产信息实体", "T_APPLY_TENANT_CAR", "承租人车产信息" }, 
				{ "com.pujjr.carcredit.domain.ApplyTenantHouse", "承租人房产信息实体", "T_APPLY_TENANT_HOUSE", "承租人房产信息" }, 
				{ "com.pujjr.carcredit.domain.CheckResult", "", "", "" }, 
				{ "com.pujjr.carcredit.domain.HisFieldComment", "数据表字段注释表实体", "T_HIS_FIELDCOMMENT", "数据表字段注释表" }, 
				{ "com.pujjr.carcredit.domain.HisOper", "历史操作记录表实体", "T_HIS_OPER", "历史操作记录表" }, 
				{ "com.pujjr.carcredit.domain.RejectRecommit", "", "", "" }, 
				{ "com.pujjr.carcredit.domain.TaskProcessResult", "", "", "" },
				{"com.pujjr.carcredit.vo.ApplyApproveVo","","",""},
				{"com.pujjr.carcredit.vo.ApplyCheckVo","","",""},
				{"com.pujjr.carcredit.vo.ApplyCloesseeVo","共租人信息实体","T_APPLY_COLESSEE","共租人信息"},
				{"com.pujjr.carcredit.vo.ApplyFamilyDebtVo","家庭负债信息实体","T_APPLY_FAMILY_DEBT","家庭负债信息"},
				{"com.pujjr.carcredit.vo.ApplyFinanceVo","融资信息实体","T_APPLY_FINANCE","融资信息"},
				{"com.pujjr.carcredit.vo.ApplyLinkmanVo","联系人信息实体","T_APPLY_LINKMAN","联系人信息"},
				{"com.pujjr.carcredit.vo.ApplySpouseVo","配偶信息实体","T_APPLY_SPOUSE","配偶信息"},
				{"com.pujjr.carcredit.vo.ApplyStatus","","",""},
				{"com.pujjr.carcredit.vo.ApplyTenantVo","承租人信息实体","T_APPLY_TENANT","承租人信息"},
				{"com.pujjr.carcredit.vo.ApplyUncommitVo","","",""},
				{"com.pujjr.carcredit.vo.ApplyVo","对象映射表实体","T_APPLY","对象映射表"}};
		hisBeanMapMapper.deleteAll();
		for (int i = 0; i < table.length; i++) {
			String[] rowRecord = table[i];
			HisBeanMap hisBeanMap = new HisBeanMap();
			hisBeanMap.setId(Utils.get16UUID());
//			for (int j = 0; j < rowRecord.length; j++) {
//				System.out.println(rowRecord[j]);
//			}
			hisBeanMap.setClassName(rowRecord[0]);
			hisBeanMap.setClassCname(rowRecord[1]);
			hisBeanMap.setTableName(rowRecord[2]);
			hisBeanMap.setTableCname(rowRecord[3]);
			hisBeanMapMapper.insert(hisBeanMap);
		}
	}

	@Override
	public void hisFieldCommentInit() {
		// TODO Auto-generated method stub
	}

}
