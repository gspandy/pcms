package com.pujjr.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pujjr.base.vo.ResponseVo;


@Controller
public class BaseController 
{
	@ExceptionHandler
	@ResponseBody
	public ResponseVo handlerException(Exception e)
	{
		e.printStackTrace();
		ResponseVo response=new ResponseVo();
		response.setSuccessResponse(false);
		response.setMessage(e.getMessage());
		return response;
	}
	
	/**
	 * ��װController��JSON���ؽ��ΪResponseVo
	 * **/
	public ResponseVo wrapperJson(Object obj)
	{
		ResponseVo response=new ResponseVo();
		response.setSuccessResponse(true);
		response.setMessage("���׳ɹ�");
		response.setData(obj);
		return response;
	}

}
