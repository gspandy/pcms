/**
 * 测试controller
 */
package com.pujjr.carcredit.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.RepositoryServiceImpl;
//import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.pujjr.base.controller.BaseController;
import com.pujjr.carcredit.service.ITestService;
import com.pujjr.carcredit.service.impl.TestServiceImpl;
import com.pujjr.utils.Base64;
import com.pujjr.utils.Utils;

@Controller
public class TestController extends BaseController {
	@Autowired
	private ITestService coreServiceImpl;
	/**
	 * rollback测试
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/coreRoleBackTest2.ctrl")
	public String coreRoleBackTest(){
		System.out.println("CoreController2->coreRoleBackTest  coreServiceImpl:"+coreServiceImpl);
		coreServiceImpl.rollback_test();
		return "rollback_test";
	}
	@ResponseBody
	@RequestMapping(value="/uploadFile")
	public void uploadFile(@Param("file")MultipartFile file) throws IOException{
		System.out.println("CoreController2->coreRoleBackTest  coreServiceImpl:"+coreServiceImpl);
		String filePath="d:\\file\\"+file.getOriginalFilename();
		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath));
	}
	
	@RequestMapping(value="/img/{fileName}", method=RequestMethod.GET)
	public void getWorkflowImg(@PathVariable String fileName,
								HttpServletRequest request,
								HttpServletResponse response) throws IOException
	{
		String filePath="d:\\file\\"+Utils.convertStr2Utf8(fileName+".jpg");
		byte[] imgBytes =  FileUtils.readFileToByteArray(new File(filePath));
		InputStream imgStream = new ByteArrayInputStream(imgBytes);
		OutputStream stream = response.getOutputStream();
		int len = 0;
	    byte[] b = new byte[1024];
	    while ((len = imgStream.read(b, 0, 1024)) != -1) 
	    {
	        stream.write(b, 0, len);
	    }
	    stream.flush();
        stream.close();
		
	}
}
