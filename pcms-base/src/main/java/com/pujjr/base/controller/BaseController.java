package com.pujjr.base.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pujjr.base.vo.ResponseVo;
import com.pujjr.utils.FormCustomDateEditor;


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
	
	public ResponseVo wrapperJson(Object obj)
	{
		ResponseVo response=new ResponseVo();
		response.setSuccessResponse(true);
		response.setData(obj);
		return response;
	}
	/**表单提交日期格式转换为本地时区**/
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z"); //yyyy-MM-dd'T'HH:mm:ssZ example
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new FormCustomDateEditor(dateFormat, false));
    }

}
