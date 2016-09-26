package com.pujjr.web;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.service.ISysAccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:conf/spring*.xml"})
public class TestSysAccount 
{
	@Autowired
	private ISysAccountService sysAccountService;
	
	@Test
	public void testGetOnlineSysAccountByWorkgroupId()
	{
		List<SysAccount> list = sysAccountService.getOnlineSysAccountByWorkgroupId("41757f87fe683ec1");
		assert(list.size()>0);
	}
}
