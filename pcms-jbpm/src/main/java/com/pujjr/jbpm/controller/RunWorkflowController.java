package com.pujjr.jbpm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.jbpm.core.command.StartProcessCommand;
import com.pujjr.jbpm.service.IRunWorkflowService;

@RestController
public class RunWorkflowController 
{
	@Autowired
	private IRunWorkflowService runWorkflowService;
	
	@RequestMapping(value="/workflow/startProcess/{workflowKey}",method=RequestMethod.GET)
	public void saveWorkflowUsertaskNodeForms(@PathVariable String workflowKey)
	{
		runWorkflowService.startProcess(workflowKey, "AAAAA", null);
	}
}
