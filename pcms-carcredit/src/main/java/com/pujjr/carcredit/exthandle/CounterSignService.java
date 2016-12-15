package com.pujjr.carcredit.exthandle;

import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.springframework.stereotype.Service;

@Service
public class CounterSignService 
{
	public boolean isComplete(ActivityExecution execution)
	{
		int nrOfCompletedInstances = Integer.valueOf(execution.getVariable("nrOfCompletedInstances").toString());
		int nrOfInstances = Integer.valueOf(execution.getVariable("nrOfInstances").toString());
		if(nrOfInstances == nrOfCompletedInstances)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
