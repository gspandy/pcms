package com.pujjr.push.service;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;

import com.pujjr.push.domain.SysPush;

public interface IPushService{
	public List<SysPush> selectWillPush();
	public void instationToUser(SysPush sysPush);
	public int updateSysPush(SysPush sysPush);
	public void messageToUser(SysPush sysPush);
	public void mailToUser(SysPush sysPush);
	public void weixinToUser(SysPush sysPush);
}
