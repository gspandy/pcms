package com.pujjr.core.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.pujjr.base.domain.SysAccount;
import com.pujjr.base.service.ISysAccountService;


public class LoginCheckFilter implements Filter {
	
	@Autowired
	private ISysAccountService sysAccountService;
	
//	@Value("${pcms.sys_mode}")
//	private String sysModel;

	public void destroy() {
		// TODO Auto-generated method stub
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request; 
	    HttpServletResponse res = (HttpServletResponse) response; 
	    HttpSession session = req.getSession(true);
	    
	    if(req.getMethod().equalsIgnoreCase("OPTIONS"))
	    {
	    	res.setStatus(200);
	    }
	    else
	    {
	    	final String authHeader = req.getHeader("Authorization");
	        try 
	        {
	        	final Claims claims = Jwts.parser().setSigningKey("secretkey")
    	                .parseClaimsJws(authHeader).getBody();
	            String userid=claims.getSubject();
	            ServletContext sc = req.getSession().getServletContext();
	            XmlWebApplicationContext cxt = (XmlWebApplicationContext)WebApplicationContextUtils.getWebApplicationContext(sc);
	            ISysAccountService sysAccountService = (ISysAccountService)cxt.getBean(ISysAccountService.class);
	            SysAccount sysAccount = sysAccountService.getSysAccountByAccountId(userid);
	            request.setAttribute("account", sysAccount);
	        	filterChain.doFilter(request,response); 
	        }
	        catch (SignatureException  e) 
	        {
	            res.setStatus(401);
	            return;
	        }
	        
	       
	    }
	    
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
