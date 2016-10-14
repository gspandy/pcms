package com.pujjr.push.dao;

import java.util.List;

import com.pujjr.push.domain.SysPush;

public interface IPushDao {
	public List<SysPush> selectWillPush();
	public int updateByPrimaryKey(SysPush record);
}
