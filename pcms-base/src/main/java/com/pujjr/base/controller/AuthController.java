package com.pujjr.base.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pujjr.base.controller.BaseController;
import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.service.ISysAccountService;
import com.pujjr.base.service.impl.KeyService;
import com.pujjr.base.vo.ResponseVo;


@Controller
public class AuthController extends BaseController 
{
	
	@Autowired
	private ISysAccountService sysAccountService;
	@Autowired
	private KeyService keyService;
	@RequestMapping(value="/auth",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVo auth(@RequestBody SysAccount account,HttpServletResponse response,HttpSession session) throws Exception
	{
		SysAccount sysAccount=sysAccountService.getSysAccountByAccountId(account.getAccountId());
		
		if(sysAccount!=null)
		{
			if(!keyService.verifyPasswd(sysAccount.getAccountId(), account.getPassword(), sysAccount.getPassword()))
			{
				throw new Exception("用户名或密码错误");
			}
			String token=Jwts.builder().setSubject(account.getAccountId())
		            .claim("roles", account.getAccountId()).setIssuedAt(new Date())
		            .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
			HashMap<String,Object> map=new HashMap<String,Object>();
			map.put("Authorization", token);
			map.put("account", sysAccount);
			return this.wrapperJson(map);
		}
		else
		{
			throw new Exception("用户名或密码错误");
		}
		
	}
	
}
