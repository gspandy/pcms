package com.pujjr.jbpm.core.script;

import java.util.Map;

import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

public class GroovyEngine 
{
	public static Object execScript(String script,String executionId,Map<String,Object> vars)
	{
		Binding bind=new Binding();
		GroovyShell shell=new GroovyShell(bind);
		ExecutionEntity execution = Context.getCommandContext().getExecutionEntityManager().findExecutionById(executionId);
		bind.setVariable("vars", vars);
		bind.setVariable("execution", execution);
		return shell.evaluate(script);
	}
}
