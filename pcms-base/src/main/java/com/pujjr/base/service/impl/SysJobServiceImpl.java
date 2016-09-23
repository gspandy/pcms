package com.pujjr.base.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.dao.SysJobMapper;
import com.pujjr.base.domain.SysJob;
import com.pujjr.base.service.ISysJobService;
import com.pujjr.utils.Utils;
@Service
public class SysJobServiceImpl implements ISysJobService {
	
	@Autowired
	private SysJobMapper sysJobDao;

	public List<SysJob> getSysJobList() {
		// TODO Auto-generated method stub
		return sysJobDao.selectAll();
	}

	public void addSysJob(SysJob record) {
		// TODO Auto-generated method stub
		record.setId(Utils.get16UUID());
		record.setCreateTime(new Date());
		record.setCreateId("admin");
		sysJobDao.insert(record);

	}

	public void deleteSysJobById(String id) {
		// TODO Auto-generated method stub
		sysJobDao.deleteByPrimaryKey(id);
	}

	public void modifySysJob(SysJob record) {
		// TODO Auto-generated method stub
		sysJobDao.updateByPrimaryKey(record);
	}

}
