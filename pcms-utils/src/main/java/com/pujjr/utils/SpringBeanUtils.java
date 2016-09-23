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
     * ͨ��spring�����ļ������õ�bean idȡ��bean����
     * @param id spring bean IDֵ
     * @return spring bean����
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
