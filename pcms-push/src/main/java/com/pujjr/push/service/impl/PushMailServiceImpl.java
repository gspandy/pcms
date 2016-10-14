package com.pujjr.push.service.impl;

import java.util.ArrayList;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.pujjr.push.domain.SysPush;
import com.pujjr.push.service.IPushMailService;

@Service
public class PushMailServiceImpl implements IPushMailService {

	@Override
	public void mailToAllUser(String message, Object obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mailToUser(SysPush sysPush) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mailToUsers(ArrayList<SysPush> sysPushList, Object obj) {
		// TODO Auto-generated method stub
		
	}


	public void sendMessage(SysPush sysPush, String email) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(SysPush sysPush, Object obj) {
		// TODO Auto-generated method stub
		
	}

}
