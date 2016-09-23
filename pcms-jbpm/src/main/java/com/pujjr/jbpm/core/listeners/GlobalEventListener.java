package com.pujjr.jbpm.core.listeners;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;

import com.pujjr.utils.SpringBeanUtils;

/**
 * 流程运行全局事件监控
 * **/
public class GlobalEventListener implements ActivitiEventListener {

	private Map<String,String> handlers=new HashMap<String, String>();
	
	public void onEvent(ActivitiEvent event) {
		// TODO Auto-generated method stub
		String eventType = event.getType().name();
		System.out.println(eventType);
		// 根据事件的类型ID,找到对应的事件处理器
		String eventHandlerBeanId = handlers.get(eventType);
		if (eventHandlerBeanId != null) 
		{
			EventHandler handler = (EventHandler) SpringBeanUtils.getBean(eventHandlerBeanId);
			handler.handle(event);
		}
	}

	public boolean isFailOnException() {
		// TODO Auto-generated method stub
		return false;
	}

	public Map<String, String> getHandlers() {
		return handlers;
	}

	public void setHandlers(Map<String, String> handlers) {
		this.handlers = handlers;
	}

	
}
