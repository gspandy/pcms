package com.pujjr.push.service.impl;

import java.util.ArrayList;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.pujjr.push.domain.SysPush;
import com.pujjr.push.service.IPushWeiXinService;

@Service
public class PushWeiXinServiceImpl implements IPushWeiXinService {

	@Override
	public void weiXinToAllUser(String message, Object obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void weiXinToUser(SysPush sysPush) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void weiXinToUsers(ArrayList<SysPush> sysPushList, Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(SysPush sysPush, Object obj) {
		// TODO Auto-generated method stub
		
	}

}
