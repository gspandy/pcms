package com.pujjr.push.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import com.alibaba.fastjson.JSONObject;
import com.pujjr.push.dao.SysPushMapper;
import com.pujjr.push.domain.SysPush;
import com.pujjr.push.enumeration.EChannelFlag;
import com.pujjr.push.enumeration.ESendFlag;
import com.pujjr.push.service.IPushHandler;
import com.pujjr.utils.Utils;
/**
 * 消息发送外部接口2016-10-10
 * @author pujjr
 *
 */
@Service
public class PushHandlerImpl implements IPushHandler {
	@Autowired
	public SysPushMapper sysPushMapper;
	
	public void insertSysPush(SysPush sysPush){
		sysPushMapper.insertSelective(sysPush);
	}
	
	@Override
	public void instationToUser(String accountId, String message, Object obj) {
		// TODO Auto-generated method stub
		SysPush sysPush = new SysPush();
		sysPush.setId(Utils.get16UUID());
		sysPush.setTarget(accountId);
		sysPush.setMessage(message);
		sysPush.setParameter(JSONObject.toJSONString(obj));
		sysPush.setChannelFlag(EChannelFlag.INSTATION.getCode());
		sysPush.setSendFlag(ESendFlag.WILL_SEND.getCode());
		sysPush.setCreateTime(Calendar.getInstance().getTime());
		this.insertSysPush(sysPush);
	}

	@Override
	public void instationToUsers(ArrayList<Map<String, TextMessage>> userMessageList, Object obj) {
		// TODO Auto-generated method stub
	}

	@Override
	public void instationToAllUser(TextMessage message, Object obj) {
		// TODO Auto-generated method stub
	}

	@Override
	public void messageToUser(String accountId, String message, Object obj) {
		// TODO Auto-generated method stub
		SysPush sysPush = new SysPush();
		sysPush.setId(Utils.get16UUID());
		sysPush.setTarget(accountId);
		sysPush.setMessage(message);
		sysPush.setParameter(JSONObject.toJSONString(obj));
		sysPush.setChannelFlag(EChannelFlag.MESSAGE.getCode());
		sysPush.setSendFlag(ESendFlag.WILL_SEND.getCode());
		sysPush.setCreateTime(Calendar.getInstance().getTime());
		this.insertSysPush(sysPush);
	}

	@Override
	public void messageToUsers(ArrayList<Map<String, TextMessage>> userMessageList, Object obj) {
		// TODO Auto-generated method stub
	}

	@Override
	public void messageToAllUser(TextMessage message, Object obj) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mailToUser(String accountId, TextMessage message, Object obj) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mailToUsers(ArrayList<Map<String, TextMessage>> userMessageList, Object obj) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mailToAllUser(TextMessage message, Object obj) {
		// TODO Auto-generated method stub
	}

	@Override
	public void weiXinToUser(String accountId, TextMessage message, Object obj) {
		// TODO Auto-generated method stub
	}

	@Override
	public void weiXinToUsers(ArrayList<Map<String, TextMessage>> userMessageList, Object obj) {
		// TODO Auto-generated method stub
	}

	@Override
	public void weiXinToAllUser(TextMessage message, Object obj) {
		// TODO Auto-generated method stub
	}

}
