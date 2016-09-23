package com.pujjr.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanUtils implements ApplicationContextAware {

	private static ApplicationContext ctx;
	
	public void setApplicationContext(ApplicationContext applicationcontext) throws BeansException {
		// TODO Auto-generated method stub
			ctx = applicationcontext;
	}
	/**
     * 通过spring配置文件中配置的bean id取得bean对象
     * @param id spring bean ID值
     * @return spring bean对象
     */
	
	public static Object getBean(String beanId)
	{
		 if (ctx == null) 
		 {
	          throw new NullPointerException("ApplicationContext is null");
	     }
	     return ctx.getBean(beanId);
	}

}
